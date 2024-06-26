package org.example.dawfilmsinterface.productos.storage.storageImage

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import java.io.File

/**
 * Interfaz que define las operaciones de almacenamiento de imágenes.
 * @since 1.0.0
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
interface StorageImage {
    /** Saca el nombre de un fichero de imagen
     * @param newFileImage fichero del cual sacamos el nombre
     * @return devuelve un string con el nombre del archivo
     */
    fun getImageName(newFileImage: File): String

    /**
     * Guarda una imagen con el nombre especificado.
     * @param fileName el nombre del archivo de imagen a guardar.
     * @return un [Result] con el archivo de imagen guardado o un error de [ProductoError].
     */
    fun saveImage(fileName: File): Result<File, ProductoError>

    /**
     * Carga una imagen con el nombre especificado.
     * @param fileName el nombre del archivo de imagen a cargar.
     * @return un [Result] con el archivo de imagen cargado o un error de [ProductoError].
     */
    fun loadImage(fileName: String): Result<File, ProductoError>

    /**
     * Elimina la imagen con el nombre especificado.
     * @param fileName el nombre del archivo de imagen a eliminar.
     * @return un [Result] que indica si la eliminación fue exitosa o un error de [ProductoError].
     */
    fun deleteImage(fileName: File): Result<Unit, ProductoError>

    /**
     * Elimina todas las imágenes almacenadas.
     * @return un [Result] con la cantidad de imágenes eliminadas o un error de [ProductoError].
     */
    fun deleteAllImages(): Result<Long, ProductoError>

    /**
     * Actualiza una imagen con el nuevo archivo especificado.
     * @param imageName el nombre de la imagen a actualizar.
     * @param newFileImage el nuevo archivo de imagen.
     * @return un [Result] con el archivo de imagen actualizado o un error de [ProductoError].
     */
    fun updateImage(imageName: String, newFileImage: File): Result<File, ProductoError>
}