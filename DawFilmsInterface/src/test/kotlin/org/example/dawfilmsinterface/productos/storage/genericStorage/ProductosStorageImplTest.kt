package org.example.dawfilmsinterface.productos.storage.genericStorage

import com.github.michaelbull.result.Ok
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.productos.storage.storageCsv.StorageCsv
import org.example.dawfilmsinterface.productos.storage.storageCsv.StorageCsvImpl
import org.example.dawfilmsinterface.productos.storage.storageImage.StorageImage
import org.example.dawfilmsinterface.productos.storage.storageImage.StorageImageImpl
import org.example.dawfilmsinterface.productos.storage.storageJson.StorageJson
import org.example.dawfilmsinterface.productos.storage.storageJson.StorageJsonImpl
import org.example.dawfilmsinterface.productos.storage.storageXml.StorageXml
import org.example.dawfilmsinterface.productos.storage.storageXml.StorageXmlImpl
import org.example.dawfilmsinterface.productos.storage.storageZip.StorageZip
import org.example.dawfilmsinterface.productos.storage.storageZip.StorageZipImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.extension.ExtendWith
import org.lighthousegames.logging.logging
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.io.File
import java.nio.file.Files
import java.time.LocalDate

private val logger = logging()
@ExtendWith(MockitoExtension::class)
class ProductosStorageImplTest {

    @Mock
    private lateinit var storageCsv: StorageCsv

    private lateinit var myFileCsv: File

    @Mock
    private lateinit var storageJson: StorageJson

    private lateinit var myFileJson: File

    @Mock
    private lateinit var storageXml: StorageXml
    private lateinit var myFileXml: File

    @Mock
    private lateinit var storageZip: StorageZip
    private lateinit var myFileZip: File

    @Mock
    private lateinit var storageImage: StorageImage
    private lateinit var myFileImg: File

    @Mock
    private lateinit var config:Config

    @InjectMocks
    private lateinit var storage: ProductosStorageImpl

    @Mock
    private lateinit var storageImpl: StorageImageImpl
    @InjectMocks
    private lateinit var storageImageImpl: StorageImageImpl

