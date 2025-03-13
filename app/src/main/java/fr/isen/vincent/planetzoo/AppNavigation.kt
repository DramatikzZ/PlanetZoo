package fr.isen.vincent.planetzoo

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import fr.isen.vincent.planetzoo.data.UserModel
import fr.isen.vincent.planetzoo.screens.LoadingScreen
import fr.isen.vincent.planetzoo.screens.controller.ScreenController
import fr.isen.vincent.planetzoo.screens.auth.AuthScreen
import fr.isen.vincent.planetzoo.screens.auth.LoginScreen
import fr.isen.vincent.planetzoo.screens.auth.SignupScreen
import fr.isen.vincent.planetzoo.screens.auth.TestScreen
import fr.isen.vincent.planetzoo.screens.content.side.ParametersScreen
import fr.isen.vincent.planetzoo.screens.content.side.ProfileScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val navController = rememberNavController()

    val isLoggedIn = Firebase.auth.currentUser!=null
    var isLoading by remember {
        mutableStateOf(true)
    }
    val firstPage = if(isLoggedIn) "home" else  ContextCompat.getString(context, R.string.auth_route)

    if(isLoggedIn) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    if (result != null && result.exists()) {
                        UserModel.isAdmin = result.getBoolean("admin") ?: false
                        Log.d("ouioui", (result.getBoolean("admin")?:false).toString())
                        UserModel.name =  result.getString("name") ?: "Nom inconnu"
                    }
                }
                isLoading = false
            }

    } else {
        isLoading = false
    }


    if (isLoading) {
        LoadingScreen()
    } else {
        NavHost(navController = navController, startDestination = firstPage) {

            composable( ContextCompat.getString(context, R.string.auth_route)) {
                AuthScreen(modifier, navController)
            }

            composable( ContextCompat.getString(context, R.string.login_route)) {
                LoginScreen(modifier, navController)
            }

            composable( ContextCompat.getString(context, R.string.signup_route)) {
                SignupScreen(modifier, navController)
            }

            composable( ContextCompat.getString(context, R.string.test_route)) {
                TestScreen(modifier, navController)
            }

            composable( "home") {
                ScreenController(modifier, navController)
            }

            composable("profile") {
                ProfileScreen()
            }

            composable("parameters") {
                ParametersScreen()
            }

        }
    }
}