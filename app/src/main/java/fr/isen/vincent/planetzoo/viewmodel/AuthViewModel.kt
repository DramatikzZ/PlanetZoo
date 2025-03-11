package fr.isen.vincent.planetzoo.viewmodel

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import fr.isen.vincent.planetzoo.R
import fr.isen.vincent.planetzoo.data.UserModel
import fr.isen.vincent.planetzoo.utils.AppUtil

class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth

    private val firestore = Firebase.firestore

    fun login(context : Context, email: String, password: String, onResult: (Boolean, String?)-> Unit ) {

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            AppUtil.showToast(context, ContextCompat.getString(context, R.string.error_message), )
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
            if(it.isSuccessful) {
                onResult(true, null)
            } else {
                onResult(false, it.exception?.localizedMessage)
            }
        }
    }

    fun signup(context : Context, email : String, name : String, password : String, onResult: (Boolean, String?)-> Unit ) {

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            AppUtil.showToast(context, ContextCompat.getString(context, R.string.error_message), )
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    var userId = it.result?.user?.uid

                    val userModel = UserModel(name, email, userId!!, false)
                    firestore
                        .collection(ContextCompat.getString(context, R.string.users_collection))
                        .document(userId)
                        .set(userModel)
                        .addOnCompleteListener {
                            dbTask-> if(dbTask.isSuccessful) {
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
}