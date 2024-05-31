package org.example.dawfilmsinterface.cine.viewmodels

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import javafx.beans.property.SimpleObjectProperty
import org.example.dawfilmsinterface.clientes.errors.ClienteError
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.services.ClienteService
import org.example.dawfilmsinterface.database.SqlDeLightManager
import org.lighthousegames.logging.logging
import java.time.LocalDate

private val logger = logging()
/**
 * Clase LoginViewModel
 *
 * Gestiona la lógica relacionada con el inicio de sesión y la validación de clientes.
 *
 * @param clienteService Servicio para gestionar clientes.
 * @param database Gestor de la base de datos.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
class LoginViewModel (
    private val clienteService: ClienteService,
    private val database: SqlDeLightManager
) {
    val state: SimpleObjectProperty<LoginState> = SimpleObjectProperty(LoginState())

    init {
        logger.debug { "Inicializando LoginViewModel" }
    }
    /**
     * Valida un cliente utilizando su email y contraseña encriptada.
     *
     * @param email Email del cliente.
     * @param encryptedPassword Contraseña encriptada del cliente.
     * @return Resultado de la validación.
     */
    fun validarCliente(email: String, encryptedPassword: String): Result<Unit, ClienteError> {
        logger.debug { "Validando Cliente" }

        return clienteService.validateCliente(email,encryptedPassword).andThen {
            changeCliente(it)
            Ok(Unit)
        }
    }
    /**
     * Cambia el cliente actual en el estado.
     *
     * @param newCliente Nuevo cliente a establecer como actual.
     */
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
    /**
     * Cambia el usuario actual a Admin.
     */
    fun changeAdmin(){
        logger.debug { "Cambiando usuario a Admin" }
        state.value = state.value.copy(currentAdmin = "Admin")
    }
    /**
     * Clase de datos LoginState
     *
     * Representa el estado del inicio de sesión.
     *
     * @param currentAdmin Nombre del administrador actual.
     * @param currentCliente Estado del cliente actual.
     * @param isAdmin Indica si el usuario actual es un administrador.
     */
    data class LoginState(
        val currentAdmin: String= "",
        val currentCliente: ClienteState = ClienteState(),
        var isAdmin: Boolean = false
    )
    /**
     * Clase de datos ClienteState
     *
     * Representa el estado de un cliente.
     *
     * @param id Identificador del cliente.
     * @param nombre Nombre del cliente.
     * @param apellido Apellido del cliente.
     * @param fechaNacimiento Fecha de nacimiento del cliente.
     * @param dni Documento Nacional de Identidad del cliente.
     * @param email Email del cliente.
     * @param numSocio Número de socio del cliente.
     */
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