package org.example.dawfilmsinterface.cache

import org.example.dawfilmsinterface.clientes.models.Cliente
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate

class CacheImplTest {
    private lateinit var cache: CacheImpl<Int, Cliente> 

    @BeforeEach
    fun initialize(){
        cache = CacheImpl(5)

    }

    @Test
    fun getAndPut() {
        val cliente = Cliente(
            id = 1,
            nombre = "cliente_nombre",
            apellido = "cliente_apellido",
            fechaNacimiento = LocalDate.of(1990, 1, 1),
            dni = "12345678X",
            email = "cliente_nombre.clientea_apellido@mail.com",
            numSocio = "001",
            password = "password"
        )

        cache.put(1, cliente)
        val result = cache.get(1)

        assertTrue(result.isOk)
        assertEquals(cliente.id, result.value.id)
        assertEquals(cliente.nombre, result.value.nombre)
        assertEquals(cliente.apellido, result.value.apellido)
        assertEquals(cliente.dni, result.value.dni)
        assertEquals(cliente.email, result.value.email)
        assertEquals(cliente.numSocio, result.value.numSocio)
    }

    @Test
    fun getNoEstaEnCache() {
        val result = cache.get(2)
        assertTrue(result.isErr)
        assertEquals("No existe el valor en la cache", result.error.message)
    }

    @Test
    fun removeOk() {
        val cliente = Cliente(
            id = 1,
            nombre = "cliente_nombre",
            apellido = "cliente_apellido",
            fechaNacimiento = LocalDate.of(1990, 1, 1),
            dni = "12345678X",
            email = "cliente_nombre.clientea_apellido@mail.com",
            numSocio = "001",
            password = "password"
        )

        cache.put(1, cliente)
        val resulRemove=cache.remove(1)
        val resulGet=cache.get(1)

        assertTrue(resulRemove.isOk)
        assertEquals(cliente,resulRemove.value)
        assertTrue(resulGet.isErr)
    }

    @Test
    fun removeNoEstaEnCache() {

        val resul=cache.remove(1)

        assertTrue(resul.isErr)
        assertEquals("No existe el valor en la cache",resul.error.message)

    }

    @Test
    fun clear() {
        val cliente = Cliente(
            id = 1,
            nombre = "cliente_nombre",
            apellido = "cliente_apellido",
            fechaNacimiento = LocalDate.of(1990, 1, 1),
            dni = "12345678X",
            email = "cliente_nombre.clientea_apellido@mail.com",
            numSocio = "001",
            password = "password"
        )
        cache.put(1,cliente)
        cache.clear()
        val resul = cache.get(1)

        assertTrue(resul.isErr)
    }

    @Test
    fun putEliminandoUltimoAlLlenarse(){
        val cliente1 = Cliente(
            id = 1,
            nombre = "cliente_nombre_1",
            apellido = "cliente_apellido_1",
            fechaNacimiento = LocalDate.of(1990, 1, 1),
            dni = "12345678X",
            email = "cliente_nombre1.clientea_apellido1@mail.com",
            numSocio = "001",
            password = "password"
        )

        val cliente2 = Cliente(
            id = 2,
            nombre = "cliente_nombre_2",
            apellido = "cliente_apellido_2",
            fechaNacimiento = LocalDate.of(1990, 1, 1),
            dni = "22345678X",
            email = "cliente_nombre2.clientea_apellido2@mail.com",
            numSocio = "002",
            password = "password"
        )

        val cliente3 = Cliente(
            id = 3,
            nombre = "cliente_nombre_3",
            apellido = "cliente_apellido_3",
            fechaNacimiento = LocalDate.of(1990, 1, 1),
            dni = "32345678X",
            email = "cliente_nombre3.clientea_apellido3@mail.com",
            numSocio = "003",
            password = "password"
        )

        val cliente4 = Cliente(
            id = 4,
            nombre = "cliente_nombre_4",
            apellido = "cliente_apellido_4",
            fechaNacimiento = LocalDate.of(1990, 1, 1),
            dni = "42345678X",
            email = "cliente_nombre4.clientea_apellido4@mail.com",
            numSocio = "004",
            password = "password"
        )

        val cliente5 = Cliente(
            id = 5,
            nombre = "cliente_nombre_5",
            apellido = "cliente_apellido_5",
            fechaNacimiento = LocalDate.of(1990, 1, 1),
            dni = "22345678X",
            email = "cliente_nombre5.clientea_apellido5@mail.com",
            numSocio = "005",
            password = "password"
        )
        val cliente6 = Cliente(
            id = 6,
            nombre = "cliente_nombre_6",
            apellido = "cliente_apellido_6",
            fechaNacimiento = LocalDate.of(1990, 1, 1),
            dni = "62345678X",
            email = "cliente_nombre6.clientea_apellido6@mail.com",
            numSocio = "006",
            password = "password"
        )

        cache.put(1,cliente1)
        cache.put(2,cliente2)
        cache.put(3,cliente3)
        cache.put(4,cliente4)
        cache.put(5,cliente5)
        cache.put(4,cliente4) //probar metiendo este que ya existe, no debería eliminar ninguno
        cache.put(6,cliente6) // este ya elimina el primer elemento de la cache antes de meterlo

        assertTrue(cache.get(1).isErr)
        assertTrue(cache.get(2).isOk)
        assertTrue(cache.get(3).isOk)
        assertTrue(cache.get(4).isOk)
        assertTrue(cache.get(5).isOk)
        assertTrue(cache.get(6).isOk)
    }
}