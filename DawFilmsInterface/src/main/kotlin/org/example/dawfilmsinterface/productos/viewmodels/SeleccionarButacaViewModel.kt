package org.example.dawfilmsinterface.productos.viewmodels

import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.image.Image
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging
import javafx.scene.image.*
import org.example.dawfilmsinterface.productos.models.butacas.Butaca

private val logger = logging()

class SeleccionarButacaViewModel (
    private val service: ProductoService
) {
    val state : SimpleObjectProperty<ButacaSeleccionadaState> = SimpleObjectProperty<ButacaSeleccionadaState>()

    fun iconoPorDefecto() {
        logger.debug { "Buscando butaca por id ${state.value.id}" }
        val butaca = service.getButacaById(state.value.id)

        if (butaca.isOk) {
            state.value.tipoButaca = butaca.value.tipoButaca
            state.value.estadoButaca = butaca.value.estadoButaca
            state.value.ocupacionButaca = butaca.value.ocupacionButaca

            if (butaca.value.ocupacionButaca == OcupacionButaca.OCUPADA) {
                state.value.icono = ImageView(Image(RoutesManager.getResourceAsStream("icons/butacaOcupada.png")))
            }

            when (butaca.value.estadoButaca) {
                EstadoButaca.FUERASERVICIO -> state.value.icono = ImageView(Image(RoutesManager.getResourceAsStream("icons/butacaFueraDeServicio.png")))
                EstadoButaca.MANTENIMIENTO -> state.value.icono = ImageView(Image(RoutesManager.getResourceAsStream("icons/butacaMantenimiento.png")))
                EstadoButaca.ACTIVA -> state.value.icono
            }
        }
    }

    data class ButacaSeleccionadaState(
        var id: String = "",
        var icono: ImageView = ImageView(Image(RoutesManager.getResourceAsStream("icons/butacaSinSeleccionar.png"))),
        var tipoButaca: TipoButaca = TipoButaca.NORMAL,
        var estadoButaca: EstadoButaca = EstadoButaca.ACTIVA,
        var ocupacionButaca: OcupacionButaca = OcupacionButaca.LIBRE
    )
}