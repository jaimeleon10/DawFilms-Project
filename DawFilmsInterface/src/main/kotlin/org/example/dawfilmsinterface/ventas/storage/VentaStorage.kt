package org.example.dawfilmsinterface.ventas.storage

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.Venta
import java.io.File

interface VentaStorage {
    fun storeJson(file: File, data: List<Venta>): Result<Long, VentaError>
    fun loadJson(file: File): Result<List<Venta>, VentaError>
}