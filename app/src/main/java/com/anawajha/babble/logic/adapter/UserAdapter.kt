package com.anawajha.babble.logic.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anawajha.babble.R
import com.anawajha.babble.databinding.ChatItemBinding
import com.anawajha.babble.logic.model.User
import com.squareup.picasso.Picasso

class UserAdapter(var activity: Activity,var users:ArrayList<User>):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(binding:ChatItemBinding):RecyclerView.ViewHolder(binding.root) {
        var image = binding.imgUser
        var userName = binding.tvUserName
        var lastMessage  = binding.tvLastMessage
        var status = binding.tvStatus
        var messageTime = binding.tvTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        Picasso.get().load(users[position].image).placeholder(R.drawable.ic_user).into(holder.image)
        holder.userName.text = users[position].name
    }

    override fun getItemCount(): Int {
        return users.size
    }
}