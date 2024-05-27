package org.example.dawfilmsinterface.productos.viewmodels

import javafx.beans.property.SimpleObjectProperty
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.example.dawfilmsinterface.ventas.services.VentaService
import org.lighthousegames.logging.logging

private val logger = logging()

class ConfirmarCompraViewModel(
    private val serviceProducto: ProductoService,
    private val ventaService: VentaService,
) {
    fun updateToComplementosList(listado: MutableMap<String, Int>): MutableMap<Complemento, Int> {
        val newList: MutableMap<Complemento, Int> = mutableMapOf()
        listado.keys.forEach { newList.put(serviceProducto.getComplementoByNombre(it).value, listado.getValue(it)) }
        return newList
    }

    fun updateToButacasList(listado: MutableList<String>): MutableList<Butaca> {
        val newList: MutableList<Butaca> = mutableListOf()
        listado.forEach { newList.add(serviceProducto.getButacaById(it).value) }
        return newList
    }

    val state: SimpleObjectProperty<GestionCompraState> = SimpleObjectProperty(GestionCompraState())

    data class GestionCompraState (
        val complementos: List<Complemento> = listOf(),
        val butacas: List<Butaca> = listOf(),
    )
}