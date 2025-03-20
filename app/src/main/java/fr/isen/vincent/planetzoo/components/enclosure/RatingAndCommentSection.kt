package fr.isen.vincent.planetzoo.components.enclosure

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DatabaseReference
import fr.isen.vincent.planetzoo.data.EnclosureModel
import fr.isen.vincent.planetzoo.data.CommentModel

@Composable
fun RatingAndCommentSection(
    hasCommented: Boolean,
    averageRating: Double,
    database: DatabaseReference,
    enclosure: EnclosureModel,
    userId: String
) {
    if (hasCommented) {
        Text(
            text = "⭐ Note moyenne : ${"%.1f".format(averageRating)}/5",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    } else {
        var rating by remember { mutableStateOf(0) }
        var comment by remember { mutableStateOf("") }

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
                    .child("enclosures").child(enclosure.firebaseId).child("comments")

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
                }
            },
            enabled = rating > 0
        ) {
            Text("Soumettre")
        }
    }
}
