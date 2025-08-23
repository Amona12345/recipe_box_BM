package com.example.recipebox.domain.usecase.recipe

import android.net.Uri
import com.example.recipebox.domain.repository.ImageRepository

class SaveImageToInternalStorageUseCase(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(uri: Uri): String {
        return repository.saveImageToInternalStorage(uri)
    }
}