package org.example.dawfilmsinterface.cine.viewmodels

import com.github.michaelbull.result.*
import javafx.beans.property.SimpleObjectProperty
import org.example.dawfilmsinterface.cine.errors.CineError
import org.example.dawfilmsinterface.cine.services.storage.CineStorageZip
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.services.ClienteService
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.database.SqlDeLightManager
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
import java.nio.file.Files
import java.time.LocalDate
import java.util.*

private val logger = logging()
/**
 * Clase MenuAdminViewModel
 *
 * Gestiona la lógica del menú de administrador, incluyendo la importación y exportación de datos.
 *
 * @param serviceProductos Servicio para gestionar productos.
 * @param serviceVentas Servicio para gestionar ventas.
 * @param serviceClientes Servicio para gestionar clientes.
 * @param storageZip Servicio para gestionar almacenamiento en formato ZIP.
 * @param storageProductos Servicio para gestionar almacenamiento de productos.
 * @param database Gestor de la base de datos.
 * @param config Configuración de la aplicación.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
class MenuAdminViewModel(
    private val serviceProductos: ProductoService,
    private val serviceVentas: VentaService,
    private val serviceClientes: ClienteService,
    private val storageZip: CineStorageZip,
    private val storageProductos: ProductosStorage,
    private val database: SqlDeLightManager,
    private val config: Config
) {
    val state: SimpleObjectProperty<MenuAdminState> = SimpleObjectProperty(MenuAdminState())
    /**
     * Importa butacas desde un archivo CSV, JSON o XML.
     *
     * @param file Archivo a importar.
     * @return Resultado de la importación.
     */
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
    /**
     * Exporta butacas a un archivo CSV, JSON o XML.
     *
     * @param file Archivo de destino.
     * @return Resultado de la exportación.
     */
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
    /**
     * Importa complementos desde un archivo CSV, JSON o XML.
     *
     * @param file Archivo a importar.
     * @return Resultado de la importación.
     */
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
    /**
     * Exporta complementos a un archivo CSV, JSON o XML.
     *
     * @param file Archivo de destino.
     * @return Resultado de la exportación.
     */
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
    /**
     * Exporta todos los datos del cine a un archivo ZIP.
     *
     * @param file Archivo de destino.
     * @return Resultado de la exportación.
     */
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
    /**
     * Importa datos desde un archivo ZIP.
     *
     * @param file Archivo a importar.
     * @return Resultado de la importación.
     */
    fun importarZip(file: File): Result<List<Any>, CineError> {
        logger.debug { "Importando datos desde ZIP" }
        storageZip.loadFromZip(file).onSuccess { listaCine ->
            val clientes: List<Cliente> = listaCine.filterIsInstance<Cliente>()
            val ventas: List<Venta> = listaCine.filterIsInstance<Venta>()
            val butacas: List<Butaca> = listaCine.filterIsInstance<Butaca>()
            val complementos: List<Complemento> = listaCine.filterIsInstance<Complemento>()

            database.initQueries()

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
    /**
     * Exporta el estado del cine en una fecha específica a un archivo JSON.
     *
     * @param file Archivo de destino.
     * @return Resultado de la exportación.
     */
    fun exportarEstadoCine(file: File): Result<Long, ProductoError> {
        logger.debug { "Exportando estado del cine" }
        val ventasFecha = serviceVentas.getAllVentasByDate(state.value.fechaEstadoCine).value
        val lineas: MutableList<LineaVenta> = mutableListOf()
        val pructosEstadoCine: MutableList<Producto> = mutableListOf()

        for (venta in ventasFecha) serviceVentas.getAllLineasByVentaID(venta.id).value.forEach { lineas.add(it) }

        for (linea in lineas) pructosEstadoCine.add(linea.producto)

        if (pructosEstadoCine.isEmpty()) {
            return Err(ProductoError.ProductoNoEncontrado("No existen ventas en la fecha dada"))
        }
        else {
            return Ok(storageProductos.storeJson(file, pructosEstadoCine).value)
        }
    }
    /**
     * Clase de datos MenuAdminState
     *
     * Representa el estado del menú de administrador.
     *
     * @param fechaEstadoCine Fecha para exportar el estado del cine.
     */
    data class MenuAdminState(
        var fechaEstadoCine: LocalDate = LocalDate.now()
    )
}