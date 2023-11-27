package com.example.todoappcompose.ui.add_edit_todo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
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
import java.time.Instant
import java.util.Date
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

    var selectedDate by mutableLongStateOf(0)
        private set

    var hour by mutableIntStateOf(0)
        private set

    var minute by mutableIntStateOf(0)
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
                    selectedDate = todo.deadline

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
            is AddEditTodoEvent.OnTimeChanged -> {
                hour = event.hour
                minute = event.minute

                // Set hour and minute on selectedDate
                selectedDate = setHourAndMinute(selectedDate, hour, minute)
            }
            is AddEditTodoEvent.OnSaveTodoClick -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendUIEvent(UIEvent.ShowSnackbar(
                            message = "Title cannot be empty"
                        ))
                        return@launch
                    }

                    // Schedule a notification
                    val notificationId = notificationRepository.scheduleNotification(selectedDate, title)

                    todoRepository.insertTodo(
                        Todo(
                            id = id.ifBlank { getRandomString(10) },
                            title = title,
                            description = description,
                            done = todo?.done ?: false,
                            deadline = when {
                                selectedDate.toInt() == 0 -> Instant.now().toEpochMilli()
                                else -> selectedDate
                            },
                            notificationId = notificationId
                        )
                    )

                    sendUIEvent(UIEvent.Navigate(Routes.TODO_LIST))
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

    private fun setHourAndMinute(epoch: Long, hour: Int, minute: Int): Long {
        // Create a Date object from the epoch time
        val date = Date(epoch)

        // Set the desired hour and minute
        date.hours = hour
        date.minutes = minute

        // Convert the modified Date back to epoch time
        return date.time
    }
}