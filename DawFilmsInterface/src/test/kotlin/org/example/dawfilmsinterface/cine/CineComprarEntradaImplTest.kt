package org.example.dawfilmsinterface.cine

import org.example.dawfilmsinterface.cine.services.storageHtml.StorageHtmlRecaudacionImpl
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import org.example.dawfilmsinterface.ventas.storage.storageHtml.StorageHtmlImpl
import org.example.dawfilmsinterface.ventas.storage.storageVenta.VentaStorageImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files
import java.time.LocalDate
import java.util.UUID

class CineComprarEntradaImplTest {
    private lateinit var storage: StorageHtmlImpl
    private lateinit var myFile: File

    @BeforeEach
    fun setUp() {
        storage = StorageHtmlImpl()
        myFile = Files.createTempFile("recaudacion", ".html").toFile()
    }

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(myFile.toPath())
    }

    @Test
    fun exportHtml() {
        val venta = Venta(
            id = UUID.randomUUID(),
            cliente = Cliente(id = 1, nombre = "Test", apellido = "test", fechaNacimiento = LocalDate.of(2000, 1, 1), dni = "12345678A", email = "test@gmail.com", numSocio = "AAA111", password = "password"),
            lineas = listOf(LineaVenta(tipoProducto = "Butaca", producto = Butaca(id = "A1", fila = 0, columna = 0, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE), cantidad = 1, precio = 5.0, createdAt = LocalDate.now(), updatedAt = LocalDate.now())),
            fechaCompra = LocalDate.now(),
            createdAt = LocalDate.now(),
            updatedAt = LocalDate.now(),
            isDeleted = false
        )

        val result = storage.exportHtml(venta, myFile)

        val htmlLines = myFile.readLines()
        val htmlText = myFile.readText()

        val actualHtmlText =
            """<html>
    <head>
        <title>Fichero de Venta</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <h1><span style="text-decoration: underline; color: #3366ff;">Ticket Cines DAWFILMS</span> 🍿🎥</h1>
            <ul>
                <li style="font-size: larger;"><strong>Identificador de compra: </strong>3152024_5.0_AAA111</li>
                <li style="font-size: larger;"><strong>Nombre: </strong>Test</li>
                <li style="font-size: larger;"><strong>Número de Socio: </strong>AAA111</li>
                <li style="font-size: larger;"><strong>Fecha de compra: </strong>31-05-2024</li>
                <li style="font-size: larger;"><strong>Productos:</strong></li>
            </ul>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Precio Unitario</th>
                        <th>Precio Total</th>
                    </tr>
                </thead>
                <tbody>
                    <tr><td>Butaca-A1</td><td>1</td><td>5,00 €</td><td>5,00 €</td></tr>
                </tbody>
            </table>
            <ul>
            <li style="font-size: larger;"><strong>Coste Total (sin IVA): </strong>3,95 €<br /><br /></li>
                <li style="font-size: larger;"><strong>Coste Total (con IVA): </strong>5,00 €<br /><br /></li>
            </ul>
            <p class="text-right lead"><span style="font-weight: bold;"><img style="display: block; margin-left: auto; margin-right: auto;" src="https://e00-elmundo.uecdn.es/assets/multimedia/imagenes/2022/04/29/16512471939657.jpg" width="554" height="359" /><br /></span></p>
            <p class="text-right lead" style="text-align: center;"><span style="font-weight: bold;">&iexcl; GRACIAS POR SU COMPRA !<br />🎫🎫🎫</span></p>
        </div>
    </body>
</html>"""

        assertTrue(result.isOk)
        assertEquals(htmlLines.size, 39)
        assertEquals(htmlText, actualHtmlText)

    }
}