    @BeforeEach
    fun setUp() {
        myFileCsv = Files.createTempFile("productos", ".csv").toFile()
        myFileJson = Files.createTempFile("productos", ".json").toFile()
        myFileXml = Files.createTempFile("productos", ".xml").toFile()
        storageImage = StorageImageImpl(Config())
        storageZip = StorageZipImpl(Config(), StorageJsonImpl())
        myFileZip = Files.createTempFile("productos", ".zip").toFile()
        myFileImg = Files.createTempFile("image", ".png").toFile()
        config = Config()
    }

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(myFileCsv.toPath())
        Files.deleteIfExists(myFileJson.toPath())
        Files.deleteIfExists(myFileXml.toPath())
        Files.deleteIfExists(myFileZip.toPath())
    }

    @Order(1)
    @Test
    fun storeCsv() {
        val data = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA, LocalDate.now(), LocalDate.now(),false)
        )

        whenever(storageCsv.storeCsv(myFileCsv, data)).thenReturn(Ok(1L))

        val csv = storage.storeCsv(myFileCsv, data)

        assertTrue { csv.isOk }
        assertEquals(data.size.toLong(), csv.value)
    }

    @Order(2)
    @Test
    fun loadCsv() {
        val data = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA, LocalDate.now(), LocalDate.now(),false)
        )

        whenever(storageCsv.storeCsv(myFileCsv, data)).thenReturn(Ok(1L))

        val save = storage.storeCsv(myFileCsv, data)
        println("Datos escritos en el fichero: $myFileCsv")

        whenever(storageCsv.loadCsv(myFileCsv)).thenReturn(Ok(data))
        val csv = storage.loadCsv(myFileCsv)

        assertTrue { csv.isOk }
        assertEquals(data, csv.value)
    }

    @Order(1)
    @Test
    fun storeJson() {
        val data = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA, LocalDate.now(), LocalDate.now(),false)
        )

        whenever(storageJson.storeJson(myFileJson, data)).thenReturn(Ok(1L))

        val json = storage.storeJson(myFileJson, data)

        assertTrue { json.isOk }
        assertEquals(data.size.toLong(), json.value)
    }

    @Order(2)
    @Test
    fun loadJson() {
        val data = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA, LocalDate.now(), LocalDate.now(),false)
        )

        whenever(storageJson.storeJson(myFileJson, data)).thenReturn(Ok(1L))

        val save = storage.storeJson(myFileJson, data)
        println("Datos escritos en el fichero: $myFileJson")

        whenever(storageJson.loadJson(myFileJson)).thenReturn(Ok(data))
        val json = storage.loadJson(myFileJson)

        assertTrue { json.isOk }
        assertEquals(data, json.value)
    }

    @Test
    fun storeXml() {
        val data = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA,
                LocalDate.now(), LocalDate.now(),false)
        )

        whenever(storageXml.storeXml(myFileXml, data)).thenReturn(Ok(1L))

        val xml = storage.storeXml(myFileXml, data)

        assertTrue { xml.isOk }
        assertEquals(data.size.toLong(), xml.value)
    }

    @Test
    fun loadXml() {
        val data = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA,
                LocalDate.now(), LocalDate.now(),false)
        )
        whenever(storageXml.storeXml(myFileXml, data)).thenReturn(Ok(1L))

        val save = storage.storeXml(myFileXml, data)
        println("Datos escritos en el fichero: $myFileXml")

        whenever(storageXml.loadXml(myFileXml)).thenReturn(Ok(data))
        val xml = storage.loadXml(myFileXml)

        assertTrue { xml.isOk }
        assertEquals(data, xml.value)
    }

    @Test
    fun saveImage() {


        val result = storageImage.saveImage(myFileImg)

        whenever(storageImpl.getImageName(myFileImg)).thenReturn(myFileImg.name)
        whenever(storageImage.saveImage(myFileImg)).thenReturn(Ok(myFileImg))

        val img = storage.saveImage(myFileImg)

        assertTrue { img.isOk }
        assertEquals("png", result.value.extension)

        Files.deleteIfExists(result.value.toPath())
    }

    @Test
    fun loadImage() {

        val file = storageImage.saveImage(myFileImg).value

        val result = storageImage.loadImage(file.name)

        assertTrue(result.isOk)
        assertEquals("png", result.value.extension)

        Files.deleteIfExists(file.toPath())
    }

    @Test
    fun deleteImage() {
        val result = storageImage.deleteImage(myFileImg)

        assertTrue(result.isOk)
    }

    @Test
    fun deleteAllImages() {
        val result = storageImage.deleteAllImages()

        assertTrue(result.isOk)
    }

    @Test
    fun updateImage() {
        val file = storageImage.saveImage(myFileImg).value

        val result = storageImage.updateImage(file.name, myFileImg)

        assertTrue(result.isOk)

        Files.deleteIfExists(result.value.toPath())
    }

    @Order(1)
    @Test
    fun exportToZip() {
        val data = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA, LocalDate.now(), LocalDate.now(),false)
        )

        whenever(storageZip.exportToZip(myFileZip, data)).thenReturn(Ok(myFileZip))

        val zip = storage.exportToZip(myFileZip, data)

        assertTrue { zip.isOk }
        assertEquals(data.size.toLong(), zip.value)
        assertEquals("zip", zip.value.extension)
        assertTrue(zip.value.isFile)
        //No podemos verificar el nombre debido a que cambia cada vez que genera un temporal nuevo
    }

    @Order(2)
    @Test
    fun loadFromZip() {
        val data = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA, LocalDate.now(), LocalDate.now(),false)
        )

        storageZip.exportToZip(myFileZip, data)
        println("Datos exportados en fichero Zip: $myFileZip")

        val result = storageZip.loadFromZip(myFileZip)

        assertTrue(result.isOk)
        assertEquals(data, result.value)
    }
}