package com.example.todoappcompose.ui.add_edit_todo

sealed class AddEditTodoEvent {
    data class OnTitleChange(val title: String): AddEditTodoEvent()
    data class OnDescriptionChange(val description: String): AddEditTodoEvent()
    data class OnDateChanged(val selectedDate: Long): AddEditTodoEvent()
    data object OnSaveTodoClick: AddEditTodoEvent()
}
