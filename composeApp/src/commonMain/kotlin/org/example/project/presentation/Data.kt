package org.example.project.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Data(
    drivers: List<DriverInfo>?
) {
    if (drivers == null) return

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (drivers.isEmpty()) {
            item {
                Text(
                    "No se encontraron datos de pilotos",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }

        } else {
            items(drivers, key = { it.driverNumber }) { driverInfo ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    box(
                        "${driverInfo.position}",
                        fontWeight = FontWeight.SemiBold,
                        shape = RoundedCornerShape(16.dp),
                        colorHex = driverInfo.teamColor
                    )

                    box(
                        driverInfo.driverName,
                        fontWeight = FontWeight.Bold,
                        shape = null,
                        colorHex = null
                    )
                    box(
                        driverInfo.interval,
                        fontWeight = FontWeight.Bold,
                        shape = null,
                        colorHex = null
                    )
                    box(
                        driverInfo.gap,
                        fontWeight = FontWeight.Bold,
                        shape = null,
                        colorHex = null
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp))
            }
        }
    }

}

private fun String.toColor(): Color {
    val colorInt = this.toLong(16)
    return Color(
        red = ((colorInt shr 16) and 0xFF) / 255f,
        green = ((colorInt shr 8) and 0xFF) / 255f,
        blue = (colorInt and 0xFF) / 255f
    )
}


@Composable
private fun box(text: String, fontWeight: FontWeight, shape: Shape?, colorHex: String?) {
    if (shape != null && colorHex.isNullOrEmpty().not()) {

        Box(
            modifier = Modifier
                .size(26.dp, 26.dp)
                .clip(shape)
                .background(colorHex.toColor()),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = fontWeight,
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(80.dp, 30.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = fontWeight,
            )
        }
    }
}