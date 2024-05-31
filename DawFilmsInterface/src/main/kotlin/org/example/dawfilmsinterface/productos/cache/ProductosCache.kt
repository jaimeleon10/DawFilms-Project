package org.example.dawfilmsinterface.productos.cache

import org.example.dawfilmsinterface.cache.CacheImpl
import org.example.dawfilmsinterface.productos.models.producto.Producto

/**
 * Implementación de caché para almacenar objetos Producto.
 * Esta caché está diseñada para almacenar objetos Producto con claves de tipo String.
 * @param size El tamaño máximo de la caché. El valor predeterminado es 5.
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */

class ProductosCache(size: Int = 5) : CacheImpl<String, Producto>(size)