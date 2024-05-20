package org.example.dawfilmsinterface.productos.storage.storageCsv

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.dto.ProductoDto
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.mappers.toButaca
import org.example.dawfilmsinterface.productos.mappers.toComplemento
import org.example.dawfilmsinterface.productos.mappers.toProductoDto
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

class StorageCsvImpl: StorageCsv {
    override fun storeCsv(file: File, data: List<Producto>): Result<Long, ProductoError> {
        logger.debug { "Cargando datos en fichero $file" }
        return try {
            file.writeText("ID,Tipo Producto,Imagen,Fila Butaca,Columna Butaca,Tipo Butaca,EstadoButaca,Ocupacion Butaca,Nombre Complemento,Precio Complemento,Categoria Complemento, Stock Complemento, CreatedAt, UpdatedAt, IsDeleted\n")
            data.map { it.toProductoDto() }
                .forEach {
                    file.appendText("${it.id},${it.tipoProducto},${it.imagen},${it.filaButaca},${it.columnaButaca},${it.tipoButaca},${it.estadoButaca},${it.ocupacionButaca},${it.nombreComplemento},${it.precioComplemento},${it.categoriaComplemento},${it.stockComplemento},${it.createdAt},${it.updatedAt},${it.isDeleted}\n")
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
                    if (data[1] == "Butaca") ProductoDto(
                        id = data[0],
                        tipoProducto = data[1],
                        imagen = data[2],
                        filaButaca = data[3].toInt(),
                        columnaButaca = data[4].toInt(),
                        tipoButaca = data[5],
                        estadoButaca = data[6],
                        ocupacionButaca = data[7],
                        nombreComplemento = null,
                        precioComplemento = null,
                        categoriaComplemento = null,
                        stockComplemento = null,
                        createdAt = data[12],
                        updatedAt = data[13],
                        isDeleted = data[14].toBoolean()
                    ).toButaca() else ProductoDto(
                        id = data[0],
                        tipoProducto = data[1],
                        imagen = data[2],
                        filaButaca = null,
                        columnaButaca = null,
                        tipoButaca = null,
                        estadoButaca = null,
                        ocupacionButaca = null,
                        nombreComplemento = data[8],
                        precioComplemento = data[9].toDouble(),
                        categoriaComplemento = data[10],
                        stockComplemento = data[11].toInt(),
                        createdAt = data[12],
                        updatedAt = data[13],
                        isDeleted = data[14].toBoolean()
                    ).toComplemento()
                }
            Ok(productsList)
        } catch (e: Exception) {
            logger.error { "Error al cargar el fichero csv de productos: ${e.message}" }
            Err(ProductoError.ProductoStorageError("Error al cargar el fichero csv de productos: ${e.message}"))
        }
    }
}