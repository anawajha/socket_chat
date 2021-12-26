package com.anawajha.babble.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.DateUtils
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.anawajha.babble.R
import com.anawajha.babble.databinding.ActivityChatBinding
import com.anawajha.babble.logic.model.Message
import com.anawajha.babble.logic.socket.ImageOperations
import com.anawajha.babble.logic.socket.SocketCreate
import com.anawajha.babble.shared.Constants
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class Chat : AppCompatActivity() {
    private lateinit var binding:ActivityChatBinding
    lateinit var app: SocketCreate
    private var mSocket: Socket? = null
    private var auth = Firebase.auth
    private var userId:String? = null
    private var destUser:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        destUser = intent.getStringExtra(Constants.DESTINATION_ID)
        Picasso.get().load(intent.getStringExtra("image")).placeholder(R.drawable.ic_user).into(binding.imgSender)
        binding.tvSenderName.text = intent.getStringExtra("name")

        auth.currentUser?.let {
            userId = it.uid
        }

        app = application as SocketCreate
        mSocket = app.getSocket();

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



        mSocket!!.on(Socket.EVENT_CONNECT_ERROR) {
            runOnUiThread {
                Log.e("EVENT_CONNECT_ERROR", "EVENT_CONNECT_ERROR: ",)
            }
        }

        mSocket!!.on(Socket.EVENT_CONNECT_TIMEOUT,  Emitter.Listener {
            runOnUiThread {
                Log.e("EVENT_CONNECT_TIMEOUT", "EVENT_CONNECT_TIMEOUT: ", )

            }
        })


        mSocket!!.on(
            Socket.EVENT_CONNECT
        ) { Log.e("onConnect", "Socket Connected!") }
        mSocket!!.on(Socket.EVENT_DISCONNECT, Emitter.Listener {
            runOnUiThread {
                Log.e("onDisconnect", "Socket onDisconnect!")

            }
        })

        mSocket!!.connect()


        binding.btnSend.setOnClickListener {
            sendTextMessage()
        }

    }// onCreate

    private fun sendTextMessage() {
        binding.edMessage.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()){
               if (userId != null && destUser != null){
                   binding.edMessage.isEnabled = true
                   mSocket!!.emit(Constants.MESSAGE, Message(userId!!,destUser!!,binding.edMessage.text.toString(),null))
               }
            }else{
                binding.edMessage.isEnabled = false
            }
        }
    }// sendMessage

    private fun sendImageMessage(image:ByteArray){
        if (userId != null && destUser != null){
            binding.edMessage.isEnabled = true
            mSocket!!.emit(Constants.MESSAGE, Message(userId!!,destUser!!,null ,image))
        }
    }// sendImageMessage

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Activity.RESULT_OK){

            val bitmap:Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,data?.data)

            sendImageMessage(ImageOperations.getBitmapAsByteArray(bitmap))

        }else if(requestCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this,"Something went error",Toast.LENGTH_SHORT).show()
        }
    }// onActivityResult
}// class