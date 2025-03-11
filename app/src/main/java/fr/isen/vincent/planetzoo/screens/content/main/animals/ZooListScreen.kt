package fr.isen.vincent.planetzoo.screens.content.main.animals

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.isen.vincent.planetzoo.data.BiomeModel
import androidx.navigation.NavController

@Composable
fun ZooListScreen(zooList: List<BiomeModel>, navController: NavController, modifier: Modifier) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(zooList) { biome ->
            BiomeCard(biome, navController)
        }
    }
}

@Composable
fun BiomeCard(biome: BiomeModel, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("enclosures/${biome.id}") },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(android.graphics.Color.parseColor(biome.color)))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Biome: ${biome.name}", style = MaterialTheme.typography.headlineSmall)
            Text(text = "Nombre d'enclos: ${biome.enclosures.size}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
