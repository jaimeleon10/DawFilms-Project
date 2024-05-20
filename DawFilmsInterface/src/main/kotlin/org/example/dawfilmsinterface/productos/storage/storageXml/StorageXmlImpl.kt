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
import org.lighthousegames.logging.logging

private val logger = logging()

class StorageXmlImpl : StorageXml {
    override fun storeXml(file: File, data: List<Producto>): Result<Long, ProductoError> {
        logger.debug{ "Guardando datos en fichero $file" }
        return try {
            val xml = XML { indent = 4 }
            val xmlString = xml.encodeToString<List<Producto>>(data)
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
            val data = xml.decodeFromString<List<Producto>>(xmlString)
            Ok(data)
        } catch (e : Exception){
            Err(ProductoError.ProductoStorageError("Error al leer el XML: ${e.message}"))
        }
    }
}