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

        val listVentasEntity = serviceVentas.getAllVentasEntity().value
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
        logger.debug { "Importando datos desde ZIP" }
        storageZip.loadFromZip(file).onSuccess { listaCine ->
            logger.warn { listaCine.forEach { println(it) } }
            val clientes: List<Cliente> = listaCine.filterIsInstance<Cliente>()
            val ventas: List<Venta> = listaCine.filterIsInstance<Venta>()
            val butacas: List<Butaca> = listaCine.filterIsInstance<Butaca>()
            val complementos: List<Complemento> = listaCine.filterIsInstance<Complemento>()

            if (clientes.isNotEmpty()) {
                serviceClientes.deleteAllClientes()
                clientes.forEach { serviceClientes.save(it) }
            }

            if (ventas.isNotEmpty()) {
                serviceVentas.deleteAllVentas()
                ventas.forEach { serviceVentas.createVenta(it) }
            }

            if (butacas.isNotEmpty()) {
                serviceProductos.deleteAllButacas()
                serviceProductos.saveAllButacas(butacas)
            }

            if (complementos.isNotEmpty()) {
                serviceProductos.deleteAllComplementos()
                serviceProductos.saveAllComplementos(complementos)
            }

        }.onFailure {
            Err(ProductoError.ProductoStorageError(it.message))
        }
        return Ok(listOf())
    }

    fun exportarEstadoCine(file: File): Result<Long, ProductoError> {
        logger.debug { "Exportando estado del cine" }
        // TODO -> CAMBIAR ESTO, DEBE RECOGER LA LISTA DE PRODUCTOS DEL STATE
        // TODO -> ESTA LISTA LLEGA DESDE EL CONTROLLER DE EXPORTARESTADOCINE DEPENDIENDO DE UNA FECHA
        return storageProductos.storeJson(file, serviceProductos.getAllProductos().value)
    }

    data class MenuAdminState(
        val fechaEstadoCine: LocalDate = LocalDate.now()
    )
}