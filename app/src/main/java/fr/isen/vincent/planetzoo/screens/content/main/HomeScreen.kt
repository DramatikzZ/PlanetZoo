package fr.isen.vincent.planetzoo.screens.content.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.R
import fr.isen.vincent.planetzoo.components.BiomeCarousel
import fr.isen.vincent.planetzoo.components.SearchBar
import fr.isen.vincent.planetzoo.components.SearchResultsList
import fr.isen.vincent.planetzoo.data.AnimalModel
import fr.isen.vincent.planetzoo.data.BiomeModel
import fr.isen.vincent.planetzoo.data.EnclosureModel
import fr.isen.vincent.planetzoo.screens.content.main.animals.AnimalCard
import fr.isen.vincent.planetzoo.utils.FirebaseHelper

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current

    val firebaseHelper = remember { FirebaseHelper() }
    val biomes = remember { mutableStateOf<List<BiomeModel>>(emptyList()) }
    val searchResults = remember { mutableStateOf<List<Pair<AnimalModel, EnclosureModel>>>(emptyList()) }

    LaunchedEffect(Unit) {
        firebaseHelper.fetchZooData { biomeList ->
            biomes.value = biomeList
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(20.dp)
        ) {
            Text(
                text = ContextCompat.getString(context, R.string.headline_zoo),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = ContextCompat.getString(context, R.string.corps_zoo),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("zooMap") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Voir la carte du zoo")
        }

        Spacer(modifier = Modifier.height(16.dp))

        SearchBar(biomes = biomes.value) { results ->
            searchResults.value = results
        }

        if (searchResults.value.isNotEmpty()) {
            SearchResultsList(searchResults = searchResults.value, navController = navController)
        } else if (biomes.value.isNotEmpty()) {
            BiomeCarousel(biomes = biomes.value, navController = navController)
        } else {
            Text("Chargement des biomes...", modifier = Modifier.padding(16.dp))
        }
    }
}
