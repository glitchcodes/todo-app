package com.example.todoappcompose.ui.about_me

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoappcompose.R
import com.example.todoappcompose.ui.theme.Blue50
import com.example.todoappcompose.ui.theme.IntroHead
import com.example.todoappcompose.ui.theme.MainBG
import com.example.todoappcompose.ui.theme.Orange

@Composable
fun AboutScreen() {
    Surface(
        color = MainBG,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.height(32.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "ABOUT US",
                        fontFamily = IntroHead,
                        fontSize = 50.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = Blue50
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val image = painterResource(id = R.drawable.about_me_1)
                    Image(
                        painter = image,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .background(Orange, shape = RoundedCornerShape(15.dp))
                        .padding(24.dp)
                ) {
                    Text(
                        text = "ToDue App is a sleek and user-friendly task management solution that simplifies the process of organizing daily activities. With its intuitive interface, users can swiftly create, read, edit, and delete tasks, ensuring a streamlined workflow. We provide a seamless and effective platform for individuals seeking a straightforward and powerful tool for their to-do lists.",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}