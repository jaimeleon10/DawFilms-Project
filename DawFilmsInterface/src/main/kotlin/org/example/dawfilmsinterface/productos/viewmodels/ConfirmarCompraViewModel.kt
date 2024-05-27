package org.example.dawfilmsinterface.productos.viewmodels

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import org.example.dawfilmsinterface.cine.viewmodels.LoginViewModel.*
import org.example.dawfilmsinterface.clientes.services.ClienteService
import org.example.dawfilmsinterface.locale.toDefaultMoneyString
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import org.example.dawfilmsinterface.ventas.services.VentaService
import org.example.dawfilmsinterface.ventas.storage.storageHtml.StorageHtml
import org.lighthousegames.logging.logging
import java.time.LocalDate
import java.util.*
import kotlin.io.path.Path

private val logger = logging()

class ConfirmarCompraViewModel(
    private val productoService: ProductoService,
    private val ventaService: VentaService,
    private val clienteService: ClienteService,
    private val storage: StorageHtml
) {
    val state: SimpleObjectProperty<GestionCompraState> = SimpleObjectProperty(GestionCompraState())

    lateinit var venta: Venta

    fun updateToComplementosList(listado: MutableMap<String, Int>) {
        listado.keys.forEach { state.value.complementos.put(productoService.getComplementoByNombre(it).value, listado.getValue(it)) }
    }

    fun updateToButacasList(listado: MutableList<String>) {
        listado.forEach { state.value.butacas.add(productoService.getButacaById(it).value) }
    }

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

    fun imprimirHtml() {
        val venta = venta
        val idsButacas = state.value.butacas.joinToString("-") { it.id }
        val file = Path("FicherosDeCompra", "entrada_${idsButacas}_${venta.cliente.numSocio}_${venta.fechaCompra}.html").toFile()
        Alert(Alert.AlertType.CONFIRMATION).apply {
            this.title = "Compra realizada con éxito"
            this.headerText = "Tu compra ha sido realizada con éxito"
            this.contentText = "Precio total de la compra: ${venta.total.toDefaultMoneyString()}"
        }.showAndWait().ifPresent { opcion ->
            if (opcion == ButtonType.OK) {
                storage.exportHtml(venta, file)
            }
        }
    }

    data class GestionCompraState (
        val lineas: MutableList<LineaVenta> = mutableListOf(),
        val complementos: MutableMap<Complemento, Int> = mutableMapOf(),
        val butacas: MutableList<Butaca> = mutableListOf(),
    )
}