package org.example.dawfilmsinterface.ventas.models

import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.time.LocalDate
import java.util.*

data class LineaVenta(
    val id: UUID = UUID.randomUUID(),
    val producto: Producto,
    val tipoProducto: String,
    val cantidad: Int,
    val precio: Double,
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now(),
    val isDeleted: Boolean = false
){
    private fun isButaca(): Boolean{
        return tipoProducto == "Butaca"
    }

    fun obtenerIdProducto(): String{
        return if(isButaca()){
            "Butaca " + (producto as Butaca).id
        } else {
            (producto as Complemento).nombre
        }
    }
}