package fr.isen.vincent.planetzoo.components.enclosure

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerField(initialTime: String, enclosureId: String, onTimeSelected: (String) -> Unit) {
    val timeParts = initialTime.split(":").map { it.toIntOrNull() ?: 0 }
    val initialHour = timeParts.getOrElse(0) { 0 }
    val initialMinute = timeParts.getOrElse(1) { 0 }
    val state = rememberTimePickerState(initialHour, initialMinute, is24Hour = true)

    Column(modifier = Modifier.padding(16.dp)) {
        TimePicker(
            state = state,
            layoutType = TimePickerLayoutType.Vertical
        )

        Button(
            onClick = {
                val selectedHour = state.hour
                val selectedMinute = state.minute
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)

                Log.d("Debug", "Modification mealTime pour l’enclos ID: $enclosureId")

                onTimeSelected(formattedTime)
            }
        ) {
            Text("Confirmer l'heure")
        }
    }
}
