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
import org.example.project.network.models.Sessions

@Composable
fun Header(
    evento: Sessions?
) {
    if (evento == null ) return
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        InfoColumn(
            modifier = Modifier.weight(1f),
            nombre = "Gran Premio de:",
            valor = evento.circuit_short_name
        )
        InfoColumn(
            modifier = Modifier.weight(1f),
            nombre = "Fecha:",
            valor = formatDate(evento.date_end)
        )
        InfoColumn(
            modifier = Modifier.weight(1f),
            nombre = "Evento:",
            valor = evento.session_name
        )

    }

}

@Composable
private fun InfoColumn(modifier: Modifier = Modifier, nombre: String, valor: String) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = nombre, textAlign = TextAlign.Center, fontWeight = W700)
        Text(text = valor, textAlign = TextAlign.Center, fontWeight = W800)
    }

}
