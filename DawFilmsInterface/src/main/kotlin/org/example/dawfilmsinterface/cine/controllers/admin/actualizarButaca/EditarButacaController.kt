package org.example.dawfilmsinterface.cine.controllers.admin.actualizarButaca

import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.Stage
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class EditarButacaController {

    @FXML
    lateinit var precioSpinner: Spinner<Double>

    @FXML
    lateinit var ocupacionComboBox: ComboBox<String>

    @FXML
    lateinit var tipoComboBox: ComboBox<String>

    @FXML
    lateinit var estadoComboBox: ComboBox<String>

    @FXML
    lateinit var idSelectedField: TextField

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

    private lateinit var stage: Stage
    fun setStage(stage: Stage) {
        this.stage = stage
    }

    @FXML
    private fun initialize() {
        saveButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            stage.close()
        }
        cancelButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            stage.close()
        }
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}"
            stage.close()
        }
        cleanButton.setOnAction {
            precioSpinner.valueFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 25.0, 5.0)
        }
    }
}