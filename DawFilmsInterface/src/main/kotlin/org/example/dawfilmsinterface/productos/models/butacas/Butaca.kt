package org.example.dawfilmsinterface.productos.models.butacas

import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.time.LocalDate

/**
 * Clase que representa una butaca en el sistema.
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property id Identificador único de la butaca.
 * @property tipoProducto Tipo de producto al que pertenece la butaca. Por defecto es "Butaca".
 * @property imagen URL de la imagen de la butaca. Por defecto es una cadena vacía.
 * @property fila Número de fila donde se encuentra la butaca.
 * @property columna Número de columna donde se encuentra la butaca.
 * @property tipoButaca Tipo de la butaca (normal, VIP, etc.).
 * @property estadoButaca Estado actual de la butaca (activa, fuera de servicio, etc.).
 * @property ocupacionButaca Ocupación actual de la butaca (libre, reservada, ocupada, etc.).
 * @property createdAt Fecha de creación de la butaca. Por defecto es la fecha y hora actual.
 * @property updatedAt Fecha de la última actualización de la butaca. Por defecto es la fecha y hora actual.
 * @property isDeleted Indica si la butaca ha sido eliminada. Por defecto es falso.
 */
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