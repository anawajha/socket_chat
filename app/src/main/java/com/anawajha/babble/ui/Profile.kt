package com.anawajha.babble.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.anawajha.babble.R
import com.anawajha.babble.databinding.ActivityProfileBinding
import com.anawajha.babble.shared.Helpers
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.*


class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val user = Firebase.auth.currentUser
    private var imageUri:Uri? = null
    lateinit var storage: FirebaseStorage
    lateinit var reference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storage = Firebase.storage
        reference = storage.reference


        user?.let {
            Picasso.get().load(user.photoUrl).placeholder(R.drawable.ic_user).into(binding.imgUser)
            binding.edName.setText(user.displayName)
            binding.edEmail.setText(user.email)
            binding.edPhoneUmber.setText(user.phoneNumber)
           if (!user.isEmailVerified){
               binding.edEmail.error = "Not verified"
           }
        }

        binding.btnSave.setOnClickListener {
            updateProfile()
        }

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.imgUpdateImage.setOnClickListener {
            ImagePicker.with(this)
                .cropSquare()
                .compress(1024)
                .start()
        }
    }// onCreate

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            imageUri = data?.data!!
            binding.imgUser.setImageURI(data.data)
        }else if (resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        }
    }// onActivityResult

    private fun updateProfile(){
        val dialog = ProgressDialog(this).apply {
            setTitle("Updating...")
            show()
        }
        val name = binding.edName.text.toString()
        if (name.isNotEmpty() && name.length > 5){
           val profileUpdates = user.let {
                userProfileChangeRequest {
                    displayName = name
                    if (imageUri != null){
                        uploadImage(imageUri!!)
                    }
                }
            }
            user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                if (it.isSuccessful){
                    dialog.dismiss()
                }
            }?.addOnFailureListener {
                Helpers.showSnackBar(binding.root,"${it.localizedMessage}")
            }

        }
        user?.reload()
    }// updateProfile


    private fun uploadImage(uri:Uri){
        reference.child("images/${UUID.randomUUID()}").putFile(uri).addOnSuccessListener {
                      it.storage.downloadUrl.addOnSuccessListener { url ->
                          user.let { user
                              val updates = userProfileChangeRequest {
                                  photoUri = url
                                  Log.d("URL",url.toString())
                              }
                              it?.updateProfile(updates)
                      }
            }
        }
    }// uploadImage

}// class