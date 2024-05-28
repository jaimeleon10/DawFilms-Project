package org.example.dawfilmsinterface.ventas.repositories

import database.DatabaseQueries
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.repositories.ClienteRepository
import org.example.dawfilmsinterface.clientes.repositories.ClienteRepositoryImpl
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.database.SqlDeLightManager
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.repositories.butacas.ButacaRepository
import org.example.dawfilmsinterface.productos.repositories.butacas.ButacaRepositoryImpl
import org.example.dawfilmsinterface.productos.repositories.complementos.ComplementoRepository
import org.example.dawfilmsinterface.productos.repositories.complementos.ComplementoRepositoryImpl
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.lighthousegames.logging.logging
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.kotlin.whenever
import org.mockito.quality.Strictness
import java.time.LocalDate
import java.util.*

/**
 * Tests para comprobar el correcto funcionamiento del repositorio de Ventas
 *
 * @author Jaime León, Alba García, Natalia González, Javier Ruiz, Germán Fernández
 * @since 1.0.0
 */

private val logger = logging()

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class VentaRepositoryImplTest {

    @Mock
    lateinit var config: Config

    @Mock
    private lateinit var butacaRepository: ButacaRepository

    @Mock
    private lateinit var complementoRepository: ComplementoRepository

    @Mock
    private lateinit var clienteRepository: ClienteRepository

    @Mock
    private lateinit var dbManager: SqlDeLightManager

    @InjectMocks
    private lateinit var ventaRepository: VentaRepositoryImpl

    private lateinit var databaseQueries: DatabaseQueries
    private lateinit var clienteMuestra: Cliente
    private lateinit var complementoMuestra: Complemento
    private lateinit var butacaMuestra: Butaca
    private lateinit var lineaVentaMuestra1: LineaVenta
    private lateinit var lineaVentaMuestra2: LineaVenta
    private lateinit var lineaVentaMuestra3: LineaVenta

    @BeforeEach
    fun setUp(){
        whenever(config.dataBaseUrl).thenReturn("jdbc:sqlite::memory:")
        whenever(config.dataBaseInMemory).thenReturn(true)
        whenever(config.databaseInitData).thenReturn(true)
        whenever(config.databaseRemoveData).thenReturn(true)

        dbManager= SqlDeLightManager(config)
        databaseQueries = dbManager.databaseQueries
        butacaRepository = ButacaRepositoryImpl(dbManager)
        complementoRepository = ComplementoRepositoryImpl(dbManager)
        clienteRepository = ClienteRepositoryImpl(dbManager)
        ventaRepository = VentaRepositoryImpl(dbManager, butacaRepository, complementoRepository, clienteRepository)

        dbManager.initialize()

        clienteMuestra = Cliente(1, "Jaime", "Leon", LocalDate.parse("2000-05-10"), "12345678A", "jleon@gmail.com", "AAA111", "password", LocalDate.now(), LocalDate.now(), false)
        complementoMuestra = Complemento("1", "Complemento", "futura_imagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA, LocalDate.now(), LocalDate.now(), false)
        butacaMuestra = Butaca("A1","Butaca", "futura_imagen.png", 0, 0, TipoButaca.NORMAL, EstadoButaca.ACTIVA, OcupacionButaca.LIBRE, LocalDate.now(), LocalDate.now(), false)
        lineaVentaMuestra1 = LineaVenta(UUID.fromString("21c712fb-5531-4f33-a744-0fdb65cd9dcf"), butacaMuestra, butacaMuestra.tipoProducto, 1, butacaMuestra.tipoButaca.precio, LocalDate.now(), LocalDate.now(), false)
        lineaVentaMuestra2 = LineaVenta(UUID.fromString("22c712fb-5531-4f33-a744-0fdb65cd9dcf"), complementoMuestra, complementoMuestra.tipoProducto, 1, complementoMuestra.precio, LocalDate.now(), LocalDate.now(), false)
    }

    @Test
    fun findAll(){
        val ventas = ventaRepository.findAll(
            clienteMuestra,
            listOf(lineaVentaMuestra1,lineaVentaMuestra2),
            LocalDate.of(2024,5,18))

        assertEquals(1,ventas.size)
    }

    @Test
    fun findById() {
        val venta=ventaRepository.findById(
            UUID.fromString("37c712fb-5531-4f33-a744-0fdb65cd9dcf")
        )

        assertEquals("37c712fb-5531-4f33-a744-0fdb65cd9dcf", venta?.id.toString())
        assertEquals(1,venta?.cliente?.id)
        assertEquals(8, venta?.total?.toLong())
        assertEquals(LocalDate.of(2024,5,20), venta?.fechaCompra )
    }

    @Test
    fun findByIdNotFound() {
        val venta = ventaRepository.findById(
            UUID.fromString("00c712fb-1111-4f33-a744-0fdb65cd9dcf")
        )

        assertEquals(null, venta)
    }

    @Test
    fun save() {
        val ventaSaved = ventaRepository.save(
            Venta(
                id = UUID.fromString("62c712fb-5531-4f33-a744-0fdb65cd9dcf"),
                cliente=clienteMuestra,
                listOf(lineaVentaMuestra1,lineaVentaMuestra2),
                LocalDate.of(2024,5,18)
            )
        )

        assertEquals(UUID.fromString("62c712fb-5531-4f33-a744-0fdb65cd9dcf"),ventaSaved.id)
        assertEquals(clienteMuestra,ventaSaved.cliente)
        assertEquals(lineaVentaMuestra1,ventaSaved.lineas[0])
        assertEquals(lineaVentaMuestra2,ventaSaved.lineas[1])
        assertEquals(LocalDate.of(2024,5,18),ventaSaved.fechaCompra)
    }

    @Test
    fun update() {
        val ventaUpdated = ventaRepository.update(
            UUID.fromString("37c712fb-5531-4f33-a744-0fdb65cd9dcf"),
            Venta(
                id = UUID.fromString("37c712fb-5531-4f33-a744-0fdb65cd9dcf"),
                cliente=clienteMuestra,
                listOf(lineaVentaMuestra1,lineaVentaMuestra2),
                LocalDate.of(2023,1,19)
            )
        )

        assertEquals(UUID.fromString("37c712fb-5531-4f33-a744-0fdb65cd9dcf"),ventaUpdated?.id)
        assertEquals(clienteMuestra,ventaUpdated?.cliente)
        assertEquals(lineaVentaMuestra1,ventaUpdated!!.lineas[0])
        assertEquals(lineaVentaMuestra2, ventaUpdated.lineas[1])
        assertEquals(LocalDate.of(2023,1,19),ventaUpdated.fechaCompra)
    }

    @Test
    fun updateNotFound() {
        val ventaUpdated = ventaRepository.update(
            UUID.fromString("000712fb-5531-4f33-a744-0fdb65cd9dcf"),
            Venta(
                id = UUID.fromString("000712fb-5531-4f33-a744-0fdb65cd9dcf"),
                cliente=clienteMuestra,
                listOf(lineaVentaMuestra1,lineaVentaMuestra2),
                LocalDate.of(2023,1,19)
            )
        )
        assertEquals(null,ventaUpdated)
    }

    @Test
    fun delete() {
        val ventaDeleted = ventaRepository.delete(UUID.fromString("37c712fb-5531-4f33-a744-0fdb65cd9dcf"))

        assertEquals(UUID.fromString("37c712fb-5531-4f33-a744-0fdb65cd9dcf"), ventaDeleted?.id)
    }

    @Test
    fun deleteNotFound() {
        val ventaDeleted = ventaRepository.delete(UUID.fromString("666712fb-5531-4f33-a744-0fdb65cd9dcf"))

        assertEquals(null, ventaDeleted)
    }

    @Test
    fun validateCliente() {

        val mockCliente = Cliente(
            id = 1,
            nombre = "User",
            apellido = "User",
            fechaNacimiento = LocalDate.of(2000,5, 10),
            dni = "12345678A",
            email = "user@user.com",
            numSocio = "AAA111",
            password = "user",
            createdAt = LocalDate.now(),
            updatedAt = LocalDate.now(),
            isDeleted = false)


        val cliente = ventaRepository.validateCliente(mockCliente)

        logger.debug { cliente }

        assertTrue(cliente.isOk)
        assertEquals (mockCliente, cliente.value)
    }

    @Test
    fun validateClienteFail() {
        val mockCliente = Cliente(
            id = -1L,
            nombre = "Pepe",
            apellido = "",
            fechaNacimiento = LocalDate.of(1997, 12, 31),
            dni = "12345678Z",
            email = "ejemplo@si.com",
            numSocio = "123",
            password = "password",
            createdAt = LocalDate.now(),
            updatedAt = LocalDate.now(),
            isDeleted = false
        )
        val cliente = ventaRepository.validateCliente(mockCliente)

        logger.debug { cliente }

        assertTrue(cliente.isErr)
    }

    @Test
    fun validateLineas() {

        val lista = listOf(lineaVentaMuestra1, lineaVentaMuestra2)

        val result = ventaRepository.validateLineas(listOf(lineaVentaMuestra1, lineaVentaMuestra2))

        assertTrue(result.isOk)
        assertEquals(lista, result.value)
    }

    @Test
    fun validateLineasButacaNoValid() {

        val butacaNoValid = Butaca(
            id="AB",
            tipoProducto = "Butaca",
            imagen = "futura_imagen.png",
            fila= 0,
            columna = 0,
            tipoButaca = TipoButaca.NORMAL,
            estadoButaca = EstadoButaca.ACTIVA,
            ocupacionButaca = OcupacionButaca.LIBRE,
            createdAt = LocalDate.of(2024,5,18),
            updatedAt = LocalDate.of(2024,5,18),
            isDeleted = false
        )

        val lineaVentaButacaNoValid = LineaVenta(
            id= UUID.fromString("000712fb-5531-4f33-a744-0fdb65cd9dcf"),
            producto = butacaNoValid,
            tipoProducto = "Butaca",
            cantidad = 1,
            precio = 5.0,
            createdAt = LocalDate.of(2024,5,18),
            updatedAt = LocalDate.of(2024,5,18)
        )

        val lista = listOf(lineaVentaButacaNoValid, lineaVentaMuestra2)

        val result = ventaRepository.validateLineas(listOf(lineaVentaButacaNoValid, lineaVentaMuestra2))

        assertTrue(result.isErr)
        assertFalse(lista == result.value)
    }

    @Test
    fun validateLineasComplementoNoValid() {

        val complementoNoValid = Complemento(
            id="A",
            tipoProducto = "Complemento",
            imagen = "'futura_imagen.png",
            nombre = "Palomitas",
            precio = 3.0,
            stock =  20,
            categoria = CategoriaComplemento.COMIDA,
            createdAt = LocalDate.of(2024,5,18),
            updatedAt = LocalDate.of(2024,5,18),
            isDeleted = false
        )

        val lineaVentaProductoNoValid = LineaVenta(
            id= UUID.fromString("000712fb-5531-4f33-a744-0fdb65cd9dcf"),
            producto = complementoNoValid,
            tipoProducto = "Butaca",
            cantidad = 1,
            precio = 5.0,
            createdAt = LocalDate.of(2024,5,18),
            updatedAt = LocalDate.of(2024,5,18)
        )



        val lista = listOf(lineaVentaMuestra1, lineaVentaProductoNoValid)

        val result = ventaRepository.validateLineas(listOf(lineaVentaMuestra1, lineaVentaProductoNoValid))

        assertTrue(result.isErr)
        assertFalse(lista == result.value)
    }
}