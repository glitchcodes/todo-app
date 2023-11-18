package com.example.todoappcompose.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant

data class Todo @RequiresApi(Build.VERSION_CODES.O) constructor(
    val id: String = "",
    val title: String = "",
    val description: String? = "",
    val done: Boolean = false,
    val deadline: Long = Instant.now().toEpochMilli(),
    val notificationId: String = ""
)


