package com.anawajha.babble.logic.firebase

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.edit
import com.anawajha.babble.shared.Constants
import com.anawajha.babble.shared.Helpers
import com.anawajha.babble.ui.CompleteProfile
import com.anawajha.babble.ui.MainActivity
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.lang.Exception
import java.util.*

class RegisterService {

    companion object{

        fun register(view: View,context: Context, email:String, password:String):Boolean{
            val sharedPref = context.getSharedPreferences("login",Context.MODE_PRIVATE)
            val auth = Firebase.auth
            var isSuccessful = false
    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
        if (it.isSuccessful){
            auth.currentUser.let {
                sharedPref.edit {
                    putBoolean("isLogin",true)
                }
            }
            context.startActivity(Intent(context,CompleteProfile::class.java))
            isSuccessful = true
        }
    }.addOnFailureListener {
        Helpers.showSnackBar(view,"${it.localizedMessage}")
        isSuccessful = false
    }
            return isSuccessful
        }// register function

        fun completeProfile(view: View, context: Context, name:String, userImage:Uri){
            val dialog = ProgressDialog(context).apply {
                setTitle("Uploading...")
                setCancelable(false)
                show()
            }

            val user = Firebase.auth.currentUser
            user?.let {
               val profileUpdates = userProfileChangeRequest {
                   setDisplayName(name)
               }
                uploadImage(it,userImage)

                it.updateProfile(profileUpdates).addOnCompleteListener {
                    if (it.isSuccessful){
                        dialog.dismiss()
                        context.startActivity(Intent(context,MainActivity::class.java))
                    }
                }.addOnFailureListener {
                    dialog.dismiss()
                    Helpers.showSnackBar(view,"${it.localizedMessage}")
                }
            }
        } // complete profile

        private fun uploadImage(user:FirebaseUser, uri:Uri){
             var storage: FirebaseStorage = Firebase.storage
             var reference: StorageReference= storage.reference
            reference.child("images/${user.uid}").putFile(uri).addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { url ->
                    user.let { user
                        val updates = userProfileChangeRequest {
                            photoUri = url
                            Log.d("URL",url.toString())
                        }
                        it.updateProfile(updates)
                    }
                }
            }
        }// uploadImage

    }// companion object

}