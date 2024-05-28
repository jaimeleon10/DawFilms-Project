package org.example.dawfilmsinterface.cine.viewmodels

import javafx.beans.property.SimpleObjectProperty

class CarritoViewModel {
    val state: SimpleObjectProperty<CarritoState> = SimpleObjectProperty(CarritoState())

    fun iniciarNuevaCompra() {
        state.value.listadoButacasSeleccionadas = mutableListOf()
        state.value.listadoComplementosSeleccionados = mutableMapOf()
        state.value.contadorButacasSeleccionadas = 0
    }

    data class CarritoState(
        var nuevaCompra: Boolean = true,
        var contadorButacasSeleccionadas: Int = 0,
        var listadoButacasSeleccionadas: MutableList<String> = mutableListOf(),
        var listadoComplementosSeleccionados: MutableMap<String, Int> = mutableMapOf()
    )
}