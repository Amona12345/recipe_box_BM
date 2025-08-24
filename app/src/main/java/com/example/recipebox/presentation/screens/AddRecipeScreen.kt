package com.example.recipebox.presentation.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.recipebox.presentation.items.add_recipe.IngredientsStep
import com.example.recipebox.presentation.items.add_recipe.NavigationBar
import com.example.recipebox.presentation.items.add_recipe.RecipeInfoStep
import com.example.recipebox.presentation.items.add_recipe.ReviewStep
import com.example.recipebox.presentation.items.add_recipe.StepIndicator
import com.example.recipebox.presentation.items.add_recipe.StepsStep
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipebox.core.handelers.CameraHandler
import com.example.recipebox.presentation.items.add_recipe.ImagePickerDialog
import com.example.recipebox.presentation.viewmodel.AddRecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    modifier: Modifier = Modifier,
    collectionId: Int? = null, // Optional collection ID
    onNavigateBack: () -> Unit,
    viewModel: AddRecipeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(collectionId) {
        collectionId?.let { id ->
            viewModel.setTargetCollectionId(id)
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val cameraHandler = remember {
        CameraHandler(
            context = context,
            onImageCaptured = { uri -> viewModel.updateImageFromCamera(uri) },
            onError = { message -> viewModel.showError(message) }
        )
    }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success -> cameraHandler.handleCameraResult(success) }

    val cameraActivityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result -> cameraHandler.handleCameraActivityResult(result) }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            try {
                cameraHandler.launchCameraMethodOne(takePictureLauncher)
            } catch (e: Exception) {
                Log.w("CameraDebug", "Method 1 failed, trying Method 2", e)
                cameraHandler.launchCameraMethodTwo(cameraActivityLauncher)
            }
        } else {
            viewModel.hideImagePickerDialog()
            viewModel.showError("Camera permission is required to take photos")
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.updateImageFromGallery(it)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraHandler.cleanup()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "New Recipe",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    TextButton(onClick = { viewModel.resetForm() }) {
                        Text("Clear all", color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5A67D8)
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF5A67D8))
        ) {
            StepIndicator(
                currentStep = uiState.currentStep,
                onStepClick = { step -> viewModel.goToStep(step) }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.White,
                        RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
            ) {
                when (uiState.currentStep) {
                    0 -> RecipeInfoStep(
                        uiState = uiState,
                        onTitleChange = viewModel::updateTitle,
                        onDescriptionChange = viewModel::updateDescription,
                        onServingsChange = viewModel::updateServings,
                        onPrepTimeChange = viewModel::updatePrepTime,
                        onCookTimeChange = viewModel::updateCookTime,
                        onDifficultyChange = viewModel::updateDifficulty,
                        onDishTypeChange = viewModel::updateDishType,
                        onCuisineChange = viewModel::updateCuisine,
                        onCaloriesChange = viewModel::updateCalories,
                        onDietaryTagToggle = viewModel::toggleDietaryTag,
                        onTagAdd = viewModel::addTag,
                        onTagRemove = viewModel::removeTag,
                        onImagePickerShow = viewModel::showImagePickerDialog,
                        onImageRemove = viewModel::removeImage
                    )
                    1 -> IngredientsStep(
                        ingredients = uiState.ingredients,
                        onAddIngredient = viewModel::addIngredient,
                        onRemoveIngredient = viewModel::removeIngredient,
                        onUpdateIngredient = viewModel::updateIngredient
                    )
                    2 -> StepsStep(
                        steps = uiState.steps,
                        onAddStep = viewModel::addStep,
                        onRemoveStep = viewModel::removeStep,
                        onUpdateStep = viewModel::updateStep
                    )
                    3 -> ReviewStep(
                        uiState = uiState,
                        onSave = viewModel::saveRecipe
                    )
                }

                NavigationBar(
                    currentStep = uiState.currentStep,
                    canProceed = viewModel.canProceedToNext(),
                    isLoading = uiState.isLoading,
                    onNext = viewModel::nextStep,
                    onPrevious = viewModel::previousStep,
                    onSave = viewModel::saveRecipe,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }

    ImagePickerDialog(
        showDialog = uiState.showImagePickerDialog,
        onDismiss = viewModel::hideImagePickerDialog,
        onCameraClick = {
            viewModel.hideImagePickerDialog()
            cameraHandler.launchCameraFlow(
                takePictureLauncher = takePictureLauncher,
                cameraActivityLauncher = cameraActivityLauncher,
                requestPermissionLauncher = cameraPermissionLauncher
            )
        },
        onGalleryClick = {
            viewModel.hideImagePickerDialog()
            galleryLauncher.launch("image/*")
        }
    )

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onNavigateBack()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearError()
        }
    }
}