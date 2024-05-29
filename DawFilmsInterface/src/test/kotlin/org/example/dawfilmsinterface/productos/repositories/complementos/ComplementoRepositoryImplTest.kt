package org.example.dawfilmsinterface.productos.repositories.complementos

import database.DatabaseQueries
import org.example.dawfilmsinterface.clientes.repositories.ClienteRepositoryImpl
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.database.SqlDeLightManager
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.lighthousegames.logging.logging
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.kotlin.whenever
import org.mockito.quality.Strictness
import java.time.LocalDate

private val logger = logging()

/**
 * Tests para comprobar el correcto funcionamiento del repositorio de complementos
 *
 * @author Jaime León, Alba García, Natalia González, Javier Ruiz, Germán Fernández
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ComplementoRepositoryImplTest {
    @Mock
    lateinit var config: Config

    private lateinit var dbManager: SqlDeLightManager
    private lateinit var databaseQueries: DatabaseQueries
    private lateinit var complementoRepository : ComplementoRepositoryImpl

    @BeforeEach
    fun setUp() {
        whenever(config.dataBaseUrl).thenReturn("jdbc:sqlite::memory:")
        whenever(config.dataBaseInMemory).thenReturn(true)
        whenever(config.databaseInitData).thenReturn(true)
        whenever(config.databaseRemoveData).thenReturn(true)

        dbManager = SqlDeLightManager(config)
        databaseQueries = dbManager.databaseQueries
        complementoRepository = ComplementoRepositoryImpl(dbManager)

        dbManager.initialize()
    }

    @Test
    fun findAll() {
        val complementos = complementoRepository.findAll()

        assertEquals(5, complementos.size)
    }

    @Test
    fun findById() {
        val complemento = complementoRepository.findById("1")

        assertEquals("1", complemento?.id)
        assertEquals("Complemento", complemento?.tipoProducto)
        assertEquals("agua.png", complemento?.imagen)
        assertEquals("Agua", complemento?.nombre)
        assertEquals(2.0, complemento?.precio)
        assertEquals(20, complemento?.stock)
        assertEquals(CategoriaComplemento.BEBIDA, complemento?.categoria)
    }

    @Test
    fun findByIdNotFound() {
        val complemento = complementoRepository.findById("99999999999999")

        assertEquals(null, complemento)
    }

    @Test
    fun findByNombre(){
        val complemento = complementoRepository.findByNombre("Agua")

        assertEquals("Agua", complemento?.nombre)
    }

    @Test
    fun findByNombreNotFound(){
        val complemento = complementoRepository.findByNombre("perro")

        assertEquals(null, complemento)
    }

    @Test
    fun saveAll(){
        val complemento = Complemento(
            id = "123",
            tipoProducto = "Complemento",
            imagen = "futura_imagen.png",
            nombre = "Soda",
            precio = 2.0,
            stock = 20,
            categoria = CategoriaComplemento.BEBIDA
        )

        val complemento2 = Complemento(
            id = "124",
            tipoProducto = "Complemento",
            imagen = "futura_imagen.png",
            nombre = "Fanta",
            precio = 2.0,
            stock = 20,
            categoria = CategoriaComplemento.BEBIDA
        )

        val lista = listOf(complemento, complemento2)

        complementoRepository.saveAll(lista)

        val lista2 = complementoRepository.findAll()


             assertEquals(7, lista2.size)
             assertEquals("Soda", lista2[5].nombre)
             assertEquals ("Fanta", lista2[6].nombre)

    }

    @Test
    fun save() {
        val complemento = complementoRepository.save(
            Complemento(
                id = "5000",
                tipoProducto = "Complemento",
                imagen = "futura_imagen.png",
                nombre = "Agua",
                precio = 2.0,
                stock = 20,
                categoria = CategoriaComplemento.BEBIDA
            )
        )

        assertEquals("5000", complemento.id)
        assertEquals("Complemento", complemento.tipoProducto)
        assertEquals("futura_imagen.png", complemento.imagen)
        assertEquals("Agua", complemento.nombre)
        assertEquals(2.0, complemento.precio)
        assertEquals(20, complemento.stock)
        assertEquals(CategoriaComplemento.BEBIDA, complemento.categoria)
    }

    @Test
    fun update() {
        val complemento = complementoRepository.update(
            "1",
            Complemento(
                id = "1",
                tipoProducto = "Complemento",
                imagen = "futura_imagen.png",
                nombre = "Complemento actualizado",
                precio = 3.50,
                stock = 50,
                categoria = CategoriaComplemento.COMIDA
            )
        )

        assertEquals("1", complemento?.id)
        assertEquals("Complemento", complemento?.tipoProducto)
        assertEquals("futura_imagen.png", complemento?.imagen)
        assertEquals("Complemento actualizado", complemento?.nombre)
        assertEquals(3.50, complemento?.precio)
        assertEquals(50, complemento?.stock)
        assertEquals(CategoriaComplemento.COMIDA, complemento?.categoria)
    }

    @Test
    fun updateNotFound() {
        val complemento = complementoRepository.update(
            "15",
            Complemento(
                id = "1",
                tipoProducto = "Complemento",
                imagen = "futura_imagen.png",
                nombre = "Complemento actualizado",
                precio = 3.50,
                stock = 50,
                categoria = CategoriaComplemento.COMIDA
            )
        )

        assertEquals(null, complemento)
    }

    @Test
    fun delete() {
        val complemento = complementoRepository.delete("1")

        assertEquals("1", complemento?.id)
    }

    @Test
    fun deleteNotFound() {
        val complemento = complementoRepository.delete("20")

        assertEquals(null, complemento)
    }

    @Test
    fun deleteAll(){
        complementoRepository.deleteAll()

        val lista = complementoRepository.findAll()

        assertEquals(0, lista.size)
    }
}