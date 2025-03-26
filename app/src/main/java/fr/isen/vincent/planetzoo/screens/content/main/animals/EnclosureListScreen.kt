package fr.isen.vincent.planetzoo.screens.content.main.animals

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.components.enclosure.EnclosureCard
import fr.isen.vincent.planetzoo.data.BiomeModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnclosureListScreen(biome: BiomeModel, navController: NavController) {
    val imageRes = getBiomeImageUrl(biome.name)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = biome.name,
                        color = Color(0xFFD7725D)
                    )
                        },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Clear, contentDescription = "Retour", tint =Color(0xFFD7725D) )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Image du biome",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            }

            itemsIndexed(biome.enclosures) { index, enclosure ->
                EnclosureCard(enclosure.copy(firebaseId = index.toString()), biome.color, navController)
            }
        }
    }
}
