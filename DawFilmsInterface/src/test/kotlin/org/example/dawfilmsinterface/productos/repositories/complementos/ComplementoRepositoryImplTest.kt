package org.example.dawfilmsinterface.productos.repositories.complementos

import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.database.SqlDeLightManager
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 * Tests para comprobar el correcto funcionamiento del repositorio de complementos
 *
 * @author Jaime León, Alba García, Natalia González, Javier Ruiz, Germán Fernández
 * @since 1.0.0
 */
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
        dbManager.initialize()
    }

    @Test
    fun findAll() {
        val complementos = complementoRepository.findAll()

        assertEquals(15, complementos.size)
    }

    @Test
    fun findById() {
        val complemento = complementoRepository.findById("1")

        assertEquals("1", complemento?.id)
        assertEquals("Complemento", complemento?.tipoProducto)
        assertEquals("Palomitas", complemento?.nombre)
        assertEquals(3.0, complemento?.precio)
        assertEquals(50, complemento?.stock)
        assertEquals(CategoriaComplemento.COMIDA, complemento?.categoria)
    }

    @Test
    fun findByIdNotFound() {
        val complemento = complementoRepository.findById("-1")

        assertEquals(null, complemento)
    }

    @Test
    fun save() {
        val complemento = complementoRepository.save(
            Complemento(
                id = "16",
                tipoProducto = "Complemento",
                nombre = "Agua",
                precio = 2.0,
                stock = 40,
                categoria = CategoriaComplemento.BEBIDA
            )
        )

        assertEquals("16", complemento.id)
        assertEquals("Complemento", complemento.tipoProducto)
        assertEquals("Agua", complemento.nombre)
        assertEquals(2.0, complemento.precio)
        assertEquals(40, complemento.stock)
        assertEquals(CategoriaComplemento.BEBIDA, complemento.categoria)
    }

    @Test
    fun update() {
        val complemento = complementoRepository.update(
            "1",
            Complemento(
                id = "1",
                tipoProducto = "Complemento",
                nombre = "Complemento actualizado",
                precio = 3.50,
                stock = 50,
                categoria = CategoriaComplemento.COMIDA
            )
        )

        assertEquals("1", complemento?.id)
        assertEquals("Complemento", complemento?.tipoProducto)
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
}