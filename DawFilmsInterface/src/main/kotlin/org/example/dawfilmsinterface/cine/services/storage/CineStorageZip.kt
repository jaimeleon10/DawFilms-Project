package org.example.dawfilmsinterface.cine.services.storage

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.cine.errors.CineError
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.ventas.models.Venta
import java.io.File

interface CineStorageZip {
    fun exportToZip(fileToZip: File, dataProducto: List<Producto>, dataCliente: List<Cliente>): Result<File, CineError> //dataVentas: List<Venta>
    fun loadFromZip(fileToUnzip: File): Result<List<Any>, CineError>
}