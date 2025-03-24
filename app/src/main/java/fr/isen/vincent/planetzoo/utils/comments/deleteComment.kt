package fr.isen.vincent.planetzoo.utils.comments


import android.util.Log
import com.google.firebase.database.DatabaseReference
import fr.isen.vincent.planetzoo.data.EnclosureModel

fun deleteComment(database: DatabaseReference, enclosure: EnclosureModel, commentId: String) {
    val commentRef = database.child("biomes")
        .child(enclosure.id_biomes)
        .child("enclosures")
        .child(enclosure.firebaseId)
        .child("comments")
        .child(commentId)

    commentRef.removeValue().addOnSuccessListener {
        Log.d("FirebaseDebug", "✅ Commentaire supprimé : $commentId")
    }.addOnFailureListener { error ->
        Log.e("FirebaseDebug", "❌ Erreur lors de la suppression : ${error.message}")
    }
}
