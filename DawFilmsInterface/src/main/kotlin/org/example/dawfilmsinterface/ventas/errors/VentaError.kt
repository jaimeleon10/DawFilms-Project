package org.example.dawfilmsinterface.ventas.errors

/**
 * Clase sellada para representar diferentes tipos de errores relacionados con las ventas.
 * @param message Mensaje de error descriptivo.
 * @since 1.0.0
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
sealed class VentaError(val message: String) {

    /**
     * Error cuando la venta no se encuentra.
     * @param message Mensaje de error descriptivo.
     */
    class VentaNoEncontrada(message: String) : VentaError(message)

    /**
     * Error cuando la venta no es válida.
     * @param message Mensaje de error descriptivo.
     */
    class VentaNoValida(message: String) : VentaError(message)

    /**
     * Error cuando la venta no se puede almacenar.
     * @param message Mensaje de error descriptivo.
     */
    class VentaNoAlmacenada(message: String) : VentaError(message)

    /**
     * Error general de almacenamiento relacionado con ventas.
     * @param message Mensaje de error descriptivo.
     */
    class VentaStorageError(message: String) : VentaError(message)
}