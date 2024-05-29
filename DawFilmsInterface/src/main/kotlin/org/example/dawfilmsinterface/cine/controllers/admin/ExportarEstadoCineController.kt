package org.example.dawfilmsinterface.cine.controllers.admin

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.DatePicker
import javafx.stage.FileChooser
import javafx.stage.Stage
import org.example.dawfilmsinterface.cine.viewmodels.MenuAdminViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Clase controller para exportar el estado del cine a través de la IU.
 * Gestiona las acciones y eventos relacionados con la exportación de datos del cine en la aplicación.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property exportButton Botón para iniciar la exportación del estado del cine.
 * @property exportDatePicker Selector de fecha para especificar la fecha de exportación.
 * @property backMenuButton Botón para regresar al menú anterior.
 * @property stage Escenario actual de la vista.
 */
class ExportarEstadoCineController: KoinComponent {
    val viewModel: MenuAdminViewModel by inject()
    @FXML
    lateinit var exportButton: Button

    @FXML
    lateinit var exportDatePicker: DatePicker

    @FXML
    lateinit var backMenuButton: Button

    /**
     * Establece el escenario actual para la vista.
     * @param stage Escenario a establecer.
     */
    private lateinit var stage: Stage
    fun setStage(stage: Stage) {
        this.stage = stage
    }
    /**
     * Función que inicializa la vista de exportación del estado del cine.
     * Asigna las acciones a los botones de exportación y regreso al menú.
     * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
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
        exportDatePicker.setOnAction {
            // TODO -> LISTA DE PRODUCTOS COMPRADOS EN X FECHA -> PASAR A VIEWMODEL PARA HACER EXPORT DE CINE POR FECHA
        }
    }
}