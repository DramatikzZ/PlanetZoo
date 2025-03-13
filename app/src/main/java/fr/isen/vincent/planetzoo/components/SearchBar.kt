package fr.isen.vincent.planetzoo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.data.BiomeModel
import fr.isen.vincent.planetzoo.data.EnclosureModel
import fr.isen.vincent.planetzoo.data.AnimalModel

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    biomes: List<BiomeModel>,
    onSearchResults: (List<Pair<AnimalModel, EnclosureModel>>) -> Unit
) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                val results = searchAnimals(searchText.text, biomes)
                onSearchResults(results)
            },
            label = { Text("Rechercher un animal") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun searchAnimals(query: String, biomes: List<BiomeModel>): List<Pair<AnimalModel, EnclosureModel>> {
    if (query.isBlank()) return emptyList()
    return biomes.flatMap { biome ->
        biome.enclosures.flatMap { enclosure ->
            enclosure.animals.filter { it.name.contains(query, ignoreCase = true) }
                .map { it to enclosure }
        }
    }
}

@Composable
fun SearchResultsList(
    searchResults: List<Pair<AnimalModel, EnclosureModel>>,
    navController: NavController
) {
    LazyColumn(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        items(searchResults) { (animal, enclosure) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .clickable { navController.navigate("animals/${enclosure.id}") },
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = animal.name, fontWeight = FontWeight.Bold)
                    Text(text = "Enclos: ${enclosure.id}")
                }
            }
        }
    }
}
