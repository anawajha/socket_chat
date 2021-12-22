package com.anawajha.babble.ui

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.anawajha.babble.R
import com.anawajha.babble.databinding.ActivityRegisterBinding
import com.anawajha.babble.logic.firebase.LoginService
import com.anawajha.babble.logic.firebase.RegisterService
import com.anawajha.babble.shared.Constants

class Register : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding
    private lateinit var dialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validation()

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this,Login::class.java))
        }

        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val email = binding.edEmail.text.toString()
        val password = binding.edPassword.text.toString()

        if (email.isNotEmpty() && email.contains(".") && email.contains("@")) {
            if (password.isNotEmpty() && password.length >= 8) {
                if (binding.cbAgreement.isChecked){
                   if (RegisterService.register(binding.root,this, email,password)){
                           finish()
                       }
                }else{
                    Toast.makeText(this,"Agree conditions to register",Toast.LENGTH_LONG).show()
                }

            } else {
                binding.tfPassword.error = "Password should be more than 8 character"
            }

        } else {
            binding.tfEmail.error = "Invalid email"
        }
    }

    private fun validation(){
        binding.edEmail.doOnTextChanged { text, start, before, count ->
            if (text!!.contains(".com") && text.contains("@")){
                binding.tfEmail.error = ""
            }else{
                binding.tfEmail.error = "Invalid email"
            }
        }

        binding.edPassword.doOnTextChanged { text, start, before, count ->
            if (text!!.length >= 8 ){
                binding.tfPassword.error = ""
            }else{
                binding.tfPassword.error = "Password should be more than 8 character"
            }
        }
    }



}