package org.example.dawfilmsinterface.cine.controllers.admin

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.DatePicker
import javafx.stage.FileChooser
import javafx.stage.Stage
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class ExportarEstadoCineController {
    @FXML
    lateinit var exportButton: Button

    @FXML
    lateinit var exportDatePicker: DatePicker

    @FXML
    lateinit var backMenuButton: Button

    private lateinit var stage: Stage
    fun setStage(stage: Stage) {
        this.stage = stage
    }

    @FXML
    private fun initialize() {
        exportButton.setOnAction {
            logger.debug { "Exportando estado del cine" }
            FileChooser().run {
                title = "Exportar estado del cine"
                extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
                showSaveDialog(RoutesManager.activeStage)
            }
        }
        backMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            stage.close()
        }
    }
}