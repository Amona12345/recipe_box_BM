package com.example.recipebox.domain.usecase.recipe

import com.example.recipebox.domain.repository.ImageRepository

class DeleteImageUseCase(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(path: String): Boolean {
        return repository.deleteImage(path)
    }
}