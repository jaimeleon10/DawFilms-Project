package org.example.dawfilmsinterface.productos.viewmodels

import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.example.dawfilmsinterface.productos.storage.genericStorage.ProductosStorage
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

class ActualizarButacaViewModel(
    private val service : ProductoService,
    private val storage : ProductosStorage
) {
    val state : SimpleObjectProperty<GestionState> = SimpleObjectProperty(GestionState())

    init {
        logger.debug { "Inicializando ActualizarButacaViewModel" }
        loadAllButacas()
        loadTypes()
    }

    private fun loadTypes() {
        logger.debug { "Cargando tipos de Tipo" }
        state.value = state.value.copy(typesTipo = TipoFiltroEstado.entries.map { it.value })
    }

    private fun loadAllButacas(){
        logger.debug { "Cargando butacas del repositorio" }
        service.getAllButacas().onSuccess {
            logger.debug { "Cargando butacas del repositorio: ${it.size}" }
            state.value = state.value.copy(butacas = it)
            updateActualState()
        }
    }

    private fun updateActualState() {
        logger.debug { "Actualizando el estado de Gestión" }
        state.value = state.value.copy(
            butaca = ButacaState()
        )
    }

    fun butacasFilteredList(tipo: String) : List<Butaca>{
        logger.debug { "Filtrando lista de Butacas: $tipo" }

        return state.value.butacas
            .filter { butaca ->
                when(tipo) {
                    TipoFiltroEstado.TODAS.value -> true
                    TipoFiltroEstado.ACTIVA.value -> butaca.estadoButaca == EstadoButaca.ACTIVA
                    TipoFiltroEstado.MANTENIMIENTO.value -> butaca.estadoButaca == EstadoButaca.MANTENIMIENTO
                    TipoFiltroEstado.FUERASERVICIO.value -> butaca.estadoButaca == EstadoButaca.FUERASERVICIO
                    else -> true
                }
            }
    }

    fun updateButacaSeleccionada(butaca: Butaca){
        logger.debug { "Actualizando estado de Butaca: $butaca" }

        var imagen = Image(RoutesManager.getResourceAsStream("images/octogatoNatalia.png"))
        var fileImage = File(RoutesManager.getResource("images/octogatoNatalia.png").toURI())

        storage.loadImage(butaca.imagen).onSuccess {
            imagen = Image(it.absoluteFile.toURI().toString())
            fileImage = it
        }

        state.value = state.value.copy(
            butaca = ButacaState(
                estado = butaca.estadoButaca.name,
                tipo = butaca.tipoButaca.name,
                ocupacion = butaca.ocupacionButaca.name,
                imagen = imagen,
                fileImage = fileImage
            )
        )
    }

    /*
    private fun editarButaca() : Result<Butaca, ProductoError> {
        logger.debug{ "Editando Butaca" }

        val updatedButacaTemp = state.value.butaca.copy()
        val fileNameTemp = state.value.butaca.oldFileImage?.name
            ?: TipoImagen.SIN_IMAGEN.value
        var updatedButaca = state.value.butaca.toModel().copy(imagen = fileNameTemp)
        return updatedButaca.validate().andThen{
            updatedButacaTemp.fileImage?.let { newFileImage ->
                if (updatedButaca.imagen == TipoImagen.SIN_IMAGEN.value || updatedButaca.imagen == TipoImagen.EMPTY.value){
                    storage.saveImage(newFileImage).onSuccess {
                        updatedButaca = updatedButaca.copy(imagen = it.name)
                    }
                }else{
                    storage.updateImage(fileNameTemp,newFileImage)
                }
            }
            service.saveButaca(updatedButaca).onSuccess {
                val index = state.value.butacas.indexOfFirst { b -> b.id == it.id }
                state.value == state.value.copy(
                    butacas = state.value.butacas.toMutableList().apply { this[index] = it }
                )
                updateActualState()
                Ok(it)
            }
        }
    }

     */

    private fun updateImageButacaOperacion(fileImage: File){
        logger.debug { "Actualizando imagen: $fileImage" }
        state.value = state.value.copy(
            butaca = state.value.butaca.copy(
                imagen = Image(fileImage.toURI().toString()),
                fileImage = fileImage,
                oldFileImage = state.value.butaca.fileImage
            )
        )
    }

    private fun updateDataButacaOperacion(
        estado: String,
        tipo: String,
        ocupacion: String,
        precio: Double,
        imagen: Image
    ){
        logger.debug { "Actualizando estado de Butaca Operacion" }
        state.value = state.value.copy(
            butaca = state.value.butaca.copy(
                estado = estado,
                tipo = tipo,
                ocupacion = ocupacion,
                precio = precio,
                imagen = imagen
            )
        )
    }

    data class GestionState(
        val typesEstado : List<String> = emptyList(),
        val typesTipo : List<String> = emptyList(),
        val typesOcupacion : List<String> = emptyList(),
        val butacas : List<Butaca> = emptyList(),

        val butaca : ButacaState = ButacaState(),

        val tipoOperacion : TipoOperacion = TipoOperacion.EDITAR
    )

    data class ButacaState(
        val id : String = "",
        val estado : String = "",
        val tipo : String = "",
        val ocupacion : String = "",
        val precio : Double = 5.00,
        val imagen : Image = Image(RoutesManager.getResourceAsStream("images/octogatoNatalia.png")),
        val fileImage : File? = null,
        val oldFileImage : File? = null
    )

    enum class TipoOperacion(val value : String){
        EDITAR("Editar")
    }

    enum class TipoImagen(val value : String){
        SIN_IMAGEN("octogatoNatalia.png"), EMPTY("")
    }

    enum class TipoFiltroEstado(val value : String){
        TODAS("Todas"), ACTIVA("Activa"), MANTENIMIENTO("Mantenimiento"), FUERASERVICIO("Fuera de servicio")
    }
}