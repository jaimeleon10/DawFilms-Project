package org.example.dawfilmsinterface.productos.viewmodels

import com.github.michaelbull.result.onFailure
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Alert
import org.example.dawfilmsinterface.cine.viewModels.LoginViewModel.*
import org.example.dawfilmsinterface.clientes.services.ClienteService
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.example.dawfilmsinterface.productos.storage.genericStorage.ProductosStorage
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import org.example.dawfilmsinterface.ventas.services.VentaService
import org.lighthousegames.logging.logging
import java.time.LocalDate
import java.util.*
import kotlin.io.path.Path

private val logger = logging()

class ConfirmarCompraViewModel(
    private val serviceProducto: ProductoService,
    private val ventaService: VentaService,
    private val clienteService: ClienteService,
    private val storage: ProductosStorage
) {
    val state: SimpleObjectProperty<GestionCompraState> = SimpleObjectProperty(GestionCompraState())

    lateinit var venta: Venta
    val lineas: MutableList<LineaVenta> = mutableListOf()

    fun updateToComplementosList(listado: MutableMap<String, Int>): MutableMap<Complemento, Int> {
        val newList: MutableMap<Complemento, Int> = mutableMapOf()
        listado.keys.forEach { newList.put(serviceProducto.getComplementoByNombre(it).value, listado.getValue(it)) }
        return newList
    }

    fun updateToButacasList(listado: MutableList<String>): MutableList<Butaca> {
        val newList: MutableList<Butaca> = mutableListOf()
        listado.forEach { newList.add(serviceProducto.getButacaById(it).value) }
        return newList
    }

    fun realizarCompra(cliente: ClienteState) {
        val clienteCompra = clienteService.getById(cliente.id.toLong()).value
        val compra = Venta(
            id = UUID.randomUUID(),
            cliente = clienteCompra,
            lineas = lineas,
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
        }

        venta = compra
    }

    fun imprimirHtml() {
        val venta = venta
        val idsButacas = state.value.butacas.joinToString("-") { it.id }
        val file = Path("FicherosDeCompra", "entrada_${idsButacas}_${venta.cliente.numSocio}_${venta.fechaCompra}.html").toFile()
        storage.exportHtml(venta, file)
    }

    data class GestionCompraState (
        val complementos: List<Complemento> = listOf(),
        val butacas: List<Butaca> = listOf(),
    )
}