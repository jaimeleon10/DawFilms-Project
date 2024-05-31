package org.example.dawfilmsinterface.ventas.services

import com.github.michaelbull.result.Ok
import database.VentaEntity
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import org.example.dawfilmsinterface.ventas.repositories.VentaRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.lighthousegames.logging.logging
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.util.*


private val logger = logging()
@ExtendWith(MockitoExtension::class)

class VentaServiceImplTest {

    @Mock
    lateinit var repoVentas: VentaRepository

    @InjectMocks
    lateinit var serviceVentas: VentaServiceImpl


    private lateinit var clienteMuestra: Cliente
    private lateinit var complementoMuestra: Complemento
    private lateinit var butacaMuestra: Butaca
    private lateinit var lineaVentaMuestra1: LineaVenta
    private lateinit var lineaVentaMuestra2: LineaVenta


    @BeforeEach
    fun setUp(){

        clienteMuestra = Cliente(1, "User", "User", LocalDate.parse("2000-05-10"), "12345678A", "user@user.com", "AAA111", "user", LocalDate.now(), LocalDate.now(), false)
        complementoMuestra = Complemento("1", "Complemento", "sinImagen.png", "Palomitas", 3.0, 20, CategoriaComplemento.COMIDA, LocalDate.now(), LocalDate.now(), false)
        butacaMuestra = Butaca("A1","Butaca", "sinImagen.png", 0, 0, TipoButaca.NORMAL, EstadoButaca.ACTIVA, OcupacionButaca.LIBRE, LocalDate.now(), LocalDate.now(), false)
        lineaVentaMuestra1 = LineaVenta(UUID.fromString("21c712fb-5531-4f33-a744-0fdb65cd9dcf"), butacaMuestra, butacaMuestra.tipoProducto, 1, butacaMuestra.tipoButaca.precio, LocalDate.now(), LocalDate.now(), false)
        lineaVentaMuestra2 = LineaVenta(UUID.fromString("22c712fb-5531-4f33-a744-0fdb65cd9dcf"), complementoMuestra, complementoMuestra.tipoProducto, 1, complementoMuestra.precio, LocalDate.now(), LocalDate.now(), false)
    }

    @Test
    fun getById() {
        val id = UUID.fromString("67c712fb-5531-4f33-a744-0fdb65cd9dcf")

        val mockVenta = Venta(
            id = UUID.fromString("67c712fb-5531-4f33-a744-0fdb65cd9dcf"),
            cliente = Cliente(
                id = 1,
                nombre = "Test1",
                apellido = "apell1",
                fechaNacimiento = LocalDate.of(2000,1,1),
                dni = "12345678A",
                email = "test1@gmail.com",
                numSocio = "AAA111",
                password = "password"),
            lineas = listOf(
                LineaVenta(
                    tipoProducto = "Butaca",
                    producto = Butaca(
                        id = "A1",
                        fila = 0,
                        columna = 0,
                        tipoButaca = TipoButaca.NORMAL,
                        estadoButaca = EstadoButaca.ACTIVA,
                        ocupacionButaca = OcupacionButaca.LIBRE
                    ),
                    cantidad = 1,
                    precio =  5.0,
                    createdAt = LocalDate.now(),
                    updatedAt = LocalDate.now(),
                )
            ),
            fechaCompra = LocalDate.now(),
            createdAt = LocalDate.now(),
            updatedAt = LocalDate.now(),
        )

        whenever(repoVentas.findById(id)).thenReturn(mockVenta)

        val venta=serviceVentas.getById(id)

        assertTrue(venta.isOk)
        assertTrue(venta.value == mockVenta)

        verify(repoVentas, times(1)).findById(id)
    }


    @Test
    fun getAllLineas(){

        whenever(repoVentas.findAllLineas()).thenReturn(listOf(lineaVentaMuestra1,lineaVentaMuestra2))

        val lineas = serviceVentas.getAllLineas()

        assertAll(
            { assertEquals(2, lineas.value.size)},
            { assertEquals("21c712fb-5531-4f33-a744-0fdb65cd9dcf", lineas.value[0].id.toString())},
            { assertEquals("22c712fb-5531-4f33-a744-0fdb65cd9dcf", lineas.value[1].id.toString())}
        )
    }

