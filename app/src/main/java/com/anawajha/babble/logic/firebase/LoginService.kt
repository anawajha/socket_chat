package com.anawajha.babble.logic.firebase

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.edit
import com.anawajha.babble.shared.Helpers
import com.anawajha.babble.ui.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginService {

    companion object{
        fun login(view:View,context: Context, email:String, password:String):Boolean{
            val sharedPref = context.getSharedPreferences("login",Context.MODE_PRIVATE)
            var auth = Firebase.auth
            var isSuccessful = false
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    sharedPref.edit {
                        putBoolean("isLogin",true)
                    }
                    isSuccessful = true
                   context.startActivity(Intent(context,MainActivity::class.java))
                }//else {
//                    Helpers.showSnakBar(view,"Incorrect email or password")
//                }
            }.addOnFailureListener {
                Helpers.showSnackBar(view,"${it.localizedMessage}")
                isSuccessful = false
            }
            return isSuccessful
            }// login function

        }// companion object
}