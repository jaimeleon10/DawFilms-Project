package org.example.dawfilmsinterface.cine.models

private const val TAM_FILAS = 5
private const val TAM_COLUMNAS = 7

class Sala {
    private val sala: Array<Array<Any?>> = Array(TAM_FILAS) { arrayOfNulls(TAM_COLUMNAS) }
}