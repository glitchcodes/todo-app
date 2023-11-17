package com.example.todoappcompose.data

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.todoappcompose.worker.NotificationWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val applicationContext: Context,
    private val workManager: WorkManager
): INotificationRepository {
    override suspend fun scheduleNotification(time: Long, message: String) {
        val notificationWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(time - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .setInputData(workDataOf(NotificationWorker.NOTIFICATION_MESSAGE_KEY to message))
            .build()

        // Enqueue the work request with WorkManager
        workManager.enqueue(notificationWorkRequest)
    }

}