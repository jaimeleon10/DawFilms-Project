package org.example.dawfilmsinterface.ventas.storage.storageHtml

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.locale.toDefaultDateString
import org.example.dawfilmsinterface.locale.toDefaultMoneyString
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.Venta
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

/**
 * Implementación de la interfaz [StorageHtml] para manejar el almacenamiento de ventas en formato HTML.
 * @property ventaRepository Repositorio de ventas para obtener información de ventas.
 * @constructor Crea una instancia de [StorageHtmlImpl] con el repositorio de ventas proporcionado.
 * @since 1.0.0
 * @param ventaRepository Repositorio de ventas.
 * @param Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz Autores del código.
 */
class StorageHtmlImpl: StorageHtml {
    override fun exportHtml(venta: Venta, file: File): Result<Unit, VentaError> {
        return try {
            val html = """
            <html>
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
                            <li style="font-size: larger;"><strong>Identificador de compra: </strong>${venta.fechaCompra.dayOfMonth}${venta.fechaCompra.monthValue}${venta.fechaCompra.year}_${venta.total}_${venta.cliente.numSocio}</li>
                            <li style="font-size: larger;"><strong>Nombre: </strong>${venta.cliente.nombre}</li>
                            <li style="font-size: larger;"><strong>Número de Socio: </strong>${venta.cliente.numSocio}</li>
                            <li style="font-size: larger;"><strong>Fecha de compra: </strong>${venta.fechaCompra.toDefaultDateString()}</li>
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
                                ${venta.lineas.joinToString("") { "<tr><td>${
                                    if (it.tipoProducto == "Butaca") {
                                        "${"Butaca"}-${it.producto.id}"
                                    } else {
                                        (it.producto as Complemento).nombre
                                    }
                                }</td><td>${it.cantidad}</td><td>${
                                    if (it.tipoProducto == "Butaca") {
                                        (it.producto as Butaca).tipoButaca.precio.toDefaultMoneyString()
                                    } else {
                                        (it.producto as Complemento).precio.toDefaultMoneyString()
                                    }
                                }</td><td>${(it.cantidad * (if (it.tipoProducto == "Butaca") {
                                    (it.producto as Butaca).tipoButaca.precio
                                } else {
                                    (it.producto as Complemento).precio
                                } )).toDefaultMoneyString()
                                }</td></tr>" }}
                            </tbody>
                        </table>
                        <ul>
                        <li style="font-size: larger;"><strong>Coste Total (sin IVA): </strong>${(venta.total * 0.79).toDefaultMoneyString()}<br /><br /></li>
                            <li style="font-size: larger;"><strong>Coste Total (con IVA): </strong>${venta.total.toDefaultMoneyString()}<br /><br /></li>
                        </ul>
                        <p class="text-right lead"><span style="font-weight: bold;"><img style="display: block; margin-left: auto; margin-right: auto;" src="https://e00-elmundo.uecdn.es/assets/multimedia/imagenes/2022/04/29/16512471939657.jpg" width="554" height="359" /><br /></span></p>
                        <p class="text-right lead" style="text-align: center;"><span style="font-weight: bold;">&iexcl; GRACIAS POR SU COMPRA !<br />🎫🎫🎫</span></p>
                    </div>
                </body>
            </html>
        """.trimIndent()
            Ok(file.writeText(html, Charsets.UTF_8))
        } catch (e: Exception) {
            logger.error { "Error al salvar fichero de venta: ${file.absolutePath}. ${e.message}" }
            Err(VentaError.VentaStorageError("Error al salvar fichero de venta: ${file.absolutePath}. ${e.message}"))
        }
    }
}