package fr.isen.vincent.planetzoo.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun TopBar(onOpenDrawer: () -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onOpenDrawer,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                Icons.Filled.Menu,
                contentDescription = ContextCompat.getString(context, R.string.menu),
                modifier = Modifier.size(32.dp),
                tint = Color(0xFF796D47),
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.logo_zoo),
            contentDescription = ContextCompat.getString(context, R.string.logo_zoo),
            modifier = Modifier
                .height(40.dp) // hauteur plus douce pour caler avec le menu
                .padding(end = 8.dp)
        )
    }
}


@Composable
fun DrawerContent(
    onCloseClick: () -> Unit,
    onProfileClick: () -> Unit,
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
            Text(ContextCompat.getString(context, R.string.menu), fontWeight = FontWeight.Bold, color = Color(0xFFD6725D),)
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = ContextCompat.getString(context, R.string.close_menu),
                    tint = Color(0xFFD6725D),
                )
            }
        }

        NavigationDrawerItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = ContextCompat.getString(context, R.string.profilepage), tint = Color(0xFF796D47)) },
            label = { if (UserModel.isAdmin) {
                Text(text = "Admin Profile", color = Color(0xFF796D47))
            } else {
                Text(ContextCompat.getString(context, R.string.profilepage), color = Color(0xFF796D47))
            } },
            selected = false,
            onClick = onProfileClick
        )

        NavigationDrawerItem(
            icon = { Icon(Icons.Filled.ExitToApp, contentDescription = "Logout", tint = Color(0xFF796D47)) },
            label = { Text("Logout", color = Color(0xFF796D47)) },
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