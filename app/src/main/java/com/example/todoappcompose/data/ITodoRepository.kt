package com.example.todoappcompose.data

import kotlinx.coroutines.flow.Flow

interface ITodoRepository {
    suspend fun getTodoById(id: Int): Todo?

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    fun getTodos(): Flow<List<Todo>>
}