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
import org.example.dawfilmsinterface.ventas.storage.VentaStorage
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
    private val storageJsonClientes: ClienteStorage,
    private val storageJsonVentas: VentaStorage
): CineStorageZip {
    private val tempDirName = "zipVentasClienteProductos"

    override fun exportToZip(
        fileToZip: File,
        dataProductos: List<Producto>,
        dataClientes: List<Cliente>,
        dataVentas: List<Venta>
    ): Result<File, CineError> { //dataVentas: List<Venta>
        logger.debug { "Exportando a ZIP $fileToZip" }
        val tempDir = Files.createTempDirectory(tempDirName)
        return try {
            dataProductos.forEach {
                val file = File(config.imagesDirectory + it.imagen)
                if (file.exists()) {
                    Files.copy(
                        file.toPath(),
                        Paths.get(tempDir.toString(), file.name),
                        StandardCopyOption.REPLACE_EXISTING
                    )
                }
            }
            storageJsonProductos.storeJson(File("$tempDir/dataProducto.json"), dataProductos)
            Files.walk(tempDir)
                .forEach { logger.debug { it } }
            val archivosProductos = Files.walk(tempDir)
                .filter { Files.isRegularFile(it) }
                .toList()
            ZipOutputStream(Files.newOutputStream(fileToZip.toPath())).use { zip ->
                archivosProductos.forEach { archivoProducto ->
                    val entradaZip = ZipEntry(tempDir.relativize(archivoProducto).toString())
                    zip.putNextEntry(entradaZip)
                    Files.copy(archivoProducto, zip)
                    zip.closeEntry()
                }
            }
            storageJsonClientes.storeJson(File("$tempDir/dataCliente.json"), dataClientes)
            Files.walk(tempDir)
                .forEach { logger.debug { it } }
            val archivosClientes = Files.walk(tempDir)
                .filter { Files.isRegularFile(it) }
                .toList()
            ZipOutputStream(Files.newOutputStream(fileToZip.toPath())).use { zip ->
                archivosClientes.forEach { archivoCliente ->
                    val entradaZip = ZipEntry(tempDir.relativize(archivoCliente).toString())
                    zip.putNextEntry(entradaZip)
                    Files.copy(archivoCliente, zip)
                    zip.closeEntry()
                }
            }
            storageJsonVentas.storeJson(File("$tempDir/dataVentas.json"), dataVentas)
            Files.walk(tempDir)
                .forEach { logger.debug { it } }
            val archivosVentas = Files.walk(tempDir)
                .filter { Files.isRegularFile(it) }
                .toList()
            ZipOutputStream(Files.newOutputStream(fileToZip.toPath())).use { zip ->
                archivosVentas.forEach { archivoVenta ->
                    val entradaZip = ZipEntry(tempDir.relativize(archivoVenta).toString())
                    zip.putNextEntry(entradaZip)
                    Files.copy(archivoVenta, zip)
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
            val dataVentas = storageJsonVentas.loadJson(File("$tempDir/dataVentas.json"))
            val listado: MutableList<Any> = mutableListOf()
            dataClientes.value.forEach { listado.add(it) }
            dataProductos.value.forEach { listado.add(it) }
            dataVentas.value.forEach { listado.add(it) }
            tempDir.toFile().deleteRecursively()
            return Ok(listado.toList())
        } catch (e: Exception) {
            logger.error { "Error al importar desde ZIP: ${e.message}" }
            Err(CineError.ImportZipError("Error al importar desde ZIP: ${e.message}"))
        }
    }


}