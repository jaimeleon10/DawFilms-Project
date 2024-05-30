package org.example.dawfilmsinterface.cine.viewmodels

import com.github.michaelbull.result.*
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.mappers.toModel
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.example.dawfilmsinterface.productos.storage.genericStorage.ProductosStorage
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class GestionButacaViewModel(
    private val service : ProductoService,
) {
    val state : SimpleObjectProperty<GestionState> = SimpleObjectProperty(GestionState())

    fun initialize() {
        logger.debug { "Inicializando ActualizarButacaViewModel" }
        loadAllButacas()
        loadTypes()
    }

    private fun loadTypes() {
        logger.debug { "Cargando tipos" }
        state.value = state.value.copy(typesEstadoFiltro = TipoFiltroEstado.entries.map { it.value })
        state.value = state.value.copy(typesTipoFiltro = TipoFiltroTipo.entries.map { it.value})
        state.value = state.value.copy(typesOcupacionFiltro = TipoFiltroOcupacion.entries.map { it.value})

        state.value = state.value.copy(typesTipo = TipoTipo.entries.map { it.value})
        state.value = state.value.copy(typesEstado = TipoEstado.entries.map { it.value})
        state.value = state.value.copy(typesOcupacion = TipoOcupacion.entries.map { it.value})
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
        logger.debug { "Actualizando el estado de Butaca" }
        state.value = state.value.copy(
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

    fun updateButacaSeleccionada(butaca: Butaca){
        logger.debug { "Actualizando estado de Butaca: $butaca" }

        state.value = state.value.copy(
            butaca = ButacaState(
                id = butaca.id,
                estado = if (butaca.estadoButaca.name == "FUERASERVICIO") "FUERA DE SERVICIO" else butaca.estadoButaca.name,
                tipo = butaca.tipoButaca.name,
                ocupacion = if (butaca.ocupacionButaca.name == "ENRESERVA") "EN RESERVA" else butaca.ocupacionButaca.name,
                precio = butaca.tipoButaca.precio,
                imagen = if (butaca.tipoButaca == TipoButaca.VIP) "icons/butacaSeleccionadaVIP.png" else "icons/butacaSeleccionada.png"
            )
        )
    }

    fun editarButaca(): Result<Butaca, ProductoError> {
        logger.debug { "Editando Butaca" }

        val updatedButaca = state.value.butaca.toModel()

        return service.updateButaca(state.value.butaca.id, updatedButaca).onSuccess {
            val index = state.value.butacas.indexOfFirst { b -> b.id == it.id }
            state.value = state.value.copy(
                butacas = state.value.butacas.toMutableList().apply { this[index] = it }
            )
            updateActualState()
            Ok(it)
        }
    }

    fun updateDataButacaOperacion(
        estado: String,
        tipo: String,
        ocupacion: String,
        precio: Double,
        imagen: String
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
        val typesEstadoFiltro : List<String> = emptyList(),
        val typesTipoFiltro : List<String> = emptyList(),
        val typesOcupacionFiltro : List<String> = emptyList(),

        val typesEstado : List<String> = emptyList(),
        val typesTipo : List<String> = emptyList(),
        val typesOcupacion : List<String> = emptyList(),

        val butacas : List<Butaca> = emptyList(),

        val butaca : ButacaState = ButacaState(),

        val tipoOperacion : TipoOperacion = TipoOperacion.EDITAR
    )

    data class ButacaState(
        val id: String = "",
        val estado: String = "",
        val tipo: String = "",
        val ocupacion: String = "",
        val precio: Double = 5.0,
        val imagen: String = "sinImagen.png"
    )

    enum class TipoOperacion(val value : String){
        EDITAR("EDITAR")
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

    enum class TipoEstado(val value : String){
        ACTIVA("ACTIVA"), MANTENIMIENTO("MANTENIMIENTO"), FUERASERVICIO("FUERA DE SERVICIO")
    }

    enum class TipoOcupacion(val value : String){
        LIBRE("LIBRE"), ENRESERVA("EN RESERVA"), OCUPADA("OCUPADA")
    }

    enum class TipoTipo(val value : String){
        NORMAL("NORMAL"), VIP("VIP")
    }
}