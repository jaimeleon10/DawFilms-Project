package org.example.dawfilmsinterface.productos.services

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.dawfilmsinterface.cache.errors.CacheError
import org.example.dawfilmsinterface.productos.cache.ProductosCache
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.repositories.butacas.ButacaRepository
import org.example.dawfilmsinterface.productos.repositories.complementos.ComplementoRepository
import org.example.dawfilmsinterface.productos.service.ProductoServiceImpl
import org.example.dawfilmsinterface.productos.validators.ButacaValidator
import org.example.dawfilmsinterface.productos.validators.ComplementoValidator
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.lighthousegames.logging.logging
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.time.LocalDate

/**
 * Tests para comprobar el correcto funcionamiento del servicio de productos
 *
 * @author Jaime León, Alba García, Natalia González, Javier Ruiz, Germán Fernández
 * @since 1.0.0
 */

private val logger = logging()
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
        Mockito.`when`(mockButacaRepository.findAll()).thenReturn(listOf())
        Mockito.`when`(mockComplementoRepository.findAll()).thenReturn(listOf())

        val results = service.getAllButacas()
        val result = service.getAllComplementos()

        assertTrue(results.isOk)
        assertTrue(result.isOk)

        verify(mockButacaRepository, times(1)).findAll()
        verify(mockComplementoRepository, times(1)).findAll()
    }

    @Test
    fun getAllButacas() {
        Mockito.`when`(mockButacaRepository.findAll()).thenReturn(listOf())

        val result = service.getAllButacas()

        assertTrue(result.isOk)

        verify(mockButacaRepository, times(1)).findAll()
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
        val id = "A1"
        val butaca = Butaca(id, "Butaca", "futura_imagen.png", 0, 0, TipoButaca.NORMAL, EstadoButaca.ACTIVA,
            OcupacionButaca.LIBRE)

        Mockito.`when`(mockProductosCache.get(id)).thenReturn(Ok(butaca))

        val result = service.getButacaById(id)

        assertTrue(result.isOk)
        assertTrue(result.value == butaca)

        verify(mockProductosCache, times(1)).get(id)
        verify(mockButacaRepository, times(0)).findById(id)
        verify(mockProductosCache, times(0)).put(id, butaca)
    }
    @Test
    fun `getButacaById when isError`() {
        val id = "A1"
        val butaca = Butaca(id, "Butaca", "futura_imagen.png", 0, 0, TipoButaca.NORMAL, EstadoButaca.ACTIVA, OcupacionButaca.LIBRE)
        val message = "No existe el valor en la cache"

        Mockito.`when`(mockProductosCache.get(id)).thenReturn(Err(CacheError(message)))
        Mockito.`when`(mockButacaRepository.findById(id)).thenReturn(null)

        val result = service.getButacaById(id)

        assertTrue(result.isErr)
        assertFalse(result.value == butaca)

        verify(mockProductosCache, times(1)).get(id)
        verify(mockButacaRepository, times(1)).findById(id)
        verify(mockProductosCache, times(0)).put(id, butaca)
    }

    @Test
    fun getComplementoById() {
        val id = "1"
        val complemento = Complemento(id, "Complemento", "futura_imagen", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA)

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
        val complemento = Complemento(id, "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA)
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
        val nombre = "Agua"
        val complemento = Complemento("1", "Complemento", "agua.png", nombre, 3.0, 20, CategoriaComplemento.BEBIDA)

        Mockito.`when`(mockComplementoRepository.findByNombre(nombre)).thenReturn(complemento)

        val result = service.getComplementoByNombre(nombre)
        logger.debug { result }

        assertTrue(result.isOk)
        assertTrue(result.value == complemento)

        verify(mockComplementoRepository, times(1)).findByNombre(nombre)
        verify(mockProductosCache, times(0)).put(nombre, complemento)
    }

    @Test
    fun `getComplementoByNombre when isError`() {
        val nombre = "Palomitas"
        val complemento = Complemento("1", "Complemento", "futura_imagen.png", nombre, 3.0, 20, CategoriaComplemento.COMIDA)

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
        val id = "A2"
        val butaca = Butaca(id, "Butaca", "futura_imagen.png", 1, 1, TipoButaca.NORMAL, EstadoButaca.ACTIVA, OcupacionButaca.LIBRE)

        Mockito.`when`(mockButacaValidator.validate(butaca)).thenReturn(Ok(butaca))
        Mockito.`when`(mockButacaRepository.save(butaca)).thenReturn(butaca)
        Mockito.`when`(mockProductosCache.put(id, butaca)).thenReturn(Ok(butaca))

        val result = service.saveButaca(butaca)

        assertTrue(result.isOk)
        assertTrue(result.value == butaca)

        verify(mockButacaValidator, times(1)).validate(butaca)
        verify(mockButacaRepository, times(1)).save(butaca)
        verify(mockProductosCache, times(1)).put(id, butaca)
    }

    @Test
    fun saveComplemento() {
        val id = "2"
        val complemento = Complemento(id, "Complemento", "futura_imagen.png", "Agua", 2.0, 20, CategoriaComplemento.BEBIDA)

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
        val id = "A1"
        val butaca = Butaca(id, "Butaca", "futura_imagen.png", 0, 0, TipoButaca.NORMAL, EstadoButaca.ACTIVA, OcupacionButaca.LIBRE)

        Mockito.`when`(mockButacaValidator.validate(butaca)).thenReturn(Ok(butaca))
        Mockito.`when`(mockButacaRepository.update(id, butaca)).thenReturn(butaca)
        Mockito.`when`(mockProductosCache.put(id, butaca)).thenReturn(Ok(butaca))

        val result = service.updateButaca(id, butaca)

        assertTrue(result.isOk)
        assertTrue(result.value == butaca)

        verify(mockButacaValidator, times(1)).validate(butaca)
        verify(mockButacaRepository, times(1)).update(id, butaca)
        verify(mockProductosCache, times(1)).put(id, butaca)
    }

    @Test
    fun updateComplemento() {
        val id = "1"
        val complemento = Complemento(id, "Complemento", "futura_imagen.png", "Palomitas", 3.5, 20, CategoriaComplemento.COMIDA)

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
        val complemento = Complemento(id, "Complemento", "futura_imagen.png", "Palomitas", 3.5, 20, CategoriaComplemento.COMIDA)

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
    fun `updateButaca when isError`() {
        val id = "A5"
        val butaca = Butaca(id, "Butaca", "futura_imagen.png", 4, 4, TipoButaca.NORMAL, EstadoButaca.ACTIVA, OcupacionButaca.LIBRE)

        Mockito.`when`(mockButacaValidator.validate(butaca)).thenReturn(Ok(butaca))
        Mockito.`when`(mockButacaRepository.update(id, butaca)).thenReturn(null)

        val result = service.updateButaca(id, butaca)

        assertTrue(result.isErr)
        assertTrue(result.error is ProductoError.ProductoNoActualizado)
        assertEquals(result.error.message, "Butaca no actualizada con id: $id")

        verify(mockButacaValidator, times(1)).validate(butaca)
        verify(mockButacaRepository, times(1)).update(id, butaca)
        verify(mockProductosCache, times(0)).put(id, butaca)
    }

    @Test
    fun deleteButaca() {
        val id = "A1"
        val butaca = Butaca(id, "Butaca", "futura_imagen.png", 0, 0, TipoButaca.NORMAL, EstadoButaca.ACTIVA, OcupacionButaca.LIBRE)

        Mockito.`when`(mockButacaRepository.delete(id)).thenReturn(butaca)
        Mockito.`when`(mockProductosCache.remove(id)).thenReturn(Ok(butaca))

        val result = service.deleteButaca(id)

        assertTrue(result.isOk)
        assertTrue(result.value == butaca)

        verify(mockButacaRepository, times(1)).delete(id)
        verify(mockProductosCache, times(1)).remove(id)
    }

    @Test
    fun deleteComplemento() {
        val id = "1"
        val complemento = Complemento(id, "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA)

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
        val complemento = Complemento(id, "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA)

        Mockito.`when`(mockComplementoRepository.delete(id)).thenReturn(null)

        val result = service.deleteComplemento(id)

        assertTrue(result.isErr)
        assertTrue(result.error is ProductoError.ProductoNoEliminado)
        assertEquals(result.error.message, "complemento no eliminado con id: $id")

        verify(mockComplementoRepository, times(1)).delete(id)
        verify(mockProductosCache, times(0)).remove(id)
        verify(mockProductosCache, times(0)).put(id, complemento)
    }
    @Test
    fun `deleteButaca when isError`() {
        val id = "A5"
        val butaca = Butaca(id, "Butaca", "futura_imagen.png", 4, 4, TipoButaca.NORMAL, EstadoButaca.ACTIVA, OcupacionButaca.LIBRE)

        Mockito.`when`(mockButacaRepository.delete(id)).thenReturn(null)

        val result = service.deleteButaca(id)

        assertTrue(result.isErr)
        assertTrue(result.error is ProductoError.ProductoNoEliminado)
        assertEquals(result.error.message, "Butaca no eliminada con id: $id")

        verify(mockButacaRepository, times(1)).delete(id)
        verify(mockProductosCache, times(0)).remove(id)
        verify(mockProductosCache, times(0)).put(id, butaca)
    }
}