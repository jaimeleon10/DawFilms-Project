package org.example.dawfilmsinterface.productos.storage.storageImage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.time.Instant

private val logger = logging()

class StorageImageImpl(
    private val config: Config
): StorageImage {
    init {
        try {
            val imagesPath = Paths.get(config.imagesDirectory)
            if (Files.notExists(imagesPath)) {
                Files.createDirectories(imagesPath)
                logger.debug { "Carpeta de imágenes creada en: ${config.imagesDirectory}" }
            }
        } catch (e: Exception) {
            logger.error { "Error al crear la carpeta de imágenes: ${e.message}" }
        }
    }

    private fun getImageName(newFileImage: File): String {
        val name = newFileImage.name
        val extension = name.substring(name.lastIndexOf(".") + 1)
        return "${Instant.now().toEpochMilli()}.$extension"
    }

    override fun saveImage(fileName: File): Result<File, ProductoError> {
        logger.debug { "Guardando imagen $fileName" }
        return try {
            val newFileName = getImageName(fileName)
            val newFileImage = Paths.get(config.imagesDirectory, newFileName).toFile()
            Files.copy(fileName.toPath(), newFileImage.toPath(), StandardCopyOption.REPLACE_EXISTING)
            Ok(newFileImage)
        }catch (e : Exception){
            Err(ProductoError.ProductoStorageError("Error al guardar la imagen: ${e.message}"))
        }
    }

    override fun loadImage(fileName: String): Result<File, ProductoError> {
        logger.debug { "Cargando imagen $fileName" }
        val file = Paths.get(config.imagesDirectory, fileName).toFile()
        return if(file.exists()){
            Ok(file)
        }else{
            Err(ProductoError.ProductoStorageError("La imagen no existe: ${file.name}"))
        }
    }

    override fun deleteImage(fileName: File): Result<Unit, ProductoError> {
        return try {
            Files.deleteIfExists(fileName.toPath())
            Ok(Unit)
        }catch (e : Exception){
            Err(ProductoError.ProductoStorageError("Error al eliminar la imagen: ${e.message}"))
        }
    }

    override fun deleteAllImages(): Result<Long, ProductoError> {
        logger.debug { "Borrando todas las imagenes" }
        return try{
            Ok(Files.walk(Paths.get(config.imagesDirectory))
                .filter{Files.isRegularFile(it)}
                .map { Files.deleteIfExists(it) }
                .count())
        }catch(e: Exception){
            Err(ProductoError.ProductoStorageError("Error al borrar todas las imagenes: ${e.message}"))
        }
    }

    override fun updateImage(imageName: String, newFileImage: File): Result<File, ProductoError> {
        logger.debug { "Actualizando imagen $imageName" }
        return try {
            val newUpdateImage = Paths.get(config.imagesDirectory, imageName).toFile()
            Files.copy(newFileImage.toPath(), newUpdateImage.toPath(), StandardCopyOption.REPLACE_EXISTING)
            Ok(newUpdateImage)
        }catch (e : Exception){
            Err(ProductoError.ProductoStorageError("Error al guardar la imagen: ${e.message}"))
        }
    }

}