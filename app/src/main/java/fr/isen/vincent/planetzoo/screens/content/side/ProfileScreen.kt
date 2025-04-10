package fr.isen.vincent.planetzoo.screens.content.side

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import fr.isen.vincent.planetzoo.components.enclosure.EnclosureCard
import fr.isen.vincent.planetzoo.data.BiomeModel
import fr.isen.vincent.planetzoo.data.CommentModel
import fr.isen.vincent.planetzoo.data.EnclosureModel
import fr.isen.vincent.planetzoo.data.UserModel
import fr.isen.vincent.planetzoo.utils.FirebaseHelper

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val userComments = remember { mutableStateListOf<Triple<String, String, CommentModel>>() }
    val closedEnclosures = remember { mutableStateListOf<EnclosureModel>() }


    LaunchedEffect(Unit) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        firebaseUser?.let {
            UserModel.uid = it.uid
            //UserModel.name = it.displayName ?: it.email?.substringBefore("@") ?: "Visiteur"
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
                .padding(8.dp),
            tint =  Color(0xFF796D47),
        )

        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Filled.AccountCircle,
                contentDescription = "profile_image",
                modifier = Modifier.size(150.dp),
                tint = Color(0xFFD7725D),
            )
            Text(
                text = UserModel.name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF796D47),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (UserModel.isAdmin) {
            Column (modifier = Modifier.padding(16.dp)) {
                Text(text = "Enclos en maintenance :",
                    color = Color(0xFFD7725D),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardColors(
                        containerColor =  Color(0xFFF5EEE2),
                        contentColor = Color.Black,
                        disabledContentColor =  Color(0xFFD2C6A1),
                        disabledContainerColor =  Color(0xFFD2C6A1),

                        )
                ) {
                    Text(
                        text = "afficher les enclos en maintenance",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }

            }
        } else {
            Column (modifier = Modifier.padding(16.dp)){
                Text(text = "Vos commentaires :",
                    color = Color(0xFFD7725D),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(16.dp))

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
                            elevation = CardDefaults.cardElevation(4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardColors(
                                containerColor =  Color(0xFFF5EEE2),
                                contentColor = Color.Black,
                                disabledContentColor =  Color(0xFFD2C6A1),
                                disabledContainerColor =  Color(0xFFD2C6A1),

                                )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("🌿 Biome: $biomeId | Enclos: $enclosureId",style = MaterialTheme.typography.bodyMedium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("${comment.comment} - ${comment.rating}/5 ⭐", fontWeight = FontWeight.Bold)

                            }
                        }
                    }
                }
            }
        }
    }
}
