package org.example.dawfilmsinterface.cine.viewmodels

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.services.ClienteService
import org.lighthousegames.logging.logging
import java.time.LocalDate
import java.util.*

private val logger = logging()
/**
 * ViewModel para la funcionalidad de registro de nuevos clientes.
 *
 * @param service Servicio para gestionar operaciones relacionadas con los clientes.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
class RegistroViewModel(
    private val service : ClienteService
) {
    /**
     * Estado del ViewModel para el registro de clientes.
     */
    val state : SimpleObjectProperty<RegistroState> = SimpleObjectProperty(RegistroState())
    /**
     * Valida los campos ingresados por el usuario durante el proceso de registro.
     *
     * @param enteredNombre Nombre ingresado por el usuario.
     * @param enteredApellido Apellido ingresado por el usuario.
     * @param enteredEmail Email ingresado por el usuario.
     * @param enteredPass Contraseña ingresada por el usuario.
     * @param enteredConfPass Confirmación de la contraseña ingresada por el usuario.
     * @param enteredDni DNI ingresado por el usuario.
     * @param selectedDate Fecha de nacimiento seleccionada por el usuario.
     * @return Booleano que indica si la validación de los campos fue exitosa.
     */
     fun validarCampos(
         enteredNombre: String,
         enteredApellido: String,
         enteredEmail: String,
         enteredPass: String,
         enteredConfPass: String,
         enteredDni: String,
         selectedDate: LocalDate
     ): Boolean {
         val dniRegex = Regex("^[0-9]{8}[A-HJ-NP-TV-Z]$")
         val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
         val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{5,}$")

         if (enteredNombre.isEmpty() || enteredNombre.isBlank()) {
             showAlertOperacion(AlertType.ERROR, "Nombre inválido", "El nombre no puede estar vacío")
             return false
         } else if (enteredApellido.isEmpty() || enteredApellido.isBlank()) {
             showAlertOperacion(AlertType.ERROR, "Apellido inválido", "El apellido no puede estar vacío")
             return false
         } else if (selectedDate > LocalDate.now()) {
             showAlertOperacion(AlertType.ERROR, "Fecha inválida", "La fecha no puede ser posterior a la fecha actual")
             return false
         } else if (enteredDni.isEmpty() || enteredDni.isBlank()) {
             showAlertOperacion(AlertType.ERROR, "DNI inválido", "El dni no puede estar vacío")
             return false
         } else if (!enteredDni.matches(dniRegex)) {
             showAlertOperacion(AlertType.ERROR, "DNI inválido", "El dni debe estar compuesto por 8 números seguidos de una letra\nLetras no válidas: I, O, Ñ, U")
             return false
         } else if (enteredEmail.isEmpty() || enteredEmail.isBlank()) {
             showAlertOperacion(AlertType.ERROR, "Email inválido", "El email no puede estar vacío")
             return false
         } else if (!enteredEmail.matches(emailRegex)) {
             showAlertOperacion(AlertType.ERROR, "Email inválido", "El email debe tener formato (****@****.***)")
             return false
         } else if (enteredPass.isEmpty() || enteredPass.isBlank()) {
             showAlertOperacion(AlertType.ERROR, "Contraseña inválida", "La contraseña no puede estar vacía")
             return false
         } else if (!enteredPass.matches(passwordRegex)) {
             showAlertOperacion(AlertType.ERROR, "Contraseña inválida", "La contraseña debe tener una longitud mínima de 5 caracteres, una mayúscula, una minúscula y un número")
             return false
         } else if (enteredConfPass.isEmpty() || enteredConfPass.isBlank()) {
             showAlertOperacion(AlertType.ERROR, "Contraseña inválida", "Las confirmación de contraseña no puede estar vacía")
             return false
         } else if (enteredConfPass != enteredPass) {
             showAlertOperacion(AlertType.ERROR, "Contraseña inválida", "Las contraseñas no coinciden")
             return false
         } else {
             state.value.cliente.nombre = enteredNombre
             state.value.cliente.apellido = enteredApellido
             state.value.cliente.fechaNacimiento = selectedDate
             state.value.cliente.dni = enteredDni
             state.value.cliente.email = enteredEmail
             state.value.cliente.password = encodeBase64(enteredPass)
             return true
         }
    }
    /**
     * Codifica una cadena en formato Base64.
     *
     * @param input Cadena a codificar.
     * @return Cadena codificada en Base64.
     */
    private fun encodeBase64(input: String): String {
        val encoder = Base64.getEncoder()
        val encodedBytes = encoder.encode(input.toByteArray(Charsets.UTF_8))
        return String(encodedBytes, Charsets.UTF_8)
    }
    /**
     * Muestra un cuadro de diálogo de alerta con el tipo, título, encabezado y mensaje especificados.
     *
     * @param alertType Tipo de la alerta.
     * @param title Título de la alerta.
     * @param header Encabezado de la alerta.
     * @param mensaje Mensaje de la alerta.
     */
    private fun showAlertOperacion(
        alertType: AlertType = AlertType.INFORMATION,
        title: String = "",
        header: String = "",
        mensaje: String = ""
    ) {
        Alert(alertType).apply {
            this.title = title
            this.headerText = header
            this.contentText = mensaje
        }.showAndWait()
    }
    /**
     * Registra un nuevo cliente utilizando los datos del estado actual.
     */
    fun registerCliente() {
        val cliente = Cliente(
            nombre = state.value.cliente.nombre,
            apellido = state.value.cliente.apellido,
            fechaNacimiento = state.value.cliente.fechaNacimiento,
            dni = state.value.cliente.dni,
            email = state.value.cliente.email,
            numSocio = generateNumSocioRandom(),
            password = state.value.cliente.password,
        )
        service.save(cliente)

        showAlertOperacion(
            AlertType.INFORMATION,
            title = "Registro completado",
            header = "Usuario registrado con éxito",
            mensaje = "¡Bienvenido ${cliente.nombre}!"
        )
    }
    /**
     * Genera un número de socio aleatorio único.
     *
     * @return Número de socio aleatorio.
     */
    private fun generateNumSocioRandom(): String {
        val letras = ('A'..'Z')
        val numeros = ('0'..'9')
        val regex = Regex("[A-Z]{3}[0-9]{3}")

        val numeroSocio = buildString {
            repeat(3) {
                append(letras.random())
            }
            repeat(3) {
                append(numeros.random())
            }
        }

        if (service.getAll().value.any { it.numSocio == numeroSocio }) generateNumSocioRandom()
        return numeroSocio
    }
    /**
     * Estado del ViewModel para el registro de clientes.
     */
    data class RegistroState(
        var cliente : ClienteState = ClienteState()
    )
    /**
     * Estado de un cliente en el proceso de registro o recuperación de contraseña.
     *
     * @param nombre Nombre del cliente.
     * @param apellido Apellido del cliente.
     * @param fechaNacimiento Fecha de nacimiento del cliente.
     * @param dni Documento Nacional de Identidad (DNI) del cliente.
     * @param email Correo electrónico del cliente.
     * @param numSocio Número de socio del cliente.
     * @param password Contraseña del cliente.
     */
    data class ClienteState(
        var nombre: String = "",
        var apellido: String = "",
        var fechaNacimiento: LocalDate = LocalDate.now(),
        var dni: String = "",
        var email: String = "",
        var numSocio: Int = 0,
        var password : String = ""
    )
}