package org.example.project.presentation


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import f1strikein.composeapp.generated.resources.Res
import f1strikein.composeapp.generated.resources.error_grave
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(
    sessionKey: String = "latest", //"10006",
    meetingKey: String = "latest" //"1256"
) {

    val vm = koinViewModel<DataScreenViewModel>()
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        vm.loadDriverData(sessionKey, meetingKey)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,

        ) { paddingValues ->
        when (uiState) {
            is DataScreenUIState.Loading -> PlatforProgressIndicator()
            is DataScreenUIState.Error -> stringResource(Res.string.error_grave)
            is DataScreenUIState.Success -> {
                val driverInfoList = (uiState as DataScreenUIState.Success).driverInfoList
                val eventInfo = (uiState as DataScreenUIState.Success).eventInfo

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    Header(
                        eventName = eventInfo.eventName,
                        date = eventInfo.date,
                        eventType = eventInfo.eventType,
                        onClick = { }
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
}