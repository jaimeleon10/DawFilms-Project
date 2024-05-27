package org.example.dawfilmsinterface.productos.storage.genericStorage

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.productos.storage.storageCsv.StorageCsv
import org.example.dawfilmsinterface.productos.storage.storageHtml.StorageHtml
import org.example.dawfilmsinterface.productos.storage.storageImage.StorageImage
import org.example.dawfilmsinterface.productos.storage.storageJson.StorageJson
import org.example.dawfilmsinterface.productos.storage.storageXml.StorageXml
import org.example.dawfilmsinterface.productos.storage.storageZip.StorageZip
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.Venta
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

private val logger = logging()

class ProductosStorageImpl(
    private val config: Config,
    private val storageCsv: StorageCsv,
    private val storageJson: StorageJson,
    private val storageXml: StorageXml,
    private val storageImage: StorageImage,
    private val storageZip: StorageZip,
    private val storageHtml: StorageHtml
) : ProductosStorage {
    init {
        logger.debug{ "Creando directorio de imagenes si no existe" }
        Files.createDirectories(Paths.get(config.imagesDirectory))
    }

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

    override fun saveImage(fileName: File): Result<File, ProductoError> {
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

    override fun exportToZip(fileToZip: File, data: List<Producto>): Result<File, ProductoError> {
        logger.debug { "Exportando a ZIP $fileToZip" }
        return storageZip.exportToZip(fileToZip, data)
    }

    override fun loadFromZip(fileToUnzip: File): Result<List<Producto>, ProductoError> {
        logger.debug { "Importando desde ZIP $fileToUnzip" }
        return storageZip.loadFromZip(fileToUnzip)
    }

    override fun exportHtml(venta: Venta, file: File): Result<Unit, VentaError> {
        logger.debug { "Exportando HTML $file" }
        return storageHtml.exportHtml(venta, file)
    }
}