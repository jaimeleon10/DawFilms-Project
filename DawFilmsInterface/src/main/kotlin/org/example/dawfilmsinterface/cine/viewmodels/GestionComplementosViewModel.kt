package org.example.dawfilmsinterface.cine.viewmodels

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.mappers.toModel
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.example.dawfilmsinterface.productos.storage.genericStorage.ProductosStorage
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

class GestionComplementosViewModel(
    private val service : ProductoService,
    private val storage : ProductosStorage
) {
    val state : SimpleObjectProperty<GestionState> = SimpleObjectProperty(GestionState())

     fun loadTypes() {
        logger.debug { "Cargando tipos" }
        state.value = state.value.copy(typesCategoria = TipoCategoria.entries.map { it.value })
        state.value = state.value.copy(availability = Disponibilidades.entries.map { it.value })
    }

     fun loadAllComplementos(){
        logger.debug { "Cargando complementos del repositorio" }
        service.getAllComplementos().onSuccess {
            logger.debug { "Cargando complementos del repositorio: ${it.size}" }
            state.value = state.value.copy(complementos = it)
            updateActualState()
        }
    }

    private fun updateActualState() {
        logger.debug { "Actualizando el estado de Complemento" }
        state.value = state.value.copy(
            complemento = ComplementoState()
        )
    }

    fun updateComplementoSeleccionado(complemento: Complemento){
        logger.debug { "Actualizando estado de Complemento: $complemento" }

        state.value = state.value.copy(
            complemento = ComplementoState(
                id = complemento.id,
                nombre = complemento.nombre,
                precio = complemento.precio,
                stock = complemento.stock,
                categoria = if(complemento.categoria.name == "COMIDA") "COMIDA" else complemento.categoria.name,
                imagen = Image(RoutesManager.getResourceAsStream("icons/logoCine.png")),
                isDeleted = complemento.isDeleted!!
            )
        )
    }

    fun editarComplemento(): Result<Complemento, ProductoError> {
        logger.debug { "Editando Complemento" }

        val updatedComplemento = state.value.complemento.toModel().copy()

        return service.updateComplemento(state.value.complemento.id, updatedComplemento).onSuccess {
            val index = state.value.complementos.indexOfFirst { complemento -> complemento.id == it.id }
            state.value = state.value.copy(
                complementos = state.value.complementos.toMutableList().apply { this[index] = it }
            )
            updateActualState()
            Ok(it)
        }
    }

    fun eliminarComplemento(): Result<Unit, ProductoError>{
        logger.debug { "Eliminando Complemento" }
        val complemento = state.value.complemento
        val myId = complemento.id

        service.deleteComplemento(myId)
        loadAllComplementos()
        return Ok(Unit)
    }

    fun createComplemento(): Result<Complemento, ProductoError>{
        logger.debug { "Creando Complemento"}
        val newId = ((service.getAllComplementos().value.maxBy { it.id }.id.toInt()) + 1).toString()
        val newComplementoTemp = state.value.complemento.copy()
        val newComplemento = newComplementoTemp.toModel().copy()
        newComplemento.id = newId

        return service.saveComplemento(newComplemento).andThen {
            state.value = state.value.copy(
                complementos = state.value.complementos + it
            )
            updateActualState()
            Ok(it)
        }

    }

    fun changeComplementoOperacion(newValue: TipoOperacion){
        logger.debug { "Cambiando tipo de operacion: $newValue" }
        if (newValue == TipoOperacion.EDITAR){
            logger.debug { "Copiando estado de Complemento seleccionado a Operacion"}
            state.value = state.value.copy(
                tipoOperacion = newValue,
                complemento = state.value.complemento
            )
        } else {
            logger.debug { "Limpiando estado de Complemento Operacion" }
            state.value = state.value.copy(
                complemento = ComplementoState(id = ((service.getAllComplementos().value.maxBy { it.id }.id.toInt()) + 1).toString()),
                tipoOperacion = newValue,
            )
        }
    }

    fun updateDataComplementoOperacion(
        id: String,
        nombre: String,
        precio: Double,
        stock: Int,
        categoria: String,
        imagen: Image,
        isDeleted: Boolean
    ){
        logger.debug { "Actualizando estado de Complemento Operación" }
        state.value = state.value.copy(
            complemento = ComplementoState(
                id = id,
                nombre = nombre,
                precio = precio,
                stock = stock,
                categoria = categoria,
                imagen = imagen,
                isDeleted = isDeleted
            )
        )
    }

    data class GestionState(
        val typesCategoria: List<String> = emptyList(),

        val availability: List<String> = emptyList(),

        val complementos : List<Complemento> = emptyList(),

        val complemento : ComplementoState = ComplementoState(),

        val tipoOperacion : TipoOperacion = TipoOperacion.NUEVO,

        val newId : String = ""
    )

    data class ComplementoState(
        var id : String = "",
        val nombre : String = "",
        val precio : Double = 0.0,
        val stock : Int = 0,
        val categoria : String = "",
        val imagen : Image = Image(RoutesManager.getResourceAsStream("icons/sinImagen.png")),
        val isDeleted : Boolean = false
    )

    enum class TipoOperacion(val value : String){
        NUEVO("NUEVO"), EDITAR("EDITAR")
    }

    enum class TipoCategoria(val value : String){
        BEBIDA("BEBIDA"), COMIDA("COMIDA")
    }

    enum class Disponibilidades(val value: String) {
        FALSE("SI"), TRUE("NO")
    }
}