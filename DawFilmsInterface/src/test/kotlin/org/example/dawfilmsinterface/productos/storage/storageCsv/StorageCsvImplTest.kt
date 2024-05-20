package org.example.dawfilmsinterface.productos.storage.storageCsv

import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.junit.jupiter.api.*
import java.io.File
import java.nio.file.Files
import java.time.LocalDate
import org.junit.jupiter.api.Assertions.*

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class StorageCsvImplTest {
    private lateinit var storageCsv: StorageCsvImpl
    private lateinit var myFile: File

    @BeforeEach
    fun setUp() {
        storageCsv = StorageCsvImpl()
        myFile = Files.createTempFile("productos", ".csv").toFile()
    }

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(myFile.toPath())
    }

    @Order(1)
    @Test
    fun storeCsv() {
        val data = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA, LocalDate.now(), LocalDate.now(),false)
        )

        val result = storageCsv.storeCsv(myFile, data)

        assertTrue { result.isOk }
        assertEquals(data.size.toLong(), result.value)
    }

    @Order(2)
    @Test
    fun loadCsv() {
        val data = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA, LocalDate.now(), LocalDate.now(),false)
        )

        storageCsv.storeCsv(myFile, data)
        println("Datos escritos en el fichero: $myFile")

        val result = storageCsv.loadCsv(myFile)

        assertTrue(result.isOk)
        assertEquals(data, result.value)
    }
}