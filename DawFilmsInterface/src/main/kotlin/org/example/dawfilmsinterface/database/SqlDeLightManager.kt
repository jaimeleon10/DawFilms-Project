package org.example.dawfilmsinterface.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import database.DatabaseQueries
import org.example.database.AppDatabase
import org.example.dawfilmsinterface.config.Config
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.productos.models.complementos.CategoriaComplemento
import org.lighthousegames.logging.logging
import java.time.LocalDate

private val logger = logging()

class SqlDeLightManager(
    private val config: Config
) {
    val databaseQueries: DatabaseQueries by lazy { initQueries() }

    init {
        initialize()
    }

    private fun initQueries(): DatabaseQueries {
        return if (config.dataBaseInMemory) {
            logger.debug { "SqlDeLightClient - InMemory" }
            JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        } else {
            logger.debug { "SqlDeLightClient - File: ${config.dataBaseUrl}" }
            JdbcSqliteDriver(config.dataBaseUrl)
        }.let { driver ->
            logger.debug { "Creando Tablas (si es necesario)" }
            AppDatabase.Schema.create(driver)
            AppDatabase(driver)
        }.databaseQueries
    }

    fun initialize() {
        if (config.databaseInitData) {
            removeAllData()
            initDataExamples()
        }
    }

    private fun initDataExamples() {
        databaseQueries.transaction {
            databaseQueries.insertButaca("A1","Butaca", "futura_imagen.png", 5.0, 0, 0, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("B2","Butaca", "futura_imagen.png", 8.0, 0, 0, TipoButaca.VIP.toString(), EstadoButaca.MANTENIMIENTO.toString(), OcupacionButaca.OCUPADA.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("C3","Butaca", "futura_imagen.png", 5.0, 0, 0, TipoButaca.NORMAL.toString(), EstadoButaca.FUERASERVICIO.toString(), OcupacionButaca.ENRESERVA.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertComplemento("1", "Complemento", "futura_imagen.png", 3.0, "Palomitas", 20, CategoriaComplemento.COMIDA.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertCliente("Jaime", "Leon", "2000-05-10", "12345678A", "jleon@gmail.com", "AAA111", "password", LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertLineaVenta("27c712fb-5531-4f33-a744-0fdb65cd9dcf", "37c712fb-5531-4f33-a744-0fdb65cd9dcf", "A1", "Butaca", 1, 5.0, LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertLineaVenta("25c712fb-5531-4f33-a744-0fdb65cd9dcf", "37c712fb-5531-4f33-a744-0fdb65cd9dcf", "1", "Complemento", 1, 3.0, LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertVenta("37c712fb-5531-4f33-a744-0fdb65cd9dcf", 1, 8.0, "2024-05-20", LocalDate.now().toString(), LocalDate.now().toString(), 0)
        }
    }

    private fun removeAllData() {
        databaseQueries.transaction {
            databaseQueries.deleteAllProductos()
            databaseQueries.deleteAllClientes()
            databaseQueries.deleteAllLineas()
            databaseQueries.deleteAllVentas()
        }
    }
}