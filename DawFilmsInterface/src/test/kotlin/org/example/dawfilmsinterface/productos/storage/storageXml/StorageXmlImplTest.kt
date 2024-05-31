package org.example.dawfilmsinterface.productos.storage.storageXml

import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.io.File
import java.nio.file.Files
import java.time.LocalDate

class StorageXmlImplTest {

    private lateinit var storageXml: StorageXml
    private lateinit var myFile: File

    @BeforeEach
    fun setUp() {
        storageXml = StorageXmlImpl()
        myFile = Files.createTempFile("productos", ".xml").toFile()
    }

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(myFile.toPath())
    }

    @Test
    fun storeXml() {
        val data = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA,
                LocalDate.now(), LocalDate.now(),false)
        )

        val result = storageXml.storeXml(myFile, data)

        assertTrue(result.isOk)
        assertEquals(data.size.toLong(), result.value)
    }

    @Test
    fun loadXml() {
        val data = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA,
                LocalDate.now(), LocalDate.now(),false)
        )

        storageXml.storeXml(myFile, data)
        println("Datos escritos en el fichero: $myFile")

        val result = storageXml.loadXml(myFile)

        assertTrue(result.isOk)
        assertEquals(data, result.value)
    }
}