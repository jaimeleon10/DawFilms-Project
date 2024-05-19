package org.example.dawfilmsinterface.productos.storage.storageJson

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.io.File

interface StorageJson {
    fun storeJson(file: File, data: List<Producto>): Result<Long, ProductoError>
    fun loadJson(file: File): Result<List<Producto>, ProductoError>
}