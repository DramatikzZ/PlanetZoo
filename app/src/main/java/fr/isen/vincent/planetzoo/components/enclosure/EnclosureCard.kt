package fr.isen.vincent.planetzoo.components.enclosure

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.data.EnclosureModel
import fr.isen.vincent.planetzoo.data.UserModel
import fr.isen.vincent.planetzoo.screens.content.main.animals.enclosure.AdminContent
import fr.isen.vincent.planetzoo.screens.content.main.animals.enclosure.VisitorContent

@Composable
fun EnclosureCard(
    enclosure: EnclosureModel,
    biomeColor: String,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("animals/${enclosure.id}") },
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(android.graphics.Color.parseColor(biomeColor)))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Enclos: ${enclosure.firebaseId}", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onPrimary)
            Text(text = "Animaux: ${enclosure.animals.joinToString { it.name }}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)

            if (UserModel.isAdmin) {
                AdminContent(enclosure)
            } else {
                VisitorContent(enclosure, biomeColor, navController)
            }
        }
    }
}
