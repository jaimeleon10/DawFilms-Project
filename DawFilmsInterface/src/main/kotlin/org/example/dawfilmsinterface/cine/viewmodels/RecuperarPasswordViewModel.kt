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
/**
 * Clase RecuperarPasswordViewModel
 *
 * Gestiona la lógica de recuperación de contraseña para los clientes.
 *
 * @param clienteService Servicio para gestionar los clientes.
 * @param clienteValidator Validador para validar los datos del cliente.
 *
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
class RecuperarPasswordViewModel (
    private val clienteService: ClienteService,
    private val clienteValidator: ClienteValidator
)
{
    /**
     * Propiedad que representa el estado actual del proceso de recuperación de contraseña.
     */
    val state: SimpleObjectProperty<RecuperarPasswordState> = SimpleObjectProperty(RecuperarPasswordState())

    init {
        logger.debug { "Inicializando RecuperarPasswordViewModel" }
    }
    /**
     * Valida el correo electrónico del usuario.
     *
     * @param email Dirección de correo electrónico del usuario.
     * @return Resultado de la validación.
     */
    fun validateUserEmail(email:String): Result<Unit,ClienteError>{
        logger.debug { "Validando email" }
        return clienteService.getByEmail(email).andThen {
            changeCliente(it)
            state.value = state.value.copy(restoreCode = state.value.restoreCode)
            Ok(Unit)
        }
    }
    /**
     * Valida el código de restauración ingresado por el usuario.
     *
     * @param code Código de restauración ingresado por el usuario.
     * @return Resultado de la validación.
     */
    fun validateRestoredCode(code:String): Result<Unit,ClienteError> {
        logger.debug { "Validando código de restauración" }
        return if (code == state.value.restoreCode) {
            Ok(Unit)
        } else {
            Err(ClienteError.ClienteValidationError("Código de recuperación incorrecto."))
        }
    }
    /**
     * Valida la contraseña ingresada por el usuario.
     *
     * @param password Contraseña ingresada por el usuario.
     * @return Resultado de la validación.
     */
    fun validateUserPassword(password:String): Result<Unit,ClienteError>{
        logger.debug { "Validando formato de password" }
        return validatePassword(password).andThen {
            changePassword(it)
            Ok(Unit)
        }
    }
    /**
     * Actualiza la contraseña del usuario en la base de datos.
     *
     * @return Resultado de la operación de actualización.
     */
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
    /**
     * Cambia la contraseña del usuario en el estado.
     *
     * @param pass Nueva contraseña del usuario.
     */
    private fun changePassword(pass:String) {
        val cryptKey = encodeBase64(pass)
        state.value = state.value.copy(
            currentUser = state.value.currentUser.copy(password = cryptKey)
        )
    }
    /**
     * Codifica una cadena de texto en formato Base64.
     *
     * @param pass Cadena de texto a codificar.
     * @return Cadena de texto codificada en Base64.
     */
    private fun encodeBase64(pass: String): String {
        val encoder = Base64.getEncoder()
        val encodedBytes = encoder.encode(pass.toByteArray(Charsets.UTF_8))
        return String(encodedBytes, Charsets.UTF_8)
    }
    /**
     * Genera un código de restauración aleatorio.
     *
     * @return Código de restauración generado.
     */
     fun generateRestoreCode(): String {
        val num1 = (100..999).random().toString()
        val num2 = (100..999).random().toString()
        return "$num1-$num2"
    }
    /**
     * Cambia el cliente en el estado.
     *
     * @param cliente Cliente recuperado de la base de datos.
     */
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
    /**
     * Valida el formato de la contraseña ingresada por el usuario.
     *
     * @param password Contraseña ingresada por el usuario.
     * @return Resultado de la validación.
     */
    private fun validatePassword(password: String):Result<String, ClienteError> {
        val regex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{5,}$")
        return if (password.matches(regex)) {
            Ok(password)
        } else {
            Err(ClienteError.ClienteValidationError("La contraseña debe tener 5 caracteres o mas, y contener al menos un número, una letra (al menos una mayúscula)"))
        }
    }
    /**
     * Estado del proceso de recuperación de contraseña.
     *
     * @param currentUser Datos del cliente actual.
     * @param restoreCode Código de restauración generado.
     * @param newPassword Nueva contraseña ingresada por el usuario.
     */
    data class RecuperarPasswordState(
        val currentUser: ClienteRecupState = ClienteRecupState(),
        var restoreCode: String = "",
        val newPassword: String = ""
    )
    /**
     * Datos del cliente actual.
     *
     * @param id Identificador del cliente.
     * @param nombre Nombre del cliente.
     * @param apellido Apellido del cliente.
     * @param fechaNacimiento Fecha de nacimiento del cliente.
     * @param dni DNI del cliente.
     * @param email Correo electrónico del cliente.
     * @param numSocio Número de socio del cliente.
     * @param password Contraseña del cliente.
     */
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