    @Test
    fun getAllVentasByCliente(){

        val venta = Venta(
            id = UUID.fromString("22c712fb-5531-4f33-a744-0fdb65cd9dcf"),
            cliente = clienteMuestra,
            lineas = listOf(lineaVentaMuestra1,lineaVentaMuestra2),
            fechaCompra = LocalDate.of(2024,5,18),
            createdAt = LocalDate.now(),
            updatedAt = LocalDate.now(),
            isDeleted = false)



        whenever(repoVentas.findAllVentasCliente(clienteMuestra, listOf(lineaVentaMuestra1,lineaVentaMuestra2), LocalDate.of(2024,5,18)))
            .thenReturn(listOf(venta))

        val ventas = serviceVentas.getAllVentasByCliente(clienteMuestra, listOf(lineaVentaMuestra1,lineaVentaMuestra2), LocalDate.of(2024,5,18))

        assertAll(
            { assertEquals(1, ventas.value.size) },
            { assertEquals("User", ventas.value[0].cliente.nombre)}
        )
    }

    @Test
    fun getAllVentasByClienteNotFound(){

        val venta = Venta(
            id = UUID.fromString("22c712fb-5531-4f33-a744-0fdb65cd9dcf"),
            cliente = clienteMuestra,
            lineas = listOf(lineaVentaMuestra1,lineaVentaMuestra2),
            fechaCompra = LocalDate.of(2024,5,18),
            createdAt = LocalDate.now(),
            updatedAt = LocalDate.now(),
            isDeleted = false)



        whenever(repoVentas.findAllVentasCliente(clienteMuestra, listOf(lineaVentaMuestra1,lineaVentaMuestra2), LocalDate.of(2024,5,18)))
            .thenReturn(null)

        val ventas = serviceVentas.getAllVentasByCliente(clienteMuestra, listOf(lineaVentaMuestra1,lineaVentaMuestra2), LocalDate.of(2024,5,18))

        logger.debug { ventas }

        assertAll(
            { assertTrue(ventas.isOk) },
            { assertEquals(null, ventas.value )}
        )
    }

    @Test
    fun getAllVentasEntity(){
        val venta = VentaEntity(
            id = "22c712fb-5531-4f33-a744-0fdb65cd9dcf",
            cliente_id = clienteMuestra.id,
            total = 0.0,
            fecha_compra = LocalDate.of(2024,5,18).toString(),
            created_at = LocalDate.now().toString(),
            updated_at = LocalDate.now().toString(),
            is_deleted = 0)

        whenever(repoVentas.findAllVentas()).thenReturn(listOf(venta))

        val ventas = serviceVentas.getAllVentasEntity()

        assertAll(
            { assertEquals(1, ventas.value.size)},
            { assertEquals("22c712fb-5531-4f33-a744-0fdb65cd9dcf", ventas.value[0].id)}
        )
    }

    @Test
    fun getAllLineasByVentaID(){

        val venta = Venta(
            id = UUID.fromString("21c712fb-5531-4f33-a744-0fdb65cd9dcf"),
            cliente = clienteMuestra,
            lineas = listOf(lineaVentaMuestra1, lineaVentaMuestra2),
            fechaCompra = LocalDate.now(),
            createdAt = LocalDate.now(),
            updatedAt = LocalDate.now(),
            isDeleted = false
        )

        whenever(repoVentas.findAllLineasByID("21c712fb-5531-4f33-a744-0fdb65cd9dcf")).thenReturn(listOf(lineaVentaMuestra1))

        val lineas = serviceVentas.getAllLineasByVentaID("21c712fb-5531-4f33-a744-0fdb65cd9dcf")

        assertAll(
            { assertEquals(1, lineas.value.size)},
            { assertEquals("21c712fb-5531-4f33-a744-0fdb65cd9dcf", lineas.value[0].id.toString())}
        )
    }

    @Test
    fun getAllLineasByVentaIDNotFound(){

        val venta = Venta(
            id = UUID.fromString("21c712fb-5531-4f33-a744-0fdb65cd9dcf"),
            cliente = clienteMuestra,
            lineas = listOf(lineaVentaMuestra1, lineaVentaMuestra2),
            fechaCompra = LocalDate.now(),
            createdAt = LocalDate.now(),
            updatedAt = LocalDate.now(),
            isDeleted = false
        )

        whenever(repoVentas.findAllLineasByID("21c712fb-5531-4f33-a744-0fdb65cd9dcf")).thenReturn(null)

        val lineas = serviceVentas.getAllLineasByVentaID("21c712fb-5531-4f33-a744-0fdb65cd9dcf")

        assertAll(
            { assertTrue(lineas.isOk) },
            { assertEquals(null, lineas.value )}
        )
    }

