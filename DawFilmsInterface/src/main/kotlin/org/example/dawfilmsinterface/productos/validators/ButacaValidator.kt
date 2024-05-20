package org.example.dawfilmsinterface.productos.validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.butacas.Butaca

class ButacaValidator {
    fun validate(butaca: Butaca): Result<Butaca, ProductoError> {
        //SOLO VALIDO LOS CAMPOS QUE SE INTRODUCEN A MANO, EL RESTO QUEDAN VALIDADOS AL USAR COMBOBOX Y SPINNER
        val regexID = Regex("^[a-eA-E][1-7]$")
        return when {
            butaca.id.isBlank() -> Err(ProductoError.ProductoValidationError("La ID no puede estar vacía"))
            !butaca.id.matches(regexID) -> Err(ProductoError.ProductoValidationError("La ID debe ser del tipo filaCol (fila: A-E, columna: 1-7) -> A1"))
            else -> Ok(butaca)
        }
    }
}