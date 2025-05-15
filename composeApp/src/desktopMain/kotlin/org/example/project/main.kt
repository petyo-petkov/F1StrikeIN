package org.example.project

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.example.project.di.initiKoin
import org.example.project.presentation.App
import org.koin.core.context.stopKoin

fun main() {
    initiKoin()
    application {
        Window(
            onCloseRequest = {
                stopKoin()
                exitApplication()
            },
            state = rememberWindowState(width = 1400.dp, height = 900.dp),
            title = "F1StrikeIN",
        ) {
            App()
        }
    }
}