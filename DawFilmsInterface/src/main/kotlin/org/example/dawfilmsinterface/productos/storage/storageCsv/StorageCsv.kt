package org.example.dawfilmsinterface.productos.storage.storageCsv

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.io.File

/**
 * Interfaz que define las operaciones de almacenamiento y carga de datos en formato CSV.
 * @since 1.0.0
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
interface StorageCsv {

    /**
     * Almacena los datos en formato CSV en el archivo especificado.
     * @param file el archivo en el que se almacenarán los datos.
     * @param data la lista de productos a almacenar.
     * @return un [Result] que indica la cantidad de elementos almacenados o un error de [ProductoError].
     */
    fun storeCsv(file: File, data: List<Producto>): Result<Long, ProductoError>

    /**
     * Lee los datos almacenados en formato CSV desde el archivo especificado.
     * @param file el archivo del que se cargarán los datos.
     * @return un [Result] con la lista de productos cargados o un error de [ProductoError].
     */
    fun loadCsv(file: File): Result<List<Producto>, ProductoError>
}
