package org.example.dawfilmsinterface.cine.services.storageHtml

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.locale.toDefaultDateString
import org.example.dawfilmsinterface.locale.toDefaultMoneyString
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

class StorageHtmlRecaudacionImpl:StorageHtmlRecaudacion {
    override fun exportHtml(listaLineas: List<LineaVenta>, file: File): Result<Unit, VentaError> {

        var totalLineas = 0.0

        listaLineas.forEach {
            totalLineas += it.precio
        }

        return try {
            val html = """
            <html>
                <head>
                    <title>Fichero de Recaudación</title>
                    <meta charset="utf-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
                </head>
                <body>
                    <div class="container">
                        <h1><span style="text-decoration: underline; color: #3366ff;">Recaudación Cines DAWFILMS</span> 🍿🎥</h1>
                        <ul>
                            <li style="font-size: larger;"><strong>Productos:</strong></li>
                        </ul>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Producto</th>
                                    <th>Cantidad</th>
                                    <th>Precio Unitario</th>
                                    <th>Precio Total</th>
                                    <th>Fecha de Compra</th>
                                </tr>
                            </thead>
                            <tbody> ${listaLineas.joinToString("") { 
                                "<tr><td>${
                                    if (it.producto.tipoProducto == "Butaca") {
                                        "${"Butaca"}-${it.producto.id}"
                                    } else {
                                        (it.producto as Complemento).nombre
                                    }
                                }</td><td>${
                                    it.cantidad
                                }</td><td>${
                                    if (it.tipoProducto == "Butaca") {
                                        (it.producto as Butaca).tipoButaca.precio.toDefaultMoneyString()
                                    } else {
                                        (it.producto as Complemento).precio.toDefaultMoneyString()
                                    }
                                }</td><td>${
                                    it.precio.toDefaultMoneyString()
                                }</td><td>${
                                    it.createdAt.toDefaultDateString()
                                }</td></tr>" }}
                            </tbody>
                        </table>
                        <ul>
                        <li style="font-size: larger;"><strong>Coste Total (sin IVA): </strong>${(totalLineas * 0.79).toDefaultMoneyString()}<br /><br /></li>
                            <li style="font-size: larger;"><strong>Coste Total (con IVA): </strong>${totalLineas.toDefaultMoneyString()}<br /><br /></li>
                        </ul>
                        <p class="text-right lead"><span style="font-weight: bold;"><img style="display: block; margin-left: auto; margin-right: auto;" src="https://e00-elmundo.uecdn.es/assets/multimedia/imagenes/2022/04/29/16512471939657.jpg" width="554" height="359" /><br /></span></p>
                    </div>
                </body>
            </html>
        """.trimIndent()
            Ok(file.writeText(html, Charsets.UTF_8))
        } catch (e: Exception) {
            logger.error { "Error al salvar fichero de recaudación: ${file.absolutePath}. ${e.message}" }
            Err(VentaError.VentaStorageError("Error al salvar fichero de recaudación: ${file.absolutePath}. ${e.message}"))
        }
    }
}