    @Test
    fun getAllVentasByDate(){
        val venta = VentaEntity(
            id = "22c712fb-5531-4f33-a744-0fdb65cd9dcf",
            cliente_id = clienteMuestra.id,
            total = 0.0,
            fecha_compra = LocalDate.now().toString(),
            created_at = LocalDate.now().toString(),
            updated_at = LocalDate.now().toString(),
            is_deleted = 0)

        whenever(repoVentas.findVentasByDate(LocalDate.now())).thenReturn(listOf(venta))

        val ventas = serviceVentas.getAllVentasByDate(LocalDate.now())

        assertAll(
            { assertEquals(1, ventas.value.size)},
            { assertEquals("22c712fb-5531-4f33-a744-0fdb65cd9dcf", ventas.value[0].id)}
        )
    }

    @Test
    fun getAllVentasByDateNotFound(){
        val venta = VentaEntity(
            id = "22c712fb-5531-4f33-a744-0fdb65cd9dcf",
            cliente_id = clienteMuestra.id,
            total = 0.0,
            fecha_compra = LocalDate.now().toString(),
            created_at = LocalDate.now().toString(),
            updated_at = LocalDate.now().toString(),
            is_deleted = 0)

        whenever(repoVentas.findVentasByDate(LocalDate.now())).thenReturn(null)

        val ventas = serviceVentas.getAllVentasByDate(LocalDate.now())

        assertAll(
            { assertTrue(ventas.isOk) },
            { assertEquals(null, ventas.value )}
        )
    }

    @Test
    fun deleteAllVentas(){
        serviceVentas.deleteAllVentas()

        val ventas = serviceVentas.getAllVentasEntity()

        assertAll(
            { assertEquals(0, ventas.value.size)}
        )
    }

    @Test
    fun getByIdError(){
        val id =UUID.fromString("57c712fb-5531-4f33-a744-0fdb65cd9dcf")

        whenever(repoVentas.findById(id)).thenReturn(null)

        val result = serviceVentas.getById(id)

        assertTrue(result.isErr)
        assertTrue(result.error is VentaError.VentaNoEncontrada)
        assertEquals(result.error.message, "Venta no encontrada con id: $id")

        verify(repoVentas,times(1)).findById(id)

    }

    @Test
    fun createVenta() {
        val mockVenta = Venta(
            id = UUID.fromString("67c712fb-5531-4f33-a744-0fdb65cd9dcf"),
            cliente = Cliente(
                id = 1,
                nombre = "Test1",
                apellido = "apell1",
                fechaNacimiento = LocalDate.of(2000,1,1),
                dni = "12345678A",
                email = "test1@gmail.com",
                numSocio = "AAA111",
                password = "password"),
            lineas = listOf(
                LineaVenta(
                    tipoProducto = "Butaca",
                    producto = Butaca(
                        id = "A1",
                        fila = 0,
                        columna = 0,
                        tipoButaca = TipoButaca.NORMAL,
                        estadoButaca = EstadoButaca.ACTIVA,
                        ocupacionButaca = OcupacionButaca.LIBRE
                    ),
                    cantidad = 1,
                    precio =  5.0,
                    createdAt = LocalDate.now(),
                    updatedAt = LocalDate.now(),
                )
            ),
            fechaCompra = LocalDate.now(),
            createdAt = LocalDate.now(),
            updatedAt = LocalDate.now(),
        )

        whenever(repoVentas.validateCliente(mockVenta.cliente)).thenReturn(Ok(mockVenta.cliente))
        whenever(repoVentas.validateLineas(mockVenta.lineas)).thenReturn(Ok(mockVenta.lineas))
        whenever(repoVentas.save(mockVenta)).thenReturn(Ok(mockVenta).value)

        val venta = serviceVentas.createVenta(mockVenta)

        assertTrue(venta.isOk)
        assertTrue(venta.value == mockVenta)
        assertTrue(venta.value.cliente == mockVenta.cliente)

        verify(repoVentas,times(1)).validateCliente(mockVenta.cliente)
        verify(repoVentas,times(1)).validateLineas(mockVenta.lineas)
        verify(repoVentas, times(1)).save(mockVenta)
    }
}