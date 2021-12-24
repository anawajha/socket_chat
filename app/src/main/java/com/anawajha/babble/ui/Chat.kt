package com.anawajha.babble.ui

import android.app.Activity
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.anawajha.babble.R
import com.anawajha.babble.databinding.ActivityChatBinding
import com.github.dhaval2404.imagepicker.ImagePicker

class Chat : AppCompatActivity() {
    private lateinit var binding:ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCaptureImage.setOnClickListener {
            ImagePicker.with(this)
                .cameraOnly()
                .compress(1024)
                .start()
        }

        binding.btnChooseImage.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .compress(1024)
                .start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Activity.RESULT_OK){

        }else if(requestCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this,"Something went error",Toast.LENGTH_SHORT).show()
        }
    }

}