package com.anawajha.babble.logic.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anawajha.babble.R
import com.anawajha.babble.databinding.UserItemBinding
import com.anawajha.babble.logic.model.User
import com.squareup.picasso.Picasso

class GroupUserAdapter(var activity: Activity, var users:ArrayList<User>,var addedUsers:ArrayList<User>):RecyclerView.Adapter<GroupUserAdapter.UserViewHolder>() {
    class UserViewHolder(binding:UserItemBinding):RecyclerView.ViewHolder(binding.root) {
        var image = binding.imgUser
        var userName = binding.tvUserName
        var lastMessage  = binding.tvLastMessage
        var status = binding.tvStatus
        var messageTime = binding.tvTime
        var card = binding.cvUserChat
    }// UserViewHolder class

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }// onCreateViewHolder

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        Picasso.get().load(users[position].image).placeholder(R.drawable.ic_user).into(holder.image)
        holder.userName.text = users[position].name

        holder.card.setOnClickListener {
            val user = users[position]
            addedUsers.add(user)
            val adapter = UserAddedAdapter(activity,users)
            adapter.notifyDataSetChanged()
        }
    }// onBindViewHolder

    override fun getItemCount(): Int {
        return users.size
    }// getItemCount


}// UserAdapter class