package com.example.todoappcompose.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [Todo::class]
)

abstract class TodoDatabase: RoomDatabase() {
    abstract val dao: TodoDAO
}