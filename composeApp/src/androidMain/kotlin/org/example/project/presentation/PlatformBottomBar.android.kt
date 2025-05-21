package org.example.project.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
actual fun PlatformBottomBar(
    event: String,
    date: String,
    eventType: String,
    onClick: () -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()


    TopAppBar(
        title = {
            Text(text = event, maxLines = 5, overflow = TextOverflow.Ellipsis)
        },
        subtitle = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = formatDate(date), maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = eventType, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        },
        modifier = Modifier.padding(vertical = 12.dp),
        navigationIcon = {
            IconButton(onClick = { onClick }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {},
        scrollBehavior = scrollBehavior
    )
}