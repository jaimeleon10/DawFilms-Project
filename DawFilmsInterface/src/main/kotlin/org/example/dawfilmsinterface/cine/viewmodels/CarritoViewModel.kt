package org.example.dawfilmsinterface.cine.viewmodels

import javafx.beans.property.SimpleObjectProperty
/**
 * ViewModel para el carrito de compra.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
class CarritoViewModel {
    val state: SimpleObjectProperty<CarritoState> = SimpleObjectProperty(CarritoState())

    /**
     * Inicia una nueva compra, reiniciando los valores del estado.
     */
    fun iniciarNuevaCompra() {
        state.value.listadoButacasSeleccionadas = mutableListOf()
        state.value.listadoComplementosSeleccionados = mutableMapOf()
        state.value.contadorButacasSeleccionadas = 0
    }

    /**
     * Estado del carrito.
     * @property nuevaCompra Indica si se trata de una nueva compra.
     * @property contadorButacasSeleccionadas Contador de butacas seleccionadas.
     * @property listadoButacasSeleccionadas Lista de butacas seleccionadas.
     * @property listadoComplementosSeleccionados Mapa de complementos seleccionados (nombre del complemento y cantidad).
     */
    data class CarritoState(
        var nuevaCompra: Boolean = true,
        var contadorButacasSeleccionadas: Int = 0,
        var listadoButacasSeleccionadas: MutableList<String> = mutableListOf(),
        var listadoComplementosSeleccionados: MutableMap<String, Int> = mutableMapOf()
    )
}