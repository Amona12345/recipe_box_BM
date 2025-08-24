package com.example.recipebox.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.recipebox.domain.model.RecipeModel
import com.example.recipebox.presentation.items.recipe_detail.IngredientsTab
import com.example.recipebox.presentation.items.recipe_detail.OverviewTab
import com.example.recipebox.presentation.items.recipe_detail.StepsTab
import com.example.recipebox.presentation.items.recipe_detail.ActionButton
import com.example.recipebox.presentation.items.recipe_detail.CollectionSelectorModal
import com.example.recipebox.presentation.items.recipe_detail.InfoChip
import com.example.recipebox.presentation.viewmodel.CollectionViewModel
import com.example.recipebox.presentation.viewmodel.RecipeViewModel
import compose.icons.FeatherIcons
import compose.icons.feathericons.Activity
import compose.icons.feathericons.BarChart
import compose.icons.feathericons.BookOpen
import compose.icons.feathericons.Users
import compose.icons.feathericons.X

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    onNavigateBack: () -> Unit = {},
    viewModel: RecipeViewModel = hiltViewModel(),
    collectionViewModel: CollectionViewModel = hiltViewModel()
) {
    var recipe by remember { mutableStateOf<RecipeModel?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var currentTab by remember { mutableStateOf(0) }
    var showCollectionModal by remember { mutableStateOf(false) }
    val scrollState = rememberLazyListState()

    val collections by collectionViewModel.collectionsWithRecipes.collectAsState()
    val collectionUiState by collectionViewModel.uiState.collectAsState()

    val isRecipeInAnyCollection = remember(recipe, collections) {
        recipe?.let { r ->
            collections.any { collection ->
                collection.recipes.any { it.id == r.id }
            }
        } ?: false
    }

    LaunchedEffect(recipeId) {
        recipe = viewModel.getRecipeById(recipeId)
        isLoading = false
    }

    if (showCollectionModal && recipe != null) {
        CollectionSelectorModal(
            recipe = recipe!!,
            collections = collections,
            onDismiss = { showCollectionModal = false },
            onAddToCollection = { collectionId ->
                collectionViewModel.addRecipeToCollection(collectionId, recipe!!.id)
            },
            onRemoveFromCollection = { collectionId ->
                collectionViewModel.removeRecipeFromCollection(collectionId, recipe!!.id)
            }
        )
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
        }
        return
    }

    if (recipe == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = FeatherIcons.X,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.error
                )
                Text(
                    "Recipe not found",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        return
    }

    val tabs = listOf("Overview", "Ingredients", "Steps")

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp) // Reduced from 300dp
                ) {
                    if (recipe!!.imageUri != null) {
                        AsyncImage(
                            model = recipe!!.imageUri,
                            contentDescription = recipe!!.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = 0.4f),
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.8f)
                                        ),
                                        startY = 0f,
                                        endY = Float.POSITIVE_INFINITY
                                    )
                                )
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                                        )
                                    )
                                )
                        ) {
                            Icon(
                                imageVector = FeatherIcons.BookOpen,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(80.dp)
                                    .align(Alignment.Center),
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 16.dp
                            ), // Reduced vertical padding
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            onClick = onNavigateBack,
                            modifier = Modifier.size(44.dp),
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                modifier = Modifier.padding(10.dp),
                                tint = Color.White
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ActionButton(
                                icon = Icons.Default.Share,
                                contentDescription = "Share",
                                onClick = { /* Share action */ }
                            )

                            ActionButton(
                                icon = if (isRecipeInAnyCollection) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (isRecipeInAnyCollection) "In collections" else "Add to collection",
                                onClick = { showCollectionModal = true },
                                tint = if (isRecipeInAnyCollection) Color.Red else Color.White
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(20.dp)
                    ) {
                        Surface(
                            modifier = Modifier.padding(bottom = 12.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = Color.Black.copy(alpha = 0.6f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFFFD700),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "4.5",
                                    color = Color.White,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Text(
                            text = recipe!!.title,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-16).dp), // Reduced offset
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 8.dp
                ) {
                    Column {
                        Column(
                            modifier = Modifier.padding(24.dp)
                        ) {
                            if (recipe!!.description.isNotEmpty()) {
                                Text(
                                    text = recipe!!.description,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    lineHeight = 24.sp
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                InfoChip(
                                    icon = FeatherIcons.Users,
                                    label = "Servings",
                                    value = "${recipe!!.servings}",
                                    modifier = Modifier.weight(1f)
                                )

                                InfoChip(
                                    icon = FeatherIcons.BarChart,
                                    label = "Level",
                                    value = recipe!!.difficulty.name.lowercase()
                                        .replaceFirstChar { it.uppercase() },
                                    modifier = Modifier.weight(1f)
                                )

                                if (recipe!!.calories != null) {
                                    InfoChip(
                                        icon = FeatherIcons.Activity,
                                        label = "Calories",
                                        value = "${recipe!!.calories}",
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            TabRow(
                                selectedTabIndex = currentTab,
                                modifier = Modifier.fillMaxWidth(),
                                containerColor = MaterialTheme.colorScheme.surface,
                                indicator = { tabPositions ->
                                    if (currentTab < tabPositions.size) {
                                        TabRowDefaults.SecondaryIndicator(
                                            modifier = Modifier
                                                .tabIndicatorOffset(tabPositions[currentTab])
                                                .height(3.dp),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            ) {
                                tabs.forEachIndexed { index, title ->
                                    Tab(
                                        selected = currentTab == index,
                                        onClick = { currentTab = index },
                                        modifier = Modifier.padding(vertical = 16.dp)
                                    ) {
                                        Text(
                                            text = title,
                                            fontWeight = if (currentTab == index) FontWeight.Bold else FontWeight.Medium,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                }
                            }

                            Box(modifier = Modifier.padding(top = 24.dp)) {
                                when (currentTab) {
                                    0 -> OverviewTab(recipe!!)
                                    1 -> IngredientsTab(recipe!!)
                                    2 -> StepsTab(recipe!!)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
