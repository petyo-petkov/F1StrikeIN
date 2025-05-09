package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.font.FontWeight.Companion.W800
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.example.project.network.F1ApiClient
import org.example.project.presentation.DataScreenViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview()
@Composable
fun App(
    apiClient: F1ApiClient = F1ApiClient(),
    sessionKey: String = "latest",
    meetingKey: String = "latest"
) {

    val vm = DataScreenViewModel(apiClient)
    val evento by vm.sessionInfo.collectAsState()

    LaunchedEffect(key1 = sessionKey, meetingKey) {
        vm.getSessionData(sessionKey, meetingKey)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.statusBars
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp)
        ) {

            HeaderCard(evento.firstOrNull())


        }
    }
}