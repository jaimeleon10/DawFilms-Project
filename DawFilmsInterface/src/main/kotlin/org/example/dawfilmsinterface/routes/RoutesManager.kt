package org.example.dawfilmsinterface.routes

import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.Stage
import javafx.stage.WindowEvent
import org.example.dawfilmsinterface.main
import org.lighthousegames.logging.logging
import java.io.InputStream
import java.net.URL
import java.util.*

private val logger = logging()

object RoutesManager {
    private lateinit var mainStage: Stage
    private lateinit var _activeStage: Stage
    val activeStage: Stage
        get() = _activeStage
    lateinit var app: Application

    enum class View(val fxml: String) {
        PANTALLA_DE_CARGA("views/pantallaDeCarga-view.fxml"),
        MENU_CLIENTE("views/menuCineCliente-view.fxml"),
        LOGIN("views/login-view.fxml")
    }

    init {
        logger.debug { "Inicializando RoutesManager" }
        Locale.setDefault(Locale.forLanguageTag("es-ES"))
    }

    fun initMainStage(stage: Stage) {
        logger.debug { "Inicializando ventana ${stage.title}" }

        val fxmlLoader = FXMLLoader(getResource(View.PANTALLA_DE_CARGA.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 920.0, 680.0)

        stage.apply {
            stage.title = "DawFilms"
            stage.isResizable = false
            stage.icons.add(Image(getResourceAsStream("icons/iconoApp.png")))
            stage.setOnCloseRequest { onAppExit(event = it) }
            stage.scene = scene
        }.show().also {
            mainStage = stage
            _activeStage = stage
        }
    }

    fun changeScene(
        myStage: Stage = activeStage,
        view: View,
        width: Double = 920.0,
        height: Double = 680.0,
    ) {
        logger.debug { "Cambiando de escena a ${view.fxml}" }
        val parentRoot = FXMLLoader.load<Pane>(this.getResource(view.fxml))
        val scene = Scene(parentRoot, width, height)
        myStage.scene = scene
    }

    private fun getResource(resource: String): URL {
        return app::class.java.getResource(resource)
            ?: throw RuntimeException("No se ha encontrado el recurso: $resource")
    }

    private fun getResourceAsStream(resource: String): InputStream {
        return app::class.java.getResourceAsStream(resource)
            ?: throw RuntimeException("No se ha encontrado el recurso como stream: $resource")
    }

    private fun onAppExit(
        title: String = "Saliendo de DawFilms...",
        headerText: String = "¿Estás seguro de que quieres salir de DawFilms?",
        contentText: String = "Si sales, se cerrará la aplicación y perderás todos los datos no guardados",
        event: WindowEvent? = null
    ) {
        logger.debug { "Cerrando la aplicación" }
        Alert(Alert.AlertType.CONFIRMATION).apply {
            this.title = title
            this.headerText = headerText
            this.contentText = contentText
        }.showAndWait().ifPresent { opcion ->
            if (opcion == ButtonType.OK) {
                Platform.exit()
            } else {
                event?.consume()
            }
        }
    }
}