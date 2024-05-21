package org.example.dawfilmsinterface.ventas.storage

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.Venta
import java.io.File

class VentaStorageImpl : VentaStorage {
    override fun storeJson(file: File, data: List<Venta>): Result<Long, VentaError> {
        TODO("Not yet implemented")
    }

    override fun loadJson(file: File): Result<List<Venta>, VentaError> {
        TODO("Not yet implemented")
    }
}