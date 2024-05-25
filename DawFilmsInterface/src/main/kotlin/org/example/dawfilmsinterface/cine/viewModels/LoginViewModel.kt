package org.example.dawfilmsinterface.cine.viewModels

import com.github.michaelbull.result.*
import javafx.beans.property.SimpleObjectProperty
import org.example.dawfilmsinterface.clientes.errors.ClienteError
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.services.ClienteService
import org.lighthousegames.logging.logging
import java.time.LocalDate

private val logger = logging()

class LoginViewModel (
    private val clienteService:ClienteService
){

    val state: SimpleObjectProperty<LoginState> = SimpleObjectProperty(LoginState())

    init {
        logger.debug { "Inicializando LoginViewModel" }
    }

    fun validarCliente(email: String, encryptedPassword: String):Result<Unit,ClienteError> {
        logger.debug { "Validando Cliente" }

        return clienteService.validateCliente(email,encryptedPassword).andThen {
            changeCliente(it)
            Ok(Unit)
        }


        /*return try {
            clienteService.validateCliente(email,encryptedPassword).andThen {
                changeCliente(it)
                Ok(Unit)
            }
            Ok(Unit)
        }
        catch (e:Exception){
            Err(ClienteError.ClienteValidationError("Cliente no válido ${e.message}"))
        }*/


    }

    private fun changeCliente(newCliente: Cliente) {
        logger.debug { "Cambiando usuario actual" }
        state.value = state.value.copy(
            currentCliente=state.value.currentCliente.copy(
                id=newCliente.id.toString(),
                nombre = newCliente.nombre,
                apellido = newCliente.apellido,
                fechaNacimiento = newCliente.fechaNacimiento,
                dni=newCliente.dni,
                email = newCliente.email,
                numSocio = newCliente.numSocio
            )
        )

        state.value =state.value.copy(currentAdmin = "")

    }

    fun changeAdmin(){
        logger.debug { "Cambiando usuario a Admin" }
        state.value= state.value.copy(currentAdmin = "Admin")
    }

    data class LoginState(
        val currentAdmin:String="",
        val currentCliente:ClienteState = ClienteState()
    )

    data class ClienteState(
        val id: String = "",
        val nombre: String="",
        val apellido: String="",
        val fechaNacimiento: LocalDate = LocalDate.now(),
        val dni: String="",
        val email: String="",
        val numSocio: String="",
    )
}