package com.example.todoappcompose.di

import com.example.todoappcompose.data.ITodoRepository
import com.example.todoappcompose.data.TodoRepository
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance("https://todo-app-61d24-default-rtdb.asia-southeast1.firebasedatabase.app")
    }

    @Provides
    @Singleton
    fun provideTodoDatabase(): FirebaseDatabase {
        return database
    }

    @Provides
    @Singleton
    fun provideTodoRepository(): ITodoRepository {
        return TodoRepository(database)
    }
}