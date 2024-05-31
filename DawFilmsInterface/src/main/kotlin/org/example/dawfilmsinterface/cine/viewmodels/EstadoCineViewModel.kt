package org.example.dawfilmsinterface.cine.viewmodels

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Clase ViewModel que gestiona el estado de una butaca de cine.
 * @param service Servicio para obtener información de las butacas.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
class EstadoCineViewModel(
    private val service: ProductoService
) {

    val state : SimpleObjectProperty<EstadoCineState> = SimpleObjectProperty(EstadoCineState())

    /**
     * Actualiza el icono de la butaca en función de su estado.
     */
    fun iconoPorDefecto() {
        logger.debug { "Actualizando icono con butaca por defecto" }
        logger.debug { "Buscando butaca por id ${state.value.id}" }
        val butaca = service.getButacaById(state.value.id)

        if (butaca.isOk) {

            state.value.tipoButaca = butaca.value.tipoButaca
            state.value.estadoButaca = butaca.value.estadoButaca
            state.value.ocupacionButaca = butaca.value.ocupacionButaca

            if (state.value.tipoButaca == TipoButaca.NORMAL) state.value.icono = Image(RoutesManager.getResourceAsStream("icons/butacaSinSeleccionar.png"))
            if (state.value.tipoButaca == TipoButaca.VIP) state.value.icono = Image(RoutesManager.getResourceAsStream("icons/butacaSinSeleccionar.png"))

            if (state.value.ocupacionButaca == OcupacionButaca.OCUPADA) state.value.icono = Image(RoutesManager.getResourceAsStream("icons/butacaOcupada.png"))
            if (state.value.ocupacionButaca == OcupacionButaca.ENRESERVA) state.value.icono = Image(RoutesManager.getResourceAsStream("icons/butacaOcupada.png"))

            if (state.value.estadoButaca == EstadoButaca.FUERASERVICIO) state.value.icono = Image(RoutesManager.getResourceAsStream("icons/butacaFueraDeServicio.png"))
            if (state.value.estadoButaca == EstadoButaca.MANTENIMIENTO) state.value.icono = Image(RoutesManager.getResourceAsStream("icons/butacaMantenimiento.png"))
        }
    }

    /**
     * Clase que define el estado de una butaca de cine.
     * @param id Identificador único de la butaca.
     * @param icono Imagen que representa el estado de la butaca.
     * @param tipoButaca Tipo de butaca (NORMAL o VIP).
     * @param estadoButaca Estado de la butaca (ACTIVA, FUERASERVICIO, MANTENIMIENTO).
     * @param ocupacionButaca Ocupación de la butaca (LIBRE, OCUPADA, ENRESERVA).
     */
    data class EstadoCineState(
        var id: String = "",
        var icono: Image = Image(RoutesManager.getResourceAsStream("icons/butacaSinSeleccionar.png")),
        var tipoButaca: TipoButaca = TipoButaca.NORMAL,
        var estadoButaca: EstadoButaca = EstadoButaca.ACTIVA,
        var ocupacionButaca: OcupacionButaca = OcupacionButaca.LIBRE,
    )
}