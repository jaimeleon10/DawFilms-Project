package org.example.dawfilmsinterface.cine.viewmodels

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.vaadin.open.Open
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import org.example.dawfilmsinterface.cine.viewmodels.LoginViewModel.*
import org.example.dawfilmsinterface.clientes.services.ClienteService
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import org.example.dawfilmsinterface.ventas.services.VentaService
import org.example.dawfilmsinterface.ventas.storage.storageHtml.StorageHtml
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate
import java.util.*
import kotlin.io.path.Path

private val logger = logging()

/**
 * ViewModel para la confirmación de compra.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property productoService Servicio de productos.
 * @property ventaService Servicio de ventas.
 * @property clienteService Servicio de clientes.
 * @property storage Almacenamiento HTML.
 */
class ConfirmarCompraViewModel(
    private val productoService: ProductoService,
    private val ventaService: VentaService,
    private val clienteService: ClienteService,
    private val storage: StorageHtml
) {
    val state: SimpleObjectProperty<GestionCompraState> = SimpleObjectProperty(GestionCompraState())

    lateinit var venta: Venta

    /**
     * Actualiza la lista de complementos en el estado.
     * @param listado Lista de complementos seleccionados.
     */
    fun updateToComplementosList(listado: MutableMap<String, Int>) {
        listado.keys.forEach { state.value.complementos.put(productoService.getComplementoByNombre(it).value, listado.getValue(it)) }
    }

    /**
     * Actualiza la lista de butacas en el estado.
     * @param listado Lista de IDs de butacas seleccionadas.
     */
    fun updateToButacasList(listado: MutableList<String>) {
        listado.forEach { state.value.butacas.add(productoService.getButacaById(it).value) }
    }

    /**
     * Realiza la compra.
     * @param cliente Estado del cliente.
     */
    fun realizarCompra(cliente: ClienteState) {
        añadirLineasCompra()
        val clienteCompra = clienteService.getById(cliente.id.toLong()).value
        val compra = Venta(
            id = UUID.randomUUID(),
            cliente = clienteCompra,
            lineas = state.value.lineas,
            fechaCompra = LocalDate.now(),
            createdAt = LocalDate.now(),
            updatedAt = LocalDate.now(),
            isDeleted = false
        )
        ventaService.createVenta(compra).onFailure {
            Alert(Alert.AlertType.ERROR).apply {
                this.title = "Error de compra"
                this.headerText = "Ha ocurrido un error durante la compra"
                this.contentText = "Volviendo al menú"
            }.showAndWait()
        }.onSuccess {
            venta = compra
            actualizarOcupacionButacas()
            actualizarStockComplementos()
        }

    }

    /**
     * Imprime el HTML de la compra.
     * @param emailCliente Correo electrónico del cliente.
     */
    fun imprimirHtml(emailCliente: String) {
        val venta = venta
        val idsButacas = state.value.butacas.joinToString("-") { it.id }
        val file = Path("FicherosDeCompra", "entrada_${idsButacas}_${venta.cliente.numSocio}_${venta.fechaCompra}.html").toFile()
        state.value.htmlFileName = file.name
        Alert(Alert.AlertType.CONFIRMATION).apply {
            this.title = "Compra realizada con éxito"
            this.headerText = "¿Deseas recibir el ticket de comprar en tú correo electrónico?"
            this.contentText = "Correo de envío: $emailCliente"
        }.showAndWait().ifPresent { opcion ->
            if (opcion == ButtonType.OK) {
                storage.exportHtml(venta, file)
                openHtml()
            }
        }
    }

    /**
     * Abre el HTML de la compra.
     */
    private fun openHtml() {
        val file = Path("data", "FicherosDeCompra", state.value.htmlFileName).toFile()
        val url = "http://localhost:63342/DawFilmsInterface/FicherosDeCompra/${file.name}"
        Open.open(url)
    }

    /**
     * Actualiza el estado de ocupación de las butacas.
     */
    private fun actualizarOcupacionButacas() {
        state.value.butacas.forEach {
            val butaca = Butaca(
                id = it.id,
                tipoProducto = it.tipoProducto,
                imagen = it.imagen,
                fila = it.fila,
                columna = it.columna,
                tipoButaca = it.tipoButaca,
                estadoButaca = it.estadoButaca,
                ocupacionButaca = OcupacionButaca.OCUPADA
            )
            productoService.updateButaca(it.id, butaca)
        }
    }

    /**
     * Actualiza el stock de los complementos.
     */
    private fun actualizarStockComplementos() {
        state.value.complementos.forEach {
            val complemento = Complemento(
                id = it.key.id,
                tipoProducto = it.key.tipoProducto,
                imagen = it.key.imagen,
                nombre = it.key.nombre,
                precio = it.key.precio,
                stock = it.key.stock - it.value,
                categoria = it.key.categoria,
            )
            productoService.updateComplemento(it.key.id, complemento)
        }
    }

    /**
     * Añade las líneas de compra al estado.
     */
    private fun añadirLineasCompra() {
        state.value.butacas.forEach {
            state.value.lineas.add(
                LineaVenta(
                    id = UUID.randomUUID(),
                    producto = it,
                    tipoProducto = it.tipoProducto,
                    cantidad = 1,
                    precio = it.tipoButaca.precio,
                    createdAt = LocalDate.now(),
                    updatedAt = LocalDate.now(),
                    isDeleted = false
                )
            )
        }
        state.value.complementos.forEach {
            state.value.lineas.add(
                LineaVenta(
                    id = UUID.randomUUID(),
                    producto = it.key,
                    tipoProducto = it.key.tipoProducto,
                    cantidad = it.value,
                    precio = it.key.precio,
                    createdAt = LocalDate.now(),
                    updatedAt = LocalDate.now(),
                    isDeleted = false
                )
            )
        }
    }

    /**
     * Elimina los últimos valores del estado.
     */
    fun deleteLastValues() {
        state.value.butacas = mutableListOf()
        state.value.complementos = mutableMapOf()
        state.value.lineas = mutableListOf()
    }

    /**
     * Estado de la gestión de compra.
     * @property htmlFileName Nombre del archivo HTML.
     * @property lineas Lista de líneas de venta.
     * @property complementos Mapa de complementos seleccionados (complemento y cantidad).
     * @property butacas Lista de butacas seleccionadas.
     */
    data class GestionCompraState (
        var htmlFileName: String = "",
        var lineas: MutableList<LineaVenta> = mutableListOf(),
        var complementos: MutableMap<Complemento, Int> = mutableMapOf(),
        var butacas: MutableList<Butaca> = mutableListOf(),
    )
}