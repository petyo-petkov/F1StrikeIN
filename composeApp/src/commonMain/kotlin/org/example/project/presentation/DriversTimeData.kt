package org.example.project.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import f1strikein.composeapp.generated.resources.Res
import f1strikein.composeapp.generated.resources.error_driver_data
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriversTimeData(
    drivers: List<DriverInfo>?
) {
    drivers?.let { drivers ->
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(drivers, key = { it.driverNumber }) { driverInfo ->
                DriverListItem(driverInfo)
                HorizontalDivider()
            }
        }
    } ?: stringResource(Res.string.error_driver_data)
}

@Composable
fun DriverListItem(driverInfo: DriverInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
                .background(Color.Transparent),
            contentAlignment = Alignment.Center

        ) {

            Text(
                text = "${driverInfo.position}",
                modifier = Modifier,
                fontWeight = FontWeight.SemiBold,
            )

        }

        Box(
            modifier = Modifier
                .padding(start = 8.dp)
                .width(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(driverInfo.teamColor.toColor()),
            contentAlignment = Alignment.Center

        ) {

            Text(
                text = driverInfo.driverName,
                modifier = Modifier,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1

            )
        }


        Text(
            text = driverInfo.interval,
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f),
            fontWeight = FontWeight.SemiBold
        )


        Text(
            text = driverInfo.gap,
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f),
            fontWeight = FontWeight.SemiBold

        )
    }
}


private fun String.toColor(): Color {
    try {
        val colorInt = this.toLong(16)
        return Color(
            red = ((colorInt shr 16) and 0xFF) / 255f,
            green = ((colorInt shr 8) and 0xFF) / 255f,
            blue = (colorInt and 0xFF) / 255f
        )
    } catch (e: NumberFormatException) {
        println("Error al convertir el color: ${e.message}")
        return Color.Transparent

    }

}