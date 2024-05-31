package org.example.dawfilmsinterface.cine.services.storageHtml

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import java.io.File

/**
 * Interfaz que define un almacenamiento para exportar datos de recaudación a un archivo HTML.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
interface StorageHtmlRecaudacion {
    /**
     * Exporta una lista de líneas de venta a un archivo HTML.
     * @param listaLineas Lista de líneas de venta a exportar.
     * @param file Archivo HTML de destino.
     * @return [Result] que indica el resultado de la operación. [Unit] en caso de éxito, [VentaError] en caso de error.
     */
    fun exportHtml(listaLineas: List<LineaVenta>, file: File): Result<Unit, VentaError>
}