package org.example.dawfilmsinterface.productos.viewmodels

import com.github.michaelbull.result.onFailure
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Alert
import org.example.dawfilmsinterface.cine.viewmodels.LoginViewModel.*
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

    fun updateToComplementosList(listado: MutableMap<String, Int>) {
        listado.keys.forEach { state.value.complementos.put(serviceProducto.getComplementoByNombre(it).value, listado.getValue(it)) }
    }

    fun updateToButacasList(listado: MutableList<String>) {
        listado.forEach { state.value.butacas.add(serviceProducto.getButacaById(it).value) }
    }

    fun realizarCompra(cliente: ClienteState) {
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
        val lineas: MutableList<LineaVenta> = mutableListOf(),
        val complementos: MutableMap<Complemento, Int> = mutableMapOf(),
        val butacas: MutableList<Butaca> = mutableListOf(),
    )
}