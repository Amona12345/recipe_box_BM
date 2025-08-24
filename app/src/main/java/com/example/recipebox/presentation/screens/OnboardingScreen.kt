package com.example.recipebox.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.R
import kotlinx.coroutines.delay

@Composable
fun OnboardingScreen(
    onGetStartedClick: () -> Unit
) {
    var currentPage by remember { mutableIntStateOf(0) }


    LaunchedEffect(Unit) {
        repeat(2) {
            delay(3000)
            currentPage += 1
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (currentPage) {
            0 -> OnboardingPage1()
            1 -> OnboardingPage2()
            else -> OnboardingPage3()
        }

        if (currentPage >= 2) {
            Button(
                onClick = onGetStartedClick,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 150.dp)
                    .size(56.dp)
            ) {
                Text(text = "Go", color = Color(0xFF4058a0), fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Composable
fun OnboardingPage1() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_1),
                contentDescription = "1",
                modifier = Modifier.size(120.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_2),
                contentDescription = "2",
                modifier = Modifier.padding(top = 16.dp).size(120.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_3),
                contentDescription = "3",
                modifier = Modifier.padding(top = 16.dp).size(120.dp)
            )
            Box(
                modifier = Modifier.padding(top = 16.dp),
                contentAlignment = Alignment.TopCenter
            ) {

                Text(
                    text = "Your Personal Guide To Be A Chef",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun OnboardingPage2() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background2),
            contentDescription = "background2",
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic1),
                contentDescription = "5",
                modifier = Modifier.size(120.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic2),
                contentDescription = "6",
                modifier = Modifier.padding(top = 16.dp).size(120.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic3),
                contentDescription = "7",
                modifier = Modifier.padding(top = 16.dp).size(120.dp)
            )
            Box(
                modifier = Modifier.padding(top = 16.dp),
                contentAlignment = Alignment.TopCenter
            ) {

                Text(
                    text = "Share the Love, Share the Recipe",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }

        }
    }
}

@Composable
fun OnboardingPage3() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background3),
            contentDescription = "background3",
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.p31),
                contentDescription = "1",
                modifier = Modifier.size(120.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.p32),
                contentDescription = "2",
                modifier = Modifier.padding(top = 16.dp).size(120.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.p33),
                contentDescription = "3",
                modifier = Modifier.padding(top = 16.dp).size(120.dp)
            )
            Box(
                modifier = Modifier.padding(top = 16.dp),
                contentAlignment = Alignment.TopCenter
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Foodify Your Global Kitchen.",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
