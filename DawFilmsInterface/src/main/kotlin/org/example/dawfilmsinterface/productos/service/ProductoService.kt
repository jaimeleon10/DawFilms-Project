package org.example.dawfilmsinterface.productos.service

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.models.producto.Producto

/**
 * Interfaz que define las operaciones disponibles para gestionar productos, butacas y complementos.
 *
 * @since 1.0.0
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
interface ProductoService {

    /**
     * Obtiene todos los productos.
     * @return un [Result] que contiene una lista de [Producto] si la operación fue exitosa o un error de [ProductoError].
     */
    fun getAllProductos(): Result<List<Producto>, ProductoError>

    /**
     * Obtiene todas las butacas.
     * @return un [Result] que contiene una lista de [Butaca] si la operación fue exitosa o un error de [ProductoError].
     */
    fun getAllButacas(): Result<List<Butaca>, ProductoError>

    /**
     * Obtiene todos los complementos.
     * @return un [Result] que contiene una lista de [Complemento] si la operación fue exitosa o un error de [ProductoError].
     */
    fun getAllComplementos(): Result<List<Complemento>, ProductoError>

    /**
     * Obtiene una butaca por su ID.
     * @param id el ID de la butaca a buscar.
     * @return un [Result] que contiene la butaca si la operación fue exitosa o un error de [ProductoError].
     */
    fun getButacaById(id: String): Result<Butaca, ProductoError>

    /**
     * Obtiene un complemento por su ID.
     * @param id el ID del complemento a buscar.
     * @return un [Result] que contiene el complemento si la operación fue exitosa o un error de [ProductoError].
     */
    fun getComplementoById(id: String): Result<Complemento, ProductoError>

    /**
     * Obtiene un complemento por su nombre.
     * @param nombre el nombre del complemento a buscar.
     * @return un [Result] que contiene el complemento si la operación fue exitosa o un error de [ProductoError].
     */
    fun getComplementoByNombre(nombre: String): Result<Complemento, ProductoError>

    /**
     * Guarda todas las butacas especificadas.
     * @param butacas la lista de butacas a guardar.
     * @return un [Result] que contiene la lista de butacas guardadas si la operación fue exitosa o un error de [ProductoError].
     */
    fun saveAllButacas(butacas: List<Butaca>): Result<List<Butaca>, ProductoError>

    /**
     * Guarda una butaca.
     * @param item la butaca a guardar.
     * @return un [Result] que contiene la butaca guardada si la operación fue exitosa o un error de [ProductoError].
     */
    fun saveButaca(item: Butaca): Result<Butaca, ProductoError>

    /**
     * Guarda todos los complementos especificados.
     * @param complementos la lista de complementos a guardar.
     * @return un [Result] que contiene la lista de complementos guardados si la operación fue exitosa o un error de [ProductoError].
     */
    fun saveAllComplementos(complementos: List<Complemento>): Result<List<Complemento>, ProductoError>

    /**
     * Guarda un complemento.
     * @param item el complemento a guardar.
     * @return un [Result] que contiene el complemento guardado si la operación fue exitosa o un error de [ProductoError].
     */
    fun saveComplemento(item: Complemento): Result<Complemento, ProductoError>

    /**
     * Actualiza una butaca existente.
     * @param id el ID de la butaca a actualizar.
     * @param item la nueva información de la butaca.
     * @return un [Result] que contiene la butaca actualizada si la operación fue exitosa o un error de [ProductoError].
     */
    fun updateButaca(id: String, item: Butaca): Result<Butaca, ProductoError>

    /**
     * Actualiza un complemento existente.
     * @param id el ID del complemento a actualizar.
     * @param item la nueva información del complemento.
     * @return un [Result] que contiene el complemento actualizado si la operación fue exitosa o un error de [ProductoError].
     */
    fun updateComplemento(id: String, item: Complemento): Result<Complemento, ProductoError>

    /**
     * Elimina todos los productos.
     * @return un [Result] que indica si la operación fue exitosa o un error de [ProductoError].
     */
    fun deleteAllProductos(): Result<Unit, ProductoError>

    /**
     * Elimina todas las butacas.
     * @return un [Result] que indica si la operación fue exitosa o un error de [ProductoError].
     */
    fun deleteAllButacas(): Result<Unit, ProductoError>

    /**
     * Elimina todos los complementos.
     * @return un [Result] que indica si la operación fue exitosa o un error de [ProductoError].
     */
    fun deleteAllComplementos(): Result<Unit, ProductoError>

    /**
     * Elimina una butaca por su ID.
     * @param id el ID de la butaca a eliminar.
     * @return un [Result] que contiene la butaca eliminada si la operación fue exitosa o un error de [ProductoError].
     */
    fun deleteButaca(id: String): Result<Butaca, ProductoError>

    /**
     * Elimina un complemento por su ID.
     * @param id el ID del complemento a eliminar.
     * @return un [Result] que contiene el complemento eliminado si la operación fue exitosa o un error de [ProductoError].
     */
    fun deleteComplemento(id: String): Result<Complemento, ProductoError>
}

