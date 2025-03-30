package fr.isen.vincent.planetzoo.screens.content.side

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import fr.isen.vincent.planetzoo.data.CommentModel
import fr.isen.vincent.planetzoo.data.UserModel
import fr.isen.vincent.planetzoo.utils.FirebaseHelper

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val userComments = remember { mutableStateListOf<Triple<String, String, CommentModel>>() }

    LaunchedEffect(Unit) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        firebaseUser?.let {
            UserModel.uid = it.uid
            UserModel.name = it.displayName ?: it.email?.substringBefore("@") ?: "Visiteur"
            UserModel.isAdmin = false
        }

        println("veruf UID ACTUEL = '${UserModel.uid}'")
        println("verif de l'appel de fethUserComments pour UID: ${UserModel.uid}")

        FirebaseHelper().fetchUserComments(UserModel.uid) { comments ->
            println(" Commentaires trouvé : ${comments.size}")
            userComments.clear()
            userComments.addAll(comments)
        }
    }


    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Icon(
            Icons.Filled.Clear,
            contentDescription = "Back",
            Modifier.clickable { navController.navigate("home") }
                .size(50.dp)
                .padding(8.dp)
        )

        Column (
            modifier = Modifier.fillMaxWidth().background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                Text(text = "Vos tâches :")
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Text(
                        text = "Exemple de tâches",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        } else {
            Column {
                Text(text = "Vos commentaires :")

                if (userComments.isEmpty()) {
                    Text(
                        text = "Aucun commentaire pour l'instant",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    userComments.forEach { (biomeId, enclosureId, comment) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("🌿 Biome: $biomeId | 🏞 Enclos: $enclosureId", style = MaterialTheme.typography.bodySmall)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("⭐ ${comment.rating}/5 - ${comment.name}", fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(comment.comment ?: "", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}
