package org.example.dawfilmsinterface.productos.storage.genericStorage

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.productos.storage.storageCsv.StorageCsv
import org.example.dawfilmsinterface.productos.storage.storageJson.StorageJson
import org.example.dawfilmsinterface.productos.storage.storageXml.StorageXml
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

private val logger = logging()

class ProductosStorageImpl(
    private val config: Config,
    private val storageCsv: StorageCsv,
    private val storageJson: StorageJson,
    private val storageXml: StorageXml
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
}