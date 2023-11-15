package com.example.todoappcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoappcompose.ui.NavigationBarComposable
import com.example.todoappcompose.ui.about_me.AboutScreen
import com.example.todoappcompose.ui.add_edit_todo.AddEditScreen
import com.example.todoappcompose.ui.theme.TodoAppComposeTheme
import com.example.todoappcompose.ui.todo_list.TodoListScreen
import com.example.todoappcompose.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TodoAppComposeTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { NavigationBarComposable(navController = navController) }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Routes.TODO_LIST
                    ) {
                        composable(Routes.TODO_LIST) {
                            TodoListScreen(
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                        composable(
                            route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
                            arguments = listOf(
                                navArgument(name = "todoId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            AddEditScreen(onPopBackStack = {
                                navController.popBackStack()
                            })
                        }
                        composable(Routes.ABOUT_ME) {
                            AboutScreen()
                        }
                    }
                }
            }
        }
    }
}

