package org.example.dawfilmsinterface.ventas.storage

import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.io.File
import java.nio.file.Files
import java.time.LocalDate
import java.util.*

class VentaStorageImplTest {

    private lateinit var storageJson: VentaStorageImpl
    private lateinit var myFile: File
    private lateinit var ventaMuestra: Venta

    @BeforeEach
    fun setUp() {
        storageJson = VentaStorageImpl()
        myFile = Files.createTempFile("ventas", ".json").toFile()
        ventaMuestra = Venta(
            id = UUID.randomUUID(),
            cliente = Cliente(
                id = 1,
                nombre = "Test",
                apellido = "test",
                fechaNacimiento = LocalDate.of(2000,1,1),
                dni = "12345678A",
                email = "test@gmail.com",
                numSocio = "AAA111",
                password = "password"),
            lineas = listOf(
                LineaVenta(
                    tipoProducto = "Butaca",
                    producto = Butaca(
                        id = "A1",
                        fila = 0,
                        columna = 0,
                        tipoButaca = TipoButaca.NORMAL,
                        estadoButaca = EstadoButaca.ACTIVA,
                        ocupacionButaca = OcupacionButaca.LIBRE
                    ),
                    cantidad = 1,
                    precio =  5.0,
                    createdAt = LocalDate.now(),
                    updatedAt = LocalDate.now(),
                )
            ),
            fechaCompra = LocalDate.now()
        )
    }

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(myFile.toPath())
    }

    @Test
    fun storeJson() {
        val data = listOf<Venta>(ventaMuestra)

        val result = storageJson.storeJson(myFile, data)

        assertTrue(result.isOk)
        assertEquals(data.size.toLong(), result.value)
    }

    @Test
    fun loadJson() {
        val data = listOf<Venta>(ventaMuestra)

        storageJson.storeJson(myFile, data)

        val result = storageJson.loadJson(myFile)

        println(result.error.message)

        assertTrue(result.isOk)
        assertEquals(data, result.value)
    }
}