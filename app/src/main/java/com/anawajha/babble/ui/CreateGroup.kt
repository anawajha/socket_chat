package com.anawajha.babble.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anawajha.babble.databinding.ActivityCreateGroupBinding

class CreateGroup : AppCompatActivity() {
    private lateinit var binding:ActivityCreateGroupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}