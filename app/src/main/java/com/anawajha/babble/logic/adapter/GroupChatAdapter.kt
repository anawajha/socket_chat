package com.anawajha.babble.logic.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anawajha.babble.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.anawajha.babble.databinding.ChatGroupItemBinding
import com.anawajha.babble.logic.model.GroupMessage
import com.anawajha.babble.logic.socket.ImageOperations


class GroupChatAdapter(var activity: Activity, var messages:ArrayList<GroupMessage>):RecyclerView.Adapter<GroupChatAdapter.MessageViewHolder>() {
    class MessageViewHolder(binding:ChatGroupItemBinding):RecyclerView.ViewHolder(binding.root){
        var imageMessage = binding.imgMessage
        var message = binding.tvMessage
        var container = binding.loMessageContainer
        var senderName = binding.tvSenderName
    }// MessageViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ChatGroupItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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
                holder.senderName.visibility = View.GONE


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
                holder.senderName.text = messages[position].source_name
                holder.senderName.visibility = View.VISIBLE
            }
        }
    }// onBindViewHolder

    override fun getItemCount(): Int {
       return messages.size
    }// getItemCount
}// MessageAdapter