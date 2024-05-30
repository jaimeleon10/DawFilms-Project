package org.example.dawfilmsinterface.ventas.services

import com.github.michaelbull.result.Ok
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import org.example.dawfilmsinterface.ventas.repositories.VentaRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)

class VentaServiceImplTest {

    @Mock
    lateinit var repoVentas: VentaRepository

    @InjectMocks
    lateinit var serviceVentas: VentaServiceImpl

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