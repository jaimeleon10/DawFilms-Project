package org.example.dawfilmsinterface.cine.viewmodels

import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.services.VentaService
import org.lighthousegames.logging.logging

private val logger = logging()

class ObtenerRecaudacionViewModel(
    private val service : VentaService
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

    data class RecaudacionState(
        val typesProducto : List<String> = emptyList(),

        val lineaVenta: LineaVentaState = LineaVentaState(),

        var lineasVentas : List<LineaVenta> = emptyList()
    )

    data class LineaVentaState(
        val id : String = "",
        val precio : Double = 5.00,
    )

    enum class TipoFiltroProducto(val value : String){
        TODOS("TODOS"), BUTACAS("BUTACAS"), COMPLEMENTOS("COMPLEMENTOS")
    }
}