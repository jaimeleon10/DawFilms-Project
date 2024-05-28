package org.example.dawfilmsinterface.clientes.services

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import database.DatabaseQueries
import org.example.dawfilmsinterface.cache.errors.CacheError
import org.example.dawfilmsinterface.clientes.cache.ClienteCache
import org.example.dawfilmsinterface.clientes.errors.ClienteError
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.repositories.ClienteRepository
import org.example.dawfilmsinterface.clientes.repositories.ClienteRepositoryImpl
import org.example.dawfilmsinterface.clientes.validators.ClienteValidator
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.database.SqlDeLightManager
import org.example.dawfilmsinterface.productos.errors.ProductoError
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

val logger = logging()
@ExtendWith(MockitoExtension::class)
class ClienteServiceImplTest {

    @Mock
    lateinit var config: Config

    @Mock
    private lateinit var mockRepo: ClienteRepositoryImpl

    @Mock
    private lateinit var mockCache: ClienteCache

    @Mock
    private lateinit var mockClienteValidator: ClienteValidator

    private lateinit var dbManager: SqlDeLightManager
    private lateinit var databaseQueries: DatabaseQueries

    @InjectMocks
    private lateinit var service: ClienteServiceImpl

    @BeforeEach
    fun setUp() {
        //whenever(config.dataBaseUrl).thenReturn("jdbc:sqlite::memory:")
        whenever(config.dataBaseInMemory).thenReturn(true)
        whenever(config.databaseInitData).thenReturn(true)
        //whenever(config.databaseRemoveData).thenReturn(true)

        dbManager = SqlDeLightManager(config)
        databaseQueries = dbManager.databaseQueries
        //mockRepo = ClienteRepositoryImpl(dbManager)

        dbManager.initialize()
    }

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
                password = "password",
                createdAt = LocalDate.now(),
                updatedAt = LocalDate.now(),
                isDeleted = false
            )
        )

        whenever(mockRepo.findAll()).thenReturn(mockCliente)

        val clientes = service.getAll()

        assertAll(
            { assertEquals(1, clientes.value.size) },
            { assertEquals ("Pepe", clientes.value[0].nombre)},
            { assertEquals( mockCliente, clientes.value) }
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
            password = "password",
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
    fun getByIdIsNotInCache() {
        val mockCliente = Cliente(
            id = 1,
            nombre = "User",
            apellido = "User",
            fechaNacimiento = LocalDate.of(2000,5,10),
            dni = "12345678A",
            email = "user@user.com",
            numSocio = "AAA111",
            password = "user",
            createdAt = LocalDate.now(),
            updatedAt = LocalDate.now(),
            isDeleted = false
        )
        val message = "No existe el valor en la cache"

        logger.debug { mockCliente }

        whenever(mockCache.get(1)).thenReturn(Err(CacheError(message)))
        whenever(mockRepo.findById(1)).thenReturn(mockCliente)
        val cliente = service.getById(1)

        logger.debug { "Este es el resultado de cliente: $cliente" }

        assertAll(
            { assertTrue(cliente.isOk) },
            { assertEquals (cliente.value, mockCliente) }
        )

        verify(mockCache, times (1)).get(1)
        verify(mockRepo, times (1)).findById(1)
        verify(mockCache, times(1)).put(1, mockCliente)
    }

    @Test
    fun getByIdIsNotFound() {
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
        val message = "No existe el valor en la cache"

        whenever(mockCache.get(-2L)).thenReturn(Err(CacheError(message)))
        whenever(mockRepo.findById(-2L)).thenReturn(null)
        val clientes = service.getById(-2L)


        assertAll(
            { assertTrue(clientes.isErr) },
            { assertFalse (clientes.value == mockCliente) }
        )

        verify (mockCache, times (1)).get(-2L)
        verify(mockRepo, times (1)).findById(-2L)
        verify(mockCache, times(0)).put(-2L, mockCliente)
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
            password = "password",
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
    fun saveNotOk() {
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

        val message = "Cliente no validado correctamente"

        whenever(mockClienteValidator.validate(mockCliente)).thenReturn(Err(ClienteError.ClienteValidationError(message)))

        val clientes = service.save(mockCliente)

        assertAll(
            { assertTrue(clientes.isErr) },
            { assertFalse (clientes.value == mockCliente) }
        )
        verify (mockClienteValidator, times(1)).validate(mockCliente)
        verify (mockRepo, times(0)).save(mockCliente)
        verify (mockCache, times(0)).put(-1L, mockCliente)
    }

    @Test
    fun update() {
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

        whenever(mockClienteValidator.validate(mockCliente)).thenReturn(Ok(mockCliente))
        whenever (mockRepo.update(-1L, mockCliente)).thenReturn(mockCliente)
        whenever(mockCache.put(-1L, mockCliente)).thenReturn(Ok(mockCliente))


        val clientes = service.update(-1L, mockCliente)

        assertAll(
            { assertTrue(clientes.isOk) },
            { assertEquals (clientes.value, mockCliente) }
        )

        verify (mockClienteValidator, times(1)).validate(mockCliente)
        verify (mockRepo, times(1)).update(-1L, mockCliente)
        verify (mockCache, times(1)).put(-1L, mockCliente)
    }

    @Test
    fun updateWrongId() {
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
        val id = -1L
        val message = "El cliente no se ha encontrado"

        whenever(mockClienteValidator.validate(mockCliente)).thenReturn(Ok (mockCliente))
        whenever (mockRepo.update(-1L, mockCliente)).thenReturn(null)



        val clientes = service.update(-1L, mockCliente)

        assertAll(
            { assertFalse(clientes.isOk) },
            { assertTrue(clientes.isErr) },
            { assertTrue(clientes.error is ClienteError.ClienteNoActualizado) },
            { assertEquals (clientes.error.message, "Cliente no actualizado con id: $id") }

        )

        verify (mockClienteValidator, times(1)).validate(mockCliente)
        verify (mockRepo, times(1)).update(-1L, mockCliente)
        verify (mockCache, times(0)).put(-1L, mockCliente)
    }

    @Test
    fun delete() {
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


        whenever (mockRepo.delete(-1L)).thenReturn(mockCliente)
        whenever(mockCache.remove(-1L)).thenReturn(Ok(mockCliente))

        val clientes = service.delete(-1L)

        assertAll(
            { assertTrue(clientes.isOk) },
            { assertEquals (clientes.value, mockCliente) }
        )

        verify (mockRepo, times(1)).delete(-1L)
        verify (mockCache, times(1)).remove(-1L)


    }

    @Test
    fun deleteWrongId() {
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

        val message = "El cliente no se ha encontrado"

        whenever (mockRepo.delete(-2L)).thenReturn(null)

        val clientes = service.delete(-2L)

        assertAll(
            { assertTrue(clientes.isErr) },
            { assertFalse (clientes.value == mockCliente) }
        )

        verify (mockRepo, times(1)).delete(-2L)


    }
}