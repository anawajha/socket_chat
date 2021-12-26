package com.anawajha.babble.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.anawajha.babble.databinding.ActivityCompleteProfileBinding
import com.anawajha.babble.logic.firebase.RegisterService
import com.anawajha.babble.shared.Helpers
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.*


class CompleteProfile : AppCompatActivity(){
    private lateinit var binding:ActivityCompleteProfileBinding
    private  var imageUri: Uri? = null
    lateinit var storage: FirebaseStorage
    lateinit var reference: StorageReference
    private val user = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storage = Firebase.storage
        reference = storage.reference

        binding.btnComplete.setOnClickListener {
            completeProfile()
        }

        binding.btnAddImage.setOnClickListener {
            ImagePicker.with(this)
                .cropSquare()
                .compress(1024)
                .start()
        }
    }// onCreate

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            imageUri = data?.data!!

            binding.imgUser.setImageURI(data.data!!)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        }
    }// onActivityResult

    private fun completeProfile(){
        val name = binding.edFullName.text.toString()
        if (name.isNotEmpty() && name.length > 5){
            if (imageUri != null){
                RegisterService.completeProfile(binding.root,this,name,imageUri!!)
            }else{
                Helpers.showSnackBar(binding.root,"Please choose profile photo")
            }
        }else{
            binding.tfFullName.error = "Please Enter a valid name"
        }
    }// completeProfile



}// CompleteProfile class