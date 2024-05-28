package org.example.dawfilmsinterface.cine.viewmodels

import javafx.beans.property.SimpleObjectProperty

class CarritoViewModel {
    val state: SimpleObjectProperty<CarritoState> = SimpleObjectProperty(CarritoState())

    data class CarritoState(
        var listadoButacasSeleccionadas: MutableList<String> = mutableListOf(),
        var listadoComplementosSeleccionados: MutableMap<String, Int> = mutableMapOf()
    )
}