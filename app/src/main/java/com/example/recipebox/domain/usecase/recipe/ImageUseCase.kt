package com.example.recipebox.domain.usecase.recipe

data class ImageUseCases(
    val saveImageToInternalStorage: SaveImageToInternalStorageUseCase,
    val deleteImage: DeleteImageUseCase,
    val getImageUri: GetImageUriUseCase
)