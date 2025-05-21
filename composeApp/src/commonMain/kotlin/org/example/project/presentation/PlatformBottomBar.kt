package org.example.project.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun PlatformBottomBar(
    event: String,
    date: String,
    eventType: String,
    onClick: () -> Unit
)