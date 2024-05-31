package org.example.dawfilmsinterface.cine.controllers

import com.vaadin.open.Open
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Hyperlink
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging
import kotlin.concurrent.thread

private val logger = logging()

/**
 * Clase controller para la pantalla de carga durante el inicio de la aplicación.
 * Gestiona las acciones y eventos relacionados con la visualización de la barra de progreso y los enlaces de GitHub de los desarrolladores.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property progressBar Barra de progreso que indica el estado de carga.
 * @property cargandoLabel Etiqueta que muestra el estado de carga.
 * @property githubGermanLink Enlace a GitHub del desarrollador German Fernández.
 * @property githubAlbaLink Enlace a GitHub de la desarrolladora Alba García.
 * @property githubJavierLink Enlace a GitHub del desarrollador Javier Ruiz.
 * @property githubNataliaLink Enlace a GitHub de la desarrolladora Natalia González.
 * @property githubJaimeLink Enlace a GitHub del desarrollador Jaime León.
 */
class PantallaDeCargaController {
    @FXML
    lateinit var progressBar: ProgressBar

    @FXML
    lateinit var cargandoLabel: Label

    @FXML
    lateinit var githubGermanLink: Hyperlink

    @FXML
    lateinit var githubAlbaLink: Hyperlink

    @FXML
    lateinit var githubJavierLink: Hyperlink

    @FXML
    lateinit var githubNataliaLink: Hyperlink

    @FXML
    lateinit var githubJaimeLink: Hyperlink

    /**
     * Función que inicializa la pantalla de carga.
     * Asigna las acciones a los enlaces de GitHub de los desarrolladores y maneja la barra de progreso durante la carga.
     */
    @FXML
    private fun initialize() {
        githubJaimeLink.setOnAction {
            val url = "https://github.com/jaimeleon10"
            abrirNavegador(url)
        }
        githubJavierLink.setOnAction {
            val url = "https://github.com/javi97ruiz"
            abrirNavegador(url)
        }
        githubNataliaLink.setOnAction {
            val url = "https://github.com/ngalvez0910"
            abrirNavegador(url)
        }
        githubGermanLink.setOnAction {
            val url = "https://github.com/germangfc"
            abrirNavegador(url)
        }
        githubAlbaLink.setOnAction {
            val url = "https://github.com/Alba448"
            abrirNavegador(url)
        }

        thread { cargaProgressBar() }
    }

    /**
     * Abre el navegador web en el enlace proporcionado.
     * @param url URL del enlace a abrir en el navegador.
     */
    private fun abrirNavegador(url: String) {
        logger.debug { "Abriendo navegador en el link: $url" }
        Open.open(url)
    }

    /**
     * Simula el progreso de carga en la barra de progreso.
     */
    private fun cargaProgressBar() {
        progressBar.progress = 0.0
        for (i in 0..100) {
            Thread.sleep(40)
            Platform.runLater {
                progressBar.progress = i.toDouble() / 100.0
                if (i % 7 == 0) updateCargandoLabel(i)
                if (progressBar.progress == 1.0) {
                    logger.debug { "Barra de carga al 100%, cambiando ventana" }
                    RoutesManager.changeScene(view = RoutesManager.View.LOGIN)
                }
            }
        }
    }

    /**
     * Actualiza el texto de la etiqueta de estado de carga.
     * @param i Número para controlar el cambio de estado de carga.
     */
    private fun updateCargandoLabel(i: Int) {
        when {
            i % 3 == 0 -> cargandoLabel.text = "Cargando."
            i % 3 == 1 -> cargandoLabel.text = "Cargando.."
            i % 3 == 2 -> cargandoLabel.text = "Cargando..."
        }
    }


}