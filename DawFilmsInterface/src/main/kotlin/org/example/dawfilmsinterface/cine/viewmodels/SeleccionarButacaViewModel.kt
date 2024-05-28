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

class SeleccionarButacaViewModel (
    private val service: ProductoService
) {
    val state : SimpleObjectProperty<ButacaSeleccionadaState> = SimpleObjectProperty(ButacaSeleccionadaState())

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

    fun butacaIsSelected(listadoButacasSeleccionadas: MutableList<String>): Boolean {
        logger.debug { "Restaurando icono de butacas seleccionadas" }
        logger.debug { "Buscando butaca por id ${state.value.id}" }
        val butaca = service.getButacaById(state.value.id)

        if (butaca.isOk && listadoButacasSeleccionadas.contains(butaca.value.id)) {
            return true
        } else {
            return false
        }
    }

    fun updateIconoButacaSelected() {
        if (state.value.tipoButaca == TipoButaca.NORMAL) state.value.icono = Image(RoutesManager.getResourceAsStream("icons/butacaSeleccionada.png"))
        if (state.value.tipoButaca == TipoButaca.VIP) state.value.icono = Image(RoutesManager.getResourceAsStream("icons/butacaSeleccionadaVIP.png"))
    }

    fun cambiarIcono() {
        logger.debug { "Actualizando icono de butaca seleccionada" }
        logger.debug { "Buscando butaca por id ${state.value.id}" }
        val butaca = service.getButacaById(state.value.id)

        if (butaca.isOk) {

            state.value.tipoButaca = butaca.value.tipoButaca
            state.value.estadoButaca = butaca.value.estadoButaca
            state.value.ocupacionButaca = butaca.value.ocupacionButaca

            if (state.value.tipoButaca == TipoButaca.NORMAL) state.value.icono = Image(RoutesManager.getResourceAsStream("icons/butacaSeleccionada.png"))
            if (state.value.tipoButaca == TipoButaca.VIP) state.value.icono = Image(RoutesManager.getResourceAsStream("icons/butacaSeleccionadaVIP.png"))
        }
    }

    data class ButacaSeleccionadaState(
        var id: String = "",
        var icono: Image = Image(RoutesManager.getResourceAsStream("icons/butacaSinSeleccionar.png")),
        var tipoButaca: TipoButaca = TipoButaca.NORMAL,
        var estadoButaca: EstadoButaca = EstadoButaca.ACTIVA,
        var ocupacionButaca: OcupacionButaca = OcupacionButaca.LIBRE,
    )
}