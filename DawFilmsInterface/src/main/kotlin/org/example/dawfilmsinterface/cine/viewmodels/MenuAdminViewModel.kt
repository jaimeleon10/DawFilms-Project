package org.example.dawfilmsinterface.cine.viewmodels

import com.github.michaelbull.result.*
import javafx.beans.property.SimpleObjectProperty
import org.example.dawfilmsinterface.cine.errors.CineError
import org.example.dawfilmsinterface.cine.services.storage.CineStorageZip
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.services.ClienteService
import org.example.dawfilmsinterface.productos.service.ProductoService
import org.example.dawfilmsinterface.productos.storage.genericStorage.ProductosStorage
import java.io.File
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import org.example.dawfilmsinterface.ventas.services.VentaService
import org.lighthousegames.logging.logging
import java.time.LocalDate
import java.util.*

private val logger = logging()

class MenuAdminViewModel(
    private val serviceProductos: ProductoService,
    private val serviceVentas: VentaService,
    private val serviceClientes: ClienteService,
    private val storageZip: CineStorageZip,
    private val storageProductos: ProductosStorage
) {
    val state: SimpleObjectProperty<MenuAdminState> = SimpleObjectProperty(MenuAdminState())

    fun importarButacas(file: File): Result<List<Producto>, ProductoError> {
        if (file.extension == "csv") {
            logger.debug { "Cargando butacas desde archivo CSV" }
            return storageProductos.loadCsv(file).onSuccess { listaProductos ->
                val listaButacas: List<Butaca> = listaProductos.filterIsInstance<Butaca>()
                if (listaButacas.size == 35) {
                    serviceProductos.deleteAllButacas()
                    serviceProductos.saveAllButacas(listaButacas)
                } else {
                    logger.debug { "Butacas no importadas, debe haber 35 exactas para coincidir con el tamaño del cine" }
                }
            }.onFailure {
                Err(ProductoError.ProductoStorageError(it.message))
            }
        } else if (file.extension == "json") {
            logger.debug { "Cargando butacas desde archivo JSON" }
            return storageProductos.loadJson(file).onSuccess { listaProductos ->
                val listaButacas: List<Butaca> = listaProductos.filterIsInstance<Butaca>()
                if (listaButacas.size == 35) {
                    serviceProductos.deleteAllButacas()
                    serviceProductos.saveAllButacas(listaButacas)
                } else {
                    logger.debug { "Butacas no importadas, debe haber 35 exactas para coincidir con el tamaño del cine" }
                }
            }.onFailure {
                Err(ProductoError.ProductoStorageError(it.message))
            }
        } else {
            logger.debug { "Cargando butacas desde archivo CSV" }
            return storageProductos.loadXml(file).onSuccess { listaProductos ->
                val listaButacas: List<Butaca> = listaProductos.filterIsInstance<Butaca>()
                if (listaButacas.size == 35) {
                    serviceProductos.deleteAllButacas()
                    serviceProductos.saveAllButacas(listaButacas)
                } else {
                    logger.debug { "Butacas no importadas, debe haber 35 exactas para coincidir con el tamaño del cine" }
                }
            }.onFailure {
                Err(ProductoError.ProductoStorageError(it.message))
            }
        }
    }

    fun exportarButacas(file: File): Result<Long, ProductoError> {
        if (file.extension == "csv") {
            logger.debug { "Guardando butacas en CSV" }
            return storageProductos.storeCsv(file, serviceProductos.getAllButacas().value)
        } else if (file.extension == "json") {
            logger.debug { "Guardando butacas en JSON" }
            return storageProductos.storeJson(file, serviceProductos.getAllButacas().value)
        } else {
            logger.debug { "Guardando butacas en XML" }
            return storageProductos.storeXml(file, serviceProductos.getAllButacas().value)
        }
    }

    fun importarComplementos(file: File): Result<List<Producto>, ProductoError> {
        if (file.extension == "csv") {
            logger.debug { "Cargando complementos de CSV" }
            return storageProductos.loadCsv(file).onSuccess { listaProductos ->
                logger.warn { listaProductos.forEach { println(it) } }
                val listaComplementos: List<Complemento> = listaProductos.filterIsInstance<Complemento>()
                serviceProductos.deleteAllComplementos()
                serviceProductos.saveAllComplementos(listaComplementos)
            }.onFailure {
                Err(ProductoError.ProductoStorageError(it.message))
            }
        } else if (file.extension == "json") {
            logger.debug { "Cargando butacas desde archivo JSON" }
            return storageProductos.loadJson(file).onSuccess { listaProductos ->
                val listaComplementos: List<Complemento> = listaProductos.filterIsInstance<Complemento>()
                serviceProductos.deleteAllComplementos()
                serviceProductos.saveAllComplementos(listaComplementos)
            }.onFailure {
                Err(ProductoError.ProductoStorageError(it.message))
            }
        } else {
            logger.debug { "Cargando butacas desde archivo CSV" }
            return storageProductos.loadXml(file).onSuccess { listaProductos ->
                val listaComplementos: List<Complemento> = listaProductos.filterIsInstance<Complemento>()
                serviceProductos.deleteAllComplementos()
                serviceProductos.saveAllComplementos(listaComplementos)
            }.onFailure {
                Err(ProductoError.ProductoStorageError(it.message))
            }
        }
    }

    fun exportarComplementos(file: File): Result<Long, ProductoError> {
        if (file.extension == "csv") {
            logger.debug { "Guardando complementos en CSV" }
            return storageProductos.storeCsv(file, serviceProductos.getAllComplementos().value)
        } else if (file.extension == "json") {
            logger.debug { "Guardando complementos en JSON" }
            return storageProductos.storeJson(file, serviceProductos.getAllComplementos().value)
        } else {
            logger.debug { "Guardando complementos en XML" }
            return storageProductos.storeXml(file, serviceProductos.getAllComplementos().value)
        }
    }

    fun exportarZip(file: File): Result<File, CineError> {
        logger.debug { "Guardando fichero ZIP" }
        val listProductos = serviceProductos.getAllProductos().value
        val listClientes = serviceClientes.getAll().value
        val listVentas = mutableListOf<Venta>()

        val listVentasEntity = serviceVentas.getAllVentas().value
        var cliente: Cliente
        var lineas: List<LineaVenta> = listOf()

        for (venta in listVentasEntity) {
            cliente = serviceClientes.getById(venta.cliente_id).value
            lineas = serviceVentas.getAllLineasByVentaID(venta.id).value
            listVentas.add(
                Venta(
                    id = UUID.fromString(venta.id),
                    cliente = cliente,
                    lineas = lineas,
                    fechaCompra = LocalDate.parse(venta.fecha_compra),
                    createdAt = LocalDate.parse(venta.created_at),
                    updatedAt = LocalDate.parse(venta.updated_at),
                    isDeleted = venta.is_deleted == 1L
                )
            )
        }
        return storageZip.exportToZip(file, listProductos, listClientes, listVentas)
    }

    fun importarZip(file: File): Result<List<Any>, CineError> {
        // TODO -> Importar zip y cargar en bbdd cada linea
        return Ok(listOf()) // BORRAR, SOLO PARA QUE NO DE ERROR
    }

    fun exportarEstadoCine() {

    }

    data class MenuAdminState(
        val hola: String = ""
    )
}