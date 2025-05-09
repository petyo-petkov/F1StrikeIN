package org.example.project

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.font.FontWeight.Companion.W800
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.example.project.network.models.Sessions
import org.example.project.presentation.formatDate


@Composable
fun HeaderCard(
    evento: Sessions?
) {


    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Información del Evento", fontWeight = W800, textDecoration = TextDecoration.Underline)

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoColumn("Nombre del Evento:", evento?.circuit_short_name ?: "No disponible")
                InfoColumn("Fecha:", (formatDate(evento?.date_end  ?: "")))
                InfoColumn("Evento:", evento?.session_name ?: "No disponible")

            }

        }
    }
}

@Composable
private fun InfoColumn(nombre: String, valor: String) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(nombre, textAlign = TextAlign.Center, fontWeight = W700)
        Text(valor, textAlign = TextAlign.Center, fontWeight = W800)
    }

}