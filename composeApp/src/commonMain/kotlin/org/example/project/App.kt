package org.example.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.network.F1ApiClient
import org.example.project.presentation.DataScreenUIState
import org.example.project.presentation.DataScreenViewModel
import org.example.project.presentation.EventData

@Composable
fun App(
    apiClient: F1ApiClient = F1ApiClient(),
    sessionKey: String = "latest",
    meetingKey: String = "latest"
) {

    val vm = DataScreenViewModel(apiClient)
    val evento by vm.sessionInfo.collectAsState()
    val uiState by vm.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        vm.getSessionData(sessionKey, meetingKey)
        vm.loadDriverData(sessionKey, meetingKey)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().padding(6.dp),
        contentWindowInsets = WindowInsets.statusBars
    ) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            when (uiState) {
                is DataScreenUIState.Loading -> CircularProgressIndicator()
                is DataScreenUIState.Error -> "Error Grave"
                is DataScreenUIState.Success -> EventData(
                    drivers = (uiState as DataScreenUIState.Success).driverInfoList,
                    evento = evento.firstOrNull()
                )

            }
        }

    }


}