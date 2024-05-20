package org.example.dawfilmsinterface.cine.controllers.cliente.comprarEntrada

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.control.ToggleButton
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class SeleccionButacasController {
    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    lateinit var selectedButacasLabel: Label

    @FXML
    lateinit var continueButton: Button

    @FXML
    lateinit var butacaE7Button: ToggleButton

    @FXML
    lateinit var butacaD7Button: ToggleButton

    @FXML
    lateinit var butacaC7Button: ToggleButton

    @FXML
    lateinit var butacaB7Button: ToggleButton

    @FXML
    lateinit var butacaA7Button: ToggleButton

    @FXML
    lateinit var butacaE6Button: ToggleButton

    @FXML
    lateinit var butacaD6Button: ToggleButton

    @FXML
    lateinit var butacaC6Button: ToggleButton

    @FXML
    lateinit var butacaB6Button: ToggleButton

    @FXML
    lateinit var butacaA6Button: ToggleButton

    @FXML
    lateinit var butacaE5Button: ToggleButton

    @FXML
    lateinit var butacaD5Button: ToggleButton

    @FXML
    lateinit var butacaC5Button: ToggleButton

    @FXML
    lateinit var butacaB5Button: ToggleButton

    @FXML
    lateinit var butacaA5Button: ToggleButton

    @FXML
    lateinit var butacaE4Button: ToggleButton

    @FXML
    lateinit var butacaD4Button: ToggleButton

    @FXML
    lateinit var butacaC4Button: ToggleButton

    @FXML
    lateinit var butacaB4Button: ToggleButton

    @FXML
    lateinit var butacaA4Button: ToggleButton

    @FXML
    lateinit var butacaE3Button: ToggleButton

    @FXML
    lateinit var butacaD3Button: ToggleButton

    @FXML
    lateinit var butacaC3Button: ToggleButton

    @FXML
    lateinit var butacaB3Button: ToggleButton

    @FXML
    lateinit var butacaA3Button: ToggleButton

    @FXML
    lateinit var butacaE2Button: ToggleButton

    @FXML
    lateinit var butacaD2Button: ToggleButton

    @FXML
    lateinit var butacaC2Button: ToggleButton

    @FXML
    lateinit var butacaB2Button: ToggleButton

    @FXML
    lateinit var butacaA2Button: ToggleButton

    @FXML
    lateinit var butacaE1Button: ToggleButton

    @FXML
    lateinit var butacaD1Button: ToggleButton

    @FXML
    lateinit var butacaC1Button: ToggleButton

    @FXML
    lateinit var butacaB1Button: ToggleButton

    @FXML
    lateinit var butacaA1Button: ToggleButton

    @FXML
    private fun initialize() {
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
        continueButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.SELECCION_COMPLEMENTOS}" }
            RoutesManager.changeScene(view = RoutesManager.View.SELECCION_COMPLEMENTOS)
        }
    }
}