package org.example.dawfilmsinterface.ventas.storage

import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.ventas.models.Venta
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.io.File
import java.nio.file.Files
import java.util.*

class VentaStorageImplTest {

    private lateinit var storageJson: VentaStorageImpl
    private lateinit var myFile: File

    @BeforeEach
    fun setUp() {
        storageJson = VentaStorageImpl()
        myFile = Files.createTempFile("ventas", ".json").toFile()
    }

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(myFile.toPath())
    }

    @Test
    fun storeJson() {
        val data = listOf<Venta>(
            Venta()
        )

        val result = storageJson.storeJson(myFile, data)

        assertTrue(result.isOk)
        assertEquals(data.size.toLong(), result.value)
    }

    @Test
    fun loadJson() {
        val data = listOf<Venta>(
            Venta()
        )

        storageJson.storeJson(myFile, data)
        println("Datos escritos en el fichero: $myFile")

        val result = storageJson.loadJson(myFile)

        assertTrue(result.isOk)
        assertEquals(data.size.toLong(), result.value)
    }
}