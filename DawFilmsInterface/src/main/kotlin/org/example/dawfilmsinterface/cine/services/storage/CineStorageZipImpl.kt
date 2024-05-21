package org.example.dawfilmsinterface.cine.services.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.cine.errors.CineError
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.storage.ClienteStorage
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.productos.storage.storageJson.StorageJson
import org.example.dawfilmsinterface.ventas.models.Venta
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import kotlin.io.path.name

private val logger = logging()

class CineStorageZipImpl (
    private val config: Config,
    private val storageJsonProductos: StorageJson,
    private val storageJsonClientes: ClienteStorage
): CineStorageZip {
    private val tempDirName = "productosClientes"

    override fun exportToZip(fileToZip: File, dataProducto: List<Producto>, dataCliente: List<Cliente>): Result<File, CineError> { //dataVentas: List<Venta>
        logger.debug { "Exportando a ZIP $fileToZip" }
        val tempDir = Files.createTempDirectory(tempDirName)
        return try {
            dataProducto.forEach {
                val file = File(config.imagesDirectory + it.imagen)
                if (file.exists()) {
                    Files.copy(
                        file.toPath(),
                        Paths.get(tempDir.toString(), file.name),
                        StandardCopyOption.REPLACE_EXISTING
                    )
                }
            }
            storageJsonProductos.storeJson(File("$tempDir/dataProducto.json"), dataProducto)
            Files.walk(tempDir)
                .forEach { logger.debug { it } }
            val archivosProducto = Files.walk(tempDir)
                .filter { Files.isRegularFile(it) }
                .toList()
            ZipOutputStream(Files.newOutputStream(fileToZip.toPath())).use { zip ->
                archivosProducto.forEach { archivoProd ->
                    val entradaZip = ZipEntry(tempDir.relativize(archivoProd).toString())
                    zip.putNextEntry(entradaZip)
                    Files.copy(archivoProd, zip)
                    zip.closeEntry()
                }
            }
            storageJsonClientes.storeJson(File("$tempDir/dataCliente.json"), dataCliente)
            Files.walk(tempDir)
                .forEach { logger.debug { it } }
            val archivosCliente = Files.walk(tempDir)
                .filter { Files.isRegularFile(it) }
                .toList()
            ZipOutputStream(Files.newOutputStream(fileToZip.toPath())).use { zip ->
                archivosCliente.forEach { archivoClient ->
                    val entradaZip = ZipEntry(tempDir.relativize(archivoClient).toString())
                    zip.putNextEntry(entradaZip)
                    Files.copy(archivoClient, zip)
                    zip.closeEntry()
                }
            }
            Ok(fileToZip)
        } catch (e: Exception) {
            logger.error { "Error al exportar a ZIP: ${e.message}" }
            Err(CineError.ExportZipError("Error al exportar a ZIP: ${e.message}"))
        }
    }

    override fun loadFromZip(fileToUnzip: File): Result<List<Any>, CineError> {
        logger.debug { "Importando desde ZIP $fileToUnzip" }
        val tempDir = Files.createTempDirectory(tempDirName)
        return try {
            ZipFile(fileToUnzip).use { zip ->
                zip.entries().asSequence().forEach { entrada ->
                    zip.getInputStream(entrada).use { input ->
                        Files.copy(
                            input,
                            Paths.get(tempDir.toString(), entrada.name),
                            StandardCopyOption.REPLACE_EXISTING
                        )
                    }
                }
            }
            Files.walk(tempDir).forEach {
                if (!it.toString().endsWith(".json") && Files.isRegularFile(it)) {
                    Files.copy(
                        it,
                        Paths.get(config.imagesDirectory, it.name),
                        StandardCopyOption.REPLACE_EXISTING
                    )
                }
            }
            val dataProductos = storageJsonProductos.loadJson(File("$tempDir/dataProducto.json"))
            val dataClientes = storageJsonClientes.loadJson(File("$tempDir/dataCliente.json"))
            val listado: MutableList<Any> = mutableListOf()
            dataClientes.value.forEach { listado.add(it) }
            dataProductos.value.forEach { listado.add(it) }
            tempDir.toFile().deleteRecursively()
            return Ok(listado.toList())
        } catch (e: Exception) {
            logger.error { "Error al importar desde ZIP: ${e.message}" }
            Err(CineError.ImportZipError("Error al importar desde ZIP: ${e.message}"))
        }
    }


}