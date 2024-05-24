package org.example.dawfilmsinterface.productos.models.butacas

import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.time.LocalDate

class Butaca (
    id: String,
    tipoProducto: String = "Butaca",
    imagen: String = "",
    val fila: Int,
    val columna: Int,
    val tipoButaca: TipoButaca,
    val estadoButaca: EstadoButaca,
    val ocupacionButaca: OcupacionButaca,
    createdAt: LocalDate = LocalDate.now(),
    updatedAt: LocalDate? = LocalDate.now(),
    isDeleted: Boolean? = false
): Producto(id, tipoProducto, imagen, createdAt, updatedAt, isDeleted) {

    override fun toString(): String {
        return "Butaca(id: $id, tipoButaca: $tipoButaca, imagen: $imagen, precio: ${tipoButaca.precio}, fila: $fila, columna: $columna, estadoButaca: $estadoButaca, ocupacionButaca: $ocupacionButaca, createdAt: $createdAt, updatedAt: $updatedAt, isDeleted: $isDeleted)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        //Comparamos clase padre
        if (!super.equals(other)) return false

        //Tipamos other como Butaca para acceder a sus atributos
        other as Butaca

        //Comparamos atributos
        if (fila != other.fila) return false
        if (columna != other.columna) return false
        if (tipoButaca != other.tipoButaca) return false
        if (estadoButaca != other.estadoButaca) return false
        if (ocupacionButaca != other.ocupacionButaca) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        //Se utiliza 31 porque es un número primero y es el menos probable de provocar errores
        result = 31 * result + fila
        result = 31 * result + columna
        result = 31 * result + tipoButaca.hashCode()
        result = 31 * result + estadoButaca.hashCode()
        result = 31 * result + ocupacionButaca.hashCode()
        return result
    }

    fun copy(
        id: String = this.id,
        tipoProducto: String = this.tipoProducto,
        imagen: String = this.imagen,
        fila: Int = this.fila,
        columna: Int = this.columna,
        tipoButaca: TipoButaca = this.tipoButaca,
        estadoButaca: EstadoButaca = this.estadoButaca,
        ocupacionButaca: OcupacionButaca = this.ocupacionButaca,
        createdAt: LocalDate = this.createdAt,
        updatedAt: LocalDate? = this.updatedAt,
        isDeleted: Boolean? = this.isDeleted
    ) : Butaca{
        return Butaca(id, tipoProducto, imagen, fila, columna, tipoButaca, estadoButaca, ocupacionButaca, createdAt, updatedAt, isDeleted)
    }
}