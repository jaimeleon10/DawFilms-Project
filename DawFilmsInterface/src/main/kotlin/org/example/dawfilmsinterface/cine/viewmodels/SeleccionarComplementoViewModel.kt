package org.example.dawfilmsinterface.cine.viewmodels

import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.example.dawfilmsinterface.productos.storage.storageImage.StorageImage
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()
/**
 * ViewModel para la gestión de complementos en la interfaz de usuario.
 *
 * @param service Servicio para interactuar con los complementos.
 * @param storage Almacén para gestionar imágenes de complementos.
 *  @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 *  @since 1.0.0
 */
class SeleccionarComplementoViewModel (
    private val service: ProductoService,
    private val storage: StorageImage
) {
    /**
     * Estado actual de la gestión de complementos.
     */
    val state: SimpleObjectProperty<GestionComplementosState> = SimpleObjectProperty(GestionComplementosState())
    /**
     * Carga todos los complementos desde el repositorio.
     */
    fun loadAllComplementos(){
        logger.debug { "Cargando complementos del repositorio" }
        service.getAllComplementos().onSuccess {
            logger.debug { "Cargando complementos del repositorio: ${it.size}" }
            state.value = state.value.copy(complementos = it.filter { complemento -> complemento.isDeleted == false })
            updateActualState()
        }
    }
    /**
     * Actualiza el estado actual de los complementos.
     */
    private fun updateActualState() {
        logger.debug { "Actualizando el estado de Complemento" }
        state.value = state.value.copy(
            complemento = ComplementoSeleccionadoState()
        )
    }
    /**
     * Actualiza el complemento seleccionado.
     *
     * @param complemento El complemento seleccionado.
     */
    fun updateComplementoSeleccionado(complemento: Complemento) {
        logger.debug { "Actualizando estado de complemento: $complemento" }

        var imagen = Image(RoutesManager.getResourceAsStream("icons/sinImagen.png"))
        var fileImage = File(RoutesManager.getResource("icons/sinImagen.png").toURI())

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
    /**
     * Estado de la gestión de complementos.
     *
     * @param complementos Lista de complementos disponibles.
     * @param complemento Estado del complemento seleccionado.
     */
    data class GestionComplementosState(
        val complementos: List<Complemento> = emptyList(),
        val complemento: ComplementoSeleccionadoState = ComplementoSeleccionadoState(),
    )
    /**
     * Estado del complemento seleccionado.
     *
     * @param id Identificador único del complemento.
     * @param nombre Nombre del complemento.
     * @param precio Precio del complemento.
     * @param stock Stock disponible del complemento.
     * @param categoria Categoría del complemento.
     * @param icono Icono representativo del complemento.
     */
    data class ComplementoSeleccionadoState(
        var id: String = "",
        val nombre: String = "",
        val precio: Double = 0.0,
        val stock: Int = 0,
        val categoria: String = "",
        var icono: Image = Image(RoutesManager.getResourceAsStream("icons/sinImagen.png")),
    )
}