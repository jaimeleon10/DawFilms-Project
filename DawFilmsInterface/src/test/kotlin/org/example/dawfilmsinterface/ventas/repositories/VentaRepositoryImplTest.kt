package org.example.dawfilmsinterface.ventas.repositories

import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.repositories.ClienteRepositoryImpl
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.database.SqlDeLightManager
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.repositories.butacas.ButacaRepositoryImpl
import org.example.dawfilmsinterface.productos.repositories.complementos.ComplementoRepositoryImpl
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDate
import java.util.*

/**
 * Tests para comprobar el correcto funcionamiento del repositorio de Ventas
 *
 * @author Jaime León, Alba García, Natalia González, Javier Ruiz, Germán Fernández
 * @since 1.0.0
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VentaRepositoryImplTest {
    private lateinit var dbManager: SqlDeLightManager
    private lateinit var ventaRepository: VentaRepositoryImpl

    private lateinit var butacaRepository: ButacaRepositoryImpl
    private lateinit var complementoRepository: ComplementoRepositoryImpl
    private lateinit var clienteRepository: ClienteRepositoryImpl

    private lateinit var clienteMuestra: Cliente
    private lateinit var complementoMuestra: Complemento
    private lateinit var butacacMuestra: Butaca
    private lateinit var lineaVentaMuestra1: LineaVenta
    private lateinit var lineaVentaMuestra2: LineaVenta
    private lateinit var lineaVentaMuestra3: LineaVenta

    @BeforeAll
    fun setUpAll(){
        println("Iniciando tests...")
        dbManager= SqlDeLightManager(Config())
        butacaRepository=ButacaRepositoryImpl(dbManager)
        complementoRepository= ComplementoRepositoryImpl(dbManager)
        clienteRepository= ClienteRepositoryImpl(dbManager)
        ventaRepository = VentaRepositoryImpl(dbManager, butacaRepository, complementoRepository, clienteRepository)

        clienteMuestra = Cliente(
            id=2,
            nombre="Jaime",
            apellido = "Leon",
            fechaNacimiento = LocalDate.of(2002,4,10),
            dni="12345678A",
            email = "jaime@gmail.com",
            numSocio = "AAA112",
            password = "password",
            createdAt = LocalDate.of(2024,5,18),
            updatedAt = LocalDate.of(2024,5,18),
            isDeleted = false
        )
        complementoMuestra = Complemento(
            id="1",
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
        butacacMuestra = Butaca(
            id="A1",
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
        lineaVentaMuestra1 = LineaVenta(
            id= UUID.fromString("000712fb-5531-4f33-a744-0fdb65cd9dcf"),
            producto = butacacMuestra,
            tipoProducto = "Butaca",
            cantidad = 1,
            precio = 5.0,
            createdAt = LocalDate.of(2024,5,18),
            updatedAt = LocalDate.of(2024,5,18)
        )
        lineaVentaMuestra2 = LineaVenta(
            id= UUID.fromString ("111712fb-5531-4f33-a744-0fdb65cd9dcf"),
            producto = complementoMuestra,
            tipoProducto = "Complemento",
            cantidad = 1,
            precio = 3.0,
            createdAt = LocalDate.of(2024,5,18),
            updatedAt = LocalDate.of(2024,5,18)
        )
        lineaVentaMuestra3 = LineaVenta(
            id= UUID.fromString ("333712fb-5531-4f33-a744-0fdb65cd9dcf"),
            producto = complementoMuestra,
            tipoProducto = "Complemento",
            cantidad = 2,
            precio = 3.0,
            createdAt = LocalDate.of(2024,5,18),
            updatedAt = LocalDate.of(2024,5,18)
        )

//        VALUES ('27c712fb-5531-4f33-a744-0fdb65cd9dcf', '37c712fb-5531-4f33-a744-0fdb65cd9dcf', '1', 'Complemento', 1, 3.0, '2024-05-18', '2024-05-18', 0);

    }


    @AfterAll
    fun tearDown(){
        dbManager.clearData()
        dbManager.insertSampleData()
    }

    @Test
    @Order(1)
    fun findAll(){
        val ventas=ventaRepository.findAll(
            clienteMuestra,
            listOf(lineaVentaMuestra1,lineaVentaMuestra2),
            LocalDate.of(2024,5,18))

        assertEquals(1,ventas.size)
    }

    @Test
    @Order(2)
    fun findById() {
        val venta=ventaRepository.findById(
            UUID.fromString("37c712fb-5531-4f33-a744-0fdb65cd9dcf")
        )

        assertEquals("37c712fb-5531-4f33-a744-0fdb65cd9dcf", venta?.id.toString())
        assertEquals(2,venta?.cliente?.id)
        assertEquals(8, venta?.total?.toLong())
        assertEquals(LocalDate.of(2024,5,18), venta?.fechaCompra )

    }
    @Test
    @Order(3)
    fun findByIdNotFound() {
        val venta = ventaRepository.findById(
            UUID.fromString("00c712fb-1111-4f33-a744-0fdb65cd9dcf")
        )

        assertEquals(null, venta)
    }

    @Test
    @Order(4)
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
    @Order(5)
    fun update() {
        val ventaUpdated = ventaRepository.update(
            UUID.fromString("37c712fb-5531-4f33-a744-0fdb65cd9dcf"),
            Venta(
                id = UUID.fromString("37c712fb-5531-4f33-a744-0fdb65cd9dcf"),
                cliente=clienteMuestra,
                listOf(lineaVentaMuestra1,lineaVentaMuestra2,lineaVentaMuestra3),
                LocalDate.of(2023,1,19)
            )
        )

        assertEquals(UUID.fromString("37c712fb-5531-4f33-a744-0fdb65cd9dcf"),ventaUpdated?.id)
        assertEquals(clienteMuestra,ventaUpdated?.cliente)
        assertEquals(lineaVentaMuestra1,ventaUpdated!!.lineas[0])
        assertEquals(lineaVentaMuestra2, ventaUpdated.lineas[1])
        assertEquals(lineaVentaMuestra3, ventaUpdated.lineas[2])
        assertEquals(LocalDate.of(2023,1,19),ventaUpdated.fechaCompra)

    }
    @Test
    @Order(6)
    fun updateNotFound() {
        val ventaUpdated = ventaRepository.update(
            UUID.fromString("000712fb-5531-4f33-a744-0fdb65cd9dcf"),
            Venta(
                id = UUID.fromString("000712fb-5531-4f33-a744-0fdb65cd9dcf"),
                cliente=clienteMuestra,
                listOf(lineaVentaMuestra1,lineaVentaMuestra2,lineaVentaMuestra3),
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
    }

    @Test
    fun validateLineas() {
    }

}