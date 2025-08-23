package com.example.recipebox.presentation.items.add_recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.core.ui_states.AddRecipeUiState

@Composable
fun ReviewStep(
    uiState: AddRecipeUiState,
    onSave: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text(
                text = "Review Your Recipe",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5A67D8)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Recipe Name",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5A67D8)
                    )
                    Text(
                        text = uiState.title.ifBlank { "Untitled Recipe" },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Recipe Details",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5A67D8)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        DetailItem("Servings", "${uiState.servings} people")
                        DetailItem("Difficulty", uiState.difficulty.name.lowercase().replaceFirstChar { it.uppercase() })
                    }

                    if (uiState.prepTime.isNotBlank()) {
                        DetailItem("Cooking Time", uiState.prepTime)
                    }

                    if (uiState.dishType.isNotBlank()) {
                        DetailItem("Dish Type", uiState.dishType)
                    }

                    if (uiState.dietaryTags.isNotEmpty()) {
                        DetailItem("Dietary", uiState.dietaryTags.joinToString(", "))
                    }

                    if (uiState.tags.isNotEmpty()) {
                        DetailItem("Tags", uiState.tags.joinToString(" #", "#"))
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Ingredients (${uiState.ingredients.count { it.name.isNotBlank() }})",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5A67D8)
                    )

                    uiState.ingredients.filter { it.name.isNotBlank() }.forEach { ingredient ->
                        Text(
                            text = "• ${ingredient.toDisplayString()}",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Cooking Steps (${uiState.steps.count { it.isNotBlank() }})",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5A67D8)
                    )

                    uiState.steps.filter { it.isNotBlank() }.forEachIndexed { index, step ->
                        Text(
                            text = "${index + 1}. $step",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        item {
            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                )
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text("Save Recipe")
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}