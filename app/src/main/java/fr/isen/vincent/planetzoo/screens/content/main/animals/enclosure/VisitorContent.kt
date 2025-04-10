package fr.isen.vincent.planetzoo.screens.content.main.animals.enclosure

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import fr.isen.vincent.planetzoo.components.enclosure.CommentList
import fr.isen.vincent.planetzoo.components.enclosure.RatingAndCommentSection
import fr.isen.vincent.planetzoo.data.CommentModel
import fr.isen.vincent.planetzoo.data.EnclosureModel

@Composable
fun VisitorContent(enclosure: EnclosureModel) {
    val database = FirebaseDatabase.getInstance().reference
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"
    val commentsList = remember { mutableStateListOf<CommentModel>() }
    var averageRating by remember { mutableStateOf(0.0) }
    var hasCommented by remember { mutableStateOf(false) }
    var isOpen by remember { mutableStateOf(enclosure.is_open) }
    var showRatingSection by remember { mutableStateOf(false) }

    LaunchedEffect(enclosure.firebaseId) {
        val enclosureRef = database.child("biomes").child(enclosure.id_biomes)
            .child("enclosures").child(enclosure.firebaseId).child("is_open")

        enclosureRef.get().addOnSuccessListener { snapshot ->
            snapshot.getValue(Boolean::class.java)?.let { isOpen = it }
        }

        val commentRef = database.child("biomes").child(enclosure.id_biomes)
            .child("enclosures").child(enclosure.firebaseId).child("comments")

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

    Text(
        text = "Note moyenne : ${"%.1f".format(averageRating)}/5 ⭐ ",
        style = MaterialTheme.typography.titleMedium,
        color = Color(0xFF796D47)
    )

    Spacer(modifier = Modifier.height(15.dp))

    CommentList(commentsList, Color(0xFF796D47), enclosure)

    Spacer(modifier = Modifier.height(15.dp))

    if (!isOpen) {
        Text(
            text = "⚠ Enclos en maintenance",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.error
        )
    } else {
        if (!showRatingSection && !hasCommented) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { showRatingSection = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD7725D),
                        contentColor = Color.White
                    )
                ) {
                    Text("Noter l'enclos")
                }
            }
        }

        if (showRatingSection || hasCommented) {
            RatingAndCommentSection(hasCommented, database, enclosure, userId,onCancel = { showRatingSection = false } )
        }
    }
}
