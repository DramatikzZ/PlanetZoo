package fr.isen.vincent.planetzoo.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.R
import fr.isen.vincent.planetzoo.utils.AppUtil
import fr.isen.vincent.planetzoo.viewmodel.AuthViewModel

@Composable
fun ForgotPasswordScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel = viewModel()) {

    val context = LocalContext.current

    var email by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }


    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = ContextCompat.getString(context, R.string.forgot_text),
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = ContextCompat.getString(context, R.string.soucis_text),
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 22.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF796D47)
            )
        )
        Image(
            painter = painterResource(id = R.drawable.banner_forgot_password),
            contentDescription = ContextCompat.getString(context, R.string.auth_image),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(text = ContextCompat.getString(context, R.string.email_text))
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFD6725D),
                unfocusedLabelColor = Color(0xFF796D47),
                focusedLabelColor = Color(0xFFD6725D),
                unfocusedBorderColor =  Color(0xFF796D47),

                )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                isLoading = true
                authViewModel.changePassword(context, email) {
                        success, errorMessage ->
                    if (success) {
                        isLoading = false
                        navController.navigate("login") {
                            popUpTo("forgot_password") { inclusive = true }
                        }
                    } else {
                        isLoading = false
                        AppUtil.showToast(context, errorMessage?: "Something went wrong")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD7725D),
                contentColor = Color.White
            )
        ) {
            Text(text = "Envoyer")
        }
    }
}