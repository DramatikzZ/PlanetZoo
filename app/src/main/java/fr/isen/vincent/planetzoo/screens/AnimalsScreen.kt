import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.isen.vincent.planetzoo.data.BiomeModel
import fr.isen.vincent.planetzoo.screens.AnimalListScreen
import fr.isen.vincent.planetzoo.screens.EnclosureListScreen
import fr.isen.vincent.planetzoo.screens.ZooListScreen
import fr.isen.vincent.planetzoo.utils.FirebaseHelper
import fr.isen.vincent.planetzoo.ui.theme.PlanetZooTheme

@Composable
fun AnimalsScreen() {
    val navController = rememberNavController()
    val firebaseHelper = FirebaseHelper()
    val zooListState = remember { mutableStateOf<List<BiomeModel>>(emptyList()) }

    firebaseHelper.fetchZooData { zooList ->
        zooListState.value = zooList
    }

    PlanetZooTheme {
        NavHost(navController = navController, startDestination = "biomes") {
            composable("biomes") {
                ZooListScreen(zooListState.value, navController)
            }
            composable(
                "enclosures/{biomeId}",
                arguments = listOf(navArgument("biomeId") { type = NavType.StringType })
            ) { backStackEntry ->
                val biomeId = backStackEntry.arguments?.getString("biomeId")
                val selectedBiome = zooListState.value.find { it.id == biomeId }
                selectedBiome?.let {
                    EnclosureListScreen(it, navController)
                }
            }
            composable(
                "animals/{enclosureId}",
                arguments = listOf(navArgument("enclosureId") { type = NavType.StringType })
            ) { backStackEntry ->
                val enclosureId = backStackEntry.arguments?.getString("enclosureId")
                val selectedEnclosure = zooListState.value
                    .flatMap { it.enclosures }
                    .find { it.id == enclosureId }
                selectedEnclosure?.let {
                    AnimalListScreen(it, navController)
                }
            }
        }
    }
}
