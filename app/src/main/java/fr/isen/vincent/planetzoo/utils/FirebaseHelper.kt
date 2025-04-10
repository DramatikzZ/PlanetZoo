package fr.isen.vincent.planetzoo.utils

import com.google.firebase.database.*
import fr.isen.vincent.planetzoo.data.BiomeModel
import fr.isen.vincent.planetzoo.data.CommentModel

class FirebaseHelper {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun fetchZooData(onResult: (List<BiomeModel>) -> Unit) {
        val zooRef = database.child("biomes")

        zooRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val zooList = mutableListOf<BiomeModel>()
                snapshot.children.forEach { zooSnapshot ->
                    val id = zooSnapshot.key ?: ""
                    val zoo = zooSnapshot.getValue(BiomeModel::class.java)?.copy(id = id)

                    zoo?.let { zooList.add(it) }
                }
                onResult(zooList)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Erreur Firebase: ${error.message}")
            }
        })
    }

    fun fetchUserComments(
        userId: String,
        onResult: (List<Triple<String, String, CommentModel>>) -> Unit
    ) {
        val resultList = mutableListOf<Triple<String, String, CommentModel>>()
        val biomesRef = database.child("biomes")

        biomesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { biomeSnapshot ->
                    val biomeId = biomeSnapshot.key ?: return@forEach
                    val enclosuresSnapshot = biomeSnapshot.child("enclosures")
                    enclosuresSnapshot.children.forEach { enclosureSnapshot ->
                        val enclosureId = enclosureSnapshot.key ?: return@forEach
                        val commentsSnapshot = enclosureSnapshot.child("comments")
                        commentsSnapshot.children.forEach { commentSnap ->
                            val comment = commentSnap.getValue(CommentModel::class.java)
                            if (comment?.uid == userId) {
                                resultList.add(Triple(biomeId, enclosureId, comment))
                            }
                        }
                    }
                }
                onResult(resultList)
            }

            override fun onCancelled(error: DatabaseError) {
                println("❌ Firebase error while loading user comments: ${error.message}")
            }
        })
    }


}


