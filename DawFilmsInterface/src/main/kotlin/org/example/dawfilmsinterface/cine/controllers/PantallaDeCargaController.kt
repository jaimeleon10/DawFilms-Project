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

    private fun abrirNavegador(url: String) {
        logger.debug { "Abriendo navegador en el link: $url" }
        Open.open(url)
    }

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

    private fun updateCargandoLabel(i: Int) {
        when {
            i % 3 == 0 -> cargandoLabel.text = "Cargando."
            i % 3 == 1 -> cargandoLabel.text = "Cargando.."
            i % 3 == 2 -> cargandoLabel.text = "Cargando..."
        }
    }


}