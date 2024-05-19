package org.example.dawfilmsinterface.productos.mappers

import database.ProductoEntity
import org.example.dawfilmsinterface.productos.dto.ButacaDto
import org.example.dawfilmsinterface.productos.dto.ComplementoDto
import org.example.dawfilmsinterface.productos.dto.ProductoDto
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.time.LocalDate

fun ProductoEntity.toProducto(): Producto {
    return when (this.tipo_producto) {
        "Butaca" -> Butaca(
            id = this.id,
            tipoProducto = this.tipo_producto,
            fila = this.fila_butaca!!.toInt(),
            columna = this.columna_butaca!!.toInt(),
            tipoButaca = TipoButaca.valueOf(this.tipo_butaca!!.uppercase()),
            estadoButaca = EstadoButaca.valueOf(this.estado_butaca!!.uppercase()),
            ocupacionButaca = OcupacionButaca.valueOf(this.ocupacion_butaca!!.uppercase()),
            createdAt = LocalDate.parse(this.created_at),
            updatedAt = this.updated_at.let { LocalDate.parse(it) },
            isDeleted = this.is_deleted.toInt() == 1
        )

        "Complemento" -> Complemento(
            id = this.id,
            tipoProducto = this.tipo_producto,
            nombre = this.nombre_complemento!!,
            precio = this.precio,
            stock = this.stock_complemento!!.toInt(),
            categoria = CategoriaComplemento.valueOf(this.categoria_complemento!!.uppercase()),
            createdAt = LocalDate.parse(this.created_at),
            updatedAt = this.updated_at.let { LocalDate.parse(it) },
            isDeleted = this.is_deleted.toInt() == 1
        )

        else -> throw IllegalArgumentException("Tipo de producto no soportado")
    }
}

fun ProductoEntity.toComplemento(): Complemento {
    return Complemento(
        id = this.id,
        tipoProducto = this.tipo_producto,
        nombre = this.nombre_complemento!!,
        precio = this.precio,
        stock = this.stock_complemento!!.toInt(),
        categoria = CategoriaComplemento.valueOf(this.categoria_complemento!!.uppercase()),
        createdAt = LocalDate.parse(this.created_at),
        updatedAt = this.updated_at.let { LocalDate.parse(it) },
        isDeleted = this.is_deleted.toInt() == 1
    )
}

fun ProductoEntity.toButaca(): Butaca {
    return Butaca(
        id = this.id,
        tipoProducto = this.tipo_producto,
        fila = this.fila_butaca!!.toInt(),
        columna = this.columna_butaca!!.toInt(),
        tipoButaca = TipoButaca.valueOf(this.tipo_butaca!!.uppercase()),
        estadoButaca = EstadoButaca.valueOf(this.estado_butaca!!.uppercase()),
        ocupacionButaca = OcupacionButaca.valueOf(this.ocupacion_butaca!!.uppercase()),
        createdAt = LocalDate.parse(this.created_at),
        updatedAt = this.updated_at.let { LocalDate.parse(it) },
        isDeleted = this.is_deleted.toInt() == 1
    )
}

fun Producto.toProductoDto(): ProductoDto {
    return when (this) {
        is Butaca -> ProductoDto(
            id = this.id,
            tipoProducto = "Butaca",
            filaButaca = this.fila,
            columnaButaca = this.columna,
            tipoButaca = this.tipoButaca.toString(),
            estadoButaca = this.estadoButaca.toString(),
            ocupacionButaca = this.ocupacionButaca.toString(),
            nombreComplemento = null,
            precioComplemento = null,
            categoriaComplemento = null,
            stockComplemento = null,
            createdAt = this.createdAt.toString(),
            updatedAt = this.updatedAt.toString(),
            isDeleted = this.isDeleted
        )

        is Complemento -> ProductoDto(
            id = this.id.toString(),
            tipoProducto = "Complemento",
            filaButaca = null,
            columnaButaca = null,
            tipoButaca = null,
            estadoButaca = null,
            ocupacionButaca = null,
            nombreComplemento = this.nombre,
            precioComplemento = this.precio,
            categoriaComplemento = this.categoria.toString(),
            stockComplemento = this.stock,
            createdAt = this.createdAt.toString(),
            updatedAt = this.updatedAt.toString(),
            isDeleted = this.isDeleted
        )

        else -> throw IllegalArgumentException("Tipo de producto no soportado")
    }
}

fun ProductoDto.toButaca(): Butaca {
    return Butaca(
        id = this.id,
        tipoProducto = this.tipoProducto,
        fila = this.filaButaca!!.toInt(),
        columna = this.columnaButaca!!.toInt(),
        tipoButaca = TipoButaca.valueOf(this.tipoButaca!!.uppercase()),
        estadoButaca = EstadoButaca.valueOf(this.estadoButaca!!.uppercase()),
        ocupacionButaca = OcupacionButaca.valueOf(this.ocupacionButaca!!.uppercase()),
        createdAt = LocalDate.parse(this.createdAt),
        updatedAt = this.updatedAt.let { LocalDate.parse(it) },
        isDeleted = this.isDeleted
    )
}

fun ProductoDto.toComplemento(): Complemento {
    return Complemento(
        id = this.id,
        tipoProducto = this.tipoProducto,
        nombre = this.nombreComplemento!!,
        precio = this.precioComplemento!!,
        stock = this.stockComplemento!!.toInt(),
        categoria = CategoriaComplemento.valueOf(this.categoriaComplemento!!.uppercase()),
        createdAt = LocalDate.parse(this.createdAt),
        updatedAt = this.updatedAt.let { LocalDate.parse(it) },
        isDeleted = this.isDeleted
    )
}

fun List<Producto>.toProductoDtoList(): List<ProductoDto> {
    return map { it.toProductoDto() }
}

fun List<ProductoDto>.toProductoList(): List<Producto> {
    return map {
        if (it.tipoProducto == "Butaca") it.toButaca()
        else it.toComplemento()
    }
}