package org.example.dawfilmsinterface.productos.validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.complementos.Complemento

class ComplementoValidator {
    fun validate(complemento: Complemento): Result<Complemento, ProductoError> {
        //SOLO VALIDO LOS CAMPOS QUE SE INTRODUCEN A MANO, EL RESTO QUEDAN VALIDADOS AL USAR COMBOBOX Y SPINNER
        return when {
            complemento.nombre.isBlank() -> Err(ProductoError.ProductoNoValido("El nombre no puede estar vacío"))
            else -> Ok(complemento)
        }
    }
}