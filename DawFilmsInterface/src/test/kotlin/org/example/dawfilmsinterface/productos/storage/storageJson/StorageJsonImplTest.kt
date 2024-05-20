package org.example.dawfilmsinterface.productos.storage.storageJson

import org.example.dawfilmsinterface.productos.dto.ProductoDto
import org.example.dawfilmsinterface.productos.mappers.toProductoList
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.nio.file.Files
import java.time.LocalDate

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class StorageJsonImplTest {

    private lateinit var storageJson: StorageJsonImpl
    private lateinit var myFile: File

    @BeforeEach
    fun setUp() {
        storageJson = StorageJsonImpl()
        myFile = Files.createTempFile("productos", ".json").toFile()
    }

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(myFile.toPath())
    }

    @Order(1)
    @Test
    fun storeJson() {
        val data = listOf(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA,
                LocalDate.now(), LocalDate.now(),false)
        )

        val result = storageJson.storeJson(myFile, data)

        assertTrue(result.isOk)
        assertEquals(data.size.toLong(), result.value)
    }

    @Order(2)
    @Test
    fun loadJson() {
        val data = listOf(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA,
                LocalDate.now(), LocalDate.now(),false)
        )

        storageJson.storeJson(myFile, data)
        println("Datos escritos en el fichero: $myFile")

        val result = storageJson.loadJson(myFile)

        assertTrue(result.isOk)
        assertEquals(data, result.value)
    }
}