package org.example.dawfilmsinterface.productos.storage.storageJson

import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.io.File
import java.nio.file.Files

class StorageJsonImplTest {

    private lateinit var storageJson: StorageJsonImpl
    private lateinit var myFile: File

    @BeforeEach
    fun setUp() {
        storageJson = StorageJsonImpl()
        myFile = Files.createTempFile("productosTest", ".json").toFile()
    }

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(myFile.toPath())
    }

    @Test
    fun storeJson() {
        val data = listOf(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA)
        )

        val result = storageJson.storeJson(myFile, data)

        assertTrue(result.isOk)
        assertEquals(data.size.toLong(), result.value)
    }

    @Test
    fun loadJson() {
        val data = listOf(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA)
        )

        storageJson.storeJson(myFile, data)

        val result = storageJson.loadJson(myFile)

        assertTrue(result.isOk)
        assertEquals(data, result.value)
    }
}