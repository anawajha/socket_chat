package com.anawajha.babble.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anawajha.babble.R
import com.anawajha.babble.databinding.ActivityChatBinding

class Chat : AppCompatActivity() {
    private lateinit var binding:ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}