package com.example.todoappcompose.di

import android.app.Application
import androidx.room.Room
import com.example.todoappcompose.data.ITodoRepository
import com.example.todoappcompose.data.TodoDatabase
import com.example.todoappcompose.data.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): ITodoRepository {
        return TodoRepository(db.dao)
    }
}