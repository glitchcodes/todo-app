package com.example.todoappcompose.ui.add_edit_todo

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoappcompose.ui.theme.Blue50
import com.example.todoappcompose.ui.theme.GrandHotel
import com.example.todoappcompose.ui.theme.MainBG
import com.example.todoappcompose.ui.theme.Purple100
import com.example.todoappcompose.util.UIEvent
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var isDatePickerOpen by remember {
        mutableStateOf(false)
    }

    val timeInMilli = Instant.now().toEpochMilli()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = timeInMilli
    )

    var selectedDate by remember {
        mutableLongStateOf(timeInMilli)
    }

    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.ROOT)


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.PopBackStack -> onPopBackStack()
                is UIEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditTodoEvent.OnSaveTodoClick)
                },
                containerColor = Purple100,
                modifier = Modifier.padding(bottom = 78.dp)
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save", tint = Color.White)
            }
        },
        content = {
            Surface(
                color = MainBG,
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column {

                        Spacer(modifier = Modifier.height(64.dp))

                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text(
                                text = when {
                                    viewModel.id.isBlank() -> "Create a Todo"
                                    else -> "Edit Todo"
                                },
                                fontFamily = GrandHotel,
                                fontSize = 64.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Blue50
                            )
                        }

                        Spacer(modifier = Modifier.height(120.dp))

                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {

                            OutlinedTextField(
                                value = viewModel.title,
                                onValueChange = {
                                    viewModel.onEvent(AddEditTodoEvent.OnTitleChange(it))
                                },
                                label = {
                                    Text(text = "Title")
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = viewModel.description,
                                onValueChange = {
                                    viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(it))
                                },
                                label = {
                                    Text(text = "Description")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = false,
                                minLines = 5
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = formatter.format(Date(selectedDate)),
                                label = {
                                    Text(text = "Deadline")
                                },
                                onValueChange = {},
                                readOnly = true,
                                interactionSource = remember { MutableInteractionSource() }
                                    .also { interactionSource ->
                                        LaunchedEffect(interactionSource) {
                                            interactionSource.interactions.collect {
                                                if (it is PressInteraction.Release) {
                                                    isDatePickerOpen = true
                                                }
                                            }
                                        }
                                    },
                                modifier = Modifier.fillMaxWidth()
                            )

                            if (isDatePickerOpen) {
                                DatePickerDialog(
                                    onDismissRequest = { isDatePickerOpen = false },
                                    confirmButton = {
                                        Button(onClick = {
                                            isDatePickerOpen = false
                                            selectedDate = datePickerState.selectedDateMillis!!

                                            viewModel.onEvent(AddEditTodoEvent.OnDateChanged(selectedDate))
                                        }) {
                                            Text(text = "Confirm")
                                        }
                                    },
                                    dismissButton = {
                                        Button(onClick = {
                                            isDatePickerOpen = false
                                        }) {
                                            Text(text = "Cancel")
                                        }
                                    }
                                ) {
                                    DatePicker(
                                        state = datePickerState
                                    )
                                }
                            }

//                            Text("Selected: ${selectedDate ?: "no selection"}")

//                            DateTimePicker(
//                                dateTimeMillis = selectedTime,
//                                onDateTimeChanged = { selectedTime = it },
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(bottom = 16.dp)
//                            )
                        }
                    }
                }
            }
        }
    )
}