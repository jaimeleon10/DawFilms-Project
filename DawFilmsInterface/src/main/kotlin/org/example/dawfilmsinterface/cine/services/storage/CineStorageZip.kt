package org.example.dawfilmsinterface.cine.services.storage

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.cine.errors.CineError
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.ventas.models.Venta
import java.io.File

interface CineStorageZip {
    fun exportToZip(fileToZip: File, dataProductos: List<Producto>, dataClientes: List<Cliente>, dataVentas: List<Venta>): Result<File, CineError>
    fun loadFromZip(fileToUnzip: File): Result<List<Any>, CineError>
}