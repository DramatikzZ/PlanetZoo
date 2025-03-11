import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.isen.vincent.planetzoo.data.BiomeModel
import fr.isen.vincent.planetzoo.screens.content.AnimalListScreen
import fr.isen.vincent.planetzoo.screens.content.EnclosureListScreen
import fr.isen.vincent.planetzoo.screens.content.ZooListScreen
import fr.isen.vincent.planetzoo.utils.FirebaseHelper
import fr.isen.vincent.planetzoo.ui.theme.PlanetZooTheme

@Composable
fun AnimalsScreen(modifier: Modifier) {
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
