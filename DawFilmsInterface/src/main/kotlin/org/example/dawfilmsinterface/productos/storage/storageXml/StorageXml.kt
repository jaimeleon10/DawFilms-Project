package org.example.dawfilmsinterface.productos.storage.storageXml

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.io.File

interface StorageXml {
    fun storeXml(file: File, data: List<Producto>): Result<Long, ProductoError>
    fun loadXml(file: File): Result<List<Producto>, ProductoError>
}