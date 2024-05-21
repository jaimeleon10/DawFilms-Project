package org.example.dawfilmsinterface.clientes.storage

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.clientes.errors.ClienteError
import org.example.dawfilmsinterface.clientes.models.Cliente
import java.io.File

interface ClienteStorageJson {
    fun storeJson(file: File, data: List<Cliente>): Result<Long, ClienteError>
    fun loadJson(file: File): Result<List<Cliente>, ClienteError>
}