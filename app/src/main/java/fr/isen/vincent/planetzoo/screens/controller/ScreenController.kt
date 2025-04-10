package fr.isen.vincent.planetzoo.screens.controller

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.R
import fr.isen.vincent.planetzoo.components.DrawerContent
import fr.isen.vincent.planetzoo.components.TopBar
import fr.isen.vincent.planetzoo.data.BiomeModel
import fr.isen.vincent.planetzoo.data.NavBarItem
import fr.isen.vincent.planetzoo.screens.content.main.HomeScreen
import fr.isen.vincent.planetzoo.screens.content.main.SecurityScreen
import fr.isen.vincent.planetzoo.screens.content.main.ServiceScreen
import fr.isen.vincent.planetzoo.screens.content.main.animals.ZooListScreen
import kotlinx.coroutines.launch

@Composable
fun ScreenController(
    modifier: Modifier = Modifier,
    navController: NavController,
    zooListState: MutableState<List<BiomeModel>>
) {

    val context = LocalContext.current


    val homePage = NavBarItem(
        title = ContextCompat.getString(context, R.string.homepage),
        selectedIcon = ImageVector.vectorResource(R.drawable.home_icon_clicked),
        unselectedIcon = ImageVector.vectorResource(R.drawable.home_icon_unclicked),
    )
    val animalsPage = NavBarItem(
        title = ContextCompat.getString(context, R.string.animalspage),
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

    val navBarItems = listOf(servicesPage, homePage, animalsPage, securityPage)

    var selectedIndex by remember { mutableIntStateOf(1) }
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
                navController
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
                }, modifier)
            },
            bottomBar = {
                NavigationBar(
                    containerColor = Color(0xFFF5EEE2),
                ) {
                    navBarItems.forEachIndexed { index, navBarItem ->
                        NavigationBarItem(
                            selected = index == selectedIndex,
                            onClick = { selectedIndex = index },
                            icon = {
                                Icon(
                                    imageVector = navBarItem.selectedIcon,
                                    contentDescription = navBarItem.title
                                )
                            },
                            label = { Text(text = navBarItem.title) },
                            colors = NavigationBarItemColors(
                                selectedIconColor = Color(0xFF796D47),
                                selectedTextColor = Color(0xFF796D47),
                                selectedIndicatorColor = Color(0xFFD2C6A1),
                                unselectedIconColor =Color(0xFFD6725D),
                                unselectedTextColor = Color(0xFFD6725D),
                                disabledIconColor = Color(0xFFE3C6C0),
                                disabledTextColor = Color(0xFFE3C6C0),
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            ContentScreen(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                selectedIndex = selectedIndex,
                navController = navController,
                zooListState = zooListState,
            )
        }
    }
}

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    navController: NavController,
    zooListState: MutableState<List<BiomeModel>>
) {
    when (selectedIndex) {
        0 -> ServiceScreen(modifier)
        1 -> HomeScreen(modifier, navController)
        2 -> ZooListScreen(zooListState.value, navController,modifier)
        3 -> SecurityScreen(modifier,navController)
    }
}
