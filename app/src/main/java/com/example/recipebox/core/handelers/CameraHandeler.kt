package com.example.recipebox.core.handelers

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.recipebox.presentation.viewmodel.AddRecipeViewModel
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.Manifest
class CameraHandler(
    private val context: Context,
    private val onImageCaptured: (Uri) -> Unit,
    private val onError: (String) -> Unit
) {


    private var tempImageFile: File? = null

    fun handleCameraResult(success: Boolean) {


        if (success) {
            tempImageFile?.let { file ->


                Handler(Looper.getMainLooper()).postDelayed({
                    if (file.exists() && file.length() > 0) {
                        try {
                            val uri = Uri.fromFile(file)
                            onImageCaptured(uri)
                        } catch (e: Exception) {
                            onError("Error processing image: ${e.message}")
                        }
                    } else {
                        checkCommonCameraLocations()
                    }
                }, 500)
            }
        } else {
            tempImageFile?.let { it.delete() }
            tempImageFile = null
        }
    }

    fun handleCameraActivityResult(result: ActivityResult) {

        if (result.resultCode == Activity.RESULT_OK) {
            tempImageFile?.let { file ->
                if (file.exists() && file.length() > 0) {
                    val uri = Uri.fromFile(file)
                    onImageCaptured(uri)
                } else {
                    result.data?.let { intent ->
                        val imageBitmap = intent.extras?.get("data") as? Bitmap
                        if (imageBitmap != null) {
                            saveBitmapToFile(imageBitmap, file)
                            val uri = Uri.fromFile(file)
                            onImageCaptured(uri)
                        } else {
                            onError("No image data received from camera")
                        }
                    }
                }
            }
        }
    }

    private fun saveBitmapToFile(bitmap: Bitmap, file: File) {
        try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private fun checkCommonCameraLocations() {

        val commonPaths = listOf(
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera"),
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Camera"),
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            context.externalCacheDir
        )

        for (dir in commonPaths) {
            if (dir?.exists() == true) {
                val files = dir.listFiles()?.sortedByDescending { it.lastModified() }
                files?.take(3)?.forEach { file ->
                }
            }
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "recipe_$timeStamp.jpg"

        val possibleDirs = listOf(
            context.externalCacheDir,
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            context.cacheDir,
            File(context.filesDir, "images").apply { mkdirs() }
        )

        for (dir in possibleDirs) {
            if (dir != null && (dir.exists() || dir.mkdirs())) {
                val file = File(dir, imageFileName)
                return file
            }
        }

        throw Exception("Could not create image file in any available directory")
    }

    fun launchCameraMethodOne(takePictureLauncher: ManagedActivityResultLauncher<Uri, Boolean>) {
        try {
            val imageFile = createImageFile()
            tempImageFile = imageFile

            imageFile.parentFile?.let { parentDir ->
                if (!parentDir.exists()) {
                    parentDir.mkdirs()
                }
            }

            val photoUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                imageFile
            )



            takePictureLauncher.launch(photoUri)

        } catch (e: Exception) {
            onError("Failed to launch camera: ${e.message}")
        }
    }

    fun launchCameraMethodTwo(cameraActivityLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        try {
            val imageFile = createImageFile()
            tempImageFile = imageFile

            val photoUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                imageFile
            )

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val resInfoList = context.packageManager.queryIntentActivities(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY)
            for (resolveInfo in resInfoList) {
                val packageName = resolveInfo.activityInfo.packageName
                context.grantUriPermission(packageName, photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            cameraActivityLauncher.launch(cameraIntent)

        } catch (e: Exception) {
            onError("Failed to launch camera: ${e.message}")
        }
    }

    fun launchCameraMethodThree(cameraActivityLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        try {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraActivityLauncher.launch(cameraIntent)

        } catch (e: Exception) {
            onError("Failed to launch camera: ${e.message}")
        }
    }

    fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    fun launchCameraFlow(
        takePictureLauncher: ManagedActivityResultLauncher<Uri, Boolean>,
        cameraActivityLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        requestPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>
    ) {

        when {
            hasCameraPermission() -> {

                try {
                    launchCameraMethodOne(takePictureLauncher)
                } catch (e1: Exception) {
                    try {
                        launchCameraMethodTwo(cameraActivityLauncher)
                    } catch (e2: Exception) {
                        launchCameraMethodThree(cameraActivityLauncher)
                    }
                }
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    fun cleanup() {
        tempImageFile?.let { file ->
            if (file.exists()) {
                file.delete()
            }
        }
        tempImageFile = null
    }
}