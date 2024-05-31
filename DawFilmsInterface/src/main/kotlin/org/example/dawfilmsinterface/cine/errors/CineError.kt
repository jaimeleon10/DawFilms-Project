package org.example.dawfilmsinterface.cine.errors

/**
 * Clase sellada que representa errores relacionados con operaciones de exportación e importación de archivos ZIP en el sistema del cine.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @param message Mensaje descriptivo del error.
 */
sealed class CineError(val message: String) {
    class ExportZipError(message: String) : CineError(message)
    class ImportZipError(message: String) : CineError(message)
}