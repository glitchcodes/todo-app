package com.example.todoappcompose.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todoappcompose.util.Routes

@Composable
fun NavigationBarComposable(
    navController: NavHostController,
    currentRoute: String
) {

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text(text = "Home") },
            selected = currentRoute == Routes.TODO_LIST,
            onClick = {
                navController.navigate(Routes.TODO_LIST)
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Add, contentDescription = "New Todo") },
            label = { Text(text = "New Todo") },
            selected = currentRoute.contains(Routes.ADD_EDIT_TODO),
            onClick = {
                navController.navigate(Routes.ADD_EDIT_TODO)
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "About") },
            label = { Text(text = "About") },
            selected = currentRoute == Routes.ABOUT_ME,
            onClick = {
                navController.navigate(Routes.ABOUT_ME)
            }
        )
    }
}

@Preview(
    showBackground = false
)
@Composable
fun BottomAppBarPreview() {
    NavigationBarComposable(rememberNavController(), Routes.TODO_LIST)
}