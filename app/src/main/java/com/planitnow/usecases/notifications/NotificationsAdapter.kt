package com.planitnow.usecases.notifications

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.solver.widgets.ConstraintWidget.GONE
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.graphql.models.type.FriendshipFriendRequestRequestStatusChoices
import com.planitnow.R
import com.planitnow.databinding.CardFriendBinding
import com.planitnow.databinding.CardNotificationBinding
import kotlinx.coroutines.runBlocking

class NotificationsAdapter(var notificationsViewModel: NotificationsViewModel):
RecyclerView.Adapter<NotificationsAdapter.ViewHolder>(){

    class ViewHolder(val binding: CardNotificationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardNotificationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friendRequest = notificationsViewModel.receivedFriendRequests.get(position)
        holder.binding.notificationType.text = holder.binding.root.context.getString(R.string.friend_request)
        holder.binding.notificationDetail.text = friendRequest.fromUser.user.username + " " + holder.binding.root.context.getString(R.string.sent_friend_request)
        if(friendRequest.isAccepted || friendRequest.requestStatus == FriendshipFriendRequestRequestStatusChoices.REJECTED){
            holder.binding.layoutNotificationButtons.visibility = View.GONE
        } else {
            holder.binding.acceptNotificationButton.setOnClickListener(){
                runBlocking {
                    var ok = notificationsViewModel.acceptFriendRequest(Integer.parseInt(friendRequest.id))
                    if(ok){
                        holder.binding.layoutNotificationButtons.visibility = View.GONE
                        Toast.makeText(holder.binding.root.context,
                            R.string.success_operation,
                            Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(holder.binding.root.context,
                            R.string.error_operation,
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
            holder.binding.rejectNotificationButton.setOnClickListener(){
                runBlocking {
                    var ok = notificationsViewModel.rejectFriendRequest(Integer.parseInt(friendRequest.id))
                    if(ok){
                        holder.binding.layoutNotificationButtons.visibility = View.GONE
                        Toast.makeText(holder.binding.root.context,
                            R.string.success_operation,
                            Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(holder.binding.root.context,
                            R.string.error_operation,
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        holder.binding.notificationPictureImageView.load(friendRequest.fromUser.urlProfilePicture){
            transformations(CircleCropTransformation())
            placeholder(R.drawable.ic_people_outline_black_24dp)
        }
    }

    override fun getItemCount(): Int {
        return notificationsViewModel.receivedFriendRequests.size
    }

}