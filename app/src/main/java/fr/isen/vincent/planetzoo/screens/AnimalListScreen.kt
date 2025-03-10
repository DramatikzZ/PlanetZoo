package fr.isen.vincent.planetzoo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.data.AnimalModel
import fr.isen.vincent.planetzoo.data.EnclosureModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalListScreen(enclosure: EnclosureModel, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Animaux de l'Enclos ${enclosure.id}") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(enclosure.animals) { animal ->
                AnimalCard(animal)
            }
        }
    }
}

@Composable
fun AnimalCard(animal: AnimalModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nom: ${animal.name}", style = MaterialTheme.typography.headlineSmall)
            Text(text = "ID: ${animal.id}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
