package com.anawajha.babble.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.anawajha.babble.databinding.ActivityLoginBinding
import com.anawajha.babble.logic.firebase.LoginService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isLogin()

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.edEmail.text.toString()
        val password = binding.edPassword.text.toString()

        if (email.isNotEmpty() && email.contains(".") && email.contains("@")) {
            if (password.isNotEmpty()) {
              if(LoginService.login(binding.root,this, email,password)) {
                  finish()
              }
            } else {
                binding.tfPassword.error = "Password should not be empty"
            }

        } else {
            binding.tfEmail.error = "Invalid email"
        }
    }

    private fun validation(){
       binding.edEmail.doOnTextChanged { text, start, before, count ->
           if (text!!.contains(".") && text.contains("@")){
               binding.tfEmail.error = ""
           }else{
               binding.tfEmail.error = "Invalid email"
           }
       }

       binding.edPassword.doOnTextChanged { text, start, before, count ->
           if (count > 8 ){
               binding.tfPassword.error = ""
           }else{
               binding.tfPassword.error = "Password should be more than 8 character"
           }
       }
   }

    private fun isLogin(){
        val auth = Firebase.auth
        val sharedPrf = this.getSharedPreferences("login",Context.MODE_PRIVATE)
        if (sharedPrf.getBoolean("isLogin",false)){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
        }

    }

}