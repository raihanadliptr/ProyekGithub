package com.example.githubuserapplication.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapplication.ItemsItem
import com.example.githubuserapplication.R

class UsersAdapter(private val users: List<ItemsItem>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>()  {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo: ImageView = itemView.findViewById(R.id.img_item_photo)
        val name: TextView = itemView.findViewById(R.id.tv_item_name)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_users, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, pos: Int) {
        viewHolder.name.text = users[pos].login
        Glide.with(viewHolder.itemView.context).load(users[pos].avatarUrl).into(viewHolder.photo)
        viewHolder.itemView.setOnClickListener{
            val intentDetail = Intent(viewHolder.itemView.context, UserDetailActivity::class.java)
            intentDetail.putExtra(UserDetailActivity.username, users[viewHolder.adapterPosition].login)
            intentDetail.putExtra(UserDetailActivity.avatarURL, users[viewHolder.adapterPosition].avatarUrl)
            viewHolder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount() = users.size
}