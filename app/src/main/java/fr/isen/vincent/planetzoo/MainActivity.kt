package fr.isen.vincent.planetzoo

import AnimalsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.isen.vincent.planetzoo.components.BottomNavBar
import fr.isen.vincent.planetzoo.data.BiomeModel
import fr.isen.vincent.planetzoo.data.NavBarItem
import fr.isen.vincent.planetzoo.screens.HomeScreen
import fr.isen.vincent.planetzoo.utils.FirebaseHelper

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseHelper = FirebaseHelper()
        val zooListState = mutableStateOf<List<BiomeModel>>(emptyList())

        firebaseHelper.fetchZooData { zooList ->
            zooListState.value = zooList
        }

        setContent {

            val navController = rememberNavController()

            val context = LocalContext.current

            val homePage = NavBarItem(
                title = ContextCompat.getString(context, R.string.homepage),
                selectedIcon = ImageVector.vectorResource(R.drawable.home_icon_clicked),
                unselectedIcon = ImageVector.vectorResource(R.drawable.home_icon_unclicked),
            )
            val animalsPage = NavBarItem(
                title = ContextCompat.getString(context, R.string. animalspage),
                selectedIcon = ImageVector.vectorResource(R.drawable.animals_icon_clicked),
                unselectedIcon = ImageVector.vectorResource(R.drawable.animals_icon_unclicked),
            )
            val servicesPage = NavBarItem(
                title = ContextCompat.getString(context, R.string.servicespage),
                selectedIcon = ImageVector.vectorResource(R.drawable.services_icon_clicked),
                unselectedIcon = ImageVector.vectorResource(R.drawable.services_icon_unclicked),
            )
            val securityPage = NavBarItem(
                title = ContextCompat.getString(context, R.string.securitypage),
                selectedIcon = ImageVector.vectorResource(R.drawable.security_icon_clicked),
                unselectedIcon = ImageVector.vectorResource(R.drawable.security_icon_unclicked),
            )

            val navBarItems = listOf(servicesPage, homePage, animalsPage,securityPage)

            PlanetZooTheme {
                /*Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavBar(navBarItems, navController) },
                ) { innerPadding ->

                    Box(Modifier.padding(innerPadding)) {
                        NavHost(navController = navController, startDestination = homePage.title) {
                            composable(homePage.title) {
                                HomeScreen()
                            }
                            composable(servicesPage.title) {
                                Text(servicesPage.title)
                            }
                            composable(securityPage.title) {
                                Text(securityPage.title)
                            }
                            composable(animalsPage.title) {
                                AnimalsScreen()
                            }
                        }

                    }
                }*/
                Scaffold (modifier = Modifier.fillMaxSize()){ innerPadding ->
                    AppNavigation(Modifier.padding(innerPadding))
                }
            }
        }
    }
}