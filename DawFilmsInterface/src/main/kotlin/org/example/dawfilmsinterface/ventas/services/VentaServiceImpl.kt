package org.example.dawfilmsinterface.ventas.services

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import database.VentaEntity
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import org.example.dawfilmsinterface.ventas.repositories.VentaRepository
import org.lighthousegames.logging.logging
import java.time.LocalDate
import java.util.*

private val logger = logging()

class VentaServiceImpl(
    private val ventaRepository: VentaRepository,
) : VentaService {
    override fun getById(id: UUID): Result<Venta, VentaError> {
        logger.debug { "Obteniendo venta por id: $id" }
        return ventaRepository.findById(id)
            ?.let { Ok(it) }
            ?: Err(VentaError.VentaNoEncontrada("Venta no encontrada con id: $id"))
    }

    override fun createVenta(venta: Venta): Result<Venta, VentaError> {
        logger.debug { "Creando venta: $venta" }
        return ventaRepository.validateCliente(venta.cliente)
            .andThen { ventaRepository.validateLineas(venta.lineas) }
            .andThen { Ok(ventaRepository.save(venta)) }
    }

    override fun getAllLineas(): Result<List<LineaVenta>, VentaError> {
        logger.debug { "Obteniendo linas de ventas" }
        return Ok(ventaRepository.findAllLineas())
    }

    override fun getAllVentasCliente(cliente: Cliente, lineas: List<LineaVenta>, fecha: LocalDate): Result<List<Venta>, VentaError> {
        logger.debug { "Obteniendo ventas del cliente ${cliente.nombre}" }
        return Ok(ventaRepository.findAllVentasCliente(cliente, lineas, fecha))
    }

    override fun getAllVentas(): Result<List<VentaEntity>, VentaError> {
        logger.debug { "Obteniendo todas las ventas}" }
        return Ok(ventaRepository.findAllVentas())
    }

    override fun getAllLineasByVentaID(id: String): Result<List<LineaVenta>, VentaError> {
        logger.debug { "Obteniendo todas las lineas de venta del id de venta: $id}" }
        return Ok(ventaRepository.findAllLineasByID(id))
    }
}