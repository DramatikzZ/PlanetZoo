package fr.isen.vincent.planetzoo.screens

import AnimalsScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.R
import fr.isen.vincent.planetzoo.components.DrawerContent
import fr.isen.vincent.planetzoo.components.TopBar
import fr.isen.vincent.planetzoo.data.NavBarItem
import fr.isen.vincent.planetzoo.screens.content.HomeScreen
import fr.isen.vincent.planetzoo.screens.content.SecurityScreen
import fr.isen.vincent.planetzoo.screens.content.ServiceScreen
import kotlinx.coroutines.launch

@Composable
fun ScreenController(modifier : Modifier = Modifier, navController: NavController) {

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

    var selectedIndex by remember {
        mutableIntStateOf(1)
    }

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
        Scaffold (
            topBar = {
                TopBar(onOpenDrawer = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                })
            },
            bottomBar = {
                NavigationBar {
                    navBarItems.forEachIndexed { index, navBarItem ->
                        NavigationBarItem(
                            selected = index==selectedIndex,
                            onClick = {
                                selectedIndex = index
                            },
                            icon = {
                                Icon(imageVector = navBarItem.selectedIcon, contentDescription = navBarItem.title)
                            },
                            label = {
                                Text(text= navBarItem.title)
                            }
                        )
                    }
                }
            }

        ){innerPadding ->
            ContentScreen(modifier = modifier.padding(innerPadding), selectedIndex)
        }
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int) {
    when(selectedIndex) {
        0-> ServiceScreen(modifier)
        1-> HomeScreen(modifier)
        2-> AnimalsScreen(modifier)
        3-> SecurityScreen(modifier)
    }
}

