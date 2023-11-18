package com.example.todoappcompose.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.todoappcompose.R

class NotificationWorker(
    context: Context,
    workerParams: WorkerParameters,
): Worker(context, workerParams) {
    companion object {
        const val NOTIFICATION_MESSAGE_KEY = "notification_message"
        const val NOTIFICATION_CHANNEL_ID = "todo_reminder"
        const val NOTIFICATION_CHANNEL_NAME = "Todo Reminder"
        const val NOTIFICATION_ID = 1
    }

    override fun doWork(): Result {
        // Retrieve notification message from input data
        val message = inputData.getString(NOTIFICATION_MESSAGE_KEY)

        // Check if the message is not null
        if (!message.isNullOrBlank()) {
            // Show the notification using NotificationManager
            showNotification(applicationContext, message)
        }

        // Indicate that the work is completed successfully
        return Result.success()
    }

    private fun showNotification(context: Context, message: String) {
        // Create a NotificationManager
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification channel for devices running Android Oreo (API 26) and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Create a notification builder
        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("ToDue Reminder")
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Show the notification
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}