package org.example.dawfilmsinterface.productos.viewmodels

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.mappers.toModel
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
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

    init {
        logger.debug { "Inicializando GestionComplementosViewModel" }
        loadAllComplementos()
    }

    private fun loadAllComplementos(){
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

    /* TODO -> USAR EN IMPORTAR BUTACAS (AQUÍ NO HACE NADA)
    fun loadButacasFromCsv(file: File): Result<List<Producto>, ProductoError> {
        logger.debug { "Cargando butacas de CSV" }
        return storage.deleteAllImages().andThen {
            storage.loadCsv(file).onSuccess { listaProductos ->
                val listaButacas: List<Producto> = listaProductos.filterIsInstance<Butaca>()
                Ok(listaButacas)
            }.onFailure {
                Err(ProductoError.ProductoStorageError(it.message))
            }
        }
    }*/

    fun updateComplementoSeleccionado(complemento: Complemento){
        logger.debug { "Actualizando estado de Complemento: $complemento" }

        var imagen = Image(RoutesManager.getResourceAsStream("images/octogatoNatalia.png"))
        var fileImage = File(RoutesManager.getResource("images/octogatoNatalia.png").toURI())

        storage.loadImage(complemento.imagen).onSuccess {
            imagen = Image(it.absoluteFile.toURI().toString())
            fileImage = it
        }

        state.value = state.value.copy(
            complemento = ComplementoState(
                id = complemento.id,
                nombre = complemento.nombre,
                precio = complemento.precio,
                stock = complemento.stock,
                categoria = complemento.categoria.toString(),
                imagen = imagen,
                fileImage = fileImage
            )
        )
    }

    fun editarComplemento(): Result<Complemento, ProductoError> {
        logger.debug { "Editando Complemento" }

        val updatedComplementoTemp = state.value.complemento.copy()
        val fileNameTemp = state.value.complemento.oldFileImage?.name ?: TipoImagen.SIN_IMAGEN.value
        var updatedComplemento = state.value.complemento.toModel().copy(imagen = fileNameTemp)

        return updatedComplementoTemp.fileImage?.let { newFileImage ->
            if (updatedComplemento.imagen == TipoImagen.SIN_IMAGEN.value || updatedComplemento.imagen == TipoImagen.EMPTY.value) {
                storage.saveImage(newFileImage).onSuccess {
                    updatedComplemento = updatedComplemento.copy(imagen = it.name)
                }
            } else {
                storage.updateImage(fileNameTemp, newFileImage)
            }

            service.updateComplemento(state.value.complemento.id, updatedComplemento).onSuccess {
                val index = state.value.complementos.indexOfFirst { b -> b.id == it.id }
                state.value = state.value.copy(
                    complementos = state.value.complementos.toMutableList().apply { this[index] = it }
                )
                updateActualState()
                Ok(it)
            }
        } ?: service.updateComplemento(state.value.complemento.id, updatedComplemento).onSuccess {
            val index = state.value.complementos.indexOfFirst { b -> b.id == it.id }
            state.value = state.value.copy(
                complementos = state.value.complementos.toMutableList().apply { this[index] = it }
            )
            updateActualState()
            Ok(it)
        }
    }


    private fun updateImageComplementoOperacion(fileImage: File){
        logger.debug { "Actualizando imagen: $fileImage" }
        state.value = state.value.copy(
            complemento = state.value.complemento.copy(
                imagen = Image(fileImage.toURI().toString()),
                fileImage = fileImage,
                oldFileImage = state.value.complemento.fileImage
            )
        )
    }

    fun updateDataComplementoOperacion(
        nombre: String,
        precio: Double,
        stock: Int,
        categoria: String,
        imagen: Image
    ){
        logger.debug { "Actualizando estado de Complemento Operacion" }
        state.value = state.value.copy(
            complemento = state.value.complemento.copy(
                nombre = nombre,
                precio = precio,
                stock = stock,
                categoria = categoria,
                imagen = imagen
            )
        )
    }

    data class GestionState(
        val complementos : List<Complemento> = emptyList(),

        val complemento : ComplementoState = ComplementoState(),

        val tipoOperacion : TipoOperacion = TipoOperacion.NUEVO
    )

    data class ComplementoState(
        val id : String = "",
        val nombre : String = "",
        val precio : Double = 3.00,
        val stock : Int = 20,
        val categoria : String = "",
        val imagen : Image = Image(RoutesManager.getResourceAsStream("images/octogatoNatalia.png")),
        val fileImage : File? = null,
        val oldFileImage : File? = null
    )

    enum class TipoOperacion(val value : String){
        NUEVO("NUEVO"), EDITAR("EDITAR")
    }

    enum class TipoImagen(val value : String){
        SIN_IMAGEN("octogatoNatalia.png"), EMPTY("")
    }
}