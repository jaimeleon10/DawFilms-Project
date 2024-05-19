package org.example.dawfilmsinterface.productos.services.productos

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.dawfilmsinterface.cache.errors.CacheError
import org.example.dawfilmsinterface.productos.cache.ProductosCache
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.repositories.butacas.ButacaRepository
import org.example.dawfilmsinterface.productos.repositories.complementos.ComplementoRepository
import org.example.dawfilmsinterface.productos.validators.ButacaValidator
import org.example.dawfilmsinterface.productos.validators.ComplementoValidator
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

/**
 * Tests para comprobar el correcto funcionamiento del servicio de productos
 *
 * @author Jaime León, Alba García, Natalia González, Javier Ruiz, Germán Fernández
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension::class)
class ProductoServiceImplTest {

    @Mock
    private lateinit var mockButacaRepository: ButacaRepository

    @Mock
    private lateinit var mockComplementoRepository: ComplementoRepository

    @Mock
    private lateinit var mockProductosCache: ProductosCache

    @Mock
    private lateinit var mockButacaValidator: ButacaValidator

    @Mock
    private lateinit var mockComplementoValidator: ComplementoValidator

    @InjectMocks
    private lateinit var service : ProductoServiceImpl

    @Test
    fun getAllProductos() {
    }

    @Test
    fun getAllButacas() {
    }

    @Test
    fun getAllComplementos() {
        Mockito.`when`(mockComplementoRepository.findAll()).thenReturn(listOf())

        val result = service.getAllComplementos()

        assertTrue(result.isOk)

        verify(mockComplementoRepository, times(1)).findAll()
    }

    @Test
    fun getButacaById() {
    }

    @Test
    fun getComplementoById() {
        val id = "1"
        val complemento = Complemento(id, "Complemento", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA)

        Mockito.`when`(mockProductosCache.get(id)).thenReturn(Ok(complemento))

        val result = service.getComplementoById(id)

        assertTrue(result.isOk)
        assertTrue(result.value == complemento)

        verify(mockProductosCache, times(1)).get(id)
        verify(mockComplementoRepository, times(0)).findById(id)
        verify(mockProductosCache, times(0)).put(id, complemento)
    }

    @Test
    fun `getComplementoById when isError`() {
        val id = "1"
        val complemento = Complemento(id, "Complemento", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA)
        val message = "No existe el valor en la cache"

        Mockito.`when`(mockProductosCache.get(id)).thenReturn(Err(CacheError(message)))
        Mockito.`when`(mockComplementoRepository.findById(id)).thenReturn(null)

        val result = service.getComplementoById(id)

        assertTrue(result.isErr)
        assertFalse(result.value == complemento)

        verify(mockProductosCache, times(1)).get(id)
        verify(mockComplementoRepository, times(1)).findById(id)
        verify(mockProductosCache, times(0)).put(id, complemento)
    }

    @Test
    fun getComplementoByNombre() {
        val nombre = "Palomitas"
        val complemento = Complemento("1", "Complemento", nombre, 3.0, 20, CategoriaComplemento.COMIDA)

        Mockito.`when`(mockComplementoRepository.findByNombre(nombre)).thenReturn(complemento)

        val result = service.getComplementoByNombre(nombre)

        assertTrue(result.isOk)
        assertTrue(result.value == complemento)

        verify(mockComplementoRepository, times(1)).findByNombre(nombre)
        verify(mockProductosCache, times(0)).put(nombre, complemento)
    }

    @Test
    fun `getComplementoByNombre when isError`() {
        val nombre = "Palomitas"
        val complemento = Complemento("1", "Complemento", nombre, 3.0, 20, CategoriaComplemento.COMIDA)

        Mockito.`when`(mockComplementoRepository.findByNombre(nombre)).thenReturn(null)

        val result = service.getComplementoByNombre(nombre)

        assertTrue(result.isErr)
        assertTrue(result.error is ProductoError.ProductoNoEncontrado)
        assertEquals(result.error.message, "Producto no encontrado con nombre: $nombre")

        verify(mockComplementoRepository, times(1)).findByNombre(nombre)
        verify(mockProductosCache, times(0)).put(nombre, complemento)
    }

    @Test
    fun saveButaca() {
    }

    @Test
    fun saveComplemento() {
        val id = "2"
        val complemento = Complemento(id, "Complemento", "Agua", 2.0, 20, CategoriaComplemento.BEBIDA)

        Mockito.`when`(mockComplementoValidator.validate(complemento)).thenReturn(Ok(complemento))
        Mockito.`when`(mockComplementoRepository.save(complemento)).thenReturn(complemento)
        Mockito.`when`(mockProductosCache.put(id, complemento)).thenReturn(Ok(complemento))

        val result = service.saveComplemento(complemento)

        assertTrue(result.isOk)
        assertTrue(result.value == complemento)

        verify(mockComplementoValidator, times(1)).validate(complemento)
        verify(mockComplementoRepository, times(1)).save(complemento)
        verify(mockProductosCache, times(1)).put(id, complemento)
    }

    @Test
    fun updateButaca() {
    }

    @Test
    fun updateComplemento() {
        val id = "1"
        val complemento = Complemento(id, "Complemento", "Palomitas", 3.5, 20, CategoriaComplemento.COMIDA)

        Mockito.`when`(mockComplementoValidator.validate(complemento)).thenReturn(Ok(complemento))
        Mockito.`when`(mockComplementoRepository.update(id, complemento)).thenReturn(complemento)
        Mockito.`when`(mockProductosCache.put(id, complemento)).thenReturn(Ok(complemento))

        val result = service.updateComplemento(id, complemento)

        assertTrue(result.isOk)
        assertTrue(result.value == complemento)

        verify(mockComplementoValidator, times(1)).validate(complemento)
        verify(mockComplementoRepository, times(1)).update(id, complemento)
        verify(mockProductosCache, times(1)).put(id, complemento)
    }

    @Test
    fun `updateComplemento when isError`() {
        val id = "5"
        val complemento = Complemento(id, "Complemento", "Palomitas", 3.5, 20, CategoriaComplemento.COMIDA)

        Mockito.`when`(mockComplementoValidator.validate(complemento)).thenReturn(Ok(complemento))
        Mockito.`when`(mockComplementoRepository.update(id, complemento)).thenReturn(null)

        val result = service.updateComplemento(id, complemento)

        assertTrue(result.isErr)
        assertTrue(result.error is ProductoError.ProductoNoActualizado)
        assertEquals(result.error.message, "Complemento no actualizado con id: $id")

        verify(mockComplementoValidator, times(1)).validate(complemento)
        verify(mockComplementoRepository, times(1)).update(id, complemento)
        verify(mockProductosCache, times(0)).put(id, complemento)
    }

    @Test
    fun deleteButaca() {
    }

    @Test
    fun deleteComplemento() {
        val id = "1"
        val complemento = Complemento(id, "Complemento", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA)

        Mockito.`when`(mockComplementoRepository.delete(id)).thenReturn(complemento)
        Mockito.`when`(mockProductosCache.remove(id)).thenReturn(Ok(complemento))

        val result = service.deleteComplemento(id)

        assertTrue(result.isOk)
        assertTrue(result.value == complemento)

        verify(mockComplementoRepository, times(1)).delete(id)
        verify(mockProductosCache, times(1)).remove(id)
    }

    @Test
    fun `deleteComplemento when isError`() {
        val id = "5"
        val complemento = Complemento(id, "Complemento", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA)

        Mockito.`when`(mockComplementoRepository.delete(id)).thenReturn(null)

        val result = service.deleteComplemento(id)

        assertTrue(result.isErr)
        assertTrue(result.error is ProductoError.ProductoNoEliminado)
        assertEquals(result.error.message, "complemento no eliminado con id: $id")

        verify(mockComplementoRepository, times(1)).delete(id)
        verify(mockProductosCache, times(0)).remove(id)
        verify(mockProductosCache, times(0)).put(id, complemento)
    }
}