package fr.isen.vincent.planetzoo.screens.content.main.animals

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.isen.vincent.planetzoo.data.BiomeModel
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.R

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
    val imageRes = getBiomeImageUrl(biome.name)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("enclosures/${biome.id}") },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(android.graphics.Color.parseColor(biome.color))),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Image du biome",
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "${biome.name}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Nombre d'enclos: ${biome.enclosures.size}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

fun getBiomeImageUrl(name: String): Int {
    return when (name) {
        "La Bergerie des reptiles" -> R.drawable.bergerie_reptiles
        "Le Vallon des cascades" -> R.drawable.vallon_cascades
        "Le Belvédère" -> R.drawable.belvedere
        "Le Plateau" -> R.drawable.plateau
        "Les Clairières" -> R.drawable.clairieres
        "Le Bois des pins" -> R.drawable.bois
        else -> R.drawable.bergerie_reptiles
    }
}
