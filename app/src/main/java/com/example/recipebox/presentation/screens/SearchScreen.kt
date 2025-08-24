package com.example.recipebox.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipebox.presentation.items.home.AllRecipesContent
import com.example.recipebox.presentation.viewmodel.RecipeViewModel
import compose.icons.FeatherIcons
import compose.icons.feathericons.Grid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onRecipeClick: (Int) -> Unit = {},
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedTags by viewModel.selectedTags.collectAsState()
    val recipes by viewModel.recipes.collectAsState()
    val allRecipes by viewModel.allRecipes.collectAsState()

    var useGridView by remember { mutableStateOf(false) }

    val hasActiveFilters = searchQuery.isNotEmpty() || selectedTags.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Search Recipes",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = { useGridView = !useGridView }
            ) {
                Icon(
                    imageVector = if (useGridView) Icons.Default.List else FeatherIcons.Grid,
                    contentDescription = if (useGridView) "List View" else "Grid View"
                )
            }
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = viewModel::onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search recipes, ingredients...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear"
                        )
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val availableTags = viewModel.getAllAvailableTags()
        if (availableTags.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(availableTags) { tag ->
                    FilterChip(
                        selected = selectedTags.contains(tag),
                        onClick = { viewModel.onTagToggle(tag) },
                        label = { Text(tag) }
                    )
                }
            }
        }

        if (hasActiveFilters) {
            TextButton(
                onClick = viewModel::clearFilters,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text("Clear all filters")
            }
        }

        if (!hasActiveFilters) {
            AllRecipesContent(
                recipes = allRecipes,
                onRecipeClick = onRecipeClick,
                useGridView = useGridView
            )
        } else if (recipes.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "No recipes found",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "Try adjusting your search terms or tags",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            AllRecipesContent(
                recipes = recipes,
                onRecipeClick = onRecipeClick,
                useGridView = useGridView
            )
        }
    }
}