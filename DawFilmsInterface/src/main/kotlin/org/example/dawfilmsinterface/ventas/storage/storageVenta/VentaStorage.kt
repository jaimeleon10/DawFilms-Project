package org.example.dawfilmsinterface.ventas.storage.storageVenta

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.Venta
import java.io.File

/**
 * Interfaz para manejar el almacenamiento de ventas en formato JSON.
 * @since 1.0.0
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
interface VentaStorage {

    /**
     * Almacena una lista de ventas en formato JSON en un archivo.
     * @param file El archivo donde se almacenarán los datos.
     * @param data La lista de ventas a almacenar.
     * @return Un [Result] que indica el éxito de la operación o un error [VentaError] si falla.
     */
    fun storeJson(file: File, data: List<Venta>): Result<Long, VentaError>

    /**
     * Carga una lista de ventas desde un archivo JSON.
     * @param file El archivo del cual se cargarán los datos.
     * @return Un [Result] que contiene la lista de ventas cargadas o un error [VentaError] si falla.
     */
    fun loadJson(file: File): Result<List<Venta>, VentaError>
}