package org.example.dawfilmsinterface.cine.controllers

import com.vaadin.open.Open
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.MenuItem
import javafx.stage.Stage
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Clase controller para la ventana "Acerca de" a través de la IU.
 * Gestiona las acciones y eventos relacionados con la visualización de información sobre los desarrolladores en la aplicación.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property exitAcercaDeButton Botón de menú para cerrar la ventana modal de Acerca de.
 * @property githubAlbaLink Botón para abrir el perfil de GitHub de Alba García en el navegador.
 * @property githubNataliaLink Botón para abrir el perfil de GitHub de Natalia González en el navegador.
 * @property githubJaviLink Botón para abrir el perfil de GitHub de Javier Ruiz en el navegador.
 * @property githubGermanLink Botón para abrir el perfil de GitHub de German Fernández en el navegador.
 * @property githubJaimeLink Botón para abrir el perfil de GitHub de Jaime León en el navegador.
 */
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

    /**
     * Establece la instancia de la ventana para controlar el cierre.
     * @param stage La instancia de la ventana actual.
     */
    // Utilizar viewModel cuando se implemente
    private lateinit var stage: Stage
    fun setStage(stage: Stage) {
        this.stage = stage
    }

    /**
     * Función que inicializa la vista de "Acerca de".
     * Asigna las acciones a los botones y elementos de menú.
     */
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

    /**
     * Abre el navegador web en el enlace proporcionado.
     * @param url La URL a la que se va a dirigir el navegador.
     */
    private fun abrirNavegador(url: String) {
        logger.debug { "Abriendo navegador en el link: $url" }
        Open.open(url)
    }
}