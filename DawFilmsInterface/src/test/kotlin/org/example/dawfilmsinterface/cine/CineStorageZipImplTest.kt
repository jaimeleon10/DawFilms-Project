package org.example.dawfilmsinterface.cine

import org.example.dawfilmsinterface.cine.services.storage.CineStorageZipImpl
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.storage.ClienteStorageImpl
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.productos.storage.storageJson.StorageJsonImpl
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import org.example.dawfilmsinterface.ventas.storage.storageVenta.VentaStorageImpl
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.nio.file.Files
import java.time.LocalDate
import java.util.*

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class CineStorageZipImplTest {
    private lateinit var storageZip: CineStorageZipImpl
    private lateinit var myFile: File

    @BeforeEach
    fun setUp() {
        storageZip = CineStorageZipImpl(Config(), StorageJsonImpl(), ClienteStorageImpl(), VentaStorageImpl())
        myFile = Files.createTempFile("cine", ".zip").toFile()
    }

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(myFile.toPath())
    }

    @Order(1)
    @Test
    fun exportToZip() {
        val dataProductos = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA, LocalDate.now(), LocalDate.now(),false)
        )
        val dataClientes = listOf<Cliente>(
            Cliente(1L, "Cliente", "Apellido", LocalDate.now(), "73948501P", "cliente@gmail.com", "38S", "password")
        )
        val dataVentas = listOf<Venta>(
            Venta(
                id = UUID.randomUUID(),
                cliente = Cliente(id = 1, nombre = "Test", apellido = "test", fechaNacimiento = LocalDate.of(2000,1,1), dni = "12345678A", email = "test@gmail.com", numSocio = "AAA111", password = "password"),
                lineas = listOf(LineaVenta(tipoProducto = "Butaca", producto = Butaca(id = "A1", fila = 0, columna = 0, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE), cantidad = 1, precio =  5.0, createdAt = LocalDate.now(), updatedAt = LocalDate.now(),)),
                fechaCompra = LocalDate.now()
            )
        )

        val result = storageZip.exportToZip(myFile, dataProductos, dataClientes, dataVentas)

        println(result.value.name)

        assertTrue(result.isOk)
        assertEquals("zip", result.value.extension)
        assertTrue(result.value.isFile)
    }

    @Order(2)
    @Test
    fun loadFromZip() {
        val dataProductos = listOf<Producto>(
            Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA, LocalDate.now(), LocalDate.now(),false)
        )
        val dataClientes = listOf<Cliente>(
            Cliente(1L, "Cliente", "Apellido", LocalDate.now(), "73948501P", "cliente@gmail.com", "38S", "password")
        )
        val dataVentas = listOf<Venta>(
            Venta(
                id = UUID.randomUUID(),
                cliente = Cliente(id = 1, nombre = "Test", apellido = "test", fechaNacimiento = LocalDate.of(2000,1,1), dni = "12345678A", email = "test@gmail.com", numSocio = "AAA111", password = "password"),
                lineas = listOf(LineaVenta(tipoProducto = "Butaca", producto = Butaca(id = "A1", fila = 0, columna = 0, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE), cantidad = 1, precio =  5.0, createdAt = LocalDate.now(), updatedAt = LocalDate.now(),)),
                fechaCompra = LocalDate.now()
            )
        )
        val listado = mutableListOf<Any>()
        dataClientes.forEach { listado.add(it) }
        dataProductos.forEach { listado.add(it) }
        dataVentas.forEach { listado.add(it) }

        storageZip.exportToZip(myFile, dataProductos, dataClientes, dataVentas)
        println("Datos exportados en fichero Zip: $myFile")

        val result = storageZip.loadFromZip(myFile)

        assertTrue(result.isOk)
        assertEquals(listado, result.value)

        listado.forEach { println(it) }
    }
}