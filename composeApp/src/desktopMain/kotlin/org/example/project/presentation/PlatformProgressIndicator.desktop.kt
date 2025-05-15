package org.example.project.presentation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
actual fun PlatforProgressIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.size(120.dp),
        strokeWidth = 6.dp,
    )
}