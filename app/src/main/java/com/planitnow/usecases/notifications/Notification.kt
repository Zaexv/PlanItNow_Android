package com.planitnow.usecases.notifications

import java.util.*
enum class NotificationType {
    FRIENDREQUEST
}

data class Notification(val id: Int, val type: NotificationType, val title: String, val description:String, val imageUrl:String, val sentDate: Date, val pending: Boolean)
