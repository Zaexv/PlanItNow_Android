package com.planitnow.usecases.viewfriends

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.transform.CircleCropTransformation
import com.planitnow.databinding.CardFriendBinding
import coil.api.load
import com.planitnow.R


class FriendsAdapter(var friendsViewModel: FriendsViewModel) :
    RecyclerView.Adapter<FriendsAdapter.ViewHolder>(){

    class ViewHolder(val binding: CardFriendBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friendsViewModel.myFriends.get(position)
        holder.binding.friendPUsernameTextView.text = friend.publicUsername
        holder.binding.friendUsernameTextView.text = "@" + friend.user.username
        holder.binding.friendBiography.text = friend.biography
        holder.binding.friendProfilePictureImageView.load(friend.urlProfilePicture){
            placeholder(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }
        holder.binding.root.setOnClickListener(){
            Toast.makeText(holder.itemView.context,"Clicked friend!:) $friend",Toast.LENGTH_LONG).show()
        }

    }

    override fun getItemCount(): Int {
        return friendsViewModel.myFriends.size
    }


}