package com.example.todoappcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoappcompose.ui.NavigationBarComposable
import com.example.todoappcompose.ui.SetStatusBarColor
import com.example.todoappcompose.ui.about_me.AboutScreen
import com.example.todoappcompose.ui.add_edit_todo.AddEditScreen
import com.example.todoappcompose.ui.theme.MainBG
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
            TodoAppComposeTheme (darkTheme = false) {
                SetStatusBarColor(color = MainBG)

                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute by remember { derivedStateOf { currentBackStackEntry?.destination?.route ?: Routes.TODO_LIST } }

                Scaffold(
                    bottomBar = { NavigationBarComposable(navController = navController, currentRoute = currentRoute) }
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
                                    type = NavType.StringType
                                    defaultValue = ""
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

