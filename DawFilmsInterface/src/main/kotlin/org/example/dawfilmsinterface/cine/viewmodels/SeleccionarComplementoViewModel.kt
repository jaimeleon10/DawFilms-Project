package org.example.dawfilmsinterface.cine.viewmodels

import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.example.dawfilmsinterface.productos.storage.storageImage.StorageImage
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class SeleccionarComplementoViewModel (
    private val service: ProductoService,
    private val storage: StorageImage
) {
    val state: SimpleObjectProperty<GestionComplementosState> = SimpleObjectProperty(GestionComplementosState())

    fun loadAllComplementos(){
        logger.debug { "Cargando complementos del repositorio" }
        service.getAllComplementos().onSuccess {
            logger.debug { "Cargando complementos del repositorio: ${it.size}" }
            state.value = state.value.copy(complementos = it.filter { complemento -> complemento.isDeleted == false })
            updateActualState()
        }
    }

    private fun updateActualState() {
        logger.debug { "Actualizando el estado de Complemento" }
        state.value = state.value.copy(
            complemento = ComplementoSeleccionadoState()
        )
    }

    fun updateComplementoSeleccionado(complemento: Complemento) {
        logger.debug { "Actualizando estado de complemento: $complemento" }

        var imagen = Image(RoutesManager.getResourceAsStream("icons/${complemento.imagen}"))
        storage.loadImage(complemento.imagen).onSuccess {
            imagen = Image(it.absoluteFile.toURI().toString())
        }

        state.value = state.value.copy(
            complemento = ComplementoSeleccionadoState(
                id = complemento.id,
                nombre = complemento.nombre,
                precio = complemento.precio,
                stock = complemento.stock,
                categoria = complemento.categoria.name,
                icono = imagen
            )
        )
    }

    data class GestionComplementosState(
        val complementos: List<Complemento> = emptyList(),
        val complemento: ComplementoSeleccionadoState = ComplementoSeleccionadoState(),
    )

    data class ComplementoSeleccionadoState(
        var id: String = "",
        val nombre: String = "",
        val precio: Double = 0.0,
        val stock: Int = 0,
        val categoria: String = "",
        var icono: Image = Image(RoutesManager.getResourceAsStream("icons/sinImagen.png")),
    )
}