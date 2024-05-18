package org.example.dawfilmsinterface.clientes.cache

import org.example.dawfilmsinterface.cache.CacheImpl
import org.example.dawfilmsinterface.clientes.models.Cliente

class ClienteCache(size: Int = 5) : CacheImpl<Int, Cliente>(size)