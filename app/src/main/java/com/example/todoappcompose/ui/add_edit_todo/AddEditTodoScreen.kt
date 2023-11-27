package com.example.todoappcompose.ui.add_edit_todo

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.TimePicker
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
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.todoappcompose.ui.TimePickerDialog
import com.example.todoappcompose.ui.theme.Blue50
import com.example.todoappcompose.ui.theme.IntroHead
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
    onNavigate: (UIEvent.Navigate) -> Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    var isDatePickerOpen by remember {
        mutableStateOf(false)
    }
    var isTimePickerOpen by remember {
        mutableStateOf(false)
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )

    val timePickerState = rememberTimePickerState(
        initialHour = 0,
        initialMinute = 0
    )

    val dateFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.ROOT)
    val timeFormatter = SimpleDateFormat("h:mm a", Locale.ROOT)


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.Navigate -> onNavigate(event)
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
                                    viewModel.id.isBlank() -> "CREATE A TODO"
                                    else -> "EDIT TODO"
                                },
                                fontFamily = IntroHead,
                                fontSize = 64.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                color = Blue50
                            )
                        }

                        Spacer(modifier = Modifier.height(64.dp))

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

                            Row {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    OutlinedTextField(
                                        value = dateFormatter.format(Date(viewModel.selectedDate)),
                                        label = {
                                            Text(text = "Date")
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
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    OutlinedTextField(
                                        value = timeFormatter.format(viewModel.selectedDate),
                                        onValueChange = {},
                                        label = {
                                            Text(text = "Time")
                                        },
                                        readOnly = true,
                                        interactionSource = remember { MutableInteractionSource() }
                                            .also { interactionSource ->
                                                LaunchedEffect(interactionSource) {
                                                    interactionSource.interactions.collect {
                                                        if (it is PressInteraction.Release) {
                                                            isTimePickerOpen = true
                                                        }
                                                    }
                                                }
                                            },
                                    )
                                }
                            }

                            // DatePicker
                            if (isDatePickerOpen) {
                                DatePickerDialog(
                                    onDismissRequest = { isDatePickerOpen = false },
                                    confirmButton = {
                                        Button(onClick = {
                                            isDatePickerOpen = false
                                            viewModel.onEvent(AddEditTodoEvent.OnDateChanged(datePickerState.selectedDateMillis!!))
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

                            if (isTimePickerOpen) {
                                TimePickerDialog(
                                    title = "Select Time",
                                    onCancel = { isTimePickerOpen = false },
                                    onConfirm = {
                                        isTimePickerOpen = false
                                        viewModel.onEvent(AddEditTodoEvent.OnTimeChanged(timePickerState.hour, timePickerState.minute))
                                    }
                                ) {
                                    TimePicker(state = timePickerState)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}