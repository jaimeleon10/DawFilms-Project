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
import java.io.File

/*class StorageHtmlImpl:StorageHtml {
    override fun exportHtml(listaLineas: List<LineaVenta>, file: File): Result<Unit, VentaError> {

        val totalLineas = 0.0

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
                        <h1><span style="text-decoration: underline; color: #3366ff;">Recaudación Cines DAWFILMS</span> 🍿🎥</h1>
                        <ul>
                            <li style="font-size: larger;"><strong>Fecha de compra: </strong>${listaLineas.createdAt.toDefaultDateString()}</li>
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
            <tr><td>${
                if (listaLineas. == "Butaca") {
                    "${"Butaca"}-${listaLineas.producto.id}"
                } else {
                    (listaLineas.producto as Complemento).nombre
                }
            }</td><td>${listaLineas.cantidad}</td><td>${
                if (listaLineas.tipoProducto == "Butaca") {
                    (listaLineas.producto as Butaca).tipoButaca.precio.toDefaultMoneyString()
                } else {
                    (listaLineas.producto as Complemento).precio.toDefaultMoneyString()
                }
            }</td><td>${(listaLineas.cantidad * (if (listaLineas.tipoProducto == "Butaca") {
                (listaLineas.producto as Butaca).tipoButaca.precio
            } else {
                (listaLineas.producto as Complemento).precio
            } )).toDefaultMoneyString()
            }</td></tr>" }}
                            </tbody>
                        </table>
                        <ul>
                        <li style="font-size: larger;"><strong>Coste Total(sin IVA): </strong>${(listaLineas.precio * 0.79).toDefaultMoneyString()}<br /><br /></li>
                            <li style="font-size: larger;"><strong>Coste Total(con IVA): </strong>${venta.total.toDefaultMoneyString()}<br /><br /></li>
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
}*/