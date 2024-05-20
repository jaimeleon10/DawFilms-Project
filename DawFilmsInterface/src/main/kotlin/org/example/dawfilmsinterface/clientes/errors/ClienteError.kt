package org.example.dawfilmsinterface.clientes.errors

sealed class ClienteError(val message: String) {
    class ClienteNoEncontrado(message: String) : ClienteError(message)
    class ClienteNoActualizado(message: String) : ClienteError(message)
    class ClienteNoEliminado(message: String) : ClienteError(message)
    class ClienteStorageError(message: String) : ClienteError(message)
    class ClienteValidationError(message: String) : ClienteError(message)
}