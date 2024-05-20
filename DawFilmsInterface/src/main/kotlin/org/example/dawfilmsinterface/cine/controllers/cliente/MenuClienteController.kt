package org.example.dawfilmsinterface.cine.controllers.cliente

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class MenuClienteController {
    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var complementsListButton: Button

    @FXML
    lateinit var exitMenuButton: MenuItem

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var exitButton: Button

    @FXML
    lateinit var showCinemaButton: Button

    @FXML
    lateinit var buyTicketButton: Button

    @FXML
    private fun initialize() {
        exitButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.LOGIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.LOGIN)
        }
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        exitMenuButton.setOnAction { RoutesManager.onAppExit() }
        buyTicketButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.SELECCION_BUTACAS}" }
            RoutesManager.changeScene(view = RoutesManager.View.SELECCION_BUTACAS)
        }
        complementsListButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.LISTADO_COMPLEMENTOS_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.LISTADO_COMPLEMENTOS_CLIENTE)
        }
        showCinemaButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MOSTRAR_ESTADO_CINE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MOSTRAR_ESTADO_CINE)
        }
    }
}