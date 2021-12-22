package com.anawajha.babble.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anawajha.babble.R
import com.anawajha.babble.databinding.ActivityMainBinding
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val auth = Firebase.auth

        Picasso.get().load(auth.currentUser?.photoUrl).placeholder(R.drawable.ic_user).into(binding.imgGoToProfile)

        binding.imgGoToProfile.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }

    }
}