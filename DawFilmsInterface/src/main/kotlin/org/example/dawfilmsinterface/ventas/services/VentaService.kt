package org.example.dawfilmsinterface.ventas.services

import com.github.michaelbull.result.Result
import database.VentaEntity
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import java.time.LocalDate
import java.util.*

interface VentaService {
    fun getById(id: UUID): Result<Venta, VentaError>
    fun createVenta(venta: Venta): Result<Venta, VentaError>
    fun getAllLineas(): Result<List<LineaVenta>, VentaError>
    fun getAllVentasByCliente(cliente: Cliente, lineas: List<LineaVenta>, fecha: LocalDate): Result<List<Venta>, VentaError>
    fun getAllVentasEntity(): Result<List<VentaEntity>, VentaError>
    fun getAllLineasByVentaID(id: String): Result<List<LineaVenta>, VentaError>
    fun deleteAllVentas(): Result<Unit, VentaError>
}