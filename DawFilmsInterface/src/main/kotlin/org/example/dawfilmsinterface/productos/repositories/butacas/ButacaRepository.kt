package org.example.dawfilmsinterface.productos.repositories.butacas

import org.example.dawfilmsinterface.productos.models.butacas.Butaca

/**
 * Interfaz que define las operaciones para acceder y manipular los datos de las butacas en el sistema.
 */
interface ButacaRepository {
    /**
     * Recupera todas las butacas almacenadas en el repositorio.
     * @return Lista de butacas.
     */
    fun findAll(): List<Butaca>
    /**
     * Busca una butaca por su identificador único.
     * @param id Identificador único de la butaca a buscar.
     * @return La butaca encontrada, o null si no se encontró ninguna.
     */
    fun findById(id: String): Butaca?
    /**
     * Guarda todas las butacas especificadas en el repositorio.
     * @param butacas Lista de butacas a guardar.
     * @return Lista de butacas guardadas.
     */
    fun saveAll(butacas: List<Butaca>): List<Butaca>
    /**
     * Guarda una butaca en el repositorio.
     * @param item La butaca a guardar.
     * @return La butaca guardada.
     */
    fun save (item: Butaca): Butaca
    /**
     * Actualiza una butaca existente en el repositorio.
     * @param id Identificador único de la butaca a actualizar.
     * @param item Nueva información de la butaca.
     * @return La butaca actualizada, o null si no se encontró ninguna butaca con el id especificado.
     */
    fun update(id: String, item: Butaca): Butaca?
    /**
     * Elimina una butaca del repositorio.
     * @param id Identificador único de la butaca a eliminar.
     * @return La butaca eliminada, o null si no se encontró ninguna butaca con el id especificado.
     */
    fun delete(id: String): Butaca?
    /**
     * Elimina todas las butacas del repositorio.
     */
    fun deleteAll()
}