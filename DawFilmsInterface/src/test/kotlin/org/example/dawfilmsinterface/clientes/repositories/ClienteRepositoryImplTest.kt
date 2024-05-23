package org.example.dawfilmsinterface.clientes.repositories

import database.DatabaseQueries
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.database.SqlDeLightManager
import org.junit.jupiter.api.*
import org.lighthousegames.logging.logging
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.kotlin.whenever
import org.mockito.quality.Strictness
import java.time.LocalDate

private val logger = logging()

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ClienteRepositoryImplTest {
    @Mock
    lateinit var config: Config

    private lateinit var dbManager: SqlDeLightManager
    private lateinit var databaseQueries: DatabaseQueries
    private lateinit var clienteRepository: ClienteRepositoryImpl

    @BeforeEach
    fun setUp() {
        whenever(config.dataBaseUrl).thenReturn("jdbc:sqlite::memory:")
        whenever(config.dataBaseInMemory).thenReturn(true)
        whenever(config.databaseInitData).thenReturn(true)
        whenever(config.databaseRemoveData).thenReturn(true)

        dbManager = SqlDeLightManager(config)
        databaseQueries = dbManager.databaseQueries
        clienteRepository = ClienteRepositoryImpl(dbManager)

        dbManager.initialize()
    }

    @Test
    fun findAll() {
        val clientes = clienteRepository.findAll()

        assertEquals(1, clientes.size)
    }

    @Test
    fun findById() {
        val cliente = clienteRepository.findById(1)

        assertEquals(1,cliente?.id)
        assertEquals("Jaime", cliente?.nombre)
        assertEquals("Leon", cliente?.apellido)
        assertEquals(LocalDate.parse("2000-05-10"), cliente?.fechaNacimiento)
        assertEquals("12345678A", cliente?.dni)
        assertEquals("jleon@gmail.com", cliente?.email)
        assertEquals("AAA111", cliente?.numSocio)
        assertEquals("password", cliente?.password)
    }

    @Test
    fun findByIdNotFound() {
        val cliente = clienteRepository.findById(5)

        assertEquals(null, cliente)
    }

    @Test
    fun save() {
        val cliente = clienteRepository.save(
            Cliente(
                -1,
                "test",
                "test",
                LocalDate.parse("2024-05-05"),
                "12345678G",
                "test@test.com",
                "ABC123",
                "1234"
            )
        )

        assertEquals(2,cliente.id)
        assertEquals("test", cliente.nombre)
        assertEquals("test", cliente.apellido)
        assertEquals(LocalDate.parse("2024-05-05"), cliente.fechaNacimiento)
        assertEquals("12345678G", cliente.dni)
        assertEquals("test@test.com", cliente.email)
        assertEquals("ABC123", cliente.numSocio)
        assertEquals("1234", cliente.password)
    }

    @Test
    fun update() {
        val cliente = clienteRepository.update(1, Cliente(
            nombre = "test-update",
            apellido = "test-update",
            fechaNacimiento = LocalDate.parse("2024-06-06"),
            dni = "12345678Z",
            email = "test-update@test-update.com",
            numSocio = "ZZZ999",
            password = "0000")
        )

        assertEquals(1,cliente?.id)
        assertEquals("test-update", cliente?.nombre)
        assertEquals("test-update", cliente?.apellido)
        assertEquals(LocalDate.parse("2024-06-06"), cliente?.fechaNacimiento)
        assertEquals("12345678Z", cliente?.dni)
        assertEquals("test-update@test-update.com", cliente?.email)
        assertEquals("ZZZ999", cliente?.numSocio)
        assertEquals("0000", cliente?.password)
    }

    @Test
    fun updateNotFound() {
        val cliente = clienteRepository.update(
            5,
            Cliente(
                -1,
                "test",
                "test",
                LocalDate.parse("2024-05-05"),
                "12345678G",
                "test@test.com",
                "ABC123",
                "1234"
            )
        )

        assertEquals(null, cliente)
    }

    @Test
    fun delete() {
        val cliente = clienteRepository.delete(1)

        assertEquals(1, cliente?.id)
    }

    @Test
    fun deleteNotFound() {
        val cliente = clienteRepository.delete(5)

        assertEquals(null, cliente)
    }
}