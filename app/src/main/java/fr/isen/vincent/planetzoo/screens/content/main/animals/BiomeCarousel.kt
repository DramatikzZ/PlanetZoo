package fr.isen.vincent.planetzoo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.data.BiomeModel

@Composable
fun BiomeCarousel(biomes: List<BiomeModel>, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Text(
            text = "Sélectionnez un Biome",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(biomes) { biome ->
                BiomeCard(biome, navController)
            }
        }
    }
}

@Composable
fun BiomeCard(biome: BiomeModel, navController: NavController) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp)
            .clickable {
                if (biome.id.isNotEmpty()) {
                    println("✅ DEBUG: Clic détecté sur le biome ${biome.id}")
                    navController.navigate("enclosures/${biome.id}") {
                        popUpTo("home") { inclusive = false }
                    }
                } else {
                    println("❌ ERREUR: Tentative de navigation avec un biome vide")
                }
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(android.graphics.Color.parseColor(biome.color)))
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = biome.name,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
