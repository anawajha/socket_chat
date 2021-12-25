package com.anawajha.babble.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.DateUtils
import android.widget.Toast
import com.anawajha.babble.databinding.ActivityChatBinding
import com.anawajha.babble.logic.socket.ImageOperations
import com.github.dhaval2404.imagepicker.ImagePicker
import java.text.SimpleDateFormat
import java.util.*


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
    }// onCreate

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Activity.RESULT_OK){

            val bitmap:Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,data?.data)
            ImageOperations.getBitmapAsByteArray(bitmap)

        }else if(requestCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this,"Something went error",Toast.LENGTH_SHORT).show()
        }
    }// onActivityResult
}// class