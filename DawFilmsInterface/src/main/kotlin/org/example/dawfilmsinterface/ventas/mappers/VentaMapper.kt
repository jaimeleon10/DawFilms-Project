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
            )
        }

        else -> throw IllegalArgumentException("Tipo de producto no soportado")
    }
}

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

fun List<Venta>.toVentaDtoList(): List<VentaDto>{
    return map { it.toVentaDto() }
}

fun List<VentaDto>.toVentaList(): List<Venta>{
    return map { it.toVenta() }
}

fun List<LineaVentaDto>.toLineaVentaList(): List<LineaVenta>{
    return map {it.toLineaVenta()}
}