package org.example.project.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun RaceFilterBottomSheet(
    selectedYear: String,
    onYearSelected: (String) -> Unit,
    selectedCircuit: String,
    onCircuitSelected: (String) -> Unit,
    selectedRaceType: String,
    onRaceTypeSelected: (String) -> Unit,
    years: List<String>,
    circuits: List<String>,
    raceTypes: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        DropdownSelector(
            label = "Año",
            options = years,
            selectedOption = selectedYear,
            onOptionSelected = onYearSelected
        )

        Spacer(modifier = Modifier.height(12.dp))

        DropdownSelector(
            label = "Circuito",
            options = circuits,
            selectedOption = selectedCircuit,
            onOptionSelected = onCircuitSelected
        )

        Spacer(modifier = Modifier.height(12.dp))

        DropdownSelector(
            label = "Tipo de carrera",
            options = raceTypes,
            selectedOption = selectedRaceType,
            onOptionSelected = onRaceTypeSelected
        )
    }
}

@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(text = label)
        Box {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {  },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Desplegar opciones",
                        modifier = Modifier.clickable {
                            expanded = !expanded
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        },
                        contentPadding = PaddingValues(horizontal = 16.dp),

                    )
                }
            }
        }
    }
}

