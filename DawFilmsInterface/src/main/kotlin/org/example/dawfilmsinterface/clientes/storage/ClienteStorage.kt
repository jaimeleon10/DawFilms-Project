package org.example.dawfilmsinterface.clientes.storage

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.clientes.errors.ClienteError
import org.example.dawfilmsinterface.clientes.models.Cliente
import java.io.File

/**
 * Interfaz que define las operaciones de almacenamiento de clientes en formato JSON.
 * Permite almacenar y cargar datos de clientes desde un archivo JSON.
 * @param file Archivo donde se almacenarán o desde donde se cargarán los datos de los clientes.
 * @param data Lista de clientes a almacenar o cargar.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
interface ClienteStorage {
    /**
     * Almacena una lista de clientes en formato JSON en un archivo.
     * @param file Archivo donde se almacenarán los datos.
     * @param data Lista de clientes a almacenar.
     * @return Un [Result] que contiene el tamaño del archivo en bytes si la operación fue exitosa, o un error [ClienteError] si falla.
     */
    fun storeJson(file: File, data: List<Cliente>): Result<Long, ClienteError>
    /**
     * Carga una lista de clientes desde un archivo JSON.
     * @param file Archivo del cual se cargarán los datos.
     * @return Un [Result] que contiene la lista de clientes si la operación fue exitosa, o un error [ClienteError] si falla.
     */
    fun loadJson(file: File): Result<List<Cliente>, ClienteError>
}