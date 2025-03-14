package fr.isen.vincent.planetzoo.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun TestScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Test screen")
        Button(
            onClick = {
                Firebase.auth.signOut()
                navController.navigate("auth") {
                    popUpTo("home") {inclusive = true}
                }
            }
        ) {
            Text(text = "Logout")
        }
    }
}