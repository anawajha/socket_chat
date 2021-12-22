package com.anawajha.babble.logic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anawajha.babble.R
import com.anawajha.babble.databinding.PeopleGroupItemBinding
import com.anawajha.babble.logic.model.User
import com.squareup.picasso.Picasso

data class UserAddedAdapter(var activity: Context, var users:ArrayList<User>):RecyclerView.Adapter<UserAddedAdapter.UserAddedViewHolder>() {

        class UserAddedViewHolder(binding: PeopleGroupItemBinding):RecyclerView.ViewHolder(binding.root) {
            var userName = binding.tvUserName
            var userImage = binding.imgAddedUser
            var imgCancel = binding.imgCancel
    }


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAddedViewHolder {
        val binding = PeopleGroupItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserAddedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAddedViewHolder, position: Int) {
        holder.userName.text = users[position].name
       Picasso.get().load(users[position].image).placeholder(R.drawable.ic_user).into(holder.userImage)

        holder.imgCancel.setOnClickListener {
            users.removeAt(position)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
}