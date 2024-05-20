package org.example.dawfilmsinterface.productos.storage.storageZip

import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.productos.storage.storageJson.StorageJsonImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import java.io.File
import java.nio.file.Files
import java.time.LocalDate
import org.junit.jupiter.api.Assertions.*

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class StorageZipImplTest {
    private lateinit var storageZip: StorageZipImpl
    private lateinit var myFile: File

    @BeforeEach
    fun setUp() {
        storageZip = StorageZipImpl(Config(), StorageJsonImpl())
        myFile = Files.createTempFile("productos", ".zip").toFile()
    }

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(myFile.toPath())
    }

    @Order(1)
    @Test
    fun exportToZip() {
        val data = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA, LocalDate.now(), LocalDate.now(),false)
        )

        val result = storageZip.exportToZip(myFile, data)

        assertTrue (result.isOk)
        assertEquals("zip", result.value.extension)
        assertTrue(result.value.isFile)
        //No podemos verificar el nombre debido a que cambia cada vez que genera un temporal nuevo
    }

    @Order(2)
    @Test
    fun loadFromZip() {
        val data = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA, LocalDate.now(), LocalDate.now(),false)
        )

        storageZip.exportToZip(myFile, data)
        println("Datos exportados en fichero Zip: $myFile")

        val result = storageZip.loadFromZip(myFile)

        assertTrue(result.isOk)
        assertEquals(data, result.value)
    }
}