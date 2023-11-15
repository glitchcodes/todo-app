package com.example.todoappcompose.ui.todo_list

import com.example.todoappcompose.data.Todo

sealed class TodoListEvent {
    data class OnTodoClick(val todo: Todo) : TodoListEvent()
    data object OnAddTodoClick : TodoListEvent()

    data class OnDeleteTodoClick(val todo: Todo) : TodoListEvent()
    data object OnUndoDeleteClick : TodoListEvent()

    data class OnDoneChange(val todo: Todo, val isDone: Boolean) : TodoListEvent()

}