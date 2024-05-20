package org.example.dawfilmsinterface.cine.controllers.admin

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.stage.FileChooser
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class MenuAdminController {
    @FXML
    lateinit var exitMenuButton: MenuItem

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var complementListButton: Button

    @FXML
    lateinit var showCineButton: Button

    @FXML
    lateinit var backUpButton: Button

    @FXML
    lateinit var getRaisingButton: Button

    @FXML
    lateinit var importComplementsButton: Button

    @FXML
    lateinit var exportCineButton: Button

    @FXML
    lateinit var updateSeatButton: Button

    @FXML
    lateinit var importSeatsButton: Button

    @FXML
    lateinit var exitButton: Button

    @FXML
    private fun initialize() {
        exitButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.LOGIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.LOGIN)
        }
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        exitMenuButton.setOnAction { RoutesManager.onAppExit() }
        importSeatsButton.setOnAction {
            logger.debug { "Importando butacas desde el explorador de archivos" }
            FileChooser().run {
                title = "Importar Butacas"
                extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
                showSaveDialog(RoutesManager.activeStage)
            }
        }
        exportCineButton.setOnAction { RoutesManager.initExportEstadoCine() }
        getRaisingButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.OBTENER_RECAUDACION}" }
            RoutesManager.changeScene(view = RoutesManager.View.OBTENER_RECAUDACION)
        }
        updateSeatButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.ACTUALIZAR_BUTACA}" }
            RoutesManager.changeScene(view = RoutesManager.View.ACTUALIZAR_BUTACA)
        }
        importComplementsButton.setOnAction {
            logger.debug { "Importando complementos desde el explorador de archivos" }
            FileChooser().run {
                title = "Importar Butacas"
                extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
                showSaveDialog(RoutesManager.activeStage)
            }
        }
        backUpButton.setOnAction {
            logger.debug { "Realizando backup y guardando en el explorador de archivos" }
            FileChooser().run {
                title = "Importar Butacas"
                extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
                showSaveDialog(RoutesManager.activeStage)
            }
        }
        showCineButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MOSTRAR_ESTADO_CINE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MOSTRAR_ESTADO_CINE)
        }
        complementListButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.LISTADO_COMPLEMENTOS_ADMIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.LISTADO_COMPLEMENTOS_ADMIN) }
    }
}