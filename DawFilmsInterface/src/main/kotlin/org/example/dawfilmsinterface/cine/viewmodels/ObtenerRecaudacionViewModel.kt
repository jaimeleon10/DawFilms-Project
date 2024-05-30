package org.example.dawfilmsinterface.cine.viewmodels

import com.github.michaelbull.result.onSuccess
import com.vaadin.open.Open
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Alert
import org.example.dawfilmsinterface.cine.services.storageHtml.StorageHtmlRecaudacion
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.services.VentaService
import org.lighthousegames.logging.logging
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.util.UUID
import kotlin.io.path.Path

private val logger = logging()

class ObtenerRecaudacionViewModel(
    private val service : VentaService,
    private val storage: StorageHtmlRecaudacion,
    private val config: Config
){
    var state : SimpleObjectProperty<RecaudacionState> = SimpleObjectProperty(RecaudacionState())

    init {
        logger.debug { "Inicializando ObtenerRecaudacionViewModel" }
        loadAllLineasVenta()
        loadTypes()
    }

    private fun loadTypes() {
        logger.debug { "Cargando tipos" }
        state.value = state.value.copy(typesProducto = TipoFiltroProducto.entries.map { it.value })
    }

    private fun loadAllLineasVenta(){
        logger.debug { "Cargando productos del repositorio" }
        service.getAllLineas().onSuccess {
            logger.debug { "Cargando productos del repositorio: ${it.size}" }
            state.value = state.value.copy(lineasVentas = it)
            updateActualState()
        }
    }

    private fun updateActualState() {
        logger.debug { "Actualizando el estado de Linea de venta" }
        state.value = state.value.copy(
            lineaVenta = LineaVentaState()
        )
    }

    fun lineasFilteredList(tipoProducto: String) : List<LineaVenta>{
        logger.debug { "Filtrando lista de Lineas de venta: $tipoProducto" }

        return state.value.lineasVentas.filter { lineaVenta ->
                when (tipoProducto) {
                    TipoFiltroProducto.TODOS.value -> true
                    TipoFiltroProducto.BUTACAS.value -> lineaVenta.tipoProducto == "Butaca"
                    TipoFiltroProducto.COMPLEMENTOS.value -> lineaVenta.tipoProducto == "Complemento"
                    else -> true
                }
            }
    }

    fun sacarInforme(lineas: List<LineaVenta>) {

        val recaudacionPath = Paths.get(config.recaudacionDirectory)

        if (Files.notExists(recaudacionPath)) {
            Files.createDirectories(recaudacionPath)
            logger.debug { "Carpeta de recaudación creada en: ${config.recaudacionDirectory}" }
        }

        val file = Path("FicherosRecaudacion", "recaudacion_${UUID.randomUUID().toString().substring(0, 5)}_${LocalDate.now()}.html").toFile()
        storage.exportHtml(lineas, file)

        state.value.htmlFileName = file.name

        Alert(Alert.AlertType.INFORMATION).apply {
            this.title = "Informe de recaudación"
            this.headerText = "Informe de recaudación descargado con éxito"
        }.showAndWait()

        openHtml()
    }

    private fun openHtml() {
        val file = Path("FicherosRecaudacion", state.value.htmlFileName).toFile()
        val url = "http://localhost:63342/DawFilmsInterface/FicherosRecaudacion/${file.name}"
        Open.open(url)
    }

    data class RecaudacionState(
        val typesProducto : List<String> = emptyList(),

        val lineaVenta: LineaVentaState = LineaVentaState(),

        var lineasVentas : List<LineaVenta> = emptyList(),

        var htmlFileName: String = ""
    )

    data class LineaVentaState(
        val id : String = "",
        val precio : Double = 5.00,
    )

    enum class TipoFiltroProducto(val value : String){
        TODOS("TODOS"), BUTACAS("BUTACAS"), COMPLEMENTOS("COMPLEMENTOS")
    }
}