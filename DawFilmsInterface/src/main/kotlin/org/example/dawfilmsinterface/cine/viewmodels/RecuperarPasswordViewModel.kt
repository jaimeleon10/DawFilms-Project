package org.example.dawfilmsinterface.cine.viewmodels

import com.github.michaelbull.result.*
import javafx.beans.property.SimpleObjectProperty
import org.example.dawfilmsinterface.clientes.errors.ClienteError
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.services.ClienteService
import org.example.dawfilmsinterface.clientes.validators.ClienteValidator
import org.lighthousegames.logging.logging
import java.time.LocalDate
import java.util.*


private val logger = logging()

class RecuperarPasswordViewModel (
    private val clienteService: ClienteService,
    private val clienteValidator: ClienteValidator
)
{

    val state: SimpleObjectProperty<RecuperarPasswordState> = SimpleObjectProperty(RecuperarPasswordState())

    init {
        logger.debug { "Inicializando RecuperarPasswordViewModel" }
    }

    fun validateUserEmail(email:String): Result<Unit,ClienteError>{
        logger.debug { "Validando email" }
        return clienteService.getByEmail(email).andThen {
            changeCliente(it)
            state.value = state.value.copy(restoreCode = state.value.restoreCode)
            Ok(Unit)
        }
    }

    fun validateRestoredCode(code:String): Result<Unit,ClienteError> {
        logger.debug { "Validando código de restauración" }
        return if (code == state.value.restoreCode) {
            Ok(Unit)
        } else {
            Err(ClienteError.ClienteValidationError("Código de recuperación incorrecto."))
        }
    }

    fun validateUserPassword(password:String): Result<Unit,ClienteError>{
        logger.debug { "Validando formato de password" }
        return validatePassword(password).andThen {
            changePassword(it)
            Ok(Unit)
        }
    }

    fun updatePassword(): Result<Cliente,ClienteError> {
        logger.debug { "Actualizando password de usuario" }

        val cliente = Cliente(
            id = state.value.currentUser.id.toLong(),
            nombre = state.value.currentUser.nombre,
            apellido = state.value.currentUser.apellido,
            fechaNacimiento = state.value.currentUser.fechaNacimiento,
            dni=state.value.currentUser.dni,
            email = state.value.currentUser.email,
            numSocio = state.value.currentUser.numSocio,
            password = state.value.currentUser.password
        )

        return clienteService.update(cliente.id, cliente).onSuccess {
            Ok(it)
        }. onFailure {
            Err(ClienteError.ClienteNoActualizado("Error actualizando el password del usuario ${cliente.email}"))
        }
    }

    private fun changePassword(pass:String) {
        val cryptKey = encodeBase64(pass)
        state.value = state.value.copy(
            currentUser = state.value.currentUser.copy(password = cryptKey)
        )
    }

    private fun encodeBase64(pass: String): String {
        val encoder = Base64.getEncoder()
        val encodedBytes = encoder.encode(pass.toByteArray(Charsets.UTF_8))
        return String(encodedBytes, Charsets.UTF_8)
    }

     fun generateRestoreCode(): String {
        val num1 = (100..999).random().toString()
        val num2 = (100..999).random().toString()
        return "$num1-$num2"
    }

    private fun changeCliente(cliente: Cliente) {
        state.value = state.value.copy(
            currentUser = state.value.currentUser.copy(
                id = cliente.id.toString(),
                nombre = cliente.nombre,
                apellido = cliente.apellido,
                fechaNacimiento = cliente.fechaNacimiento,
                dni = cliente.dni,
                email = cliente.email,
                numSocio = cliente.numSocio,
                password = ""
            )
        )

    }

    private fun validatePassword(password: String):Result<String, ClienteError> {
        val regex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{5,}$")
        return if (password.matches(regex)) {
            Ok(password)
        } else {
            Err(ClienteError.ClienteValidationError("La contraseña debe tener 5 caracteres o mas, y contener al menos un número, una letra (al menos una mayúscula)"))
        }
    }

    data class RecuperarPasswordState(
        val currentUser: ClienteRecupState = ClienteRecupState(),
        var restoreCode: String = "",
        val newPassword: String = ""
    )

    data class ClienteRecupState(
        val id: String = "",
        val nombre: String = "",
        val apellido: String = "",
        val fechaNacimiento: LocalDate = LocalDate.now(),
        val dni: String = "",
        val email: String = "",
        val numSocio: String = "",
        val password: String = ""
    )
}
