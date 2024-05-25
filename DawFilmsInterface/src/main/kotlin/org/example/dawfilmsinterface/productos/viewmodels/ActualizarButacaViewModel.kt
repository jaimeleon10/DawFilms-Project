package org.example.dawfilmsinterface.productos.viewmodels

import com.github.michaelbull.result.*
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.mappers.toModel
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.models.producto.Producto
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
        logger.debug { "Cargando tipos" }
        state.value = state.value.copy(typesEstado = TipoFiltroEstado.entries.map { it.value })
        state.value = state.value.copy(typesTipo = TipoFiltroTipo.entries.map { it.value})
        state.value = state.value.copy(typesOcupacion = TipoFiltroOcupacion.entries.map { it.value})
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
            typesEstado = state.value.butacas.map { it.estadoButaca.toString() },
            butaca = ButacaState()
        )
    }

    fun butacasFilteredList(estado: String, tipo: String, ocupacion: String) : List<Butaca>{
        logger.debug { "Filtrando lista de Butacas: $estado" }

        return state.value.butacas
            .filter { butaca ->
                when (estado) {
                    TipoFiltroEstado.TODAS.value -> true
                    TipoFiltroEstado.ACTIVA.value -> butaca.estadoButaca == EstadoButaca.ACTIVA
                    TipoFiltroEstado.MANTENIMIENTO.value -> butaca.estadoButaca == EstadoButaca.MANTENIMIENTO
                    TipoFiltroEstado.FUERASERVICIO.value -> butaca.estadoButaca == EstadoButaca.FUERASERVICIO
                    else -> true
                }
            }.filter { butaca ->
                when (tipo) {
                    TipoFiltroTipo.TODAS.value -> true
                    TipoFiltroTipo.NORMAL.value -> butaca.tipoButaca == TipoButaca.NORMAL
                    TipoFiltroTipo.VIP.value -> butaca.tipoButaca == TipoButaca.VIP
                    else -> true
                }
            }.filter { butaca ->
                when (ocupacion) {
                    TipoFiltroOcupacion.TODAS.value -> true
                    TipoFiltroOcupacion.LIBRE.value -> butaca.ocupacionButaca == OcupacionButaca.LIBRE
                    TipoFiltroOcupacion.ENRESERVA.value -> butaca.ocupacionButaca == OcupacionButaca.ENRESERVA
                    TipoFiltroOcupacion.OCUPADA.value -> butaca.ocupacionButaca == OcupacionButaca.OCUPADA
                    else -> true
                }
            }
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
                id = butaca.id,
                estado = butaca.estadoButaca.name,
                tipo = butaca.tipoButaca.name,
                ocupacion = butaca.ocupacionButaca.name,
                imagen = imagen,
                fileImage = fileImage
            )
        )
    }

    fun editarButaca(): Result<Butaca, ProductoError> {
        logger.debug { "Editando Butaca" }

        val updatedButacaTemp = state.value.butaca.copy()
        val fileNameTemp = state.value.butaca.oldFileImage?.name ?: TipoImagen.SIN_IMAGEN.value
        var updatedButaca = state.value.butaca.toModel().copy(imagen = fileNameTemp)

        return updatedButacaTemp.fileImage?.let { newFileImage ->
            if (updatedButaca.imagen == TipoImagen.SIN_IMAGEN.value || updatedButaca.imagen == TipoImagen.EMPTY.value) {
                storage.saveImage(newFileImage).onSuccess {
                    updatedButaca = updatedButaca.copy(imagen = it.name)
                }
            } else {
                storage.updateImage(fileNameTemp, newFileImage)
            }

            service.updateButaca(state.value.butaca.id, updatedButaca).onSuccess {
                val index = state.value.butacas.indexOfFirst { b -> b.id == it.id }
                state.value = state.value.copy(
                    butacas = state.value.butacas.toMutableList().apply { this[index] = it }
                )
                updateActualState()
                Ok(it)
            }
        } ?: service.updateButaca(state.value.butaca.id, updatedButaca).onSuccess {
            val index = state.value.butacas.indexOfFirst { b -> b.id == it.id }
            state.value = state.value.copy(
                butacas = state.value.butacas.toMutableList().apply { this[index] = it }
            )
            updateActualState()
            Ok(it)
        }
    }


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

    fun updateDataButacaOperacion(
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
        EDITAR("EDITAR")
    }

    enum class TipoImagen(val value : String){
        SIN_IMAGEN("octogatoNatalia.png"), EMPTY("")
    }

    enum class TipoFiltroEstado(val value : String){
        TODAS("TODAS"), ACTIVA("ACTIVA"), MANTENIMIENTO("MANTENIMIENTO"), FUERASERVICIO("FUERA DE SERVICIO")
    }

    enum class TipoFiltroOcupacion(val value : String){
        TODAS("TODAS"), LIBRE("LIBRE"), ENRESERVA("EN RESERVA"), OCUPADA("OCUPADA")
    }

    enum class TipoFiltroTipo(val value : String){
        TODAS("TODAS"), NORMAL("NORMAL"), VIP("VIP")
    }
}