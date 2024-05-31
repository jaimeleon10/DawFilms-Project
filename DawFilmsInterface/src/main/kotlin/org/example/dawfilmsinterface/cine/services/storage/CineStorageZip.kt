package org.example.dawfilmsinterface.cine.services.storage

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.cine.errors.CineError
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.ventas.models.Venta
import java.io.File
/**
 * Interfaz que define métodos para operaciones de exportación e importación de datos a/desde archivos ZIP en el sistema del cine.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
interface CineStorageZip {
    /**
     * Exporta datos a un archivo ZIP.
     * @param fileToZip Archivo en el que se exportarán los datos.
     * @param dataProductos Lista de productos a exportar.
     * @param dataClientes Lista de clientes a exportar.
     * @param dataVentas Lista de ventas a exportar.
     * @return [Result] que contiene el archivo ZIP resultante en caso de éxito, o un error de tipo [CineError] en caso de fallo.
     */
    fun exportToZip(fileToZip: File, dataProductos: List<Producto>, dataClientes: List<Cliente>, dataVentas: List<Venta>): Result<File, CineError>

    /**
     * Carga datos desde un archivo ZIP.
     * @param fileToUnzip Archivo ZIP del que se cargarán los datos.
     * @return [Result] que contiene una lista de elementos cargados desde el archivo ZIP en caso de éxito, o un error de tipo [CineError] en caso de fallo.
     */
    fun loadFromZip(fileToUnzip: File): Result<List<Any>, CineError>
}