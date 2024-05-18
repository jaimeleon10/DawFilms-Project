package org.example.dawfilmsinterface.cine.controllers.admin.actualizarButaca

import javafx.fxml.FXML
import javafx.scene.control.*
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class ActualizarButacaController {
    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    lateinit var precioSelectedField: TextField

    @FXML
    lateinit var ocupacionSelectedField: TextField

    @FXML
    lateinit var tipoSelectedField: TextField

    @FXML
    lateinit var estadoSelectedField: TextField

    @FXML
    lateinit var idSelectedField: TextField

    @FXML
    lateinit var editButton: Button

    @FXML
    lateinit var idFilterComboBox: ComboBox<Any>

    @FXML
    lateinit var ocupacionFilterComboBox: ComboBox<Any>

    @FXML
    lateinit var tipoFilterComboBox: ComboBox<Any>

    @FXML
    lateinit var estadoFilterComboBox: ComboBox<Any>

    @FXML
    lateinit var backMenuButton: Button

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var complementosTable: TableView<Any>

    @FXML
    private fun initialize() {
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
        }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
        }
        editButton.setOnAction { RoutesManager.initEditarButaca() }
    }
}