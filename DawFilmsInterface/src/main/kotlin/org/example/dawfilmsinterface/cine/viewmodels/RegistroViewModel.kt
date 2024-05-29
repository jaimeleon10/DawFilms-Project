package org.example.dawfilmsinterface.cine.viewmodels

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.services.ClienteService
import org.lighthousegames.logging.logging
import java.time.LocalDate

private val logger = logging()

class RegistroViewModel(
    private val service : ClienteService
) {
    val state : SimpleObjectProperty<RegistroState> = SimpleObjectProperty(RegistroState())

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
         val passwordRegex = Regex("^[a-zA-Z0-9]{5,}$")

         if (enteredNombre.isEmpty() || enteredNombre.isBlank()) {
             showAlertOperacion("Nombre inválido", "El nombre no puede estar vacío")
             return false
         } else if (enteredApellido.isEmpty() || enteredApellido.isBlank()) {
             showAlertOperacion("Apellido inválido", "El apellido no puede estar vacío")
             return false
         } else if (selectedDate > LocalDate.now()) {
             showAlertOperacion("Fecha inválida", "La fecha no puede ser posterior a la fecha actual")
             return false
         } else if (enteredDni.isEmpty() || enteredDni.isBlank()) {
             showAlertOperacion("DNI inválido", "El dni no puede estar vacío")
             return false
         } else if (!enteredDni.matches(dniRegex)) {
             showAlertOperacion("DNI inválido", "El dni debe estar compuesto por 8 números seguidos de una letra\nLetras no válidas: I, O, Ñ, U")
             return false
         } else if (enteredEmail.isEmpty() || enteredEmail.isBlank()) {
             showAlertOperacion("Email inválido", "El email no puede estar vacío")
             return false
         } else if (!enteredEmail.matches(emailRegex)) {
             showAlertOperacion("Email inválido", "El email debe tener formato (****@****.***)")
             return false
         } else if (enteredPass.isEmpty() || enteredPass.isBlank()) {
             showAlertOperacion("Contraseña inválida", "La contraseña no puede estar vacía")
             return false
         } else if (!enteredPass.matches(passwordRegex)) {
             showAlertOperacion("Contraseña inválida", "La contraseña debe tener una longitud mínima de 5 caracteres alfanuméricos")
             return false
         } else if (enteredConfPass.isEmpty() || enteredConfPass.isBlank()) {
             showAlertOperacion("Contraseña inválida", "Las confirmación de contraseña no puede estar vacía")
             return false
         } else if (enteredConfPass != enteredPass) {
             showAlertOperacion("Contraseña inválida", "Las contraseñas no coinciden")
             return false
         } else {
             state.value.cliente.nombre = enteredNombre
             state.value.cliente.apellido = enteredApellido
             state.value.cliente.fechaNacimiento = selectedDate
             state.value.cliente.dni = enteredDni
             state.value.cliente.email = enteredEmail
             state.value.cliente.password = enteredPass
             return true
         }
    }

    private fun showAlertOperacion(
        title: String = "",
        mensaje: String = ""
    ) {
        Alert(AlertType.ERROR).apply {
            this.title = title
            this.headerText = mensaje
        }.showAndWait()
    }

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
    }

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

    data class RegistroState(
        var cliente : ClienteState = ClienteState()
    )

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