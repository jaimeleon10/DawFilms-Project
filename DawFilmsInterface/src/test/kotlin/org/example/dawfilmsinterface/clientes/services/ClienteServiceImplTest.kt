package org.example.dawfilmsinterface.clientes.services

import com.github.michaelbull.result.Ok
import org.example.dawfilmsinterface.clientes.cache.ClienteCache
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.repositories.ClienteRepository
import org.example.dawfilmsinterface.clientes.validators.ClienteValidator
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


@ExtendWith(MockitoExtension::class)
class ClienteServiceImplTest {


    @Mock
    private lateinit var mockRepo: ClienteRepository

    @Mock
    private lateinit var mockCache: ClienteCache

    @Mock
    private lateinit var mockClienteValidator: ClienteValidator

    @InjectMocks
    private lateinit var service: ClienteServiceImpl
    @Test
    fun getAll() {
        val mockCliente = listOf(
            Cliente(
                id = -1L,
                nombre = "Pepe",
                apellido = "",
                fechaNacimiento = LocalDate.of(1997, 12, 31),
                dni = "12345678Z",
                email = "ejemplo@si.com",
                numSocio = "123",
                createdAt = LocalDate.now(),
                updatedAt = LocalDate.now(),
                isDeleted = false
            )
        )

        whenever(mockRepo.findAll()).thenReturn(mockCliente)

        val clientes = service.getAll()

        assertAll(
            { assertEquals(1, clientes.value.size) },
            { assertEquals ("Pepe", clientes.value[0].nombre)}
        )

        verify(mockRepo, times(1)).findAll()
    }

    @Test
    fun getById() {
        val mockCliente = Cliente(
            id = -1L,
            nombre = "Pepe",
            apellido = "",
            fechaNacimiento = LocalDate.of(1997, 12, 31),
            dni = "12345678Z",
            email = "ejemplo@si.com",
            numSocio = "123",
            createdAt = LocalDate.now(),
            updatedAt = LocalDate.now(),
            isDeleted = false
        )

        whenever(mockCache.get(-1L)).thenReturn(Ok(mockCliente) )


        val clientes = service.getById(-1L)


        assertAll(
            { assertTrue(clientes.isOk) },
            { assertEquals (clientes.value, mockCliente) }
        )

        verify (mockCache, times (1)).get(-1L)
        verify(mockRepo, times (0)).findById(-1L)
        verify(mockCache, times(0)).put(-1L, mockCliente)
    }

    @Test
    fun save() {
        val mockCliente = Cliente(
            id = -1L,
            nombre = "Pepe",
            apellido = "",
            fechaNacimiento = LocalDate.of(1997, 12, 31),
            dni = "12345678Z",
            email = "ejemplo@si.com",
            numSocio = "123",
            createdAt = LocalDate.now(),
            updatedAt = LocalDate.now(),
            isDeleted = false
        )

        whenever(mockClienteValidator.validate(mockCliente)).thenReturn(Ok(mockCliente))
        whenever(mockRepo.save(mockCliente)).thenReturn(mockCliente)
        whenever(mockCache.put(-1L, mockCliente)).thenReturn(Ok(mockCliente))

        val clientes = service.save(mockCliente)

        assertAll(
            { assertTrue(clientes.isOk) },
            { assertEquals (clientes.value, mockCliente) }
        )
        verify (mockClienteValidator, times(1)).validate(mockCliente)
        verify (mockRepo, times(1)).save(mockCliente)
        verify (mockCache, times(1)).put(-1L, mockCliente)
    }

    @Test
    fun update() {
    }

    @Test
    fun delete() {
    }
}