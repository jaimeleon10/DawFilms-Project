package org.example.dawfilmsinterface.ventas.repositories

import com.github.michaelbull.result.Result
import database.VentaEntity
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import java.time.LocalDate
import java.util.*

/**
 * Interfaz para la gestión de ventas en el repositorio.
 * @since 1.0.0
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
interface VentaRepository {

    /**
     * Encuentra todas las ventas de un cliente en una fecha dada.
     * @param cliente el cliente cuyas ventas se desean encontrar.
     * @param lineas las líneas de venta asociadas a las ventas.
     * @param fechaCompra la fecha de compra de las ventas.
     * @return una lista de ventas del cliente en la fecha especificada.
     */
    fun findAllVentasCliente(cliente: Cliente, lineas: List<LineaVenta>, fechaCompra: LocalDate): List<Venta>

    /**
     * Obtiene todas las líneas de venta.
     * @return una lista de todas las líneas de venta.
     */
    fun findAllLineas(): List<LineaVenta>

    /**
     * Obtiene todas las ventas.
     * @return una lista de todas las ventas.
     */
    fun findAllVentas(): List<VentaEntity>

    /**
     * Obtiene todas las líneas de venta por ID de venta.
     * @param idVenta el ID de la venta para la cual se desean obtener las líneas de venta.
     * @return una lista de líneas de venta asociadas al ID de la venta.
     */
    fun findAllLineasByID(idVenta: String): List<LineaVenta>

    /**
     * Encuentra una venta por su ID.
     * @param id el ID de la venta a buscar.
     * @return la venta correspondiente al ID especificado, o nulo si no se encuentra.
     */
    fun findById(id: UUID): Venta?

    /**
     * Encuentra ventas por fecha de compra.
     * @param fechaCompra la fecha de compra de las ventas.
     * @return una lista de ventas realizadas en la fecha especificada.
     */
    fun findVentasByDate(fechaCompra: LocalDate): List<VentaEntity>

    /**
     * Guarda una venta.
     * @param venta la venta a guardar.
     * @return la venta guardada.
     */
    fun save(venta: Venta): Venta

    /**
     * Actualiza una venta.
     * @param id el ID de la venta a actualizar.
     * @param venta la nueva información de la venta.
     * @return la venta actualizada, o nulo si la venta no existe.
     */
    fun update(id: UUID, venta: Venta): Venta?

    /**
     * Elimina una venta por su ID.
     * @param id el ID de la venta a eliminar.
     * @return la venta eliminada, o nulo si la venta no existe.
     */
    fun delete (id: UUID): Venta?

    /**
     * Elimina todas las ventas.
     */
    fun deleteAllVentas()

    /**
     * Valida la información de un cliente.
     * @param cliente el cliente a validar.
     * @return un [Result] que contiene el cliente si es válido, o un error si no lo es.
     */
    fun validateCliente(cliente: Cliente): Result<Cliente, VentaError>

    /**
     * Valida las líneas de venta.
     * @param lineas las líneas de venta a validar.
     * @return un [Result] que contiene las líneas de venta si son válidas, o un error si no lo son.
     */
    fun validateLineas(lineas: List<LineaVenta>): Result<List<LineaVenta>, VentaError>
}