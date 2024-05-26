package org.example.dawfilmsinterface.productos.viewmodels

import javafx.beans.property.SimpleObjectProperty

class CarritoViewModel {
    val state: SimpleObjectProperty<CarritoState> = SimpleObjectProperty(CarritoState())

    data class CarritoState(
        val listadoButacasSeleccionadas: MutableList<String> = mutableListOf(),
        val listadoComplementosSeleccionados: MutableMap<String, Int> = mutableMapOf()
    )
}