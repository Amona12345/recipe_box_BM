package com.example.recipebox.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import com.example.recipebox.R

@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF4058a0)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo1),
                contentDescription = "RecipeBox Logo",
                modifier = Modifier.fillMaxSize(0.35f)
            )
        }
    }

    LaunchedEffect(Unit) {
        delay(2000)
        onNavigateToOnboarding()
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(onNavigateToOnboarding = {})
}
