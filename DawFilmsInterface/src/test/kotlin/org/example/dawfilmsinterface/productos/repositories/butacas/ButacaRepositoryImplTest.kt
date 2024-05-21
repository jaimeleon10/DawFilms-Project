package org.example.dawfilmsinterface.productos.repositories.butacas

import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.database.SqlDeLightManager
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals

/**
 * Tests para comprobar el correcto funcionamiento del repositorio de Butaca
 *
 * @author Jaime León, Alba García, Natalia González, Javier Ruiz, Germán Fernández
 * @since 1.0.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ButacaRepositoryImplTest {
    private lateinit var dbManager : SqlDeLightManager
    private lateinit var butacaRepository : ButacaRepositoryImpl

    @BeforeAll
    fun setUpAll() {
        println("Iniciando tests...")
        dbManager = SqlDeLightManager(Config())
        butacaRepository = ButacaRepositoryImpl(dbManager)
    }

    @AfterAll
    fun tearDown() {
        dbManager.clearData()
        dbManager.insertSampleData()
    }
    @Test
    @Order(1)
    fun findAll() {
        val butaca = butacaRepository.findAll()

        assertEquals(1, butaca.size)
    }

    @Test
    @Order(2)
    fun findById() {
        val butaca = butacaRepository.findById("A1")

        assertEquals("A1", butaca?.id)
        assertEquals("Butaca", butaca?.tipoProducto)
        assertEquals("futura_imagen.png", butaca?.imagen)
        assertEquals(0, butaca?.fila)
        assertEquals(0, butaca?.columna)
        assertEquals(TipoButaca.NORMAL, butaca?.tipoButaca)
        assertEquals(EstadoButaca.ACTIVA, butaca?.estadoButaca)
        assertEquals(OcupacionButaca.LIBRE, butaca?.ocupacionButaca)


    }

    @Test
    @Order(3)
    fun findByIdNotFound() {
        val butaca = butacaRepository.findById("5")

        assertEquals(null, butaca)
    }

    @Test
    @Order(4)
    fun save() {
        val butaca = butacaRepository.save(
            Butaca(
                id = "A2",
                tipoProducto = "Butaca",
                imagen = "futura_imagen2.png",
                fila = 1,
                columna = 1,
                tipoButaca = TipoButaca.NORMAL,
                estadoButaca = EstadoButaca.ACTIVA,
                ocupacionButaca = OcupacionButaca.LIBRE
            )
        )

        assertEquals("A2", butaca.id)
        assertEquals("Butaca", butaca.tipoProducto)
        assertEquals("futura_imagen2.png", butaca.imagen)
        assertEquals(1, butaca.fila)
        assertEquals(1, butaca.columna)
        assertEquals(TipoButaca.NORMAL, butaca.tipoButaca)
        assertEquals(EstadoButaca.ACTIVA, butaca.estadoButaca)
        assertEquals(OcupacionButaca.LIBRE, butaca.ocupacionButaca)
    }

    @Test
    @Order(5)
    fun update() {
        val butaca = butacaRepository.update(
            "A1",
            Butaca(
                id = "A1",
                tipoProducto = "Butaca_update",
                imagen = "futura_imagen2.png",
                fila = 1,
                columna = 1,
                tipoButaca = TipoButaca.NORMAL,
                estadoButaca = EstadoButaca.ACTIVA,
                ocupacionButaca = OcupacionButaca.LIBRE
            )
        )

        assertEquals("A1", butaca?.id)
        assertEquals("Butaca_update", butaca?.tipoProducto)
        assertEquals("futura_imagen2.png", butaca?.imagen)
        assertEquals(1, butaca?.fila)
        assertEquals(1, butaca?.columna)
        assertEquals(TipoButaca.NORMAL, butaca?.tipoButaca)
        assertEquals(EstadoButaca.ACTIVA, butaca?.estadoButaca)
        assertEquals(OcupacionButaca.LIBRE, butaca?.ocupacionButaca)
    }

    @Test
    @Order(6)
    fun updateNotFound() {
        val butaca = butacaRepository.update(
            "15",
            Butaca(
                id = "A1",
                tipoProducto = "Butaca",
                imagen = "futura_imagen2.png",
                fila = 1,
                columna = 1,
                tipoButaca = TipoButaca.NORMAL,
                estadoButaca = EstadoButaca.ACTIVA,
                ocupacionButaca = OcupacionButaca.LIBRE
            )
        )

        assertEquals(null, butaca)
    }

    @Test
    @Order(7)
    fun delete() {
        val butaca = butacaRepository.delete("A2")

        assertEquals("A2", butaca?.id)
    }

    @Test
    @Order(8)
    fun deleteNotFound() {
        val butaca = butacaRepository.delete("20")

        assertEquals(null, butaca)
    }
}