package org.example.dawfilmsinterface.productos.repositories.complementos

import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.database.SqlDeLightManager
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

/**
 * Tests para comprobar el correcto funcionamiento del repositorio de complementos
 *
 * @author Jaime León, Alba García, Natalia González, Javier Ruiz, Germán Fernández
 * @since 1.0.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ComplementoRepositoryImplTest {
    private lateinit var dbManager : SqlDeLightManager
    private lateinit var complementoRepository : ComplementoRepositoryImpl

    @BeforeAll
    fun setUpAll() {
        println("Iniciando tests...")
        dbManager = SqlDeLightManager(Config())
        complementoRepository = ComplementoRepositoryImpl(dbManager)
    }

    @BeforeEach
    fun setUp() {
        dbManager
    }

    @AfterAll
    fun tearDown() {
        dbManager.clearData()
        dbManager.insertSampleData()
    }

    @Test
    @Order(1)
    fun findAll() {
        val complementos = complementoRepository.findAll()

        assertEquals(1, complementos.size)
    }

    @Test
    @Order(2)
    fun findById() {
        val complemento = complementoRepository.findById("1")

        assertEquals("1", complemento?.id)
        assertEquals("Complemento", complemento?.tipoProducto)
        assertEquals("futura_imagen.png", complemento?.imagen)
        assertEquals("Palomitas", complemento?.nombre)
        assertEquals(3.0, complemento?.precio)
        assertEquals(20, complemento?.stock)
        assertEquals(CategoriaComplemento.COMIDA, complemento?.categoria)
    }

    @Test
    @Order(3)
    fun findByIdNotFound() {
        val complemento = complementoRepository.findById("5")

        assertEquals(null, complemento)
    }

    @Test
    @Order(4)
    fun save() {
        val complemento = complementoRepository.save(
            Complemento(
                id = "2",
                tipoProducto = "Complemento",
                imagen = "futura_imagen.png",
                nombre = "Agua",
                precio = 2.0,
                stock = 20,
                categoria = CategoriaComplemento.BEBIDA
            )
        )

        assertEquals("2", complemento.id)
        assertEquals("Complemento", complemento.tipoProducto)
        assertEquals("futura_imagen.png", complemento?.imagen)
        assertEquals("Agua", complemento.nombre)
        assertEquals(2.0, complemento.precio)
        assertEquals(20, complemento.stock)
        assertEquals(CategoriaComplemento.BEBIDA, complemento.categoria)
    }

    @Test
    @Order(5)
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
    @Order(6)
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
    @Order(7)
    fun delete() {
        val complemento = complementoRepository.delete("1")

        assertEquals("1", complemento?.id)
    }

    @Test
    @Order(8)
    fun deleteNotFound() {
        val complemento = complementoRepository.delete("20")

        assertEquals(null, complemento)
    }
}