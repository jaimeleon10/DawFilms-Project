package org.example.dawfilmsinterface.productos.storage.genericStorage

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.producto.Producto

interface ProductosStorage {
    fun storeCsv(): Result<Long, ProductoError>
    fun loadCsv(): Result<Producto, ProductoError>
    fun storeJson(): Result<Long, ProductoError>
    fun loadJson(): Result<Producto, ProductoError>
    fun storeXml(): Result<Long, ProductoError>
    fun loadXml(): Result<Producto, ProductoError>
    fun exportHtml(): Result<Long, ProductoError>
}