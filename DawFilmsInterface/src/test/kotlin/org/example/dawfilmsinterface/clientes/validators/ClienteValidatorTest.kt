package org.example.dawfilmsinterface.clientes.validators

import org.example.dawfilmsinterface.clientes.errors.ClienteError
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClienteValidatorTest {
    private lateinit var clienteValidator: ClienteValidator
    private lateinit var cliente: Cliente

    @BeforeEach
    fun setUp(){
        clienteValidator = ClienteValidator()
        cliente = Cliente(
            nombre = "test",
            apellido = "test",
            fechaNacimiento = LocalDate.parse("2024-05-05"),
            dni = "12345678G",
            email = "test@test.com",
            numSocio = "ABC123",
            password = "1234"
        )
    }

    @Test
    fun validate() {
        val result = clienteValidator.validate(cliente)

        assertTrue(result.isOk)
    }

    @Test
    fun `validate when nombre no cumple la expresión regular de nombreapellido`() {
        val cliente = Cliente(
            nombre = "T35T",
            apellido = "test",
            fechaNacimiento = LocalDate.parse("2024-05-05"),
            dni = "12345678G",
            email = "test@test.com",
            numSocio = "ABC123",
            password = "1234"
        )

        val error = clienteValidator.validate(cliente).error

        assertTrue { error is ClienteError.ClienteValidationError }
    }

    @Test
    fun `validate when apellido no cumple la expresión regular de nombreapellido`() {
        val cliente = Cliente(
            nombre = "test",
            apellido = "T35T",
            fechaNacimiento = LocalDate.parse("2024-05-05"),
            dni = "12345678G",
            email = "test@test.com",
            numSocio = "ABC123",
            password = "1234"
        )

        val error = clienteValidator.validate(cliente).error

        assertTrue { error is ClienteError.ClienteValidationError }
    }

    @Test
    fun `validate when dni no cumple la expresión regular de dni`() {
        val cliente = Cliente(
            nombre = "test",
            apellido = "test",
            fechaNacimiento = LocalDate.parse("2024-05-05"),
            dni = "A1B2",
            email = "test@test.com",
            numSocio = "ABC123",
            password = "1234"
        )

        val error = clienteValidator.validate(cliente).error

        assertTrue { error is ClienteError.ClienteValidationError }
    }

    @Test
    fun `validate when email no cumple la expresión regular de dni`() {
        val cliente = Cliente(
            nombre = "test",
            apellido = "test",
            fechaNacimiento = LocalDate.parse("2024-05-05"),
            dni = "12345678G",
            email = "micorreo:)@email.com",
            numSocio = "ABC123",
            password = "1234"
        )

        val error = clienteValidator.validate(cliente).error

        assertTrue { error is ClienteError.ClienteValidationError }
    }
}