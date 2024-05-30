package org.example.dawfilmsinterface.cine.services.storageHtml

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import java.io.File

interface StorageHtmlRecaudacion {
    fun exportHtml(listaLineas: List<LineaVenta>, file: File): Result<Unit, VentaError>
}