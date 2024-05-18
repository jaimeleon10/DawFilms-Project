package org.example.dawfilmsinterface.cine.controllers

import com.vaadin.open.Open
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.MenuItem
import javafx.stage.Stage
import org.lighthousegames.logging.logging

private val logger = logging()

class AcercaDeController {
    @FXML
    lateinit var exitAcercaDeButton: MenuItem

    @FXML
    lateinit var githubAlbaLink: Button

    @FXML
    lateinit var githubNataliaLink: Button

    @FXML
    lateinit var githubJaviLink: Button

    @FXML
    lateinit var githubGermanLink: Button

    @FXML
    lateinit var githubJaimeLink: Button

    // Utilizar viewModel cuando se implemente
    private lateinit var stage: Stage
    fun setStage(stage: Stage) {
        this.stage = stage
    }

    @FXML
    private fun initialize(){
        exitAcercaDeButton.setOnAction {
            logger.debug { "Cerrando ventana modal" }
            stage.close()
        }
        githubJaviLink.setOnAction {
            val url = "https://github.com/javi97ruiz"
            abrirNavegador(url)
        }
        githubNataliaLink.setOnAction {
            val url = "https://github.com/ngalvez0910"
            abrirNavegador(url)
        }
        githubJaimeLink.setOnAction {
            val url = "https://github.com/jaimeleon10"
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
    }

    private fun abrirNavegador(url: String) {
        logger.debug { "Abriendo navegador en el link: $url" }
        Open.open(url)
    }
}