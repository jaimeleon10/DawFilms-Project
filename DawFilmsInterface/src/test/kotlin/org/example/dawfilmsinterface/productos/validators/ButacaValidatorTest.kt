package org.example.dawfilmsinterface.productos.validators

import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
/**
 * Tests para comprobar el correcto funcionamiento del validador de butacas
 *
 * @author Jaime León, Alba García, Natalia González, Javier Ruiz, Germán Fernández
 * @since 1.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ButacaValidatorTest {

    private lateinit var butacaValidator: ButacaValidator
    private lateinit var butaca: Butaca

    @BeforeEach
    fun setUp() {
        butacaValidator = ButacaValidator()
        butaca = Butaca(
            "A1",
            "Butaca",
            "futura_imagen.png",
            0,
            0,
            TipoButaca.NORMAL,
            EstadoButaca.ACTIVA,
            OcupacionButaca.LIBRE
        )
    }

    @Test
    fun validate() {
        butacaValidator.validate(butaca)
    }

    @Test
    fun `validate when id es una cadena vacía`() {
        val butacaInvalida = Butaca("", "Butaca", "futura_imagen.png", 0, 0, TipoButaca.NORMAL, EstadoButaca.ACTIVA, OcupacionButaca.LIBRE)

        val error = butacaValidator.validate(butacaInvalida).error

        assertTrue { error is ProductoError.ProductoValidationError }
    }

    @Test
    fun `validate when id no cumple la expresión regular`() {
        val butacaInvalida = Butaca("A-1", "Butaca", "futura_imagen.png", 0, 0, TipoButaca.NORMAL, EstadoButaca.ACTIVA, OcupacionButaca.LIBRE)

        val error = butacaValidator.validate(butacaInvalida).error

        assertTrue { error is ProductoError.ProductoValidationError }
    }
}