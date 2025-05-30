package org.example.project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import org.example.project.network.F1ApiClient
import org.example.project.network.models.Drivers
import org.example.project.network.models.Intervals
import org.example.project.network.models.Position

class DataScreenViewModel(
    private val apiClient: F1ApiClient
) : ViewModel() {

    private val _uiState = MutableStateFlow<DataScreenUIState>(DataScreenUIState.Idle)
    val uiState: StateFlow<DataScreenUIState> = _uiState

    private val _eventInfo = MutableStateFlow(EventInfo())
    val eventInfo: StateFlow<EventInfo> = _eventInfo

    private var realtimeDataJob: Job? = null
    private var staticDataJob: Job? = null

    fun loadLiveDriverData() {

        realtimeDataJob?.cancel()
        staticDataJob?.cancel()
        _uiState.value = DataScreenUIState.Loading
        realtimeDataJob = viewModelScope.launch(Dispatchers.IO) {
            try {

                val session = apiClient.getSessions("latest", "latest").firstOrNull()
                val actualSessionKey = session?.session_key.toString()
                val actualMeetingKey = session?.meeting_key.toString()

                val meeting= apiClient.getMeetings(
                    meetingKey = actualMeetingKey
                ).firstOrNull()

                _eventInfo.value = EventInfo(
                    date = session?.date_start ?: "",
                    eventName = meeting?.meeting_official_name ?: "",
                    eventType = session?.session_type ?: "",
                    country = session?.country_name ?: "",
                    circuit = session?.circuit_short_name ?: ""
                )


                val drivers = apiClient.getDrivers(
                    sessionKey = actualSessionKey,
                    meetingKey = actualMeetingKey
                )

                val intervalsFlow = apiClient.getIntervalsFlow(
                    sessionKey = actualSessionKey,
                    meetingKey = actualMeetingKey
                )

                val positionsFlow = apiClient.getPositionFlow(
                    sessionKey = actualSessionKey,
                    meetingKey = actualMeetingKey
                )

                combine(
                    intervalsFlow,
                    positionsFlow
                ) { intervals, positions ->
                    val driverInfoList = processData(drivers, intervals, positions)

                    DataScreenUIState.Success(
                        driverInfoList = driverInfoList,
                    )

                }.collect { uiState ->
                    _uiState.value = uiState
                }

            } catch (e: Exception) {
                _uiState.value = DataScreenUIState.Error(e.message ?:
                "Error al cargar los datos en tiempo real")
            }
        }
    }

    fun loadStaticDriverData(
        year: Int? = null,
        circuit: String? = null,
        event: String? = null
    ) {
        staticDataJob?.cancel()
        realtimeDataJob?.cancel()
        _uiState.value = DataScreenUIState.Loading
        staticDataJob = viewModelScope.launch(Dispatchers.IO) {

            try {
                val session = apiClient.getSessions(
                    year = year,
                    circuiShortName = circuit,
                    sessionName = event
                ).lastOrNull()

                val actualSessionKey = session?.session_key.toString()
                val actualMeetingKey = session?.meeting_key.toString()

                val staticMeeting = apiClient.getMeetings(
                    meetingKey = actualMeetingKey
                ).firstOrNull()

                _eventInfo.value = EventInfo(
                    date = session?.date_start ?: "",
                    eventName = staticMeeting?.meeting_official_name ?: "",
                    eventType = session?.session_type ?: "",
                    country = session?.country_name ?: "",
                    circuit = session?.circuit_short_name ?: ""
                )

                val drivers = apiClient.getDrivers(
                    sessionKey = actualSessionKey,
                    meetingKey = actualMeetingKey
                )
                val intervals = apiClient.getStaticIntervals(
                    sessionKey = actualSessionKey,
                    meetingKey = actualMeetingKey
                )
                val positions = apiClient.getStaticPosition(
                    sessionKey = actualSessionKey,
                    meetingKey = actualMeetingKey
                )
                val driverInfoList = processData(drivers, intervals, positions)
                _uiState.value = DataScreenUIState.Success(
                    driverInfoList = driverInfoList,
                )
            }catch (e: Exception) {
                _uiState.value = DataScreenUIState.Error(e.message ?:
                "Error al cargar los datos estaticos")
            }

        }

    }

    override fun onCleared() {
        super.onCleared()
        realtimeDataJob?.cancel()
        staticDataJob?.cancel()
    }

    fun refreshData() {
        realtimeDataJob?.cancel()
        staticDataJob?.cancel()
       loadStaticDriverData()

    }
}


private fun processData(
    drivers: List<Drivers>,
    intervals: List<Intervals>,
    positions: List<Position>
): List<DriverInfo> {

    val driverInfoMap = mutableMapOf<Int, DriverInfo>()

    drivers.forEach { driver ->
        driverInfoMap[driver.driver_number] = DriverInfo(
            driverName = driver.name_acronym,
            teamColor = driver.team_colour,
            driverNumber = driver.driver_number
        )
    }


    val latestPositions = positions
        .groupBy { it.driver_number }
        .mapValues { (_, posList) ->
            posList.maxByOrNull { it.date }
        }

    latestPositions.forEach { (driverNumber, posData) ->
        if (posData != null) {
            driverInfoMap[driverNumber]?.let { currentInfo ->
                driverInfoMap[driverNumber] = currentInfo.copy(
                    position = posData.position
                )

            }
        }
    }

    val latestIntervals = intervals
        .groupBy { it.driver_number }
        .mapValues { (_, intList) ->
            intList.maxByOrNull { it.date }
        }

    latestIntervals.forEach { (driverNumber, intData) ->
        if (intData != null && driverNumber != -1) {
            val currentInfo =
                driverInfoMap[driverNumber]
            if (currentInfo != null) {
                val gap = extractJsonValue(intData.gap_to_leader)
                val interval = extractJsonValue(intData.interval)

                driverInfoMap[driverNumber] = currentInfo.copy(
                    gap = gap,
                    interval = interval
                )
            }
        }
    }
    return driverInfoMap.values.toList().sortedBy { it.position }
}


private fun extractJsonValue(element: JsonElement?): String {
    if (element == null) return "-"
    return try {
        when {
            element is JsonPrimitive && element.isString -> element.content
            element is JsonPrimitive -> element.jsonPrimitive.content
            else -> element.toString()
        }
    } catch (e: Exception) {
        "Error extraer los valores del JsonElement: ${e.message}"
    }
}

sealed class DataScreenUIState {
    object Idle : DataScreenUIState()
    object Loading : DataScreenUIState()
    data class Error(val message: String) : DataScreenUIState()
    data class Success(
        val driverInfoList: List<DriverInfo>
    ) : DataScreenUIState()

}

@Serializable
data class DriverInfo(
    val position: Int? = -1,
    val driverName: String = "",
    val teamColor: String = "",
    val interval: String = "",
    val gap: String = "",
    val driverNumber: Int
)

@Serializable
data class EventInfo(
    val date: String = "",
    val eventName: String = "",
    val eventType: String = "",
    val country: String = "",
    val circuit: String = ""
)



