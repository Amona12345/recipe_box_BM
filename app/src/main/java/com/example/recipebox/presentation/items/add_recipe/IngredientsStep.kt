package com.example.recipebox.presentation.items.add_recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.recipebox.domain.model.IngredientModel

@Composable
fun IngredientsStep(
    ingredients: List<IngredientModel>,
    onAddIngredient: () -> Unit,
    onRemoveIngredient: (Int) -> Unit,
    onUpdateIngredient: (Int, String, String, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(ingredients) { index, ingredient ->
            IngredientCard(
                index = index,
                ingredient = ingredient,
                onRemove = { onRemoveIngredient(index) },
                onUpdate = { name, amount, unit ->
                    onUpdateIngredient(index, name, amount, unit)
                },
                canRemove = ingredients.size > 1
            )
        }

        item {
            Button(
                onClick = onAddIngredient,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5A67D8)
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Ingredient")
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}