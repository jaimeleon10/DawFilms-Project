package org.example.dawfilmsinterface.clientes.cache

import org.example.dawfilmsinterface.cache.CacheImpl
import org.example.dawfilmsinterface.clientes.models.Cliente

/**
 * Implementación de caché específica para objetos de tipo [Cliente].
 * @param size Tamaño máximo de la caché. Por defecto, es 5.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
class ClienteCache(size: Int = 5) : CacheImpl<Long, Cliente>(size)