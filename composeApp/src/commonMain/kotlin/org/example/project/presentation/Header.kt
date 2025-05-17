package org.example.project.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.font.FontWeight.Companion.W800
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import f1strikein.composeapp.generated.resources.Res
import f1strikein.composeapp.generated.resources.header_date
import f1strikein.composeapp.generated.resources.header_event
import f1strikein.composeapp.generated.resources.header_grand_prix
import f1strikein.composeapp.generated.resources.header_no_data
import org.example.project.network.models.Sessions
import org.jetbrains.compose.resources.stringResource

@Composable
fun Header(
    evento: Sessions?
) {
    evento?.let { evento ->
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            InfoColumn(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.header_grand_prix),
                value = evento.circuit_short_name
            )
            InfoColumn(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.header_date),
                value = formatDate(evento.date_end)
            )
            InfoColumn(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.header_event),
                value = evento.session_name
            )

        }
    } ?: stringResource(Res.string.header_no_data)
}

@Composable
private fun InfoColumn(modifier: Modifier = Modifier, label: String, value: String) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label, textAlign = TextAlign.Center, fontWeight = W700)
        Text(text = value, textAlign = TextAlign.Center, fontWeight = W800)
    }

}
