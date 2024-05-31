package org.example.dawfilmsinterface.productos.repositories.butacas

import database.DatabaseQueries
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.database.SqlDeLightManager
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.lighthousegames.logging.logging
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.kotlin.whenever
import org.mockito.quality.Strictness
import java.time.LocalDate

/**
 * Tests para comprobar el correcto funcionamiento del repositorio de Butaca
 *
 * @author Jaime León, Alba García, Natalia González, Javier Ruiz, Germán Fernández
 * @since 1.0.0
 */
private val logger = logging()
@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ButacaRepositoryImplTest {
    @Mock
    lateinit var config: Config

    private lateinit var dbManager: SqlDeLightManager
    private lateinit var databaseQueries: DatabaseQueries
    private lateinit var butacaRepository: ButacaRepositoryImpl

    @BeforeEach
    fun setUp() {
        whenever(config.dataBaseUrl).thenReturn("jdbc:sqlite::memory:")
        whenever(config.dataBaseInMemory).thenReturn(true)
        whenever(config.databaseInitData).thenReturn(true)
        whenever(config.databaseRemoveData).thenReturn(true)

        dbManager = SqlDeLightManager(config)
        databaseQueries = dbManager.databaseQueries
        butacaRepository = ButacaRepositoryImpl(dbManager)

        dbManager.initialize()
    }

    @Test
    fun findAll() {
        val butaca = butacaRepository.findAll()

        assertEquals(35, butaca.size)
    }

    @Test
    fun findById() {
        val butaca = butacaRepository.findById("A1")

        assertEquals("A1", butaca?.id)
        assertEquals("Butaca", butaca?.tipoProducto)
        assertEquals("sinImagen.png", butaca?.imagen)
        assertEquals(0, butaca?.fila)
        assertEquals(0, butaca?.columna)
        assertEquals(TipoButaca.NORMAL, butaca?.tipoButaca)
        assertEquals(EstadoButaca.ACTIVA, butaca?.estadoButaca)
        assertEquals(OcupacionButaca.LIBRE, butaca?.ocupacionButaca)


    }

    @Test
    fun findByIdNotFound() {
        val butaca = butacaRepository.findById("5")

        assertEquals(null, butaca)
    }

    @Test
    fun save() {
        val butaca = butacaRepository.save(
            Butaca(
                id = "A8",
                tipoProducto = "Butaca",
                imagen = "futura_imagen2.png",
                fila = 1,
                columna = 1,
                tipoButaca = TipoButaca.NORMAL,
                estadoButaca = EstadoButaca.ACTIVA,
                ocupacionButaca = OcupacionButaca.LIBRE
            )
        )

        assertEquals("A8", butaca.id)
        assertEquals("Butaca", butaca.tipoProducto)
        assertEquals("futura_imagen2.png", butaca.imagen)
        assertEquals(1, butaca.fila)
        assertEquals(1, butaca.columna)
        assertEquals(TipoButaca.NORMAL, butaca.tipoButaca)
        assertEquals(EstadoButaca.ACTIVA, butaca.estadoButaca)
        assertEquals(OcupacionButaca.LIBRE, butaca.ocupacionButaca)
    }

    @Test
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
    fun delete() {
        val butaca = butacaRepository.delete("A1")

        assertEquals("A1", butaca?.id)
    }

    @Test
    fun deleteNotFound() {
        val butaca = butacaRepository.delete("20")

        assertEquals(null, butaca)
    }

    @Test
    fun saveAll() {
        val butaca =
            Butaca(
                id = "A8",
                tipoProducto = "Butaca",
                imagen = "futura_imagen2.png",
                fila = 1,
                columna = 1,
                tipoButaca = TipoButaca.NORMAL,
                estadoButaca = EstadoButaca.ACTIVA,
                ocupacionButaca = OcupacionButaca.LIBRE
            )


        val butaca2 =
            Butaca(
                id = "A9",
                tipoProducto = "Butaca",
                imagen = "futura_imagen2.png",
                fila = 1,
                columna = 1,
                tipoButaca = TipoButaca.NORMAL,
                estadoButaca = EstadoButaca.ACTIVA,
                ocupacionButaca = OcupacionButaca.LIBRE
            )

        val butaca3 =
            Butaca(
                id = "A10",
                tipoProducto = "Butaca",
                imagen = "futura_imagen2.png",
                fila = 1,
                columna = 1,
                tipoButaca = TipoButaca.NORMAL,
                estadoButaca = EstadoButaca.ACTIVA,
                ocupacionButaca = OcupacionButaca.LIBRE
            )

        val lista = listOf(butaca, butaca2, butaca3)

        val lista2 = butacaRepository.saveAll(lista)

        assertEquals(3, lista2.size)
        assertEquals("A8", lista2[0].id)
        assertEquals("A9", lista2[1].id)
        assertEquals("A10", lista2[2].id)
    }



    @Test
    fun deleteAll(){
        butacaRepository.deleteAll()

        val lista = butacaRepository.findAll()

        assertEquals(0, lista.size)

    }
}