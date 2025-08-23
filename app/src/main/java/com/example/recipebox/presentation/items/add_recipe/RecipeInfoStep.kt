package com.example.recipebox.presentation.items.add_recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipebox.core.enums.DifficultyEnum
import com.example.recipebox.core.ui_states.AddRecipeUiState
import compose.icons.FeatherIcons
import compose.icons.feathericons.Edit3
import compose.icons.feathericons.FileText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeInfoStep(
    uiState: AddRecipeUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onServingsChange: (Int) -> Unit,
    onPrepTimeChange: (String) -> Unit,
    onCookTimeChange: (String) -> Unit,
    onDifficultyChange: (DifficultyEnum) -> Unit,
    onDishTypeChange: (String) -> Unit,
    onCuisineChange: (String) -> Unit,
    onCaloriesChange: (String) -> Unit,
    onDietaryTagToggle: (String) -> Unit,
    onTagAdd: (String) -> Unit,
    onTagRemove: (String) -> Unit,
    onImagePickerShow: () -> Unit,
    onImageRemove: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            ImageUploadCard(
                imageUri = uiState.imageUri,
                onImageClick = onImagePickerShow,
                onImageRemove = onImageRemove
            )
        }

        // Recipe Title
        item {
            TextFieldCust(
                value = uiState.title,
                onValueChange = onTitleChange,
                label = "Recipe Name",
                placeholder = "What's your amazing recipe?",
                leadingIcon = FeatherIcons.Edit3,
                isRequired = true
            )
        }

        item {
            TextFieldCust(
                value = uiState.description,
                onValueChange = onDescriptionChange,
                label = "Description",
                placeholder = "Tell us about your recipe...",
                leadingIcon = FeatherIcons.FileText,
                minLines = 3,
                maxLines = 5
            )
        }

        item {
            QuickInfoRow(
                servings = uiState.servings,
                prepTime = uiState.prepTime,
                cookTime = uiState.cookTime,
                calories = uiState.calories,
                onServingsChange = onServingsChange,
                onPrepTimeChange = onPrepTimeChange,
                onCookTimeChange = onCookTimeChange,
                onCaloriesChange = onCaloriesChange
            )
        }

        item {
            DifficultySelector(
                selectedDifficulty = uiState.difficulty,
                onDifficultyChange = onDifficultyChange
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CuisineDropdown(
                    selectedCuisine = uiState.cuisine,
                    onCuisineChange = onCuisineChange,
                    modifier = Modifier.weight(1f)
                )

                DishTypeDropdown(
                    selectedDishType = uiState.dishType,
                    onDishTypeChange = onDishTypeChange,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            DietaryTagsSection(
                selectedTags = uiState.dietaryTags,
                onTagToggle = onDietaryTagToggle
            )
        }

        item {
            CustomTagsSection(
                tags = uiState.tags,
                onTagAdd = onTagAdd,
                onTagRemove = onTagRemove
            )
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
