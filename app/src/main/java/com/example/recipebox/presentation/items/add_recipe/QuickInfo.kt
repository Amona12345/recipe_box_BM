package com.example.recipebox.presentation.items.add_recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.recipebox.ui.theme.RecipeColors
import com.example.recipebox.ui.theme.RecipeTypography
import compose.icons.FeatherIcons
import compose.icons.feathericons.Clock
import compose.icons.feathericons.Info
import compose.icons.feathericons.Users
import compose.icons.feathericons.Zap

@Composable
fun QuickInfoRow(
    servings: Int,
    prepTime: String,
    cookTime: String,
    calories: String,
    onServingsChange: (Int) -> Unit,
    onPrepTimeChange: (String) -> Unit,
    onCookTimeChange: (String) -> Unit,
    onCaloriesChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = RecipeColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = FeatherIcons.Info,
                    contentDescription = null,
                    tint = RecipeColors.Primary,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Recipe Details",
                    style = RecipeTypography.titleMedium.copy(
                        color = RecipeColors.Primary,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.height(160.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    QuickInfoItem(
                        icon = FeatherIcons.Users,
                        label = "Servings",
                        value = servings.toString(),
                        onValueChange = { onServingsChange(it.toIntOrNull() ?: 1) },
                        placeholder = "4",
                        isNumeric = true
                    )
                }

                item {
                    QuickInfoItem(
                        icon = FeatherIcons.Clock,
                        label = "Prep Time",
                        value = prepTime,
                        onValueChange = onPrepTimeChange,
                        placeholder = "15 min"
                    )
                }

                item {
                    QuickInfoItem(
                        icon = FeatherIcons.Clock,
                        label = "Cook Time",
                        value = cookTime,
                        onValueChange = onCookTimeChange,
                        placeholder = "30 min"
                    )
                }

                item {
                    QuickInfoItem(
                        icon = FeatherIcons.Zap,
                        label = "Calories",
                        value = calories,
                        onValueChange = onCaloriesChange,
                        placeholder = "250",
                        isNumeric = true
                    )
                }
            }
        }
    }
}
