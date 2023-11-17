package com.example.todoappcompose.ui.add_edit_todo

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

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
                            TextField(
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
                            TextField(
                                value = viewModel.description,
                                onValueChange = {
                                    viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(it))
                                },
                                label = {
                                    Text(text = "Description")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = false,
                                maxLines = 5
                            )
                        }
                    }
                }
            }
        }
    )
}