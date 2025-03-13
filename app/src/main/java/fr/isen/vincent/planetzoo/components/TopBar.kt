package fr.isen.vincent.planetzoo.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import fr.isen.vincent.planetzoo.R
import fr.isen.vincent.planetzoo.data.UserModel


@Composable
fun TopBar(onOpenDrawer: () -> Unit) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = onOpenDrawer) {
            Icon(
                Icons.Filled.Menu,
                contentDescription = ContextCompat.getString(context, R.string.menu),
                modifier = Modifier.size(40.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.logo_zoo),
            contentDescription = ContextCompat.getString(context, R.string.logo_zoo),
            modifier = Modifier.size(width = 160.dp, height = 55.dp)
        )
    }
}

@Composable
fun DrawerContent(
    onCloseClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    navController: NavController
) {

    val context = LocalContext.current
    ModalDrawerSheet {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(ContextCompat.getString(context, R.string.menu), fontWeight = FontWeight.Bold)
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = ContextCompat.getString(context, R.string.close_menu)
                )
            }
        }

        NavigationDrawerItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = ContextCompat.getString(context, R.string.profilepage)) },
            label = { Text(ContextCompat.getString(context, R.string.profilepage)) },
            selected = false,
            onClick = onProfileClick
        )

        NavigationDrawerItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = ContextCompat.getString(context, R.string.parameters)) },
            label = { Text(ContextCompat.getString(context, R.string.parameters)) },
            selected = false,
            onClick = onSettingsClick
        )

        if(UserModel.isAdmin) {
            NavigationDrawerItem(
                icon = { Icon(Icons.Filled.Settings, contentDescription = "Admin only") },
                label = { Text("Admin only") },
                selected = false,
                onClick = onSettingsClick
            )
        }

        NavigationDrawerItem(
            icon = { Icon(Icons.Filled.ExitToApp, contentDescription = "Logout") },
            label = { Text("Logout") },
            selected = false,
            onClick = {
                Firebase.auth.signOut()
                navController.navigate("auth") {
                    popUpTo("home") {inclusive = true}
                }
            }
        )
    }
}