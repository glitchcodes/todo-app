package com.example.todoappcompose.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoappcompose.data.INotificationRepository
import com.example.todoappcompose.data.ITodoRepository
import com.example.todoappcompose.data.Todo
import com.example.todoappcompose.util.Routes
import com.example.todoappcompose.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoRepository: ITodoRepository,
    private val notificationRepository: INotificationRepository
): ViewModel() {
    val todos = todoRepository.getTodos()

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedTodo: Todo? = null

    fun onEvent(event: TodoListEvent) {
        when(event) {
            is TodoListEvent.OnTodoClick -> {
                // Navigate to "Add" screen but with details of the item
                sendUIEvent(UIEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }
            is TodoListEvent.OnAddTodoClick -> {
                // Navigate to "Add" screen
                sendUIEvent(UIEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvent.OnDeleteTodoClick -> {
                viewModelScope.launch {
                    // Cache the deleted item for undo process
                    deletedTodo = event.todo

                    // Cancel the scheduled notification
                    notificationRepository.cancelNotification(event.todo.notificationId)

                    // Remove the item from the database
                    todoRepository.deleteTodo(event.todo)

                    // Send a snackbar to the screen
                    sendUIEvent(UIEvent.ShowSnackbar(
                        message = "Todo deleted",
                        action = "Undo"
                    ))
                }
            }
            is TodoListEvent.OnUndoDeleteClick -> {
                // Undo the deletion
                deletedTodo?.let { todo ->
                    viewModelScope.launch {
                        todoRepository.insertTodo(todo)
                    }
                }
            }
            is TodoListEvent.OnDoneChange -> {
                // Update the status of the item
                viewModelScope.launch {
                    todoRepository.insertTodo(
                        event.todo.copy(
                            done = event.done
                        )
                    )
                }
            }
        }
    }

    private fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}