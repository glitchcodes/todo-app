package com.example.todoappcompose.di

import android.content.Context
import androidx.work.WorkManager
import com.example.todoappcompose.data.INotificationRepository
import com.example.todoappcompose.data.ITodoRepository
import com.example.todoappcompose.data.NotificationRepository
import com.example.todoappcompose.data.TodoRepository
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideNotificationRepository(
        @ApplicationContext applicationContext: Context,
        workManager: WorkManager
    ): INotificationRepository {
        return NotificationRepository(applicationContext, workManager)
    }

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext appContext: Context): WorkManager {
        return WorkManager.getInstance(appContext)
    }
}