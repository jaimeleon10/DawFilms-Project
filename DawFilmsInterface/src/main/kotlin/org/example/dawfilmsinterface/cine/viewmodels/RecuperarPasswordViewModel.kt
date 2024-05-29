package org.example.dawfilmsinterface.cine.viewmodels

import com.github.michaelbull.result.Err
import javafx.beans.property.SimpleObjectProperty
import org.example.dawfilmsinterface.clientes.errors.ClienteError
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.services.ClienteService
import org.lighthousegames.logging.logging
import java.time.LocalDate
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen

private val logger = logging()

class RecuperarPasswordViewModel
    (private val clienteService: ClienteService)
{

    val state:SimpleObjectProperty<RecuperarPasswordState> = SimpleObjectProperty(RecuperarPasswordState())

    init {
        logger.debug { "Inicializando RecuperarPasswordViewModel" }
    }

    fun validateUserEmail(email:String): Result<Unit,ClienteError>{
        logger.debug { "Validando email" }
        return clienteService.getByEmail(email).andThen {
            changeCliente(it)
            state.value = state.value.copy(restoreCode = generateRestoreCode())
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

    private fun generateRestoreCode(): String {
        return "783-964"
    }

    private fun changeCliente(cliente: Cliente) {
        state.value = state.value.copy(
            currentUser =state.value.currentUser.copy(
                id=cliente.id.toString(),
                nombre = cliente.nombre,
                apellido = cliente.apellido,
                fechaNacimiento = cliente.fechaNacimiento,
                dni=cliente.dni,
                email = cliente.email,
                numSocio = cliente.numSocio,
                password = ""
            )
        )

    }
}



data class RecuperarPasswordState (
    val currentUser: ClienteRecupState = ClienteRecupState(),
    val restoreCode: String="",
    val newPassword: String = ""
)
data class ClienteRecupState(
    val id: String = "",
    val nombre: String="",
    val apellido: String="",
    val fechaNacimiento: LocalDate = LocalDate.now(),
    val dni: String="",
    val email: String="",
    val numSocio: String="",
    val password: String=""
)