package org.example.dawfilmsinterface.cine.controllers.cliente.comprarEntrada

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.control.TableView
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class ConfirmarCompraController {
    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var butacasTable: TableView<Any>

    @FXML
    lateinit var complementosTable: TableView<Any>

    @FXML
    lateinit var cancelarCompraButton: Button

    @FXML
    lateinit var confirmarCompraButton: Button

    @FXML
    lateinit var cantidadButacasLabel: Label

    @FXML
    lateinit var cantidadComplementosLabel: Label

    @FXML
    private fun initialize() {
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
        confirmarCompraButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
        cancelarCompraButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
    }
}