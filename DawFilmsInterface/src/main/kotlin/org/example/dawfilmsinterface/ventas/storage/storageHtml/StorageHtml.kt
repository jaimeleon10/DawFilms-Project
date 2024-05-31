package org.example.dawfilmsinterface.ventas.storage.storageHtml

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.Venta
import java.io.File

/**
 * Interfaz para manejar el almacenamiento de ventas en formato HTML.
 * Esta interfaz proporciona métodos para exportar ventas a archivos HTML.
 * @since 1.0.0
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
interface StorageHtml {

    /**
     * Exporta una venta a un archivo HTML.
     * @param venta La venta a exportar.
     * @param file El archivo HTML de destino.
     * @return Un [Result] que indica el éxito de la operación o un error [VentaError] si falla.
     */
    fun exportHtml(venta: Venta, file: File): Result<Unit, VentaError>
}