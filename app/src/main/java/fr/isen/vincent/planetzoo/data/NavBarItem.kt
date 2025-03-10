package fr.isen.vincent.planetzoo.data

import androidx.compose.ui.graphics.vector.ImageVector

data class NavBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)