package com.example.recipebox.data.repository

import android.content.Context
import android.net.Uri
import com.example.recipebox.domain.repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val context: Context
) : ImageRepository {

    override suspend fun saveImageToInternalStorage(uri: Uri): String = withContext(Dispatchers.IO) {
        val timestamp = System.currentTimeMillis()
        val filename = "recipe_image_$timestamp.jpg"
        val file = File(context.filesDir, "recipe_images/$filename")

        file.parentFile?.mkdirs()

        context.contentResolver.openInputStream(uri)?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        file.absolutePath
    }

    override suspend fun deleteImage(path: String): Boolean = withContext(Dispatchers.IO) {
        try {
            File(path).delete()
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getImageUri(path: String): Uri? {
        return try {
            Uri.fromFile(File(path))
        } catch (e: Exception) {
            null
        }
    }
}