package com.example.recipebox.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipebox.domain.model.CollectionModel
import com.example.recipebox.presentation.viewmodel.CollectionViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionsScreen(
    viewModel: CollectionViewModel = hiltViewModel(),
    onCollectionClick: (Int) -> Unit
) {
    val collections by viewModel.collections.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf<Pair<Boolean, CollectionModel?>>(false to null) }
    var showDeleteDialog by remember { mutableStateOf<Pair<Boolean, CollectionModel?>>(false to null) }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "My Collections",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Collection", tint = Color.White)
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (collections.isEmpty()) {
                Text(
                    "No collections yet. Add one!",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(collections) { collection ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onCollectionClick(collection.id) },
                            elevation = CardDefaults.cardElevation(6.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    collection.name,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Row {
                                    IconButton(onClick = {
                                        showEditDialog = true to collection
                                    }) {
                                        Icon(
                                            Icons.Default.Edit,
                                            contentDescription = "Edit",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    IconButton(onClick = {
                                        showDeleteDialog = true to collection
                                    }) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    if (showAddDialog) {
        var text by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("New Collection") },
            text = {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Collection name") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (text.isNotBlank()) {
                        scope.launch { viewModel.createCollection(text) }
                        showAddDialog = false
                    }
                }) { Text("Add") }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }


    if (showEditDialog.first) {
        var text by remember { mutableStateOf(showEditDialog.second?.name ?: "") }
        AlertDialog(
            onDismissRequest = { showEditDialog = false to null },
            title = { Text("Edit Collection") },
            text = {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Collection name") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (text.isNotBlank()) {
                        scope.launch {
                            showEditDialog.second?.let {
                                viewModel.updateCollection(it.copy(name = text))
                            }
                        }
                        showEditDialog = false to null
                    }
                }) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false to null }) {
                    Text("Cancel")
                }
            }
        )
    }


    if (showDeleteDialog.first) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false to null },
            title = { Text("Delete Collection") },
            text = { Text("Are you sure you want to delete this collection?") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog.second?.let { viewModel.deleteCollection(it.id) }
                    showDeleteDialog = false to null
                }) { Text("Delete", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false to null }) {
                    Text("Cancel")
                }
            }
        )
    }
}
