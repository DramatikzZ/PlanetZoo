package fr.isen.vincent.planetzoo.components.enclosure

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DatabaseReference
import fr.isen.vincent.planetzoo.data.EnclosureModel
import fr.isen.vincent.planetzoo.data.CommentModel
import fr.isen.vincent.planetzoo.data.UserModel


@Composable
fun RatingAndCommentSection(
    hasCommented: Boolean,
    database: DatabaseReference,
    enclosure: EnclosureModel,
    userId: String,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        if (!hasCommented) {
            var rating by remember { mutableStateOf(0) }
            var comment by remember { mutableStateOf("") }

            Text(
                text = "Votre évaluation :",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF796D47)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                (1..5).forEach { index ->
                    Icon(
                        imageVector = if (index <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = "Star $index",
                        modifier = Modifier
                            .size(32.dp)
                            .padding(horizontal = 4.dp)
                            .clickable { rating = index },
                        tint = if (index <= rating) Color(0xFFFFC107) else Color.Gray
                    )
                }
            }

            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Laisser un commentaire") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFD6725D),
                    unfocusedLabelColor = Color(0xFF796D47),
                    focusedLabelColor = Color(0xFFD6725D),
                    unfocusedBorderColor = Color(0xFF796D47),
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFD7725D),
                    ),
                ) {
                    Text("Annuler")
                }

                Button(
                    onClick = {
                        val commentRef = database.child("biomes").child(enclosure.id_biomes)
                            .child("enclosures").child(enclosure.firebaseId).child("comments")

                        commentRef.get().addOnSuccessListener { snapshot ->
                            val existingComments = snapshot.children.mapNotNull {
                                it.getValue(CommentModel::class.java)
                            }.toMutableList()

                            val newComment = CommentModel(
                                id = existingComments.size.toString(),
                                comment = comment,
                                uid = userId,
                                rating = rating,
                                name = UserModel.name
                            )

                            existingComments.add(newComment)
                            commentRef.setValue(existingComments)
                        }
                    },
                    enabled = rating > 0,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD7725D),
                        contentColor = Color.White
                    )
                ) {
                    Text("Soumettre")
                }
            }
        }
    }
}
