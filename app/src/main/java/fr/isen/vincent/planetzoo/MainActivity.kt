package fr.isen.vincent.planetzoo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.isen.vincent.planetzoo.ui.theme.PlanetZooTheme


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import fr.isen.vincent.planetzoo.data.ZooModel
import fr.isen.vincent.planetzoo.utils.FirebaseHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val firebaseHelper = FirebaseHelper()
        val zooListState = mutableStateOf<List<ZooModel>>(emptyList())

        // Lire les données et mettre à jour la liste
        firebaseHelper.fetchZooData { zooList ->
            zooListState.value = zooList
        }

        setContent {
            PlanetZooTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ZooListScreen(zooListState.value, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ZooListScreen(zooList: List<ZooModel>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(zooList) { zoo ->
            ZooCard(zoo)
        }
    }
}

@Composable
fun ZooCard(zoo: ZooModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Zoo: ${zoo.name}", style = MaterialTheme.typography.headlineSmall)
            Text(text = "Couleur: ${zoo.color}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Nombre d'enclos: ${zoo.enclosures.size}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PlanetZooTheme {
        ZooListScreen(
            listOf(
                Zoo(id = "1", color = "#70D5C2", name = "La Bergerie des reptiles", enclosures = listOf()),
                Zoo(id = "2", color = "#A4BDCC", name = "Le Vallon des cascades", enclosures = listOf())
            )
        )
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PlanetZooTheme {
        Greeting("Android")
    }
}*/