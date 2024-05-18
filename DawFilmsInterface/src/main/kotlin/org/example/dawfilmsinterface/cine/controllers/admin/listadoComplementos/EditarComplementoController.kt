package org.example.dawfilmsinterface.cine.controllers.admin.listadoComplementos

import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.Stage
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class EditarComplementoController {
    @FXML
    lateinit var categoriaComboBox: ComboBox<Any>

    @FXML
    lateinit var priceSpinner: Spinner<Double>

    @FXML
    lateinit var stockSpinner: Spinner<Int>

    @FXML
    lateinit var nombreField: TextField

    @FXML
    lateinit var idField: TextField

    @FXML
    lateinit var saveButton: Button

    @FXML
    lateinit var cleanButton: Button

    @FXML
    lateinit var cancelButton: Button

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    lateinit var tituloLabel: Label

    private lateinit var stage: Stage
    fun setStage(stage: Stage) {
        this.stage = stage
    }

    @FXML
    private fun initialize() {
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            stage.close()
        }
        saveButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            stage.close()
        }
        cancelButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            stage.close()
        }
        cleanButton.setOnAction {
            priceSpinner.valueFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 25.0, 5.0)
        }
    }
}