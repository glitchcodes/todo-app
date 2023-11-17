package com.example.todoappcompose.ui.todo_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoappcompose.data.Todo
import com.example.todoappcompose.ui.theme.BebasNeue
import com.example.todoappcompose.ui.theme.LightBlueGray

@Composable
fun TodoItem(
    todo: Todo,
    onEvent: (TodoListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = LightBlueGray,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clip(shape = RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = todo.title,
                        fontFamily = BebasNeue,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                }

                todo.description?.let {
                    if (it.isNotBlank()) {
                        Spacer(modifier = Modifier.absolutePadding(
                            top = 6.dp,
                        ))
                        Text(
                            text = it,
                            fontFamily = BebasNeue,
                            fontWeight = FontWeight.Normal
                            )
                    }
                }
            }
            IconButton(onClick = {
                onEvent(TodoListEvent.OnDeleteTodoClick(todo))
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
            Checkbox(
                checked = todo.done,
                onCheckedChange = { isChecked ->
                    onEvent(TodoListEvent.OnDoneChange(todo, isChecked))
                }
            )
        }
    }
}