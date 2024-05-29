package org.example.dawfilmsinterface.cine.viewmodels

import javafx.beans.property.SimpleObjectProperty
import org.example.dawfilmsinterface.clientes.services.ClienteService
import org.lighthousegames.logging.logging
import java.time.LocalDate

private val logger = logging()

class RegistroViewModel(
    private val service : ClienteService
) {
    val state : SimpleObjectProperty<RegistroState> = SimpleObjectProperty(RegistroState())

    private fun validarCliente(){

    }

    data class RegistroState(
        val cliente : ClienteState = ClienteState()
    )

    data class ClienteState(
        val id: String = "",
        val nombre: String="",
        val apellido: String="",
        val fechaNacimiento: LocalDate = LocalDate.now(),
        val dni: String="",
        val email: String="",
        val password : String=""
    )
}