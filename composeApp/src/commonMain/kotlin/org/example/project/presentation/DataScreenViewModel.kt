package org.example.project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import org.example.project.network.F1ApiClient
import org.example.project.network.models.Drivers
import org.example.project.network.models.Intervals
import org.example.project.network.models.Position
import org.example.project.network.models.Sessions

class DataScreenViewModel(
    private val apiClient: F1ApiClient
) : ViewModel() {


    private val _uiState = MutableStateFlow<DataScreenUIState>(DataScreenUIState.Loading)
    val uiState: StateFlow<DataScreenUIState> = _uiState

    private val _sessionInfo = MutableStateFlow<List<Sessions>>(emptyList())
    val sessionInfo: StateFlow<List<Sessions>> = _sessionInfo


    fun getSessionData(sessionKey: String, meetingKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _sessionInfo.value =
                apiClient.getSessions(sessionKey = sessionKey, meetingKey = meetingKey)
        }
    }


    fun loadDriverData(sessionKey: String, meetingKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val intervalsFlow = apiClient.getIntervals(
                    sessionKey = sessionKey,
                    meetingKey = meetingKey
                )

                val positionsFlow = apiClient.getPosition(
                    sessionKey = sessionKey,
                    meetingKey = meetingKey
                )

                val drivers = apiClient.getDrivers(sessionKey = sessionKey, meetingKey = meetingKey)

                combine(
                    intervalsFlow,
                    positionsFlow
                ) { intervals, positions ->
                    val driverInfoList = processData(drivers, intervals, positions)

                    DataScreenUIState.Success(driverInfoList = driverInfoList)

                }.collect { uiState ->
                    _uiState.update { uiState }
                }

            } catch (e: Exception) {
                _uiState.update {
                    DataScreenUIState.Error(e.message ?: "Error al cargar los datos")
                }
            }
        }
    }
}

private fun processData(
    drivers: List<Drivers>,
    intervals: List<Intervals>,
    positions: List<Position>
): List<DriverInfo> {
    // Crear un mapa de la información de pilotos por número de piloto
    val driverInfoMap = mutableMapOf<Int, DriverInfo>()

    // Primero, procesar los datos de los pilotos
    drivers.forEach { driver ->
        driverInfoMap[driver.driver_number] = DriverInfo(
            driverName = driver.name_acronym,
            teamColor = driver.team_colour,
            driverNumber = driver.driver_number
        )
    }

    // Añadir las posiciones (usando la fecha más reciente para cada piloto)
    val latestPositions = positions
        .groupBy { it.driver_number }
        .mapValues { (_, posList) ->
            posList.maxByOrNull { it.date }
        }

    latestPositions.forEach { (driverNumber, posData) ->
        if (posData != null) {
            val currentInfo = driverInfoMap[driverNumber] ?: DriverInfo(driverNumber = driverNumber)
            driverInfoMap[driverNumber] = currentInfo.copy(position = posData.position)
        }
    }

    // Añadir los intervalos (usando la fecha más reciente)
    val latestIntervals = intervals
        .groupBy { it.driver_number ?: -1 }
        .mapValues { (_, intList) ->
            intList.maxByOrNull { it.date ?: "" }
        }

    latestIntervals.forEach { (driverNumber, intData) ->
        if (intData != null && driverNumber != -1) {
            val currentInfo = driverInfoMap[driverNumber] ?: DriverInfo(driverNumber = driverNumber)

            // Extraer los valores de gap e interval
            val gap = extractJsonValue(intData.gap_to_leader)
            val interval = extractJsonValue(intData.interval)

            driverInfoMap[driverNumber] = currentInfo.copy(
                gap = gap,
                interval = interval
            )
        }
    }

    // Convertir a lista y ordenar por posición
    return driverInfoMap.values.toList().sortedBy { it.position }
}

// Función auxiliar para extraer valores de JsonElement
private fun extractJsonValue(element: JsonElement?): String {

    if (element == null) return "-"

    return try {
        when {
            element is JsonPrimitive -> element.jsonPrimitive.content
            else -> element.toString()
        }
    } catch (e: Exception) {
        "Error extraer los valores del JsonElement: ${e.message}"
    }

}

sealed class DataScreenUIState {
    object Loading : DataScreenUIState()
    data class Error(val message: String) : DataScreenUIState()
    data class Success(
        val driverInfoList: List<DriverInfo>
    ) : DataScreenUIState()

}

data class DriverInfo(
    val position: Int = 0,
    val driverName: String = "",
    val teamColor: String = "",
    val interval: String = "",
    val gap: String = "",
    val driverNumber: Int = 0
)

