package com.example.todoappcompose.data

data class Todo(
    val id: String = "",
    val title: String = "",
    val description: String? = "",
    val done: Boolean = false,
    val deadline: Long = 0,
    val notificationId: String = ""
)


