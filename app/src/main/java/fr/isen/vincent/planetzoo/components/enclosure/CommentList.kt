package fr.isen.vincent.planetzoo.components.enclosure

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fr.isen.vincent.planetzoo.data.CommentModel
import fr.isen.vincent.planetzoo.data.EnclosureModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import fr.isen.vincent.planetzoo.data.UserModel
import fr.isen.vincent.planetzoo.utils.comments.deleteComment

@Composable
fun CommentList(commentsList: List<CommentModel>, textColor: Color, enclosure: EnclosureModel) {
    val database = FirebaseDatabase.getInstance().reference

    Text(
        text = "Commentaires :",
        style = MaterialTheme.typography.headlineSmall,
        color = Color(0xFFD7725D)
    )
    Spacer(modifier = Modifier.height(10.dp))

    if (commentsList.isEmpty()) {
        Text(
            text = "Aucun commentaire",
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp)
        ) {
            items(commentsList) { commentItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column (modifier = Modifier.weight(1f)){
                        Row{
                            Text(
                                text = "⭐ ${commentItem.rating}/5 - ${commentItem.name} " ,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                        }
                        Row{
                            Text(
                                text = "${commentItem.comment}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = textColor
                            )
                        }

                    }



                    if (UserModel.isAdmin) {
                        IconButton(onClick = {
                            commentItem.id?.let { commentId ->
                                deleteComment(database, enclosure, commentId)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Supprimer",
                                tint = Color.Red
                            )
                        }
                    }
                }
                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}
