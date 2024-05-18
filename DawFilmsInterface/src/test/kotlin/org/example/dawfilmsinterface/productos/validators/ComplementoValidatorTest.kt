package org.example.dawfilmsinterface.productos.validators

import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance

/**
 * Tests para comprobar el correcto funcionamiento del validador de complementos
 *
 * @author Jaime León, Alba García, Natalia González, Javier Ruiz, Germán Fernández
 * @since 1.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ComplementoValidatorTest {

    private lateinit var complementoValidator : ComplementoValidator
    private lateinit var complemento : Complemento

    @BeforeEach
    fun setUp() {
        complementoValidator = ComplementoValidator()
        complemento = Complemento("1", "Complemento", "Palomitas", 3.0, 50, CategoriaComplemento.COMIDA)
    }

    @Test
    fun validate() {
        complementoValidator.validate(complemento)
    }

    @Test
    fun `validate when nombre es un número`() {
        val complementoInvalido = Complemento(
            id = "1",
            tipoProducto = "Complemento",
            nombre = "234",
            precio = 3.00,
            stock = 50,
            categoria = CategoriaComplemento.COMIDA
        )

        val error = complementoValidator.validate(complementoInvalido).error

        assertTrue(error is ProductoError.ProductoNoValido)
    }
}