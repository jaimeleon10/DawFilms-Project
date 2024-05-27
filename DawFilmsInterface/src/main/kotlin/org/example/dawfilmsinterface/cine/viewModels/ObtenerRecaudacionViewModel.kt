package org.example.dawfilmsinterface.cine.viewmodels

import javafx.beans.property.SimpleObjectProperty
import org.example.dawfilmsinterface.productos.models.producto.Producto

class ObtenerRecaudacionViewModel{
    val state : SimpleObjectProperty<RecaudacionState> = SimpleObjectProperty(RecaudacionState())

    data class RecaudacionState(
        val typesProducto : List<String> = emptyList(),

        val cantidad : Int = 3,
        val precioUnitario : Double = 5.00,
        val precioTotal : Double = 10.0,

        val producto: ProductoState = ProductoState(),

        val productos : List<Producto> = emptyList()
    )

    data class ProductoState(
        val precio : Double = 5.00,
    )

    enum class TipoFiltroProducto(val value : String){
        TODOS("TODOS"), BUTACAS("BUTACAS"), COMPLEMENTOS("COMPLEMENTOS")
    }
}