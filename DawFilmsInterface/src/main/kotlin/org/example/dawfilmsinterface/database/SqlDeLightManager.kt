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

    fun initQueries(): DatabaseQueries {
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
            databaseQueries.insertButaca("A1","Butaca", "sinImagen.png", 5.0, 0, 0, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("A2","Butaca", "sinImagen.png", 5.0, 0, 1, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("A3","Butaca", "sinImagen.png", 5.0, 0, 2, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("A4","Butaca", "sinImagen.png", 5.0, 0, 3, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("A5","Butaca", "sinImagen.png", 5.0, 0, 4, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("A6","Butaca", "sinImagen.png", 5.0, 0, 5, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("A7","Butaca", "sinImagen.png", 5.0, 0, 6, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("B1","Butaca", "sinImagen.png", 5.0, 1, 0, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("B2","Butaca", "sinImagen.png", 5.0, 1, 1, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("B3","Butaca", "sinImagen.png", 5.0, 1, 2, TipoButaca.VIP.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("B4","Butaca", "sinImagen.png", 5.0, 1, 3, TipoButaca.VIP.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("B5","Butaca", "sinImagen.png", 5.0, 1, 4, TipoButaca.VIP.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("B6","Butaca", "sinImagen.png", 5.0, 1, 5, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("B7","Butaca", "sinImagen.png", 5.0, 1, 6, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("C1","Butaca", "sinImagen.png", 5.0, 2, 0, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("C2","Butaca", "sinImagen.png", 5.0, 2, 1, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("C3","Butaca", "sinImagen.png", 5.0, 2, 2, TipoButaca.VIP.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("C4","Butaca", "sinImagen.png", 5.0, 2, 3, TipoButaca.VIP.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("C5","Butaca", "sinImagen.png", 5.0, 2, 4, TipoButaca.VIP.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("C6","Butaca", "sinImagen.png", 5.0, 2, 5, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("C7","Butaca", "sinImagen.png", 5.0, 2, 6, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("D1","Butaca", "sinImagen.png", 5.0, 3, 0, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("D2","Butaca", "sinImagen.png", 5.0, 3, 1, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("D3","Butaca", "sinImagen.png", 5.0, 3, 2, TipoButaca.VIP.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("D4","Butaca", "sinImagen.png", 5.0, 3, 3, TipoButaca.VIP.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("D5","Butaca", "sinImagen.png", 5.0, 3, 4, TipoButaca.VIP.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("D6","Butaca", "sinImagen.png", 5.0, 3, 5, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("D7","Butaca", "sinImagen.png", 5.0, 3, 6, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("E1","Butaca", "sinImagen.png", 5.0, 4, 0, TipoButaca.NORMAL.toString(), EstadoButaca.FUERASERVICIO.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("E2","Butaca", "sinImagen.png", 5.0, 4, 1, TipoButaca.NORMAL.toString(), EstadoButaca.FUERASERVICIO.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("E3","Butaca", "sinImagen.png", 5.0, 4, 2, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("E4","Butaca", "sinImagen.png", 5.0, 4, 3, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("E5","Butaca", "sinImagen.png", 5.0, 4, 4, TipoButaca.NORMAL.toString(), EstadoButaca.ACTIVA.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("E6","Butaca", "sinImagen.png", 5.0, 4, 5, TipoButaca.NORMAL.toString(), EstadoButaca.MANTENIMIENTO.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertButaca("E7","Butaca", "sinImagen.png", 5.0, 4, 6, TipoButaca.NORMAL.toString(), EstadoButaca.MANTENIMIENTO.toString(), OcupacionButaca.LIBRE.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)

            databaseQueries.insertComplemento("1", "Complemento", "1717094754269.png", 2.0, "Agua", 20, CategoriaComplemento.BEBIDA.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertComplemento("2", "Complemento", "1717094798369.png", 3.0, "Refresco", 20, CategoriaComplemento.BEBIDA.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertComplemento("3", "Complemento", "1717094788589.png", 3.0, "Palomitas", 20, CategoriaComplemento.COMIDA.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertComplemento("4", "Complemento", "1717094776442.png", 2.0, "Pistachos", 20, CategoriaComplemento.COMIDA.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertComplemento("5", "Complemento", "1717094765516.png", 2.5, "Patatas", 20, CategoriaComplemento.COMIDA.toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)

            databaseQueries.insertCliente("User", "User", "2000-05-10", "12345678A", "user@user.com", "AAA111", "VXNlcjE=", LocalDate.now().toString(), LocalDate.now().toString(), 0)

            databaseQueries.insertLineaVenta("27c712fb-5531-4f33-a744-0fdb65cd9dcf", "37c712fb-5531-4f33-a744-0fdb65cd9dcf", "A1", "Butaca", 1, 5.0, LocalDate.now().toString(), LocalDate.now().toString(), 0)
            databaseQueries.insertLineaVenta("25c712fb-5531-4f33-a744-0fdb65cd9dcf", "37c712fb-5531-4f33-a744-0fdb65cd9dcf", "1", "Complemento", 1, 3.0, LocalDate.now().toString(), LocalDate.now().toString(), 0)

            databaseQueries.insertVenta("37c712fb-5531-4f33-a744-0fdb65cd9dcf", 1, 7.0, LocalDate.now().toString(), LocalDate.now().toString(), LocalDate.now().toString(), 0)
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