package org.example.dawfilmsinterface.productos.storage.storageCsv

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.io.File

interface StorageCsv {
    fun storeCsv(file: File, data: List<Producto>): Result<Long, ProductoError>
    fun loadCsv(file: File): Result<List<Producto>, ProductoError>
}