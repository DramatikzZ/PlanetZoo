package fr.isen.vincent.planetzoo.viewmodel

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import fr.isen.vincent.planetzoo.R
import fr.isen.vincent.planetzoo.data.UserModel
import fr.isen.vincent.planetzoo.utils.AppUtil

class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth

    private val firestore = Firebase.firestore

    fun login(context: Context, email: String, password: String, onResult: (Boolean, String?)-> Unit ) {

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            AppUtil.showToast(context, ContextCompat.getString(context, R.string.error_message))
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
            if(it.isSuccessful) {

                Firebase.firestore.collection("users")
                    .document(FirebaseAuth.getInstance().currentUser?.uid!!)
                    .get().addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            val result = task.result
                            if (result != null && result.exists()) {
                                UserModel.isAdmin = result.getBoolean("admin") ?: false
                                UserModel.name =  result.getString("name") ?: "Nom inconnu"

                                onResult(true, null)
                            }
                        }
                    }
            } else {
                onResult(false, it.exception?.localizedMessage)
            }
        }
    }

    fun signup(context : Context, email : String, name : String, password : String, onResult: (Boolean, String?)-> Unit ) {

        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            AppUtil.showToast(context, ContextCompat.getString(context, R.string.error_message), )
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    var userId = it.result?.user?.uid

                    val userModel = UserModel(name, email, userId!!, false, "Black")
                    firestore
                        .collection(ContextCompat.getString(context, R.string.users_collection))
                        .document(userId)
                        .set(userModel)
                        .addOnCompleteListener {
                            dbTask-> if(dbTask.isSuccessful) {
                                UserModel.name = name
                                UserModel.isAdmin = false
                                sendEmailVerification {success, message ->
                                    if (!success) {
                                        AppUtil.showToast(context, "Erreur : $message")
                                    }
                                }
                                onResult(true, null)
                            } else {
                                onResult(false, ContextCompat.getString(context, R.string.auth_image))
                            }
                        }
                }else {
                    onResult(false, it.exception?.localizedMessage)
                }
            }
    }

    fun changePassword(context: Context, email: String, onResult: (Boolean, String?)-> Unit) {
        if (email.isEmpty()) {
            onResult(false, "Veuillez entrer une adresse e-mail")
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, "Email de réinitialisation envoyé")
                    AppUtil.showToast(context, "tout est bien qui marche bien")
                } else {
                    onResult(false, task.exception?.localizedMessage ?: "Une erreur est survenue")
                }
            }
    }

    private fun sendEmailVerification(onResult: (Boolean, String?) -> Unit) {
        val user = auth.currentUser

        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, "Email de vérification envoyé. Veuillez vérifier votre boîte mail.")
                } else {
                    onResult(false, task.exception?.localizedMessage ?: "Échec de l'envoi de l'email de vérification.")
                }
            }
    }

    fun checkEmailVerification(onResult: (Boolean, String?) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser

        user?.reload()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (user.isEmailVerified) {
                    onResult(true, "✅ Email vérifié !")
                } else {
                    onResult(false, "❌ Email non vérifié. Veuillez vérifier votre boîte mail.")
                }
            } else {
                onResult(false, "⚠ Erreur lors de la vérification de l'email.")
            }
        }
    }

}