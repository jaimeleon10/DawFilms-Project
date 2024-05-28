package org.example.dawfilmsinterface.cine.viewmodels

import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.services.VentaService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class ObtenerRecaudacionViewModelTest {

    @Mock
    lateinit var ventaService: VentaService

    @InjectMocks
    lateinit var viewModel: ObtenerRecaudacionViewModel

    private val lineaVentaButaca = LineaVenta(UUID.randomUUID(), Butaca("A1", "Butaca", "butacaSinSeleccionar.png", 1, 1, TipoButaca.NORMAL, EstadoButaca.ACTIVA, OcupacionButaca.LIBRE), "Butaca", 20, 5.0)

    private val lineaVentaComplemento = LineaVenta(UUID.randomUUID(), Complemento("1", "Complemento", "pistacho.png", "Pistachos", 2.00, 20, CategoriaComplemento.COMIDA), "Complemento", 5, 2.00)

    private val lineasList = listOf(lineaVentaComplemento, lineaVentaButaca)

    @Test
    fun getState() {

    }

    @Test
    fun lineasFilteredListByButaca() {
        viewModel.state.value.lineasVentas = lineasList

        whenever(viewModel.lineasFilteredList("Butaca")).thenReturn(listOf(lineaVentaButaca))

        val result = viewModel.lineasFilteredList("Butaca")

        assertEquals(result.size, lineasList.size)
    }

    @Test
    fun lineasFilteredListByComplemento(){
        viewModel.state.value.lineasVentas = lineasList

        whenever(viewModel.lineasFilteredList("Complemento")).thenReturn(listOf(lineaVentaComplemento))

        val result = viewModel.lineasFilteredList("Complemento")

        assertEquals(result.size, lineasList.size)
    }
}