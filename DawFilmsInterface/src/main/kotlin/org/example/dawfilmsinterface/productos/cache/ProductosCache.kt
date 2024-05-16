package org.example.dawfilmsinterface.productos.cache

import org.example.dawfilmsinterface.cache.CacheImpl
import org.example.dawfilmsinterface.productos.models.producto.Producto

class ProductosCache(size: Int = 5) : CacheImpl<String, Producto>(size)