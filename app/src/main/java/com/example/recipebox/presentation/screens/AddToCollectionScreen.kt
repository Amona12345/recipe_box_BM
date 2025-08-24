package com.example.recipebox.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import com.example.recipebox.presentation.items.collection.SelectableRecipeCard
import com.example.recipebox.presentation.viewmodel.CollectionViewModel
import com.example.recipebox.presentation.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToCollectionScreen(
    collectionId: Int,
    onNavigateBack: () -> Unit = {},
    recipeViewModel: RecipeViewModel = hiltViewModel(),
    collectionViewModel: CollectionViewModel = hiltViewModel()
) {
    val allRecipes by recipeViewModel.allRecipes.collectAsState()
    val collectionsWithRecipes by collectionViewModel.collectionsWithRecipes.collectAsState()
    val uiState by collectionViewModel.uiState.collectAsState()

    val currentCollection = collectionsWithRecipes.find { it.collection.id == collectionId }
    val recipesInCollection = currentCollection?.recipes?.map { it.id } ?: emptyList()

    val availableRecipes = allRecipes.filter { recipe ->
        !recipesInCollection.contains(recipe.id)
    }

    var selectedRecipes by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredRecipes = availableRecipes.filter { recipe ->
        if (searchQuery.isEmpty()) true
        else recipe.title.contains(searchQuery, ignoreCase = true) ||
                recipe.description.contains(searchQuery, ignoreCase = true) ||
                recipe.tags.any { it.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Add to ${currentCollection?.collection?.name ?: "Collection"}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (selectedRecipes.isNotEmpty()) {
                        TextButton(
                            onClick = {
                                collectionViewModel.addRecipesToCollection(
                                    collectionId,
                                    selectedRecipes.toList()
                                )
                                onNavigateBack()
                            },
                            enabled = !uiState.isLoading
                        ) {
                            Text("Add (${selectedRecipes.size})")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search recipes...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                filteredRecipes.isEmpty() && availableRecipes.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "All recipes added!",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            text = "All your recipes are already in this collection",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                filteredRecipes.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
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
                            text = "Try adjusting your search terms",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredRecipes) { recipe ->
                            SelectableRecipeCard(
                                recipe = recipe,
                                isSelected = selectedRecipes.contains(recipe.id),
                                onSelectionChanged = { isSelected ->
                                    selectedRecipes = if (isSelected) {
                                        selectedRecipes + recipe.id
                                    } else {
                                        selectedRecipes - recipe.id
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
