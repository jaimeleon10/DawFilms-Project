package org.example.dawfilmsinterface.cine.viewmodels

import javafx.beans.property.SimpleObjectProperty

class CarritoViewModel {
    val state: SimpleObjectProperty<CarritoState> = SimpleObjectProperty(CarritoState())

    fun iniciarNuevaCompra() {
        state.value.listadoButacasSeleccionadas = mutableListOf()
        state.value.listadoComplementosSeleccionados = mutableMapOf()
    }

    data class CarritoState(
        var nuevaCompra: Boolean = true,
        var listadoButacasSeleccionadas: MutableList<String> = mutableListOf(),
        var listadoComplementosSeleccionados: MutableMap<String, Int> = mutableMapOf()
    )
}