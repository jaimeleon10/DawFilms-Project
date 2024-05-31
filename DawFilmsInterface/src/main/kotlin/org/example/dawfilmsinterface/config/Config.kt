package org.example.dawfilmsinterface.config

import org.lighthousegames.logging.logging
import java.io.File
import java.io.InputStream
import java.util.*

private val logger = logging()

private const val CONFIG_FILE_NAME = "application.properties"

/**
 * Clase Config donde cargaremos toda la configuración
 * @property databaseUrl url de la base de datos
 * @property databaseInit true inicia base de datos
 * @property databaseRemoveData true borra los datos de la base de datos
 * @property cacheSize tamaño de la cache
 * @author Jaime León, Alba García, Natalia González, Javier Ruiz, Germán Fernández
 * @since 1.0.0
 */
class Config {

    private val actualDirectory = System.getProperty("user.dir")

    val recaudacionDirectory by lazy {
        val path = readProperty("app.recaudacion") ?: "FicherosRecaudacion"
        "$actualDirectory${File.separator}$path"
    }

    val imagesDirectory by lazy {
        val path = readProperty("app.images") ?: "imagenes"
        "$actualDirectory${File.separator}$path/"
    }

    val dataBaseUrl: String by lazy {
        readProperty("app.database.url") ?: "jdbc:sqlite:defaultdawfilms.db"
    }

    val databaseInitData: Boolean by lazy {
        readProperty("app.database.init")?.toBoolean() ?: false
    }

    val databaseRemoveData: Boolean by lazy {
        readProperty("app.database.removedata")?.toBoolean() ?: false
    }

    val dataBaseInMemory: Boolean by lazy {
        readProperty("app.database.inMemory")?.toBoolean() ?: false
    }

    val cacheSize: Int by lazy {
        readProperty("app.cache.size")?.toInt() ?: 10
    }

    init {
        logger.debug { "Cargando configuración del cine" }
    }

    /**
     * Lee una propiedad específica desde un archivo de configuración.
     * Esta función lee una propiedad específica del archivo de configuración definido y devuelve su valor.
     * Si la propiedad no se encuentra o no se puede leer, se maneja el error y se devuelve null.
     * @param propiedad Nombre de la propiedad que se desea leer.
     * @return El valor de la propiedad si se encuentra en el archivo de configuración, o null si hay algún error.
     * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    private fun readProperty(propiedad: String): String? {
        return try {
            logger.debug { "Leyendo propiedad: $propiedad" }
            val properties = Properties()
            val inputStream: InputStream = ClassLoader.getSystemResourceAsStream(CONFIG_FILE_NAME)
                ?: throw Exception("No se puede leer el fichero de configuración $CONFIG_FILE_NAME")
            properties.load(inputStream)
            properties.getProperty(propiedad)
        } catch (e: Exception) {
            logger.error { "Error al leer la propiedad $propiedad: ${e.message}" }
            null
        }
    }
}