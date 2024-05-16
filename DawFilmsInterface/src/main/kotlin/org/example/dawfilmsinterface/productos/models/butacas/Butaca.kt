package org.example.dawfilmsinterface.productos.models.butacas

import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.time.LocalDate

class Butaca (
    id: String,
    tipoProducto: String = "Butaca",
    val fila: Int,
    val columna: Int,
    val tipoButaca: TipoButaca,
    val estadoButaca: EstadoButaca,
    val ocupacionButaca: OcupacionButaca,
    createdAt: LocalDate = LocalDate.now(),
    updatedAt: LocalDate? = null,
    isDeleted: Boolean? = false
): Producto(id, tipoProducto, createdAt, updatedAt, isDeleted) {

    override fun toString(): String {
        return "Butaca(id: $id, tipoButaca: $tipoButaca, precio: ${tipoButaca.precio}, fila: $fila, columna: $columna, estadoButaca: $estadoButaca, ocupacionButaca: $ocupacionButaca, createdAt: $createdAt, updatedAt: $updatedAt, isDeleted: $isDeleted)"
    }
}