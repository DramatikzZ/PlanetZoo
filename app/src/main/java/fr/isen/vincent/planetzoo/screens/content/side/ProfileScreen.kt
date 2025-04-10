package fr.isen.vincent.planetzoo.screens.content.side

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import fr.isen.vincent.planetzoo.data.*
import fr.isen.vincent.planetzoo.utils.FirebaseHelper

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val userComments = remember { mutableStateListOf<Triple<String, String, CommentModel>>() }
    val closedEnclosures = remember { mutableStateListOf<Pair<String, EnclosureModel>>() }

    LaunchedEffect(Unit) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        firebaseUser?.let { UserModel.uid = it.uid }

        FirebaseHelper().fetchUserComments(UserModel.uid) { comments ->
            userComments.clear()
            userComments.addAll(comments)
        }

        val database = FirebaseDatabase.getInstance().reference
        val biomesRef = database.child("biomes")

        biomesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                closedEnclosures.clear()

                snapshot.children.forEach { biomeSnap ->
                    val biomeId = biomeSnap.key ?: return@forEach
                    val enclosuresSnap = biomeSnap.child("enclosures")

                    enclosuresSnap.children.forEach { enclosureSnap ->
                        val isOpen = enclosureSnap.child("is_open").getValue(Boolean::class.java) ?: true
                        if (!isOpen) {
                            val enclosure = enclosureSnap.getValue(EnclosureModel::class.java)
                            enclosure?.let {
                                val updated = it.copy(firebaseId = enclosureSnap.key ?: "", id_biomes = biomeId)
                                closedEnclosures.add(Pair(biomeId, updated))
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Firebase error: ${error.message}")
            }
        })
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Icon(
            Icons.Filled.Clear,
            contentDescription = "Back",
            modifier = Modifier
                .clickable { navController.navigate("home") }
                .size(50.dp)
                .padding(8.dp),
            tint = Color(0xFF796D47),
        )

        Column(
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
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Enclos en maintenance :",
                    color = Color(0xFFD7725D),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                if (closedEnclosures.isEmpty()) {
                    Text(
                        text = "Aucun enclos en maintenance actuellement.",
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    closedEnclosures.forEach { (_, enclosure) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EEE2)),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "🔧 Enclos ${enclosure.firebaseId}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFFD7725D)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                if (enclosure.animals.isEmpty()) {
                                    Text("Aucun animal dans cet enclos.")
                                } else {
                                    enclosure.animals.forEach {
                                        Text("- ${it.name}")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Vos commentaires :",
                    color = Color(0xFFD7725D),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (userComments.isEmpty()) {
                    Text(
                        text = "Aucun commentaire pour l'instant",
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    userComments.forEach { (biomeId, enclosureId, comment) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EEE2)),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("🌿 Biome: $biomeId | Enclos: $enclosureId")
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
