package org.example.dawfilmsinterface.productos.storage.storageImage

import org.example.dawfilmsinterface.config.Config
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files

private val logger = logging()  //TODO USAR LOGGER

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class StorageImageImplTest {
    private lateinit var storageImage: StorageImageImpl
    private lateinit var myFile: File

    @BeforeEach
    fun setUp() {
        storageImage = StorageImageImpl(Config())
        //val dir = Path(System.getProperty("user.dir"), "data")
        myFile = Files.createTempFile("image", ".png").toFile()
    }

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(myFile.toPath())
    }

    @Test
    fun saveImage() {
        val result = storageImage.saveImage(myFile)

        assertTrue { result.isOk }
        assertEquals("png", result.value.extension)
    }

    @Test
    fun loadImage() {

        //TODO REVISAR, CREO QUE FALLA PORQUE LAS IMAGENES NO SE ESTÁN ALMACENANDO EN DIRECTORIO IMAGENES
        val file = storageImage.saveImage(myFile).value

        val result = storageImage.loadImage(file.name)

        assertTrue(result.isOk)
        assertEquals("png", result.value.extension)
    }

    @Test
    fun deleteImage() {
        val result = storageImage.deleteImage(myFile)

        assertTrue(result.isOk)
    }

    @Test
    fun deleteAllImages() {
        val result = storageImage.deleteAllImages()

        assertTrue(result.isOk)
    }

    @Test
    fun updateImage() {
        val file = storageImage.saveImage(myFile).value

        val result = storageImage.updateImage(file.name, myFile)

        assertTrue(result.isOk)
    }
}