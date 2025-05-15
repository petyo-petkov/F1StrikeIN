package org.example.project.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(
    sessionKey: String = "latest",
    meetingKey: String = "latest"
) {

    val vm = koinViewModel<DataScreenViewModel>()

    val evento by vm.sessionInfo.collectAsStateWithLifecycle()
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        vm.getSessionData(sessionKey, meetingKey)
        vm.loadDriverData(sessionKey, meetingKey)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,

        ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            when (uiState) {
                is DataScreenUIState.Loading -> PlatforProgressIndicator()
                is DataScreenUIState.Error -> "Error Grave"
                is DataScreenUIState.Success -> EventData(
                    drivers = (uiState as DataScreenUIState.Success).driverInfoList,
                    evento = evento.firstOrNull()
                )

            }
        }

    }


}