package org.example.dawfilmsinterface.ventas.mappers

import database.LineaVentaEntity
import database.VentaEntity
import org.example.dawfilmsinterface.clientes.mappers.toCliente
import org.example.dawfilmsinterface.clientes.mappers.toClienteDto
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.productos.dto.ButacaDto
import org.example.dawfilmsinterface.productos.mappers.toButaca
import org.example.dawfilmsinterface.productos.mappers.toComplemento
import org.example.dawfilmsinterface.productos.mappers.toProductoDto
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.ventas.dto.LineaVentaDto
import org.example.dawfilmsinterface.ventas.dto.VentaDto
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import java.time.LocalDate
import java.util.*

/**
 * Extensión para convertir una entidad de LineaVenta a un objeto de dominio LineaVenta.
 * @param producto Producto asociado a la línea de venta.
 * @return Objeto de dominio LineaVenta.
 * @since 1.0.0
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
fun LineaVentaEntity.toLineaVenta(producto: Producto): LineaVenta {
    return LineaVenta(
        id = UUID.fromString(this.id),
        producto = producto,
        tipoProducto = this.producto_tipo,
        cantidad = this.cantidad.toInt(),
        precio = this.precio,
        createdAt = LocalDate.parse(this.created_at),
        updatedAt = LocalDate.parse(this.updated_at),
    )
}

/**
 * Extensión para convertir una entidad de Venta a un objeto de dominio Venta.
 * @param cliente Cliente asociado a la venta.
 * @param lineas Lista de líneas de venta.
 * @param fechaCompra Fecha de compra de la venta.
 * @return Objeto de dominio Venta.
 */
fun VentaEntity.toVenta(cliente: Cliente, lineas: List<LineaVenta>, fechaCompra: LocalDate): Venta {
    return Venta(
        id = UUID.fromString(this.id),
        cliente = cliente,
        lineas = lineas,
        fechaCompra = fechaCompra,
        createdAt = LocalDate.parse(this.created_at),
        updatedAt = LocalDate.parse(this.updated_at),
    )
}

/**
 * Extensión para convertir un objeto de dominio LineaVenta a su correspondiente DTO.
 * @return DTO de LineaVenta.
 */
fun LineaVenta.toLineaVentaDto(): LineaVentaDto {
    return when (this.producto) {
        is Butaca -> {
            LineaVentaDto(
                id = this.id.toString(),
                producto = this.producto.toProductoDto(),
                tipoProducto = "Butaca",
                cantidad = this.cantidad,
                precio = this.precio,
                createdAt = this.createdAt.toString(),
                updatedAt = this.updatedAt.toString(),
                isDeleted = this.isDeleted
            )
        }

        is Complemento -> {
            LineaVentaDto(
                id = this.id.toString(),
                producto = this.producto.toProductoDto(),
                tipoProducto = "Complemento",
                cantidad = this.cantidad,
                precio = this.precio,
                createdAt = this.createdAt.toString(),
                updatedAt = this.updatedAt.toString(),
                isDeleted = this.isDeleted
            )
        }

        else -> throw IllegalArgumentException("Tipo de producto no soportado")
    }
}

/**
 * Extensión para convertir un DTO de LineaVenta a su correspondiente objeto de dominio.
 * @return Objeto de dominio LineaVenta.
 */
fun LineaVentaDto.toLineaVenta(): LineaVenta {
    return when (this.producto.tipoProducto) {
        "Butaca" -> {
            LineaVenta(
                id = UUID.fromString(this.id),
                producto = this.producto.toButaca(),
                tipoProducto = "Butaca",
                cantidad = this.cantidad,
                precio = this.precio,
                createdAt = LocalDate.parse(this.createdAt),
                updatedAt = LocalDate.parse(this.updatedAt),
            )
        }

        "Complemento" -> {
            LineaVenta(
                id = UUID.fromString(this.id),
                producto = this.producto.toComplemento(),
                tipoProducto = "Complemento",
                cantidad = this.cantidad,
                precio = this.precio,
                createdAt = LocalDate.parse(this.createdAt),
                updatedAt = LocalDate.parse(this.updatedAt),
            )
        }

        else -> throw IllegalArgumentException("Tipo de producto no soportado")
    }
}

/**
 * Extensión para convertir un objeto de dominio Venta a su correspondiente DTO.
 * @return DTO de Venta.
 */
fun Venta.toVentaDto(): VentaDto {
    return VentaDto(
        id = this.id.toString(),
        cliente = this.cliente.toClienteDto(),
        lineas = this.lineas.map { it.toLineaVentaDto() },
        total = this.lineas.sumOf { it.precio },
        fechaCompra = this.fechaCompra.toString(),
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString(),
        isDeleted = this.isDeleted
    )
}

/**
 * Extensión para convertir un DTO de Venta a su correspondiente objeto de dominio.
 * @return Objeto de dominio Venta.
 */
fun VentaDto.toVenta(): Venta{
    return Venta(
        id = UUID.fromString(this.id),
        cliente = this.cliente.toCliente(),
        lineas = this.lineas.toLineaVentaList(),
        fechaCompra = LocalDate.parse(this.fechaCompra),
        createdAt = LocalDate.parse(this.createdAt),
        updatedAt = LocalDate.parse(this.updatedAt),
        isDeleted = this.isDeleted
    )
}

/**
 * Extensión para convertir una lista de objetos de dominio Venta a una lista de DTOs de Venta.
 * @return Lista de DTOs de Venta.
 */
fun List<Venta>.toVentaDtoList(): List<VentaDto>{
    return map { it.toVentaDto() }
}

/**
 * Extensión para convertir una lista de DTOs de Venta a una lista de objetos de dominio Venta.
 * @return Lista de objetos de dominio Venta.
 */
fun List<VentaDto>.toVentaList(): List<Venta>{
    return map { it.toVenta() }
}

/**
 * Extensión para convertir una lista de DTOs de LineaVenta a una lista de objetos de dominio LineaVenta.
 * @return Lista de objetos de dominio LineaVenta.
 */
fun List<LineaVentaDto>.toLineaVentaList(): List<LineaVenta>{
    return map {it.toLineaVenta()}
}