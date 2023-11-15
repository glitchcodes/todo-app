package com.example.todoappcompose.data

import kotlinx.coroutines.flow.Flow

class TodoRepository(
    private val dao: TodoDAO
): ITodoRepository {
    override suspend fun getTodoById(id: Int): Todo? {
        return dao.getTodoById(id)
    }

    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }

    override fun getTodos(): Flow<List<Todo>> {
        return dao.getTodos()
    }
}