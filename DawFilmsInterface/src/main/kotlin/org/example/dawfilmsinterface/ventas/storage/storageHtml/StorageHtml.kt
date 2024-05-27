package org.example.dawfilmsinterface.ventas.storage.storageHtml

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.Venta
import java.io.File

interface StorageHtml {
    fun exportHtml(venta: Venta, file: File): Result<Unit, VentaError>
}