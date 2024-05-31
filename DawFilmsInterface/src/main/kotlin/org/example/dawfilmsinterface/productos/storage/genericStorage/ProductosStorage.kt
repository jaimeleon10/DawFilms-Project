package org.example.dawfilmsinterface.productos.storage.genericStorage

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.Venta
import java.io.File

/**
 * Interfaz que define operaciones para almacenar y recuperar productos en varios formatos, gestionar imágenes y realizar
 * operaciones de importación y exportación en formato ZIP.
 *
 * @since 1.0.0
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
interface ProductosStorage {

    /**
     * Almacena los datos de productos en formato CSV en el archivo especificado.
     * @param file el archivo en el que se almacenarán los datos.
     * @param data la lista de productos a almacenar.
     * @return un [Result] que indica la cantidad de elementos almacenados o un error de [ProductoError].
     */
    fun storeCsv(file: File, data: List<Producto>): Result<Long, ProductoError>

    /**
     * Carga los datos de productos desde un archivo CSV.
     * @param file el archivo del que se cargarán los datos.
     * @return un [Result] que contiene la lista de productos cargados si la operación fue exitosa o un error de [ProductoError].
     */
    fun loadCsv(file: File): Result<List<Producto>, ProductoError>

    /**
     * Almacena los datos de productos en formato JSON en el archivo especificado.
     *
     * @param file el archivo en el que se almacenarán los datos.
     * @param data la lista de productos a almacenar.
     * @return un [Result] que indica la cantidad de elementos almacenados o un error de [ProductoError].
     */
    fun storeJson(file: File, data: List<Producto>): Result<Long, ProductoError>

    /**
     * Carga los datos de productos desde un archivo JSON.
     * @param file el archivo del que se cargarán los datos.
     * @return un [Result] que contiene la lista de productos cargados si la operación fue exitosa o un error de [ProductoError].
     */

    fun loadJson(file: File): Result<List<Producto>, ProductoError>

    /**
     * Almacena los datos de productos en formato XML en el archivo especificado.
     * @param file el archivo en el que se almacenarán los datos.
     * @param data la lista de productos a almacenar.
     * @return un [Result] que indica la cantidad de elementos almacenados o un error de [ProductoError].
     */
    fun storeXml(file: File, data: List<Producto>): Result<Long, ProductoError>

    /**
     * Carga los datos de productos desde un archivo XML.
     * @param file el archivo del que se cargarán los datos.
     * @return un [Result] que contiene la lista de productos cargados si la operación fue exitosa o un error de [ProductoError].
     */
    fun loadXml(file: File): Result<List<Producto>, ProductoError>

    /**
     * Guarda una imagen en el sistema de archivos.
     * @param fileName el nombre del archivo de imagen.
     * @return un [Result] que contiene el archivo guardado si la operación fue exitosa o un error de [ProductoError].
     */
    fun saveImage(fileName: File): Result<File, ProductoError>

    /**
     * Carga una imagen del sistema de archivos.
     * @param fileName el nombre del archivo de imagen.
     * @return un [Result] que contiene el archivo cargado si la operación fue exitosa o un error de [ProductoError].
     */
    fun loadImage(fileName: String): Result<File, ProductoError>

    /**
     * Elimina una imagen del sistema de archivos.
     * @param fileImage el archivo de imagen a eliminar.
     * @return un [Result] que indica si la operación fue exitosa o un error de [ProductoError].
     */
    fun deleteImage(fileImage: File): Result<Unit, ProductoError>

    /**
     * Elimina todas las imágenes del sistema de archivos.
     * @return un [Result] que indica la cantidad de imágenes eliminadas o un error de [ProductoError].
     */
    fun deleteAllImages(): Result<Long, ProductoError>

    /**
     * Actualiza una imagen en el sistema de archivos.
     * @param imageName el nombre de la imagen a actualizar.
     * @param newFileImage el nuevo archivo de imagen.
     * @return un [Result] que contiene el archivo actualizado si la operación fue exitosa o un error de [ProductoError].
     */
    fun updateImage(imageName: String, newFileImage: File): Result<File, ProductoError>

    /**
     * Exporta los datos de productos a un archivo ZIP.
     * @param fileToZip el archivo ZIP en el que se exportarán los datos.
     * @param data la lista de productos a exportar.
     * @return un [Result] que contiene el archivo ZIP creado si la operación fue exitosa o un error de [ProductoError].
     */
    fun exportToZip(fileToZip: File, data: List<Producto>): Result<File, ProductoError>

    /**
     * Carga los datos de productos desde un archivo ZIP.
     * @param fileToUnzip el archivo ZIP del que se cargarán los datos.
     * @return un [Result] que contiene la lista de productos cargados si la operación fue exitosa o un error de [ProductoError].
     */
    fun loadFromZip(fileToUnzip: File): Result<List<Producto>, ProductoError>
}