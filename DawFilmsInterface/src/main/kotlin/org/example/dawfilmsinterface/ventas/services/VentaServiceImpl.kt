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

/**
 * Implementación de [VentaService] que proporciona funcionalidades para interactuar con las ventas.
 * @property ventaRepository El repositorio de ventas que maneja el almacenamiento y recuperación de datos de las ventas.
 * @since 1.0.0
 * @param ventaRepository El repositorio de ventas.
 * @param authors Los autores de la implementación.
 */
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

    override fun getAllVentasByCliente(cliente: Cliente, lineas: List<LineaVenta>, fecha: LocalDate): Result<List<Venta>, VentaError> {
        logger.debug { "Obteniendo ventas del cliente ${cliente.nombre}" }
        return Ok(ventaRepository.findAllVentasCliente(cliente, lineas, fecha))
    }

    override fun getAllVentasEntity(): Result<List<VentaEntity>, VentaError> {
        logger.debug { "Obteniendo todas las ventas}" }
        return Ok(ventaRepository.findAllVentas())
    }

    override fun getAllLineasByVentaID(id: String): Result<List<LineaVenta>, VentaError> {
        logger.debug { "Obteniendo todas las lineas de venta del id de venta: $id}" }
        return Ok(ventaRepository.findAllLineasByID(id))
    }

    override fun getAllVentasByDate(fechaCompra: LocalDate): Result<List<VentaEntity>, VentaError> {
        logger.debug { "Obteniendo todas las ventas dada una fecha" }
        return Ok(ventaRepository.findVentasByDate(fechaCompra))
    }

    override fun deleteAllVentas(): Result<Unit, VentaError> {
        logger.debug { "Borrando todas las ventas" }
        return Ok(ventaRepository.deleteAllVentas())
    }
}