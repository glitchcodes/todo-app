package com.example.todoappcompose.ui.add_edit_todo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoappcompose.data.INotificationRepository
import com.example.todoappcompose.data.ITodoRepository
import com.example.todoappcompose.data.Todo
import com.example.todoappcompose.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val todoRepository: ITodoRepository,
    private val notificationRepository: INotificationRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var todo by mutableStateOf<Todo?>(null)
        private set

    var id by mutableStateOf("")
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var selectedDate by mutableLongStateOf(Instant.now().toEpochMilli())
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<String>("todoId")

        if (todoId != null) {
            viewModelScope.launch {
                id = todoId

                todoRepository.getTodoById(todoId)?.let { todo ->
                    title = todo.title
                    description = todo.description ?: ""

                    this@AddEditTodoViewModel.todo = todo
                }
            }
        }
    }

    fun onEvent(event: AddEditTodoEvent) {
        when (event) {
            is AddEditTodoEvent.OnTitleChange -> {
                title = event.title
            }
            is AddEditTodoEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddEditTodoEvent.OnDateChanged -> {
                selectedDate = event.selectedDate
            }
            is AddEditTodoEvent.OnSaveTodoClick -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendUIEvent(UIEvent.ShowSnackbar(
                            message = "Title cannot be empty"
                        ))
                        return@launch
                    }

                    todoRepository.insertTodo(
                        Todo(
                            id = id.ifBlank { getRandomString(10) },
                            title = title,
                            description = description,
                            done = todo?.done ?: false,
                            deadline = selectedDate
                        )
                    )

                    // Schedule a notification
                    notificationRepository.scheduleNotification(selectedDate, title)

                    sendUIEvent(UIEvent.PopBackStack)
                }
            }
        }
    }

    private fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}