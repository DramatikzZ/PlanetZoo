package fr.isen.vincent.planetzoo.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.data.NavBarItem


@Composable
fun BottomNavBar(tabBarItems: List<NavBarItem>, navController: NavController) {
    var selectedTabIndex by rememberSaveable {
        mutableStateOf(1)
    }

    NavigationBar {
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    navController.navigate(tabBarItem.title)
                },
                icon = {
                    NavBarIconView(
                        isSelected = selectedTabIndex == index,
                        selectedIcon = tabBarItem.selectedIcon,
                        unselectedIcon = tabBarItem.unselectedIcon,
                        title = tabBarItem.title
                    )
                },
                label = {Text(tabBarItem.title)})
        }
    }
}


@Composable
fun NavBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String
) {

    Icon(
        imageVector = if (isSelected) {selectedIcon} else {unselectedIcon},
        contentDescription = title
    )
}