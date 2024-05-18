package org.example.dawfilmsinterface.clientes.validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.clientes.errors.ClienteError
import org.example.dawfilmsinterface.clientes.models.Cliente

class ClienteValidator {
    fun validate(cliente: Cliente): Result<Cliente, ClienteError> {
        val regexNombreApellido = Regex("^[a-zA-Z](?:[a-zA-Z ]{1,18}[a-zA-Z])?\$")
        val regexDNI = Regex("^[0-9]{8}[A-HJ-NP-TV-Z]$")
        val regexEmail = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")
        return when {
            !cliente.nombre.matches(regexNombreApellido) -> Err(ClienteError.ClienteValidationError("El nombre deben ser caracteres con una longitud entre 3 y 20"))
            !cliente.apellido.matches(regexNombreApellido) -> Err(ClienteError.ClienteValidationError("Los apellidos deben ser caracteres con una longitud entre 3 y 20"))
            !cliente.dni.matches(regexDNI) -> Err(ClienteError.ClienteValidationError("El DNI no es válido, debe estar formado por 8 dígitos seguido de 1 letra excepto (I,O,U,Ñ)"))
            !cliente.email.matches(regexEmail) -> Err(ClienteError.ClienteValidationError("El email introducido no es válido. Ejemplo: email@email.com"))
            else -> Ok(cliente)
        }
    }
}