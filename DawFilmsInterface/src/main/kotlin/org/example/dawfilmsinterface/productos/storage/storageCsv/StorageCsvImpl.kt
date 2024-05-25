package org.example.dawfilmsinterface.productos.storage.storageCsv

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.dto.ProductoDto
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.mappers.toButaca
import org.example.dawfilmsinterface.productos.mappers.toComplemento
import org.example.dawfilmsinterface.productos.mappers.toProductoDto
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.lighthousegames.logging.logging
import java.io.File
import java.time.LocalDate

private val logger = logging()

class StorageCsvImpl: StorageCsv {
    override fun storeCsv(file: File, data: List<Producto>): Result<Long, ProductoError> {
        logger.debug { "Cargando datos en fichero $file" }
        return try {
            file.writeText("id,tipo producto,imagen,precio,fila butaca,columna butaca,tipo butaca,estado butaca,ocupacion butaca,nombre complemento,stock complemento,categoria complemento,created at,updated at,is deleted\n")
            data.map { it.toProductoDto() }
                .forEach { productoDto ->
                    if (productoDto.tipoProducto == "Butaca") {
                        file.appendText("${productoDto.id},${productoDto.tipoProducto},${productoDto.imagen},${TipoButaca.valueOf(productoDto.tipoButaca!!).precio},${productoDto.filaButaca},${productoDto.columnaButaca},${productoDto.tipoButaca},${productoDto.estadoButaca},${productoDto.ocupacionButaca},${productoDto.nombreComplemento},${productoDto.stockComplemento},${productoDto.categoriaComplemento},${productoDto.createdAt},${productoDto.updatedAt},${productoDto.isDeleted}\n")
                    } else {
                        file.appendText("${productoDto.id},${productoDto.tipoProducto},${productoDto.imagen},${productoDto.precioComplemento},${productoDto.filaButaca},${productoDto.columnaButaca},${productoDto.tipoButaca},${productoDto.estadoButaca},${productoDto.ocupacionButaca},${productoDto.nombreComplemento},${productoDto.stockComplemento},${productoDto.categoriaComplemento},${productoDto.createdAt},${productoDto.updatedAt},${productoDto.isDeleted}\n")
                    }
                }
            Ok(data.size.toLong())
        } catch (e: Exception) {
            logger.error { "Error al guardar el fichero csv de productos: ${e.message}" }
            Err(ProductoError.ProductoStorageError("Error al guardar el fichero csv de productos: ${e.message}"))
        }
    }

    override fun loadCsv(file: File): Result<List<Producto>, ProductoError> {
        logger.debug { "Cargando datos del fichero $file" }
        return try {
            val productsList = file.readLines().drop(1)
                .map {
                    val data = it.split(",")
                    if (data[1] == "Butaca") {
                        ProductoDto(
                            id = data[0],
                            tipoProducto = data[1],
                            imagen = data[2],
                            filaButaca = data[4].toInt(),
                            columnaButaca = data[5].toInt(),
                            tipoButaca = data[6],
                            estadoButaca = data[7],
                            ocupacionButaca = data[8],
                            nombreComplemento = null,
                            precioComplemento = null,
                            categoriaComplemento = null,
                            stockComplemento = null,
                            createdAt = if (data[12].isNullOrEmpty()) LocalDate.now().toString() else data[12],
                            updatedAt = if (data[13].isNullOrEmpty()) LocalDate.now().toString() else data[13],
                            isDeleted = if (data[14].isEmpty()) false else data[14].toBoolean()
                        ).toButaca()
                    } else {
                        ProductoDto(
                            id = data[0],
                            tipoProducto = data[1],
                            imagen = data[2],
                            filaButaca = null,
                            columnaButaca = null,
                            tipoButaca = null,
                            estadoButaca = null,
                            ocupacionButaca = null,
                            nombreComplemento = data[9],
                            precioComplemento = data[3].toDouble(),
                            categoriaComplemento = data[11],
                            stockComplemento = data[10].toInt(),
                            createdAt = if (data[12].isNullOrEmpty()) LocalDate.now().toString() else data[12],
                            updatedAt = if (data[13].isNullOrEmpty()) LocalDate.now().toString() else data[13],
                            isDeleted = if (data[14].isEmpty()) false else data[14].toBoolean()
                        ).toComplemento()
                    }
                }
            Ok(productsList)
        } catch (e: Exception) {
            logger.error { "Error al cargar el fichero csv de productos: ${e.message}" }
            Err(ProductoError.ProductoStorageError("Error al cargar el fichero csv de productos: ${e.message}"))
        }
    }
}