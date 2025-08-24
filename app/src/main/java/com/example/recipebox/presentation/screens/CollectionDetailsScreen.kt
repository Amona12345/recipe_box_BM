package com.example.recipebox.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipebox.presentation.items.home.AllRecipesContent
import com.example.recipebox.presentation.viewmodel.CollectionViewModel
import compose.icons.FeatherIcons
import compose.icons.feathericons.Grid

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CollectionDetailsScreen(
    collectionId: Int,
    onBackClick: () -> Unit,
    onAddRecipeToCollection: (Int) -> Unit = {},
    viewModel: CollectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val collectionsWithRecipes by viewModel.collectionsWithRecipes.collectAsState()
    var useGridView by remember { mutableStateOf(true) }

    val collection = collectionsWithRecipes.find { it.collection.id == collectionId }

    var showDeleteDialog by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = collection?.collection?.name ?: "Collection",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { useGridView = !useGridView }) {
                        Icon(
                            imageVector = if (useGridView) Icons.Default.List else FeatherIcons.Grid,
                            contentDescription = if (useGridView) "List View" else "Grid View"
                        )
                    }
                    IconButton(onClick = { onAddRecipeToCollection(collectionId) }) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Recipe"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddRecipeToCollection(collectionId) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Recipe", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                when {
                    collection == null -> {
                        Text(
                            text = "Collection not found",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    collection.recipes.isEmpty() && !uiState.isLoading -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No recipes in this collection yet",
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Add your first recipe to get started!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { onAddRecipeToCollection(collectionId) },
                                enabled = !uiState.isLoading
                            ) {
                                Text("Add Recipe")
                            }
                        }
                    }

                    else -> {
                        AllRecipesContent(
                            recipes = collection.recipes,
                            onRecipeClick = { /* Could implement viewing recipe details */ },
                            useGridView = useGridView
                        )
                    }
                }
            }

            uiState.successMessage?.let { successMessage ->
                LaunchedEffect(successMessage) {
                    kotlinx.coroutines.delay(3000)
                    viewModel.clearSuccess()
                }

                Snackbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    action = {
                        TextButton(onClick = { viewModel.clearSuccess() }) {
                            Text("OK")
                        }
                    }
                ) {
                    Text(successMessage)
                }
            }

            uiState.errorMessage?.let { errorMessage ->
                Snackbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    action = {
                        TextButton(onClick = { viewModel.clearError() }) {
                            Text("Dismiss")
                        }
                    }
                ) {
                    Text(errorMessage)
                }
            }
        }

        showDeleteDialog?.let { recipeId ->
            val recipeToDelete = collection?.recipes?.find { it.id == recipeId }
            AlertDialog(
                onDismissRequest = { showDeleteDialog = null },
                title = { Text("Remove Recipe") },
                text = {
                    Text("Are you sure you want to remove \"${recipeToDelete?.title ?: "this recipe"}\" from the collection?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.removeRecipeFromCollection(collectionId, recipeId)
                            showDeleteDialog = null
                        },
                        enabled = !uiState.isLoading
                    ) {
                        Text("Remove", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDeleteDialog = null },
                        enabled = !uiState.isLoading
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}