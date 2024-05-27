package org.example.dawfilmsinterface.ventas.services

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import java.util.*

interface VentaService {
    fun getById(id: UUID): Result<Venta, VentaError>
    fun createVenta(venta: Venta): Result<Venta, VentaError>
    fun getAllLineas(): Result<List<LineaVenta>, VentaError>
}