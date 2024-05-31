package org.example.dawfilmsinterface.productos.storage.storageXml

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.io.File
import nl.adaptivity.xmlutil.serialization.XML
import org.example.dawfilmsinterface.productos.dto.ProductoDto
import org.example.dawfilmsinterface.productos.mappers.toProductoDtoList
import org.example.dawfilmsinterface.productos.mappers.toProductoList
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Implementación de [StorageXml] que permite almacenar y cargar datos en formato XML.
 * @since 1.0.0
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
class StorageXmlImpl : StorageXml {
    override fun storeXml(file: File, data: List<Producto>): Result<Long, ProductoError> {
        logger.debug{ "Guardando datos en fichero $file" }
        return try {
            val xml = XML { indent = 4 }
            val xmlString = xml.encodeToString<List<ProductoDto>>(data.toProductoDtoList())
            file.writeText(xmlString)
            Ok(data.size.toLong())
        }catch (e : Exception) {
            Err(ProductoError.ProductoStorageError("Error al escribir el XML: ${e.message}"))
        }
    }

    override fun loadXml(file: File): Result<List<Producto>, ProductoError> {
        logger.debug { "Cargando datos en fichero $file" }
        val xml = XML{}
        return try {
            val xmlString = file.readText()
            val data = xml.decodeFromString<List<ProductoDto>>(xmlString)
            Ok(data.toProductoList())
        } catch (e : Exception){
            Err(ProductoError.ProductoStorageError("Error al leer el XML: ${e.message}"))
        }
    }
}