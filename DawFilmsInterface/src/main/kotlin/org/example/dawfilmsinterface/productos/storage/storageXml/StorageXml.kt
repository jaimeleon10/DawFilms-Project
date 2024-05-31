package org.example.dawfilmsinterface.productos.storage.storageXml

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.io.File

/**
 * Interfaz que define métodos para almacenar y cargar datos en formato XML.
 *
 * @since 1.0.0
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
interface StorageXml {

    /**
     * Almacena los datos proporcionados en formato XML en el archivo especificado.
     * @param file el archivo donde se almacenarán los datos en formato XML.
     * @param data la lista de productos a almacenar.
     * @return un [Result] que indica el resultado de la operación, donde Ok contiene la cantidad de elementos almacenados y Err contiene un error.
     */
    fun storeXml(file: File, data: List<Producto>): Result<Long, ProductoError>

    /**
     * Carga los datos en formato XML desde el archivo especificado.
     * @param file el archivo del cual se cargarán los datos en formato XML.
     * @return un [Result] que indica el resultado de la operación, donde Ok contiene la lista de productos cargados y Err contiene un error.
     */
    fun loadXml(file: File): Result<List<Producto>, ProductoError>
}