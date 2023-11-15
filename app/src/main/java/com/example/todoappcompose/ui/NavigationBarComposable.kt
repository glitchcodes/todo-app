package com.example.todoappcompose.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todoappcompose.util.Routes

@Composable
fun NavigationBarComposable(
    navController: NavHostController
) {
    var currentScreen by remember { mutableStateOf(Routes.TODO_LIST) }

    NavigationBar(
//        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text(text = "Home") },
            selected = currentScreen == Routes.TODO_LIST,
            onClick = {
                currentScreen = Routes.TODO_LIST
                navController.navigate(Routes.TODO_LIST)
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Add, contentDescription = "New Todo") },
            label = { Text(text = "New Todo") },
            selected = currentScreen == Routes.ADD_EDIT_TODO,
            onClick = {
                currentScreen = Routes.ADD_EDIT_TODO
                navController.navigate(Routes.ADD_EDIT_TODO)
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "About") },
            label = { Text(text = "About") },
            selected = currentScreen == Routes.ABOUT_ME,
            onClick = {
                currentScreen = Routes.ABOUT_ME
                navController.navigate(Routes.ABOUT_ME)
            }
        )
    }
}

//@Composable
//fun NavigationBarItem(
//    navController: NavHostController,
//    route: String,
//    icon: ImageVector,
//    label: String
//) {
//    IconButton(onClick = { navController.navigate(route) }) {
//        Icon(imageVector = icon, contentDescription = label)
//    }
//}

@Preview(
    showBackground = false
)
@Composable
fun BottomAppBarPreview() {
    NavigationBarComposable(rememberNavController())
}