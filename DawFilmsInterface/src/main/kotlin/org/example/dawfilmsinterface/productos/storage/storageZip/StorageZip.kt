package org.example.dawfilmsinterface.productos.storage.storageZip

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.io.File

interface StorageZip {
    fun exportToZip(fileToZip: File, data: List<Producto>): Result<File, ProductoError>
    fun loadFromZip(fileToUnzip: File): Result<List<Producto>, ProductoError>
}