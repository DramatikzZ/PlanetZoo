package fr.isen.vincent.planetzoo.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import fr.isen.vincent.planetzoo.R

@Composable
fun AuthScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.banner_auth),
            contentDescription = ContextCompat.getString(context, R.string.auth_image),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = ContextCompat.getString(context, R.string.auth_title),
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navController.navigate(ContextCompat.getString(context, R.string.login_route))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
            ) {
            Text(
                text = ContextCompat.getString(context, R.string.login_title),
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton (
            onClick = {
                navController.navigate(ContextCompat.getString(context, R.string.signup_route))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                text = ContextCompat.getString(context, R.string.signup_title),
                fontSize = 22.sp
            )
        }
    }
}