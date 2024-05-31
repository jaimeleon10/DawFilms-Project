package org.example.dawfilmsinterface.productos.repositories.complementos

import org.example.dawfilmsinterface.productos.models.complementos.Complemento

/**
 * Interfaz que define las operaciones disponibles para interactuar con los complementos en el repositorio.
 * @since 1.0.0
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
interface ComplementoRepository {

    /**
     * Recupera todos los complementos almacenados en el repositorio.
     * @return Lista de complementos.
     */
    fun findAll(): List<Complemento>

    /**
     * Busca un complemento por su identificador único en el repositorio.
     * @param id Identificador único del complemento a buscar.
     * @return El complemento encontrado, o null si no se encontró ninguno.
     */
    fun findById(id: String): Complemento?

    /**
     * Busca un complemento por su nombre en el repositorio.
     * @param nombre Nombre del complemento a buscar.
     * @return El complemento encontrado, o null si no se encontró ninguno.
     */
    fun findByNombre(nombre: String): Complemento?

    /**
     * Guarda todos los complementos especificados en el repositorio.
     * @param complementos Lista de complementos a guardar.
     * @return Lista de complementos guardados.
     */
    fun saveAll (complementos: List<Complemento>): List<Complemento>

    /**
     * Guarda un complemento en el repositorio.
     * @param item El complemento a guardar.
     * @return El complemento guardado.
     */
    fun save (item: Complemento): Complemento

    /**
     * Actualiza un complemento existente en el repositorio.
     * @param id Identificador único del complemento a actualizar.
     * @param item Nueva información del complemento.
     * @return El complemento actualizado, o null si no se encontró ningún complemento con el id especificado.
     */
    fun update(id: String, item: Complemento): Complemento?

    /**
     * Elimina un complemento del repositorio.
     * @param id Identificador único del complemento a eliminar.
     * @return El complemento eliminado, o null si no se encontró ningún complemento con el id especificado.
     */
    fun delete(id: String): Complemento?

    /**
     * Elimina todos los complementos del repositorio.
     */
    fun deleteAll()
}