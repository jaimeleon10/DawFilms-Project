package org.example.dawfilmsinterface.cine.controllers.admin.listadoComplementos

import javafx.fxml.FXML
import javafx.scene.control.*
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class ListadoComplementosAdminController {
    @FXML
    lateinit var backMenuButton: Button

    @FXML
    lateinit var deleteButton: Button

    @FXML
    lateinit var editButton: Button

    @FXML
    lateinit var addButton: Button

    @FXML
    lateinit var stockSelectedField: TextField

    @FXML
    lateinit var precioSelectedField: TextField

    @FXML
    lateinit var nombreSelectedField: TextField

    @FXML
    lateinit var idSelectedField: TextField

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    lateinit var complementosTable: TableView<Any>

    @FXML
    private fun initialize() {
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
        }
        addButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.EDITAR_COMPLEMENTO}" }
            RoutesManager.initEditarComplemento("AÑADIR COMPLEMENTO")
        }
        editButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.EDITAR_COMPLEMENTO}" }
            RoutesManager.initEditarComplemento("EDITAR COMPLEMENTO")
        }
        backMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
        }
    }
}