package org.example.dawfilmsinterface.productos.storage.genericStorage

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.Venta
import java.io.File

interface ProductosStorage {
    fun storeCsv(file: File, data: List<Producto>): Result<Long, ProductoError>
    fun loadCsv(file: File): Result<List<Producto>, ProductoError>
    fun storeJson(file: File, data: List<Producto>): Result<Long, ProductoError>
    fun loadJson(file: File): Result<List<Producto>, ProductoError>
    fun storeXml(file: File, data: List<Producto>): Result<Long, ProductoError>
    fun loadXml(file: File): Result<List<Producto>, ProductoError>
    fun saveImage(fileName: File): Result<File, ProductoError>
    fun loadImage(fileName: String): Result<File, ProductoError>
    fun deleteImage(fileImage: File): Result<Unit, ProductoError>
    fun deleteAllImages(): Result<Long, ProductoError>
    fun updateImage(imageName: String, newFileImage: File): Result<File, ProductoError>
    fun exportToZip(fileToZip: File, data: List<Producto>): Result<File, ProductoError>
    fun loadFromZip(fileToUnzip: File): Result<List<Producto>, ProductoError>
}