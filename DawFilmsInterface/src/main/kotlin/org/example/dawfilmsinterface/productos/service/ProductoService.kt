package org.example.dawfilmsinterface.productos.service

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.models.producto.Producto

interface ProductoService {
    fun getAllProductos(): Result<List<Producto>, ProductoError>
    fun getAllButacas(): Result<List<Butaca>, ProductoError>
    fun getAllComplementos(): Result<List<Complemento>, ProductoError>
    fun getButacaById(id: String): Result<Butaca, ProductoError>
    fun getComplementoById(id: String): Result<Complemento, ProductoError>
    fun getComplementoByNombre(nombre: String): Result<Complemento, ProductoError>
    fun saveAllButacas(butacas: List<Butaca>): Result<List<Butaca>, ProductoError>
    fun saveButaca(item: Butaca): Result<Butaca, ProductoError>
    fun saveComplemento(item: Complemento): Result<Complemento, ProductoError>
    fun updateButaca(id: String, item: Butaca): Result<Butaca, ProductoError>
    fun updateComplemento(id: String, item: Complemento): Result<Complemento, ProductoError>
    fun deleteAllProductos(): Result<Unit, ProductoError>
    fun deleteButaca(id: String): Result<Butaca, ProductoError>
    fun deleteComplemento(id: String): Result<Complemento, ProductoError>
}