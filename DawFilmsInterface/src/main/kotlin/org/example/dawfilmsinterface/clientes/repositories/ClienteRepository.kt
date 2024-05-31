package org.example.dawfilmsinterface.clientes.repositories

import org.example.dawfilmsinterface.clientes.models.Cliente

/**
 * Interfaz que define las operaciones para acceder y manipular los datos de clientes.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
interface ClienteRepository {
    /**
     * Recupera todos los clientes almacenados.
     * @return Una lista de todos los clientes almacenados.
     */
    fun findAll(): List<Cliente>
    /**
     * Busca un cliente por su identificador.
     * @param id Identificador del cliente a buscar.
     * @return El cliente encontrado, o null si no se encuentra.
     */
    fun findById(id: Long): Cliente?
    /**
     * Guarda un nuevo cliente.
     * @param cliente El cliente a guardar.
     * @return El cliente guardado.
     */
    fun save(cliente: Cliente): Cliente
    /**
     * Actualiza un cliente existente.
     * @param id Identificador del cliente a actualizar.
     * @param cliente Los nuevos datos del cliente.
     * @return El cliente actualizado, o null si no se encuentra el cliente con el ID especificado.
     */
    fun update(id: Long, cliente: Cliente): Cliente?
    /**
     * Elimina un cliente por su identificador.
     * @param id Identificador del cliente a eliminar.
     * @return El cliente eliminado, o null si no se encuentra el cliente con el ID especificado.
     */
    fun delete(id: Long): Cliente?
    /**
     * Elimina todos los clientes.
     */
    fun deleteAll()
    /**
     * Valida las credenciales de un cliente.
     * @param email Correo electrónico del cliente.
     * @param password Contraseña del cliente.
     * @return El cliente si las credenciales son válidas, o null si no coinciden con ningún cliente.
     */
    fun validate(email: String, password: String): Cliente?
    /**
     * Busca un cliente por su correo electrónico.
     * @param email Correo electrónico del cliente a buscar.
     * @return El cliente encontrado por su correo electrónico, o null si no se encuentra.
     */
    fun findByEmail(email: String): Cliente?
}