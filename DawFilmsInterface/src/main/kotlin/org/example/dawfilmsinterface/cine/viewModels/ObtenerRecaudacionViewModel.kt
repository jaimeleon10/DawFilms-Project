package org.example.dawfilmsinterface.cine.viewmodels

import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.productos.service.ProductoService

class ObtenerRecaudacionViewModel(
    private val service : ProductoService
) {

    data class RecaudacionState(
        val producto : Producto,
        val fecha : String,
        val cantidad : Int,
        val precioUnitario : Double,
        val precioTotal : Double
    )
}