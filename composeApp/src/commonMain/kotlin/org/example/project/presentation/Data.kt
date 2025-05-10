package org.example.project.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Data(
    drivers: List<DriverInfo>?,
) {
    if (drivers == null) return

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (drivers.isEmpty()) {
            Text(
                "No se encontraron datos de pilotos",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            drivers.forEach { driverInfo ->
                key(driverInfo.driverNumber) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        box(
                            "${driverInfo.position}",
                            fontWeight = FontWeight.W600,
                            shape = RoundedCornerShape(12.dp),
                            colorHex = driverInfo.teamColor
                        )

                        box(
                            driverInfo.driverName,
                            fontWeight = FontWeight.W700,
                            shape = null,
                            colorHex = null
                        )
                        box(
                            driverInfo.interval,
                            fontWeight = FontWeight.W600,
                            shape = null,
                            colorHex = null
                        )
                        box(
                            driverInfo.gap,
                            fontWeight = FontWeight.W600,
                            shape = null,
                            colorHex = null
                        )
                    }
                    HorizontalDivider()
                }
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
                .size(20.dp, 20.dp)
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
                .size(80.dp, 18.dp),
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