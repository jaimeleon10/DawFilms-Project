package org.example.dawfilmsinterface.productos.storage.storageJson

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.dawfilmsinterface.productos.dto.ProductoDto
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.mappers.toProductoDtoList
import org.example.dawfilmsinterface.productos.mappers.toProductoList
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

/**
 * Implementación de [StorageJson] que permite almacenar y cargar datos en formato JSON.
 * @since 1.0.0
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
class StorageJsonImpl: StorageJson {
    override fun storeJson(file: File, data: List<Producto>): Result<Long, ProductoError> {
        logger.debug { "Guardando datos en fichero $file" }
        return try {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
            val jsonString = json.encodeToString<List<ProductoDto>>(data.toProductoDtoList())
            file.writeText(jsonString)
            Ok(data.size.toLong())
        } catch (e: Exception) {
            Err(ProductoError.ProductoStorageError("Error al escribir el JSON: ${e.message}"))
        }
    }

    override fun loadJson(file: File): Result<List<Producto>, ProductoError> {
        logger.debug { "Cargando datos del fichero $file" }
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
        return try {
            val jsonString = file.readText()
            val data = json.decodeFromString<List<ProductoDto>>(jsonString)
            Ok(data.toProductoList())
        } catch (e: Exception) {
            Err(ProductoError.ProductoStorageError("Error al leer el JSON: ${e.message}"))
        }
    }
}