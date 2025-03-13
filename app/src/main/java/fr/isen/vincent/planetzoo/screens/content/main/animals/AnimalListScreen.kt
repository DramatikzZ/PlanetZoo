package fr.isen.vincent.planetzoo.screens.content.main.animals

import GeminiHelper.parseAnimalInfo
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import fr.isen.vincent.planetzoo.data.AnimalModel
import fr.isen.vincent.planetzoo.data.EnclosureModel
import kotlinx.coroutines.launch




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalListScreen(enclosure: EnclosureModel, navController: NavController, modifier: Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val animalInfos = remember { mutableStateMapOf<String, String>() }
    val animalImages = remember { mutableStateMapOf<String, String>() }

    LaunchedEffect(enclosure.animals) {
        enclosure.animals.forEach { animal ->
            coroutineScope.launch {
                val info = GeminiHelper.fetchAnimalInfo(animal.name)
                val imageUrl = GeminiHelper.fetchAnimalImage(animal.name)
                animalInfos[animal.id] = info
                animalImages[animal.id] = imageUrl
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
        LazyColumn(modifier = modifier.padding(innerPadding)) {
            items(enclosure.animals) { animal ->
                val info = animalInfos[animal.id] ?: "Chargement des infos..."
                val imageUrl = animalImages[animal.id]
                AnimalCard(animal, info, imageUrl)
            }
        }
    }
}

@Composable
fun AnimalCard(animal: AnimalModel, info: String, imageUrl: String?) {
    var isExpanded by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = { isExpanded = !isExpanded }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
                Box(modifier = Modifier.size(80.dp)) {
                    if(isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(80.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        onLoading = {
                            isLoading = true
                        },
                        onSuccess = {
                            isLoading = false
                        },
                        onError = {
                            isLoading = false
                        },
                        modifier = Modifier
                            .size(80.dp)
                            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .padding(4.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = animal.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "ID: ${animal.id}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (isExpanded) {
                    val formattedInfo = parseAnimalInfo(info)
                    Text(
                        text = formattedInfo,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(
                        text = "Clique pour en savoir plus...",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
