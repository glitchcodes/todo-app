package com.example.todoappcompose.ui.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todoappcompose.R
import com.example.todoappcompose.ui.theme.Blue50
import com.example.todoappcompose.ui.theme.PetitCochon
import com.example.todoappcompose.util.Routes

@Composable
fun OnboardingScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.onboarding),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.height(84.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ToDue",
                        fontFamily = PetitCochon,
                        fontSize = 74.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        textAlign = TextAlign.Center,
                        color = Blue50
                    )
                }

                Spacer(modifier = Modifier.height(64.dp))
                
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Routes.TODO_LIST)
                        }
                    ) {
                        Text(text = "MY TODO LIST")
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            navController.navigate(Routes.ADD_EDIT_TODO)
                        }
                    ) {
                        Text(text = "CREATE A TODO")
                    }
                }
            }
        }
    }
}