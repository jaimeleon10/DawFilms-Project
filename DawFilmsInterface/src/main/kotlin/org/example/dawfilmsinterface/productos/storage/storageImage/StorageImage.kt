package org.example.dawfilmsinterface.productos.storage.storageImage

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import java.io.File

interface StorageImage {
    fun getImageName(newFileImage: File): String
    fun saveImage(fileName: File): Result<File, ProductoError>
    fun loadImage(fileName: String): Result<File, ProductoError>
    fun deleteImage(fileName: File): Result<Unit, ProductoError>
    fun deleteAllImages(): Result<Long, ProductoError>
    fun updateImage(imageName: String, newFileImage: File): Result<File, ProductoError>
}