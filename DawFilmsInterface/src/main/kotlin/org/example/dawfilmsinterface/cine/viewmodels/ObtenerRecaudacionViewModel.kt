package org.example.dawfilmsinterface.cine.viewmodels

import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.lighthousegames.logging.logging

private val logger = logging()

class ObtenerRecaudacionViewModel(
    private val service : ProductoService
){
    val state : SimpleObjectProperty<RecaudacionState> = SimpleObjectProperty(RecaudacionState())

    init {
        logger.debug { "Inicializando ObtenerRecaudacionViewModel" }
        loadAllProductos()
        loadTypes()
    }

    private fun loadTypes() {
        logger.debug { "Cargando tipos" }
        state.value = state.value.copy(typesProducto = TipoFiltroProducto.entries.map { it.value })
    }

    private fun loadAllProductos(){
        logger.debug { "Cargando productos del repositorio" }
        service.getAllProductos().onSuccess {
            logger.debug { "Cargando productos del repositorio: ${it.size}" }
            state.value = state.value.copy(productos = it)
            updateActualState()
        }
    }

    private fun updateActualState() {
        logger.debug { "Actualizando el estado de Producto" }
        state.value = state.value.copy(
            producto = ProductoState()
        )
    }

    fun productosFilteredList(tipoProducto: String) : List<Producto>{
        logger.debug { "Filtrando lista de Productos: $tipoProducto" }

        return state.value.productos
            .filter { producto ->
                when (tipoProducto) {
                    TipoFiltroProducto.TODOS.value -> true
                    TipoFiltroProducto.BUTACAS.value -> producto.tipoProducto == "Butaca"
                    TipoFiltroProducto.COMPLEMENTOS.value -> producto.tipoProducto == "Complemento"
                    else -> true
                }
            }
    }

    data class RecaudacionState(
        val typesProducto : List<String> = emptyList(),

        val cantidad : Int = 3,
        val precioUnitario : Double = 5.00,
        val precioTotal : Double = 10.0,

        val producto: ProductoState = ProductoState(),

        val productos : List<Producto> = emptyList()
    )

    data class ProductoState(
        val precio : Double = 5.00,
    )

    enum class TipoFiltroProducto(val value : String){
        TODOS("TODOS"), BUTACAS("BUTACAS"), COMPLEMENTOS("COMPLEMENTOS")
    }
}