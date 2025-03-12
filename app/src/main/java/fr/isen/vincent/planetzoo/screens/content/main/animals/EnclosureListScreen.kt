package fr.isen.vincent.planetzoo.screens.content.main.animals

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.isen.vincent.planetzoo.data.BiomeModel
import fr.isen.vincent.planetzoo.data.EnclosureModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnclosureListScreen(biome: BiomeModel, navController: NavController) {
    println("✅ DEBUG: Chargement de l'écran des enclos pour le biome ${biome.id}")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = biome.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Clear, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(biome.enclosures) { enclosure ->
                EnclosureCard(enclosure, biome.color, navController)
            }
        }
    }
}


@Composable
fun EnclosureCard(enclosure: EnclosureModel, biomeColor: String, navController: NavController) {
    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }
    val fakeComments = listOf("Super enclos, les animaux ont l'air heureux!", "Très bien entretenu, j'adore!", "Belle diversité d'animaux, top!")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("animals/${enclosure.id}") },
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(android.graphics.Color.parseColor(biomeColor)))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Enclos: ${enclosure.id}", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onPrimary)
            Text(text = "Animaux: ${enclosure.animals.joinToString { it.name }}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)

            Spacer(modifier = Modifier.height(8.dp))
            Row {
                (1..5).forEach { index ->
                    Icon(
                        imageVector = if (index <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = "Star $index",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { rating = index },
                        tint = if (index <= rating) Color.Yellow else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Laisser un commentaire") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { /* Simuler l'envoi du commentaire */ }) {
                Text("Soumettre")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Commentaires:", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onPrimary)
            if (fakeComments.isNotEmpty()) {
                Text(text = fakeComments.last(), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
                if (fakeComments.size > 1) {
                    Text(
                        text = "Voir plus...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Blue,
                        modifier = Modifier.clickable {
                            navController.navigate("comments")


                }
                    )
                }
            }
        }
    }
}