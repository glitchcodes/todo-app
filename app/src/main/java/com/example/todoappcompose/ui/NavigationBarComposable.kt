package com.example.todoappcompose.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todoappcompose.ui.theme.Brown
import com.example.todoappcompose.ui.theme.Purple100
import com.example.todoappcompose.util.Routes

@Composable
fun NavigationBarComposable(
    navController: NavHostController,
    currentRoute: String
) {

    NavigationBar(
        containerColor = Purple100,
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White) },
            label = { Text(text = "Home", color = Color.White) },
            selected = currentRoute == Routes.TODO_LIST,
            onClick = {
                navController.navigate(Routes.TODO_LIST)
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Brown
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Add, contentDescription = "New", tint = Color.White) },
            label = { Text(text = "New", color = Color.White) },
            selected = currentRoute.contains(Routes.ADD_EDIT_TODO),
            onClick = {
                navController.navigate(Routes.ADD_EDIT_TODO)
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Brown
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "About", tint = Color.White) },
            label = { Text(text = "About", color = Color.White) },
            selected = currentRoute == Routes.ABOUT_ME,
            onClick = {
                navController.navigate(Routes.ABOUT_ME)
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Brown
            )
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