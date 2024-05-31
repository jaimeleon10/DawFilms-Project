package org.example.dawfilmsinterface.clientes.errors

/**
 * Clase sellada que representa los posibles errores relacionados con operaciones de cliente.
 * @property message Mensaje de error asociado.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
sealed class ClienteError(val message: String) {

    /**
     * Error que indica que el cliente no ha sido encontrado.
     */
    class ClienteNoEncontrado(message: String) : ClienteError(message)

    /**
     * Error que indica que el cliente no ha sido actualizado correctamente.
     */
    class ClienteNoActualizado(message: String) : ClienteError(message)

    /**
     * Error que indica que el cliente no ha sido eliminado correctamente.
     */
    class ClienteNoEliminado(message: String) : ClienteError(message)

    /**
     * Error relacionado con problemas de almacenamiento del cliente.
     */
    class ClienteStorageError(message: String) : ClienteError(message)

    /**
     * Error relacionado con validación de datos del cliente.
     */
    class ClienteValidationError(message: String) : ClienteError(message)
}