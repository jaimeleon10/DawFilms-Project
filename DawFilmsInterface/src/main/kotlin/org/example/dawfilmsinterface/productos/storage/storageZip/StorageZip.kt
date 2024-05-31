package org.example.dawfilmsinterface.productos.storage.storageZip

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.io.File

/**
 * Interface que define las operaciones para exportar e importar datos en formato ZIP.
 * @since 1.0.0
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
interface StorageZip {

    /**
     * Exporta los datos proporcionados en un archivo ZIP.
     * @param fileToZip el archivo ZIP en el que se exportarán los datos.
     * @param data la lista de productos a exportar.
     * @return un [Result] que indica el resultado de la operación, donde Ok contiene el archivo ZIP exportado y Err contiene un error.
     */
    fun exportToZip(fileToZip: File, data: List<Producto>): Result<File, ProductoError>

    /**
     * Carga los datos desde un archivo ZIP.
     *
     * @param fileToUnzip el archivo ZIP desde el cual se cargarán los datos.
     * @return un [Result] que indica el resultado de la operación, donde Ok contiene la lista de productos cargados y Err contiene un error.
     */
    fun loadFromZip(fileToUnzip: File): Result<List<Producto>, ProductoError>
}