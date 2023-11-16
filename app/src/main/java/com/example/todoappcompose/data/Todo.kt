package com.example.todoappcompose.data

data class Todo(
    val id: String = "",
    val title: String = "",
    val description: String? = "",
    val isDone: Boolean = false
)


