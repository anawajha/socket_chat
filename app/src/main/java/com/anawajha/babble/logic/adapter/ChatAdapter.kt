package com.anawajha.babble.logic.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anawajha.babble.R
import com.anawajha.babble.databinding.ChatItemBinding
import com.anawajha.babble.logic.model.Message
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.anawajha.babble.logic.socket.ImageOperations


class ChatAdapter(var activity: Activity, var messages:ArrayList<Message>,var userId:String?):RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {
    class MessageViewHolder(binding:ChatItemBinding):RecyclerView.ViewHolder(binding.root){
        var imageMessage = binding.imgMessage
        var message = binding.tvMessage
        var container = binding.loMessageContainer
    }// MessageViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MessageViewHolder(binding)
    }// onCreateViewHolder

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        userId?.let {
            if (messages[position].source_id.equals(userId)){
                holder.container.setBackgroundResource(R.drawable.sender_item)
                holder.message.setTextColor(R.color.purple)
                holder.container.gravity = Gravity.END

                if (messages[position].message != null){
                    holder.message.text = messages[position].message!!
                    holder.message.visibility = View.VISIBLE
                    holder.imageMessage.visibility = View.GONE

                }else if(messages[position].imageMessage != null){
                    holder.imageMessage.setImageBitmap(ImageOperations.getImage(messages[position].imageMessage!!))
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