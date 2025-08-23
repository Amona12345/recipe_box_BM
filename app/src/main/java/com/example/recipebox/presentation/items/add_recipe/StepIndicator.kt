package com.example.recipebox.presentation.items.add_recipe

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.recipebox.ui.theme.RecipeColors
import com.example.recipebox.ui.theme.RecipeTypography
import compose.icons.FeatherIcons
import compose.icons.feathericons.Check
import compose.icons.feathericons.CheckCircle
import compose.icons.feathericons.Info
import compose.icons.feathericons.List
import compose.icons.feathericons.Navigation

@Composable
fun StepIndicator(
    currentStep: Int,
    onStepClick: (Int) -> Unit
) {
    val steps = listOf(
        Triple("Info", FeatherIcons.Info, "Recipe Information"),
        Triple("Items", FeatherIcons.List, "Ingredients"),
        Triple("Steps", FeatherIcons.Navigation, "Cooking Steps"),
        Triple("Review", FeatherIcons.CheckCircle, "Review & Save")
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            steps.forEachIndexed { index, (title, icon, description) ->
                val isActive = index == currentStep
                val isCompleted = index < currentStep
                val animatedSize by animateDpAsState(
                    targetValue = if (isActive) 48.dp else 40.dp,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                )

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(animatedSize)
                            .background(
                                brush = when {
                                    isCompleted -> Brush.linearGradient(RecipeColors.SuccessGradient)
                                    isActive -> Brush.linearGradient(RecipeColors.PrimaryGradient)
                                    else -> Brush.linearGradient(
                                        listOf(
                                            RecipeColors.OnSurfaceVariant.copy(alpha = 0.3f),
                                            RecipeColors.OnSurfaceVariant.copy(alpha = 0.2f)
                                        )
                                    )
                                },
                                shape = CircleShape
                            )
                            .clickable { onStepClick(index) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isCompleted) FeatherIcons.Check else icon,
                            contentDescription = description,
                            tint = if (isActive || isCompleted) Color.White else RecipeColors.OnSurfaceVariant,
                            modifier = Modifier.size(if (isActive) 24.dp else 20.dp)
                        )
                    }

                    Text(
                        text = title,
                        style = RecipeTypography.bodyMedium.copy(
                            fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Medium,
                            color = when {
                                isActive -> RecipeColors.Primary
                                isCompleted -> RecipeColors.Success
                                else -> RecipeColors.OnSurfaceVariant
                            }
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}