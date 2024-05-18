package org.example.dawfilmsinterface.cine.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class EstadoCineController {
    @FXML
    lateinit var backMenuButton: Button

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    private fun initialize() {
        backMenuButton.setOnAction {
            if (usernameField.text == "admin") {
                logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
                RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
            } else {
                logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
                RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
            }
        }
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            if (usernameField.text == "admin") {
                logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
                RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
            } else {
                logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
                RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
            }
        }
    }
}