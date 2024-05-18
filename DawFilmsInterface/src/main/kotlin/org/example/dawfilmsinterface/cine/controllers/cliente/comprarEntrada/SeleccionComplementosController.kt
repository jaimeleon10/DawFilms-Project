package org.example.dawfilmsinterface.cine.controllers.cliente.comprarEntrada

import javafx.fxml.FXML
import javafx.scene.control.*
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class SeleccionComplementosController {
    @FXML
    lateinit var addComplementButton: Button

    @FXML
    lateinit var complementField: TextField

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    lateinit var quantityComplementSpinner: Spinner<Double>

    @FXML
    lateinit var complementosTable: TableView<Any>

    @FXML
    lateinit var selectedComplementosLabel: Label

    @FXML
    lateinit var continueButton: Button

    @FXML
    lateinit var selectedComplementosQuantityLabel: Label

    @FXML
    private fun initialize() {
        continueButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.CONFIRMAR_COMPRA}" }
            RoutesManager.changeScene(view = RoutesManager.View.CONFIRMAR_COMPRA)
        }
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }

    }
}