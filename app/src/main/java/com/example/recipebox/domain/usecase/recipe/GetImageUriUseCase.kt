package com.example.recipebox.domain.usecase.recipe

import android.net.Uri
import com.example.recipebox.domain.repository.ImageRepository

class GetImageUriUseCase(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(path: String): Uri? {
        return repository.getImageUri(path)
    }
}