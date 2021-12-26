package com.anawajha.babble.logic.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anawajha.babble.R
import com.anawajha.babble.databinding.ChatItemBinding
import com.anawajha.babble.logic.model.User
import com.anawajha.babble.shared.Constants
import com.anawajha.babble.ui.Chat
import com.squareup.picasso.Picasso

class UserAdapter(var activity: Activity,var users:ArrayList<User>):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(binding:ChatItemBinding):RecyclerView.ViewHolder(binding.root) {
        var image = binding.imgUser
        var userName = binding.tvUserName
        var lastMessage  = binding.tvLastMessage
        var status = binding.tvStatus
        var messageTime = binding.tvTime
        var card = binding.cvUserChat
    }// UserViewHolder class

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }// onCreateViewHolder

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        Picasso.get().load(users[position].image).placeholder(R.drawable.ic_user).into(holder.image)
        holder.userName.text = users[position].name

        holder.card.setOnClickListener {
            val user = users[position]
            val i = Intent(activity,Chat::class.java)
            i.putExtra(Constants.DESTINATION_ID,user.id)
            i.putExtra("name",user.name)
            i.putExtra("email",user.email)
            i.putExtra("image",user.image)
            activity.startActivity(i)
        }
    }// onBindViewHolder

    override fun getItemCount(): Int {
        return users.size
    }// getItemCount


}// UserAdapter class