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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.isen.vincent.planetzoo.data.BiomeModel
import fr.isen.vincent.planetzoo.screens.EnclosureListScreen
import fr.isen.vincent.planetzoo.screens.ZooListScreen
import fr.isen.vincent.planetzoo.utils.FirebaseHelper



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val firebaseHelper = FirebaseHelper()
        val zooListState = mutableStateOf<List<BiomeModel>>(emptyList())

        firebaseHelper.fetchZooData { zooList ->
            zooListState.value = zooList
        }

        setContent {
            PlanetZooTheme {
                AppNavigation(zooListState.value)
            }
        }
    }
}

@Composable
fun AppNavigation(zooList: List<BiomeModel>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "biomes") {
        composable("biomes") {
            ZooListScreen(zooList, navController)
        }

        composable(
            "enclosures/{biomeId}",
            arguments = listOf(navArgument("biomeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val biomeId = backStackEntry.arguments?.getString("biomeId")
            val selectedBiome = zooList.find { it.id == biomeId }
            selectedBiome?.let {
                EnclosureListScreen(it, navController)
            }
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