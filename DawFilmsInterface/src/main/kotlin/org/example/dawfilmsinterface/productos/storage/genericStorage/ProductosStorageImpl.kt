package org.example.dawfilmsinterface.productos.storage.genericStorage

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.productos.storage.storageCsv.StorageCsv
import org.example.dawfilmsinterface.productos.storage.storageImage.StorageImage
import org.example.dawfilmsinterface.productos.storage.storageJson.StorageJson
import org.example.dawfilmsinterface.productos.storage.storageXml.StorageXml
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

private val logger = logging()
/**
 * Interfaz que define las operaciones de almacenamiento y recuperación de productos en diferentes formatos,
 * gestión de imágenes y operaciones de importación y exportación en formato ZIP.
 * @since 1.0.0
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
class ProductosStorageImpl(
    private val config: Config,
    private val storageCsv: StorageCsv,
    private val storageJson: StorageJson,
    private val storageXml: StorageXml,
    private val storageImage: StorageImage,
) : ProductosStorage {

    override fun storeCsv(file: File, data: List<Producto>): Result<Long, ProductoError> {
        logger.debug { "Guardando datos en fichero $file" }
        return storageCsv.storeCsv(file, data)
    }

    override fun loadCsv(file: File): Result<List<Producto>, ProductoError> {
        logger.debug { "Cargando datos en fichero $file" }
        return storageCsv.loadCsv(file)
    }

    override fun storeJson(file: File, data: List<Producto>): Result<Long, ProductoError> {
        logger.debug { "Guardando datos en fichero $file" }
        return storageJson.storeJson(file, data)
    }

    override fun loadJson(file: File): Result<List<Producto>, ProductoError> {
        logger.debug { "Cargando datos en fichero $file"}
        return storageJson.loadJson(file)
    }

    override fun storeXml(file: File, data: List<Producto>): Result<Long, ProductoError> {
        logger.debug { "Guardando datos en fichero $file"}
        return storageXml.storeXml(file, data)
    }

    override fun loadXml(file: File): Result<List<Producto>, ProductoError> {
        logger.debug { "Cargando datos en fichero $file" }
        return storageXml.loadXml(file)
    }

    override fun getImageName(fileImage: File): Result<String, ProductoError> {
        logger.debug { "Sacando nombre de imagen" }
        return Ok(storageImage.getImageName(fileImage))
    }

    override fun saveImage(fileName: File): Result<File, ProductoError> {
        Files.createDirectories(Paths.get(config.imagesDirectory))
        logger.debug { "Guardando imagen $fileName" }
        return storageImage.saveImage(fileName)
    }

    override fun loadImage(fileName: String): Result<File, ProductoError> {
        logger.debug { "Cargando imagen $fileName" }
        return storageImage.loadImage(fileName)
    }

    override fun deleteImage(fileImage: File): Result<Unit, ProductoError> {
        logger.debug { "Borrando imagen $fileImage" }
        return storageImage.deleteImage(fileImage)
    }

    override fun deleteAllImages(): Result<Long, ProductoError> {
        logger.debug { "Borrando todas las imagenes" }
        return storageImage.deleteAllImages()
    }

    override fun updateImage(imageName: String, newFileImage: File): Result<File, ProductoError> {
        logger.debug { "Actualizando la imagen $imageName" }
        return storageImage.updateImage(imageName, newFileImage)
    }
}