package fr.isen.vincent.planetzoo.utils

import com.google.firebase.database.*
import fr.isen.vincent.planetzoo.data.ZooModel

class FirebaseHelper {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun fetchZooData(onResult: (List<ZooModel>) -> Unit) {
        val zooRef = database//.child("zoos") // Adaptez le chemin selon votre base Firebase

        zooRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val zooList = mutableListOf<ZooModel>()
                snapshot.children.forEach { zooSnapshot ->
                    val zoo = zooSnapshot.getValue(ZooModel::class.java)
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


fun addZoo(zoo: ZooModel) {
    val database = FirebaseDatabase.getInstance().reference
    val newZooRef = database//.child("zoos").push()
    newZooRef.setValue(zoo)
        .addOnSuccessListener {
            println("Données ajoutées avec succès")
        }
        .addOnFailureListener { e ->
            println("Erreur lors de l'ajout: ${e.message}")
        }
}
