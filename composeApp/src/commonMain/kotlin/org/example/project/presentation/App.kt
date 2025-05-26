package org.example.project.presentation


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import f1strikein.composeapp.generated.resources.Res
import f1strikein.composeapp.generated.resources.error_grave
import org.example.project.network.circuits
import org.example.project.network.eventTypes
import org.example.project.network.years
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    println("RECOMPONIEDO ------------------------- APP()")
    val vm = koinViewModel<DataScreenViewModel>()
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        println("RECOMPONIEDO ------------------------- LaunchedEffect")
        vm.loadDriverData(sessionKey = "latest", meetingKey = "latest")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,

        ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val currentState = uiState) {
                is DataScreenUIState.Idle -> {
                    println("RECOMPONIEDO ------------------------- IDLE")
                    "Esperando datos..."
                }

                is DataScreenUIState.Loading -> {
                    println("RECOMPONIEDO ------------------------- LOADING")
                    PlatforProgressIndicator()
                }

                is DataScreenUIState.Error -> {
                    println("RECOMPONIEDO ------------------------- ERROR")
                    stringResource(Res.string.error_grave)
                }

                is DataScreenUIState.Success -> {
                    println("RECOMPONIEDO ------------------------- SUCCESS")
                    val eventInfo = currentState.eventInfo
                    val driverInfoList = currentState.driverInfoList

                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {

                        Header(
                            eventName = eventInfo?.eventName ,
                            date = eventInfo.date,
                            eventType = eventInfo.eventType,
                            onClick = {
                                showBottomSheet = true
                            }
                        )

                        Card(
                            modifier = Modifier
                                .weight(1f),
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomEnd = 16.dp,
                                bottomStart = 16.dp
                            ),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(
                                1.dp,
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                        ) {
                            DriversTimeData(driverInfoList)

                        }


                    }

                }


            }
        }
        if (showBottomSheet) {
            BottomSheet(
                onDismiss = {
                    showBottomSheet = false
                },
                onOKClick = { year, circuit, event ->
                    vm.loadDriverData(
                        year = year,
                        circuit = circuit,
                        event = event
                    )
                },
                onRefreshClick = {
                    vm.Refresh()
                }
            )
        }
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismiss: () -> Unit,
    onOKClick: (year: Int, circuit: String, event: String) -> Unit,
    onRefreshClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    var selectedYear by remember { mutableStateOf("2025") }
    var selectedCircuit by remember { mutableStateOf("Selecciona una opción") }
    var selectedEventType by remember { mutableStateOf("Selecciona una opción") }


    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        sheetMaxWidth = 680.dp,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        scrimColor = Color.Transparent,
    ) {

        RaceFilterBottomSheet(
            selectedYear = selectedYear,
            onYearSelected = { selectedYear = it },
            selectedCircuit = selectedCircuit,
            onCircuitSelected = { selectedCircuit = it },
            selectedRaceType = selectedEventType,
            onRaceTypeSelected = { selectedEventType = it },
            years = years,
            circuits = circuits,
            raceTypes = eventTypes,
            onDismiss = { onDismiss() },
            onOkClick = {
                onOKClick(
                    selectedYear.toInt(),
                    selectedCircuit,
                    selectedEventType
                )
            },
            onRefreshClick = { onRefreshClick() }
        )

    }

}

