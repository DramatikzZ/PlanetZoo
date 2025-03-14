package fr.isen.vincent.planetzoo.utils

import com.google.firebase.database.*
import fr.isen.vincent.planetzoo.data.BiomeModel

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

}


fun addZoo(zoo: BiomeModel) {
    val database = FirebaseDatabase.getInstance().reference
    val newZooRef = database

    newZooRef.setValue(zoo.copy(id = newZooRef.key ?: ""))
        .addOnSuccessListener {
            println("Données ajoutées avec succès")
        }
        .addOnFailureListener { e ->
            println("Erreur lors de l'ajout: ${e.message}")
        }
}