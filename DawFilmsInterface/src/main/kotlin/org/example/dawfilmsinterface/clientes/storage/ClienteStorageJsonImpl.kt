package org.example.dawfilmsinterface.clientes.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.json.Json
import org.example.dawfilmsinterface.clientes.dto.ClienteDto
import org.example.dawfilmsinterface.clientes.errors.ClienteError
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.productos.dto.ProductoDto
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.mappers.toProductoList
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

class ClienteStorageJsonImpl: ClienteStorageJson {
    override fun storeJson(file: File, data: List<Cliente>): Result<Long, ClienteError> {
        logger.debug { "Guardando datos en fichero $file" }
        return try {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
            val jsonString = json.encodeToString<List<ClienteDto>>(data)
            file.writeText(jsonString)
            Ok(data.size.toLong())
        }catch(e:Exception){
            Err(ClienteError.ClienteStorageError("Error al escribir el JSON: ${e.message}"))
        }
    }

    override fun loadJson(file: File): Result<List<Cliente>, ClienteError> {
        logger.debug { "Cargando datos en fichero $file" }
        val json = Json{
            prettyPrint = true
            ignoreUnknownKeys = true
        }
        return try {
            val jsonString = file.readText()
            val data = json.decodeFromString<List<Cliente>>(jsonString)
            Ok(data)
        }catch (e: Exception) {
            Err(ClienteError.ClienteStorageError("Error al leer el JSON: ${e.message}"))
        }
    }
}