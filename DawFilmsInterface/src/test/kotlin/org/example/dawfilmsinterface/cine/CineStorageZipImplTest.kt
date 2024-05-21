package org.example.dawfilmsinterface.cine

import org.example.dawfilmsinterface.cine.services.storage.CineStorageZipImpl
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.storage.ClienteStorageImpl
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.productos.storage.storageJson.StorageJsonImpl
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.nio.file.Files
import java.time.LocalDate

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class CineStorageZipImplTest {
    private lateinit var storageZip: CineStorageZipImpl
    private lateinit var myFile: File

    @BeforeEach
    fun setUp() {
        storageZip = CineStorageZipImpl(Config(), StorageJsonImpl(), ClienteStorageImpl())
        myFile = Files.createTempFile("cine", ".zip").toFile()
    }

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(myFile.toPath())
    }

    @Order(1)
    @Test
    fun exportToZip() {
        val dataProducto = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA, LocalDate.now(), LocalDate.now(),false)
        )
        val dataCliente = listOf<Cliente>(
            Cliente(1L, "Cliente", "Apellido", LocalDate.now(), "73948501P", "cliente@gmail.com", "38S", "password")
        )

        val result = storageZip.exportToZip(myFile, dataProducto, dataCliente)

        println(result.value.name)

        assertTrue(result.isOk)
        assertEquals("zip", result.value.extension)
        assertTrue(result.value.isFile)
    }

    @Order(2)
    @Test
    fun loadFromZip() {
        val dataProducto = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA, LocalDate.now(), LocalDate.now(),false)
        )
        val dataCliente = listOf<Cliente>(
            Cliente(1L, "Cliente", "Apellido", LocalDate.now(), "73948501P", "cliente@gmail.com", "38S", "password")
        )
        val listado = mutableListOf<Any>()
        dataCliente.forEach { listado.add(it) }
        dataProducto.forEach { listado.add(it) }

        storageZip.exportToZip(myFile, dataProducto, dataCliente)
        println("Datos exportados en fichero Zip: $myFile")

        val result = storageZip.loadFromZip(myFile)

        assertTrue(result.isOk)
        assertEquals(listado, result.value)

        listado.forEach { println(it) }
    }
}