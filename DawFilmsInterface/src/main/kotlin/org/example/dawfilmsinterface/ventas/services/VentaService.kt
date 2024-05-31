package org.example.dawfilmsinterface.ventas.services

import com.github.michaelbull.result.Result
import database.VentaEntity
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import java.time.LocalDate
import java.util.*

/**
 * Interfaz que define las operaciones disponibles para interactuar con las ventas.
 * @since 1.0.0
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
interface VentaService {

    /**
     * Obtiene una venta por su ID.
     * @param id El ID de la venta.
     * @return Un [Result] que contiene la venta si se encuentra, o un error [VentaError] si no se encuentra.
     */
    fun getById(id: UUID): Result<Venta, VentaError>

    /**
     * Crea una nueva venta.
     * @param venta La venta a crear.
     * @return Un [Result] que contiene la venta creada si la operación tiene éxito, o un error [VentaError] si falla.
     */
    fun createVenta(venta: Venta): Result<Venta, VentaError>

    /**
     * Obtiene todas las líneas de venta.
     * @return Un [Result] que contiene una lista de [LineaVenta] si la operación tiene éxito, o un error [VentaError] si falla.
     */
    fun getAllLineas(): Result<List<LineaVenta>, VentaError>

    /**
     * Obtiene todas las ventas de un cliente en una fecha determinada.
     * @param cliente El cliente cuyas ventas se desean obtener.
     * @param lineas Las líneas de venta asociadas a las ventas.
     * @param fecha La fecha de compra de las ventas.
     * @return Un [Result] que contiene una lista de [Venta] si la operación tiene éxito, o un error [VentaError] si falla.
     */
    fun getAllVentasByCliente(cliente: Cliente, lineas: List<LineaVenta>, fecha: LocalDate): Result<List<Venta>, VentaError>

    /**
     * Obtiene todas las ventas como entidades.
     * @return Un [Result] que contiene una lista de [VentaEntity] si la operación tiene éxito, o un error [VentaError] si falla.
     */
    fun getAllVentasEntity(): Result<List<VentaEntity>, VentaError>

    /**
     * Obtiene todas las líneas de venta asociadas a una venta por su ID.
     * @param id El ID de la venta.
     * @return Un [Result] que contiene una lista de [LineaVenta] si la operación tiene éxito, o un error [VentaError] si falla.
     */
    fun getAllLineasByVentaID(id: String): Result<List<LineaVenta>, VentaError>

    /**
     * Obtiene todas las ventas realizadas en una fecha determinada.
     * @param fechaCompra La fecha de compra de las ventas.
     * @return Un [Result] que contiene una lista de [VentaEntity] si la operación tiene éxito, o un error [VentaError] si falla.
     */
    fun getAllVentasByDate(fechaCompra: LocalDate): Result<List<VentaEntity>, VentaError>

    /**
     * Elimina todas las ventas.
     * @return Un [Result] que indica el éxito de la operación o un error [VentaError] si falla.
     */
    fun deleteAllVentas(): Result<Unit, VentaError>
}