package fr.isen.vincent.planetzoo.screens.content.main.animals

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.isen.vincent.planetzoo.data.BiomeModel
import fr.isen.vincent.planetzoo.data.EnclosureModel
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.data.CommentModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import fr.isen.vincent.planetzoo.data.UserModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnclosureListScreen(biome: BiomeModel, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = biome.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(Icons.Default.Clear, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(biome.enclosures) { enclosure ->
                EnclosureCard(enclosure, biome.color, navController)
            }
        }
    }
}


@Composable
fun VisitorContent(
    enclosure: EnclosureModel,
    biomeColor: String,
    navController: NavController
) {
    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }
    var hasCommented by remember { mutableStateOf(false) }
    var averageRating by remember { mutableStateOf(0.0) }

    val database = FirebaseDatabase.getInstance().reference
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"
    val commentsList = remember { mutableStateListOf<CommentModel>() }

    LaunchedEffect(enclosure.id) {
        val commentRef = database.child("biomes").child(enclosure.id_biomes)
            .child("enclosures").child(enclosure.id).child("comments")

        commentRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                commentsList.clear()
                var totalRating = 0
                var count = 0
                hasCommented = false

                for (commentSnapshot in snapshot.children) {
                    val comment = commentSnapshot.getValue(CommentModel::class.java)
                    comment?.let {
                        commentsList.add(it)
                        totalRating += it.rating
                        count++

                        if (it.uid == userId) {
                            hasCommented = true
                        }
                    }
                }

                averageRating = if (count > 0) totalRating.toDouble() / count else 0.0
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseDebug", "❌ Erreur lors du chargement des commentaires: ${error.message}")
            }
        })
    }

    Spacer(modifier = Modifier.height(8.dp))

    if (hasCommented) {
        Text(
            text = "⭐ Note moyenne : ${"%.1f".format(averageRating)}/5",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    } else {
        Row {
            (1..5).forEach { index ->
                Icon(
                    imageVector = if (index <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "Star $index",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { rating = index },
                    tint = if (index <= rating) Color.Yellow else Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Laisser un commentaire") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val commentRef = database.child("biomes").child(enclosure.id_biomes)
                    .child("enclosures").child(enclosure.id).child("comments")

                commentRef.get().addOnSuccessListener { snapshot ->
                    val existingComments = snapshot.children.mapNotNull { it.getValue(CommentModel::class.java) }.toMutableList()

                    val newComment = CommentModel(
                        id = existingComments.size.toString(),
                        comment = comment,
                        uid = userId,
                        rating = rating
                    )

                    existingComments.add(newComment)

                    commentRef.setValue(existingComments)
                    comment = ""
                    rating = 0
                }
            },
            enabled = rating > 0
        ) {
            Text("Soumettre")
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(text = "Commentaires:", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onPrimary)

    if (commentsList.isNotEmpty()) {
        commentsList.forEach { commentItem ->
            Text(
                text = "⭐ ${commentItem.rating}/5 - ${commentItem.comment}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    } else {
        Text(
            text = "Aucun commentaire",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun EnclosureCard(enclosure: EnclosureModel, biomeColor: String, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("animals/${enclosure.id}") },
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(android.graphics.Color.parseColor(biomeColor)))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Enclos: ${enclosure.id}", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onPrimary)
            Text(text = "Animaux: ${enclosure.animals.joinToString { it.name }}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
            VisitorContent(enclosure, biomeColor, navController)
        }
    }
}


//
//@Composable
//fun EnclosureCard(enclosure: EnclosureModel, biomeColor: String, navController: NavController) {
//    var rating by remember { mutableStateOf(0) }
//    var comment by remember { mutableStateOf("") }
//    var hasCommented by remember { mutableStateOf(false) }
//    var averageRating by remember { mutableStateOf(0.0) }
//
//    val database = FirebaseDatabase.getInstance().reference
//    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"
//
//    val commentsList = remember { mutableStateListOf<CommentModel>() }
//
//    LaunchedEffect(enclosure.id) {
//        val commentRef = database.child("biomes").child(enclosure.id_biomes)
//            .child("enclosures").child(enclosure.id).child("comments")
//
//        commentRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                commentsList.clear()
//                var totalRating = 0
//                var count = 0
//                hasCommented = false
//
//                for (commentSnapshot in snapshot.children) {
//                    val comment = commentSnapshot.getValue(CommentModel::class.java)
//                    comment?.let {
//                        commentsList.add(it)
//                        totalRating += it.rating
//                        count++
//
//                        if (it.uid == userId) {
//                            hasCommented = true
//                        }
//                    }
//                }
//
//                averageRating = if (count > 0) totalRating.toDouble() / count else 0.0
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("FirebaseDebug", "❌ Erreur lors du chargement des commentaires: ${error.message}")
//            }
//        })
//    }
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .clickable { navController.navigate("animals/${enclosure.id}") },
//        elevation = CardDefaults.cardElevation(6.dp),
//        colors = CardDefaults.cardColors(containerColor = Color(android.graphics.Color.parseColor(biomeColor)))
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "Enclos: ${enclosure.id}", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onPrimary)
//            Text(text = "Animaux: ${enclosure.animals.joinToString { it.name }}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            if (hasCommented) {
//                Text(
//                    text = "⭐ Note moyenne : ${"%.1f".format(averageRating)}/5",
//                    style = MaterialTheme.typography.headlineSmall,
//                    color = MaterialTheme.colorScheme.onPrimary
//                )
//            } else {
//                Row {
//                    (1..5).forEach { index ->
//                        Icon(
//                            imageVector = if (index <= rating) Icons.Filled.Star else Icons.Outlined.Star,
//                            contentDescription = "Star $index",
//                            modifier = Modifier
//                                .size(24.dp)
//                                .clickable { rating = index },
//                            tint = if (index <= rating) Color.Yellow else Color.Gray
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                OutlinedTextField(
//                    value = comment,
//                    onValueChange = { comment = it },
//                    label = { Text("Laisser un commentaire") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Button(
//                    onClick = {
//                        val commentRef = database.child("biomes").child(enclosure.id_biomes)
//                            .child("enclosures").child(enclosure.id).child("comments")
//
//                        commentRef.get().addOnSuccessListener { snapshot ->
//                            val existingComments = snapshot.children.mapNotNull { it.getValue(CommentModel::class.java) }.toMutableList()
//
//                            val newComment = CommentModel(
//                                id = existingComments.size.toString(),
//                                comment = comment,
//                                uid = userId,
//                                rating = rating
//                            )
//
//                            existingComments.add(newComment)
//
//                            commentRef.setValue(existingComments)
//                            comment = ""
//                            rating = 0
//                        }
//                    },
//                    enabled = rating > 0
//                ) {
//                    Text("Soumettre")
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text(text = "Commentaires:", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onPrimary)
//
//            if (commentsList.isNotEmpty()) {
//                commentsList.forEach { commentItem ->
//                    Text(
//                        text = "⭐ ${commentItem.rating}/5 - ${commentItem.comment}",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.onPrimary
//                    )
//                }
//            } else {
//                Text(
//                    text = "Aucun commentaire",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.onPrimary
//                )
//            }
//        }
//    }
//}
//
