package org.example.dawfilmsinterface.productos.validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.complementos.Complemento

/**
 * Clase para validar los datos de un complemento.
 * @since 1.0.0
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
class ComplementoValidator {

    /**
     * Valida los campos de un complemento.
     * @param complemento el complemento a validar.
     * @return un [Result] que contiene el complemento si es válido, o un error si no lo es.
     */
    fun validate(complemento: Complemento): Result<Complemento, ProductoError> {
        //SOLO VALIDO LOS CAMPOS QUE SE INTRODUCEN A MANO, EL RESTO QUEDAN VALIDADOS AL USAR COMBOBOX Y SPINNER
        val regexNombreComplemento = Regex("^[a-zA-Z]+$")

        return when {
            complemento.nombre.isBlank() -> Err(ProductoError.ProductoValidationError("El nombre no puede estar vacío"))
            !complemento.nombre.matches(regexNombreComplemento) -> Err(ProductoError.ProductoValidationError("El nombre no puede contener números o carácteres especiales"))
            else -> Ok(complemento)
        }
    }
}