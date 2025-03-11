package fr.isen.vincent.planetzoo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.data.AnimalModel
import fr.isen.vincent.planetzoo.data.EnclosureModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalListScreen(enclosure: EnclosureModel, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val animalInfos = remember { mutableStateMapOf<String, String>() }

    LaunchedEffect(enclosure.animals) {
        enclosure.animals.forEach { animal ->
            coroutineScope.launch {
                val info = GeminiHelper.fetchAnimalInfo(animal.name)
                animalInfos[animal.id] = info
            }
        }
    }

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
                AnimalCard(animal, animalInfos[animal.id] ?: "Chargement des infos...")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalCard(animal: AnimalModel, info: String) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = { showDialog = true }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nom: ${animal.name}", style = MaterialTheme.typography.headlineSmall)
            Text(text = "ID: ${animal.id}", style = MaterialTheme.typography.bodyMedium)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Informations sur ${animal.name}") },
            text = { Text(info) },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Fermer")
                }
            }
        )
    }
}
