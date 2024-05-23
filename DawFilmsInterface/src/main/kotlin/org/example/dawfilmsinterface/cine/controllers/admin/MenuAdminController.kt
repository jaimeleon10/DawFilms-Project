package org.example.dawfilmsinterface.cine.controllers.admin

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.stage.FileChooser
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Clase controller para el menú de administración a través de la IU.
 * Gestiona las acciones y eventos relacionados con las diferentes funcionalidades del menú de administración en la aplicación.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property exitMenuButton Botón de menú para salir de la aplicación.
 * @property acercaDeMenuButton Botón de menú que nos mostrará la información relevante de los desarrolladores.
 * @property usernameField Label que muestra el nombre de usuario.
 * @property complementListButton Botón para acceder al listado de complementos.
 * @property showCineButton Botón para mostrar el estado del cine.
 * @property backUpButton Botón para realizar un respaldo de datos.
 * @property getRaisingButton Botón para obtener la recaudación.
 * @property importComplementsButton Botón para importar complementos desde un fichero externo.
 * @property exportCineButton Botón para exportar el estado del cine.
 * @property updateSeatButton Botón para actualizar las butacas.
 * @property importSeatsButton Botón para importar butacas desde un fichero externo.
 * @property exitButton Botón para cerrar la sesión y regresar a la vista de login.
 */
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

    /**
     * Función que inicializa la vista del menú de administración.
     * Asigna las acciones a los botones y elementos de menú.
     * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
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