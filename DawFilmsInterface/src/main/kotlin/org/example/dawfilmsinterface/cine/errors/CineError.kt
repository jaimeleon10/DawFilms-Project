package org.example.dawfilmsinterface.cine.errors

sealed class CineError(val message: String) {
    class ExportZipError(message: String) : CineError(message)
    class ImportZipError(message: String) : CineError(message)
}