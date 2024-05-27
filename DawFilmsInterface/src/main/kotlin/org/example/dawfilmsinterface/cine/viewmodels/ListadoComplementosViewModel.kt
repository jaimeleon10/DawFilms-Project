package org.example.dawfilmsinterface.cine.viewmodels

import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.lighthousegames.logging.logging

private val logger = logging()

class ListadoComplementosViewModel(
    private val service: ProductoService
) {
    fun loadAllComplementos() {
        logger.debug { "Cargando complementos del repositorio" }
        service.getAllComplementos().onSuccess {
            logger.debug { "Cargando complementos del repositorio: ${it.size}" }
            state.value = state.value.copy(complementos = it.filter { it.isDeleted == false })
        }
    }

    val state: SimpleObjectProperty<ListadoComplementosState> = SimpleObjectProperty(ListadoComplementosState())

    data class ListadoComplementosState(
        val complementos: List<Complemento> = emptyList()
    )
}