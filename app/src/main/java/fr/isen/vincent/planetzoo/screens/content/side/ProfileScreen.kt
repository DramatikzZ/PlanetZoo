package fr.isen.vincent.planetzoo.screens.content.side

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import fr.isen.vincent.planetzoo.data.UserModel

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    Column (
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ){
        Icon(
            Icons.Filled.Clear,
            contentDescription = "Back",
            Modifier.clickable { navController.navigate("home")}
                .size(50.dp)
                .padding(8.dp)
        )

        Column (
            modifier = Modifier
                .fillMaxWidth().background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                Icons.Filled.AccountCircle,
                contentDescription = "profile_image",
                modifier = Modifier.size(150.dp)
            )
            Text(
                text = UserModel.name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (UserModel.isAdmin) {
            Column {
                Text(
                    text = "Vos taches :",
                )
                Card (modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ){
                    Text(
                        text = "Exemple de taches",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        } else {
            Column {
                Text(
                    text = "Vos commentaires :",
                )
                Card (modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ){
                    Text(
                        text = "Exemple de commentaire",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}