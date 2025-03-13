package fr.isen.vincent.planetzoo.screens.content.side

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.isen.vincent.planetzoo.data.UserModel

@Composable
fun ProfileScreen(){

    Column (modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(text= "Name = ${UserModel.name}")

        Spacer(modifier = Modifier.height(10.dp))

        Text(text="Admin = ${UserModel.isAdmin}")
    }
}