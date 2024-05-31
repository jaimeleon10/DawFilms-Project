package org.example.dawfilmsinterface.clientes.services

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.clientes.errors.ClienteError
import org.example.dawfilmsinterface.clientes.models.Cliente

/**
 * Interfaz que define las operaciones de servicio para clientes.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
interface ClienteService {
    /**
     * Obtiene todos los clientes.
     * @return Un [Result] que contiene una lista de clientes si la operación fue exitosa, o un error [ClienteError] si falla.
     */
    fun getAll(): Result<List<Cliente>, ClienteError>
    /**
     * Obtiene un cliente por su identificador.
     * @param id Identificador del cliente.
     * @return Un [Result] que contiene el cliente si la operación fue exitosa, o un error [ClienteError] si falla.
     */
    fun getById(id: Long): Result<Cliente, ClienteError>
    /**
     * Guarda un nuevo cliente.
     * @param cliente Cliente a guardar.
     * @return Un [Result] que contiene el cliente guardado si la operación fue exitosa, o un error [ClienteError] si falla.
     */
    fun save(cliente: Cliente): Result<Cliente, ClienteError>
    /**
     * Actualiza un cliente existente.
     * @param id Identificador del cliente a actualizar.
     * @param cliente Cliente con los nuevos datos.
     * @return Un [Result] que contiene el cliente actualizado si la operación fue exitosa, o un error [ClienteError] si falla.
     */
    fun update(id: Long, cliente: Cliente): Result<Cliente, ClienteError>
    /**
     * Elimina un cliente por su identificador.
     * @param id Identificador del cliente a eliminar.
     * @return Un [Result] que contiene el cliente eliminado si la operación fue exitosa, o un error [ClienteError] si falla.
     */
    fun delete(id: Long): Result<Cliente, ClienteError>
    /**
     * Elimina todos los clientes.
     * @return Un [Result] que contiene Unit si la operación fue exitosa, o un error [ClienteError] si falla.
     */
    fun deleteAllClientes(): Result<Unit, ClienteError>
    /**
     * Valida las credenciales de un cliente.
     * @param email Correo electrónico del cliente.
     * @param encryptedPassword Contraseña encriptada del cliente.
     * @return Un [Result] que contiene el cliente si las credenciales son válidas, o un error [ClienteError] si falla.
     */
    fun validateCliente(email:String,encryptedPassword:String): Result<Cliente,ClienteError>
    /**
     * Obtiene un cliente por su correo electrónico.
     * @param email Correo electrónico del cliente.
     * @return Un [Result] que contiene el cliente si la operación fue exitosa, o un error [ClienteError] si falla.
     */
    fun getByEmail(email: String): Result<Cliente, ClienteError>
}