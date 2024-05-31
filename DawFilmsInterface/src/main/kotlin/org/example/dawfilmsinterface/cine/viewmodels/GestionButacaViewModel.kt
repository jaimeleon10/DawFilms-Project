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

/**
 * Clase ViewModel que gestiona la funcionalidad de la pantalla de gestión de butacas.
 *
 * @param service Servicio para obtener y actualizar información de butacas.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
class GestionButacaViewModel(
    private val service : ProductoService,
) {
    val state : SimpleObjectProperty<GestionState> = SimpleObjectProperty(GestionState())

    fun initialize() {
        logger.debug { "Inicializando ActualizarButacaViewModel" }
        loadAllButacas()
        loadTypes()
    }

    /**
     * Carga los tipos de estado, tipo y ocupación de las butacas.
     */
    private fun loadTypes() {
        logger.debug { "Cargando tipos" }
        state.value = state.value.copy(typesEstadoFiltro = TipoFiltroEstado.entries.map { it.value })
        state.value = state.value.copy(typesTipoFiltro = TipoFiltroTipo.entries.map { it.value})
        state.value = state.value.copy(typesOcupacionFiltro = TipoFiltroOcupacion.entries.map { it.value})

        state.value = state.value.copy(typesTipo = TipoTipo.entries.map { it.value})
        state.value = state.value.copy(typesEstado = TipoEstado.entries.map { it.value})
        state.value = state.value.copy(typesOcupacion = TipoOcupacion.entries.map { it.value})
    }

    /**
     * Carga la lista de butacas del repositorio.
     */
    private fun loadAllButacas(){
        logger.debug { "Cargando butacas del repositorio" }
        service.getAllButacas().onSuccess {
            logger.debug { "Cargando butacas del repositorio: ${it.size}" }
            state.value = state.value.copy(butacas = it)
            updateActualState()
        }
    }

    /**
     * Actualiza el estado de la butaca seleccionada.
     */
    private fun updateActualState() {
        logger.debug { "Actualizando el estado de Butaca" }
        state.value = state.value.copy(
            butaca = ButacaState()
        )
    }

    /**
     * Obtiene una lista de butacas filtradas por estado, tipo y ocupación.
     *
     * @param estado El estado de la butaca a filtrar.
     * @param tipo El tipo de la butaca a filtrar.
     * @param ocupacion La ocupación de la butaca a filtrar.
     * @return Una lista de butacas filtradas.
     */
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
    /**
     * Actualiza el estado de la butaca seleccionada.
     *
     * @param butaca La butaca seleccionada a actualizar.
     */
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

    /**
     * Función para editar una butaca.
     * @return Un resultado que representa la operación de edición de la butaca.
     */
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

    /**
     * Función para actualizar los datos de operación de una butaca.
     * @param estado Estado de la butaca.
     * @param tipo Tipo de la butaca.
     * @param ocupacion Ocupación de la butaca.
     * @param precio Precio de la butaca.
     * @param imagen Ruta de la imagen asociada a la butaca.
     */
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
    /**
     * Estado de la gestión de butacas en la aplicación.
     * @property typesEstadoFiltro Lista de tipos de estado para filtros.
     * @property typesTipoFiltro Lista de tipos de butacas para filtros.
     * @property typesOcupacionFiltro Lista de tipos de ocupación para filtros.
     * @property typesEstado Lista de tipos de estado de las butacas.
     * @property typesTipo Lista de tipos de las butacas.
     * @property typesOcupacion Lista de tipos de ocupación de las butacas.
     * @property butacas Lista de butacas cargadas.
     * @property butaca Estado de la butaca seleccionada.
     * @property tipoOperacion Tipo de operación en curso.
     */
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

    /**
     * Estado individual de una butaca.
     * @property id Identificador de la butaca.
     * @property estado Estado de la butaca.
     * @property tipo Tipo de la butaca.
     * @property ocupacion Ocupación de la butaca.
     * @property precio Precio de la butaca.
     * @property imagen Ruta de la imagen asociada a la butaca.
     */
    data class ButacaState(
        val id: String = "",
        val estado: String = "",
        val tipo: String = "",
        val ocupacion: String = "",
        val precio: Double = 5.0,
        val imagen: String = "sinImagen.png"
    )

    /**
     * Enumeración de los tipos de operación.
     * @property value Valor del tipo de operación.
     */
    enum class TipoOperacion(val value : String){
        EDITAR("EDITAR")
    }

    /**
     * Enumeración de los tipos de filtro de estado.
     * @property value Valor del tipo de filtro de estado.
     */
    enum class TipoFiltroEstado(val value : String){
        TODAS("TODAS"), ACTIVA("ACTIVA"), MANTENIMIENTO("MANTENIMIENTO"), FUERASERVICIO("FUERA DE SERVICIO")
    }

    /**
     * Enumeración de los tipos de filtro de ocupación.
     * @property value Valor del tipo de filtro de ocupación.
     */
    enum class TipoFiltroOcupacion(val value : String){
        TODAS("TODAS"), LIBRE("LIBRE"), ENRESERVA("EN RESERVA"), OCUPADA("OCUPADA")
    }

    /**
     * Enumeración de los tipos de filtro de tipo.
     * @property value Valor del tipo de filtro de tipo.
     */
    enum class TipoFiltroTipo(val value : String){
        TODAS("TODAS"), NORMAL("NORMAL"), VIP("VIP")
    }

    /**
     * Enumeración de los tipos de estado de las butacas.
     * @property value Valor del tipo de estado de la butaca.
     */
    enum class TipoEstado(val value : String){
        ACTIVA("ACTIVA"), MANTENIMIENTO("MANTENIMIENTO"), FUERASERVICIO("FUERA DE SERVICIO")
    }

    /**
     * Enumeración de los tipos de ocupación de las butacas.
     * @property value Valor del tipo de ocupación de la butaca.
     */
    enum class TipoOcupacion(val value : String){
        LIBRE("LIBRE"), ENRESERVA("EN RESERVA"), OCUPADA("OCUPADA")
    }

    /**
     * Enumeración de los tipos de butacas.
     * @property value Valor del tipo de butaca.
     */
    enum class TipoTipo(val value : String){
        NORMAL("NORMAL"), VIP("VIP")
    }
}