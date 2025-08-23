package com.example.recipebox.domain.repository

import android.net.Uri

interface ImageRepository {
    suspend fun saveImageToInternalStorage(uri: Uri): String
    suspend fun deleteImage(path: String): Boolean
    suspend fun getImageUri(path: String): Uri?
}