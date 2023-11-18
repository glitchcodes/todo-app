package com.example.todoappcompose.data

interface INotificationRepository {
    suspend fun scheduleNotification(time: Long, message: String): String
    suspend fun cancelNotification(notificationId: String)
}