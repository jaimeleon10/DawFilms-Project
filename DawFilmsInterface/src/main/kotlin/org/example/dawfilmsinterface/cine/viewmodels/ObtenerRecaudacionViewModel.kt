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
/**
 * Clase ObtenerRecaudacionViewModel
 *
 * Gestiona la lógica de obtención y exportación de la recaudación, incluyendo la carga y filtrado de datos.
 *
 * @param service Servicio para gestionar ventas.
 * @param storage Servicio para exportar los datos de recaudación en formato HTML.
 * @param config Configuración de la aplicación.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
class ObtenerRecaudacionViewModel(
    private val service : VentaService,
    private val storage: StorageHtmlRecaudacion,
    private val config: Config
){
    var state : SimpleObjectProperty<RecaudacionState> = SimpleObjectProperty(RecaudacionState())

    fun initialize() {
        logger.debug { "Inicializando ObtenerRecaudacionViewModel" }
        loadAllLineasVenta()
        loadTypes()
    }
    /**
     * Carga los tipos de producto en el estado.
     */
    private fun loadTypes() {
        logger.debug { "Cargando tipos" }
        state.value = state.value.copy(typesProducto = TipoFiltroProducto.entries.map { it.value })
    }
    /**
     * Carga todas las líneas de venta del servicio.
     */
    private fun loadAllLineasVenta(){
        logger.debug { "Cargando productos del repositorio" }
        service.getAllLineas().onSuccess {
            logger.debug { "Cargando productos del repositorio: ${it.size}" }
            state.value = state.value.copy(lineasVentas = it)
            updateActualState()
        }
    }
    /**
     * Carga todas las líneas de venta del servicio.
     */
    private fun updateActualState() {
        logger.debug { "Actualizando el estado de Linea de venta" }
        state.value = state.value.copy(
            lineaVenta = LineaVentaState()
        )
    }
    /**
     * Filtra la lista de líneas de venta según el tipo de producto especificado.
     *
     * @param tipoProducto Tipo de producto a filtrar.
     * @return Lista de líneas de venta filtradas.
     */
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
    /**
     * Genera un informe de recaudación en formato HTML.
     *
     * @param lineas Lista de líneas de venta para el informe.
     */
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
    /**
     * Abre el archivo HTML generado en el navegador.
     */
    private fun openHtml() {
        val file = Path("FicherosRecaudacion", state.value.htmlFileName).toFile()
        val url = "http://localhost:63342/DawFilmsInterface/FicherosRecaudacion/${file.name}"
        Open.open(url)
    }
    /**
     * Clase de datos RecaudacionState
     *
     * Representa el estado de la recaudación.
     *
     * @param typesProducto Lista de tipos de productos.
     * @param lineaVenta Estado de la línea de venta.
     * @param lineasVentas Lista de líneas de venta.
     * @param htmlFileName Nombre del archivo HTML generado.
     */
    data class RecaudacionState(
        val typesProducto : List<String> = emptyList(),

        val lineaVenta: LineaVentaState = LineaVentaState(),

        var lineasVentas : List<LineaVenta> = emptyList(),

        var htmlFileName: String = ""
    )
    /**
     * Clase de datos LineaVentaState
     *
     * Representa el estado de una línea de venta.
     *
     * @param id ID de la línea de venta.
     * @param precio Precio de la línea de venta.
     */
    data class LineaVentaState(
        val id : String = "",
        val precio : Double = 5.00,
    )
    /**
     * Enumeración TipoFiltroProducto
     *
     * Representa los diferentes tipos de filtro de productos.
     *
     * @param value Valor del tipo de filtro de producto.
     */
    enum class TipoFiltroProducto(val value : String){
        TODOS("TODOS"), BUTACAS("BUTACAS"), COMPLEMENTOS("COMPLEMENTOS")
    }
}