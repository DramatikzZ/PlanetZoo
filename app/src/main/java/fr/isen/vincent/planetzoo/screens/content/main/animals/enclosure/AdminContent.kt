package fr.isen.vincent.planetzoo.screens.content.main.animals.enclosure



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase
import fr.isen.vincent.planetzoo.data.EnclosureModel
import androidx.compose.material3.Button
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.ui.platform.LocalContext
import fr.isen.vincent.planetzoo.components.enclosure.CommentDialog
import fr.isen.vincent.planetzoo.components.enclosure.TimePickerField
import fr.isen.vincent.planetzoo.data.UserModel


@Composable
fun AdminContent(enclosure: EnclosureModel) {
    val database = FirebaseDatabase.getInstance().reference
    var isClosed by remember { mutableStateOf(enclosure.is_open.not()) }
    var mealTime by remember { mutableStateOf(enclosure.meal) }
    val context = LocalContext.current

    LaunchedEffect(enclosure.firebaseId) {
        val enclosureRef = database.child("biomes").child(enclosure.id_biomes)
            .child("enclosures").child(enclosure.firebaseId)

        enclosureRef.child("is_open").get().addOnSuccessListener { snapshot ->
            snapshot.getValue(Boolean::class.java)?.let { isClosed = !it }
        }

        enclosureRef.child("meal").get().addOnSuccessListener { snapshot ->
            snapshot.getValue(String::class.java)?.let { mealTime = it }
        }
    }

    var showTimePicker by remember { mutableStateOf(false) }
    var showModComm by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(8.dp).padding(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = if (isClosed) "Enclos fermé" else "Enclos ouvert",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFFD6725D)
        )
        Switch(
            checked = isClosed,
            onCheckedChange = {
                isClosed = it
                database.child("biomes").child(enclosure.id_biomes)
                    .child("enclosures").child(enclosure.firebaseId)
                    .child("is_open").setValue(!it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFFD73E30),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFF72DA76),
                uncheckedBorderColor = Color(0xFF72DA76)
            )
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(text = "Repas à : $mealTime",
        style = MaterialTheme.typography.headlineSmall,
        color = Color(0xFFD6725D)
    )

    Spacer(modifier = Modifier.height(8.dp))

    Button(
        onClick = { showTimePicker = true },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF796D47),
            contentColor = Color.White
        )) {
        Text("Modifier l'heure du repas")
    }

    Button(
        onClick = { showModComm = true },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFD7725D),
            contentColor = Color.White
        )) {
        Text("Modérer les commentaires")
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                Button(onClick = { showTimePicker = false }) {
                    Text("Annuler")
                }
            },
            text = {
                TimePickerField(mealTime, enclosure.firebaseId) { selectedTime ->
                    mealTime = selectedTime
                    database.child("biomes").child(enclosure.id_biomes)
                        .child("enclosures").child(enclosure.firebaseId)
                        .child("meal")
                        .setValue(mealTime)
                    showTimePicker = false
                }
            }
        )
    }

    if (showModComm) {
        CommentDialog(
            showDialog = showModComm,
            onDismiss = { showModComm = false },
            enclosure = enclosure
        )
    }
}
