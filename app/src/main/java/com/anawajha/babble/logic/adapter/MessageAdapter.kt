package com.anawajha.babble.logic.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.anawajha.babble.R
import com.anawajha.babble.databinding.MessageItemBinding
import com.anawajha.babble.logic.model.Message
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.FrameLayout
import com.anawajha.babble.logic.socket.ImageOperations
import com.anawajha.babble.ui.Chat


class MessageAdapter(var activity: Activity, var messages:ArrayList<Message>):RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    class MessageViewHolder(binding:MessageItemBinding):RecyclerView.ViewHolder(binding.root){
        var imageMessage = binding.imgMessage
        var message = binding.tvMessage
        var container = binding.loMessageContainer
    }// MessageViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MessageViewHolder(binding)
    }// onCreateViewHolder

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val userId:String? = Firebase.auth.currentUser?.uid
        userId?.let {
            if (messages[position].source_id.equals(userId)){
                holder.container.setBackgroundResource(R.drawable.sender_item)
                holder.message.setTextColor(R.color.purple)
                holder.container.gravity = Gravity.END

                if (messages[position].message is String){
                    holder.message.text = messages[position].message as String
                    holder.message.visibility = View.VISIBLE
                    holder.imageMessage.visibility = View.GONE

                }else if(messages[position].message is ByteArray){
                    holder.imageMessage.setImageBitmap(ImageOperations.getImage(messages[position].message as ByteArray))
                    holder.message.visibility = View.GONE
                    holder.imageMessage.visibility = View.VISIBLE
                }

            }else{
                holder.container.setBackgroundResource(R.drawable.recevier_item)
                holder.message.setTextColor(R.color.white)
                holder.container.gravity = Gravity.START
            }
        }
    }// onBindViewHolder

    override fun getItemCount(): Int {
       return messages.size
    }// getItemCount
}// MessageAdapter