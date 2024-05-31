package org.example.dawfilmsinterface.productos.errors

/**
 * Clase sellada que representa los posibles errores relacionados con operaciones de productos.
 * Esta clase se utiliza para manejar errores específicos de productos en la capa de servicios.
 * @property message El mensaje de error asociado al error.
 * @constructor Crea una instancia de ProductoError con el mensaje especificado.
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
sealed class ProductoError(val message: String) {
    /**
     * Error que indica que un producto no ha sido encontrado.
     * @param message El mensaje de error asociado a esta excepción.
     */
    class ProductoNoEncontrado(message: String) : ProductoError(message)
    /**
     * Error de validación de producto.
     * @param message El mensaje de error asociado a esta excepción.
     */
    class ProductoValidationError(message: String) : ProductoError(message)
    /**
     * Error que indica que un producto no ha sido actualizado correctamente.
     * @param message El mensaje de error asociado a esta excepción.
     */
    class ProductoNoActualizado(message: String) : ProductoError(message)
    /**
     * Error que indica que un producto no ha sido eliminado correctamente.
     * @param message El mensaje de error asociado a esta excepción.
     */
    class ProductoNoEliminado(message: String) : ProductoError(message)
    /**
     * Error relacionado con el almacenamiento de productos.
     * @param message El mensaje de error asociado a esta excepción.
     */
    class ProductoStorageError(message: String) : ProductoError(message)
}