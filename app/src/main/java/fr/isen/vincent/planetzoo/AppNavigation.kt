package fr.isen.vincent.planetzoo

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import fr.isen.vincent.planetzoo.data.BiomeModel
import fr.isen.vincent.planetzoo.screens.controller.ScreenController
import fr.isen.vincent.planetzoo.screens.auth.*
import fr.isen.vincent.planetzoo.screens.content.main.animals.*
import fr.isen.vincent.planetzoo.screens.content.side.*
import fr.isen.vincent.planetzoo.utils.FirebaseHelper
import androidx.navigation.navArgument

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val navController = rememberNavController()
    val zooListState = remember { mutableStateOf<List<BiomeModel>>(emptyList()) }

    val isLoggedIn = Firebase.auth.currentUser != null
    val firstPage = if (isLoggedIn) "home" else ContextCompat.getString(context, R.string.auth_route)

    LaunchedEffect(Unit) {
        FirebaseHelper().fetchZooData { biomes ->
            zooListState.value = biomes
            println("✅ DEBUG: Biomes chargés depuis Firebase, nombre de biomes: ${biomes.size}")
        }
    }

    NavHost(navController = navController, startDestination = firstPage) {

        composable(ContextCompat.getString(context, R.string.auth_route)) {
            AuthScreen(modifier, navController)
        }

        composable(ContextCompat.getString(context, R.string.login_route)) {
            LoginScreen(modifier, navController)
        }

        composable(ContextCompat.getString(context, R.string.signup_route)) {
            SignupScreen(modifier, navController)
        }

        composable(ContextCompat.getString(context, R.string.test_route)) {
            TestScreen(modifier, navController)
        }

        composable("home") {
            ScreenController(modifier, navController)
        }

        composable(
            "enclosures/{biomeId}",
            arguments = listOf(navArgument("biomeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val biomeId = backStackEntry.arguments?.getString("biomeId")
            println("✅ DEBUG: Navigation vers enclosures/$biomeId")

            val selectedBiome = zooListState.value.find { it.id == biomeId }

            if (selectedBiome != null) {
                println("✅ DEBUG: Biome trouvé : ${selectedBiome.name}")
                EnclosureListScreen(selectedBiome, navController)
            } else {
                println("❌ ERREUR: Aucun biome trouvé pour ID = $biomeId")
            }
        }

        composable("comments") {
            println("✅ DEBUG: Navigation vers la page unique des commentaires")
            CommentsScreen(navController)
        }



        composable("profile") { ProfileScreen() }
        composable("parameters") { ParametersScreen() }
    }
}
