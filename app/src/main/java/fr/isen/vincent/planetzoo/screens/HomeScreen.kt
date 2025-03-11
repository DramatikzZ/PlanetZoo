package fr.isen.vincent.planetzoo.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.vincent.planetzoo.R
import kotlinx.coroutines.launch


@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeContent(navController) }
        composable("profile") { ProfileScreen() }
        composable("parameters") { ParametersScreen() }
    }
}


@Composable
fun HomeContent(navController: NavController) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onCloseClick = { scope.launch { drawerState.close() } },
                onProfileClick = {
                    scope.launch { drawerState.close() }
                    navController.navigate("profile")
                },
                onSettingsClick = {
                    scope.launch { drawerState.close() }
                    navController.navigate("parameters")
                }
            )
        },
        gesturesEnabled = true,
        scrimColor = Color.Black.copy(alpha = 0.3f)
    ) {
        Scaffold(
            topBar = {
                TopBar(onOpenDrawer = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                })
            }
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                MiddlePage()
            }
        }
    }
}


@Composable
fun TopBar(onOpenDrawer: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = onOpenDrawer) {
            Icon(
                Icons.Filled.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(40.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.logo_zoo),
            contentDescription = "Logo Zoo",
            modifier = Modifier.size(width = 160.dp, height = 55.dp)
        )
    }
}

@Composable
fun DrawerContent(onCloseClick: () -> Unit, onProfileClick: () -> Unit, onSettingsClick: () -> Unit) {
    ModalDrawerSheet {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Menu", fontWeight = FontWeight.Bold)
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Fermer le menu"
                )
            }
        }

        NavigationDrawerItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profil") },
            label = { Text("Profil") },
            selected = false,
            onClick = onProfileClick
        )

        NavigationDrawerItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Paramètres") },
            label = { Text("Paramètres") },
            selected = false,
            onClick = onSettingsClick
        )
    }
}


@Composable
fun MiddlePage() {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(20.dp)
        ){
            Text(
                text = "BIENVENUE AU PARC ANIMALIER DE LA BARBEN !",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Le parc est ouvert tous les jours, y compris les jours fériés.\n\n" +
                        "Au cœur de la Provence et d’un site Natura 2000, le Parc animalier de La Barben est une invitation à l’évasion.\n\n" +
                        "9 km de sentiers vous guident à la rencontre de 130 espèces différentes, à l’ombre des chênes.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text("Carousel")
        }
    }
}
