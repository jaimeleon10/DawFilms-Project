package org.example.dawfilmsinterface.cache

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.cache.errors.CacheError
/**
 * Interfaz genérica para la caché
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @see CacheImpl
 * @see CacheError
 */
interface Cache<K, T> {
    /**
     * Función que devuelve los datos en la caché
     * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    fun get(key: K): Result<T, CacheError>
    /**
     * Función que introduce los datos en la caché
     * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    fun put(key: K, value: T): Result<T, Nothing>
    /**
     * Función que elimina un dato de la caché
     * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    fun remove(key: K): Result<T, CacheError>
    /**
     * Función que elimina todos los datos de la caché
     * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    fun clear(): Result<Unit, Nothing>
}