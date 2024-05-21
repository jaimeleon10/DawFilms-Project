package org.example.dawfilmsinterface.clientes.storage

import org.example.dawfilmsinterface.clientes.models.Cliente
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.nio.file.Files
import java.time.LocalDate

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ClienteStorageImplTest {

    private lateinit var storageJson: ClienteStorageImpl
    private lateinit var myFile: File

    @BeforeEach
    fun setUp() {
        storageJson = ClienteStorageImpl()
        myFile = Files.createTempFile("clientes", ".json").toFile()
    }

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(myFile.toPath())
    }

    @Order(1)
    @Test
    fun storeJson() {
        val data = listOf(
            Cliente(1L, "Cliente", "Apellido", LocalDate.now(), "73948501P", "cliente@gmail.com", "38S", "password")
        )

        val result = storageJson.storeJson(myFile,data)

        assertTrue(result.isOk)
        assertEquals(data.size.toLong(), result.value)
    }

    @Order(2)
    @Test
    fun loadJson() {
        val data = listOf(
            Cliente(1L, "Cliente", "Apellido", LocalDate.now(), "73948501P", "cliente@gmail.com", "38S", "password")
        )

        storageJson.storeJson(myFile,data)

        val result = storageJson.loadJson(myFile)

        assertTrue(result.isOk)
        assertEquals(data, result.value)
    }
}