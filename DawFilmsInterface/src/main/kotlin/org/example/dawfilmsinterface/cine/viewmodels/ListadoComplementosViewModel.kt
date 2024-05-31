package org.example.dawfilmsinterface.cine.viewmodels

import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.lighthousegames.logging.logging

private val logger = logging()
/**
 * Clase ListadoComplementosViewModel
 *
 * Gestiona la lógica relacionada con la carga de complementos no eliminados.
 *
 * @param service Servicio para gestionar productos.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
class ListadoComplementosViewModel(
    private val service: ProductoService
) {
    /**
     * Carga todos los complementos no eliminados del servicio en el estado actual.
     */
    fun loadAllComplementos() {
        logger.debug { "Cargando complementos del repositorio" }
        service.getAllComplementos().onSuccess {
            logger.debug { "Cargando complementos del repositorio: ${it.size}" }
            state.value = state.value.copy(complementos = it.filter { it.isDeleted == false })
        }
    }

    val state: SimpleObjectProperty<ListadoComplementosState> = SimpleObjectProperty(ListadoComplementosState())
    /**
     * Clase de datos ListadoComplementosState
     *
     * Representa el estado de la lista de complementos.
     *
     * @param complementos Lista de complementos no eliminados.
     */
    data class ListadoComplementosState(
        val complementos: List<Complemento> = emptyList()
    )
}