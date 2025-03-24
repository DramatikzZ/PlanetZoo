package fr.isen.vincent.planetzoo.components.enclosure

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.vincent.planetzoo.data.CommentModel
import fr.isen.vincent.planetzoo.data.EnclosureModel


@Composable
fun CommentDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    enclosure: EnclosureModel
) {
    if (showDialog) {
        val database = FirebaseDatabase.getInstance().reference
        val commentsList = remember { mutableStateListOf<CommentModel>() }

        LaunchedEffect(enclosure.firebaseId) {
            val commentRef = database.child("biomes").child(enclosure.id_biomes)
                .child("enclosures").child(enclosure.firebaseId).child("comments")

            commentRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    commentsList.clear()
                    for (commentSnapshot in snapshot.children) {
                        val comment = commentSnapshot.getValue(CommentModel::class.java)
                        comment?.let { commentsList.add(it) }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseDebug", "❌ Erreur lors du chargement des commentaires: ${error.message}")
                }
            })
        }

        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()) // Ajout du scroll
                ) {
                    Text(
                        text = "Commentaires",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    CommentList(commentsList, Color.Black, enclosure)

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(onClick = onDismiss, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Text("Fermer")
                    }
                }
            }
        }
    }
}