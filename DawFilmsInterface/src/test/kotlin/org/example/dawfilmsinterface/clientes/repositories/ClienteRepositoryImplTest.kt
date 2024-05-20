package org.example.dawfilmsinterface.clientes.repositories

import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.database.SqlDeLightManager
import org.junit.jupiter.api.*
import org.lighthousegames.logging.logging
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

private val logger = logging()

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClienteRepositoryImplTest {
    private lateinit var dbManager: SqlDeLightManager
    private lateinit var clienteRepository: ClienteRepositoryImpl

    @BeforeAll
    fun setUpAll() {
        logger.debug { "Iniciando tests..." }
        dbManager = SqlDeLightManager(Config())
        clienteRepository = ClienteRepositoryImpl(dbManager)
    }

    @AfterAll
    fun tearDown() {
        dbManager.clearData()
        dbManager.insertSampleData()
    }

    @Test
    @Order(1)
    fun findAll() {
        val clientes = clienteRepository.findAll()

        assertEquals(2, clientes.size)
    }

    @Test
    @Order(2)
    fun findById() {
        val cliente = clienteRepository.findById(1)

        assertEquals(1,cliente?.id)
        assertEquals("admin", cliente?.nombre)
        assertEquals("admin", cliente?.apellido)
        assertEquals(LocalDate.parse("2024-01-01"), cliente?.fechaNacimiento)
        assertEquals("11111111A", cliente?.dni)
        assertEquals("admin@admin.com", cliente?.email)
        assertEquals("AAA111", cliente?.numSocio)
        assertEquals("admin", cliente?.password)
    }

    @Test
    @Order(3)
    fun findByIdNotFound() {
        val cliente = clienteRepository.findById(5)

        assertEquals(null, cliente)
    }

    @Test
    @Order(4)
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

        assertEquals(3,cliente.id)
        assertEquals("test", cliente.nombre)
        assertEquals("test", cliente.apellido)
        assertEquals(LocalDate.parse("2024-05-05"), cliente.fechaNacimiento)
        assertEquals("12345678G", cliente.dni)
        assertEquals("test@test.com", cliente.email)
        assertEquals("ABC123", cliente.numSocio)
        assertEquals("1234", cliente.password)
    }

    @Test
    @Order(5)
    fun update() {
        val cliente = clienteRepository.update(2, Cliente(
            nombre = "test-update",
            apellido = "test-update",
            fechaNacimiento = LocalDate.parse("2024-06-06"),
            dni = "12345678Z",
            email = "test-update@test-update.com",
            numSocio = "ZZZ999",
            password = "0000")
        )

        assertEquals(2,cliente?.id)
        assertEquals("test-update", cliente?.nombre)
        assertEquals("test-update", cliente?.apellido)
        assertEquals(LocalDate.parse("2024-06-06"), cliente?.fechaNacimiento)
        assertEquals("12345678Z", cliente?.dni)
        assertEquals("test-update@test-update.com", cliente?.email)
        assertEquals("ZZZ999", cliente?.numSocio)
        assertEquals("0000", cliente?.password)
    }

    @Test
    @Order(6)
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
    @Order(7)
    fun delete() {
        val cliente = clienteRepository.delete(1)

        assertEquals(1, cliente?.id)
    }

    @Test
    @Order(8)
    fun deleteNotFound() {
        val cliente = clienteRepository.delete(5)

        assertEquals(null, cliente)
    }
}