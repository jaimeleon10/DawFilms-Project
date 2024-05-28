package org.example.dawfilmsinterface.cine.controllers.admin

import javafx.fxml.FXML
import javafx.scene.Cursor.DEFAULT
import javafx.scene.Cursor.WAIT
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.stage.FileChooser
import org.example.dawfilmsinterface.cine.viewmodels.LoginViewModel
import org.example.dawfilmsinterface.cine.viewmodels.MenuAdminViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import com.github.michaelbull.result.*

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
class MenuAdminController: KoinComponent {
    val viewModel: MenuAdminViewModel by inject()

    val loginViewModel: LoginViewModel by inject()

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
    lateinit var importBackUpButton: Button

    @FXML
    lateinit var getRaisingButton: Button

    @FXML
    lateinit var exportComplementsButton: Button

    @FXML
    lateinit var importComplementsButton: Button

    @FXML
    lateinit var exportCineButton: Button

    @FXML
    lateinit var updateSeatButton: Button

    @FXML
    lateinit var exportSeatsButton: Button

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
        importSeatsButton.setOnAction {
            logger.debug { "Importando butacas desde el explorador de archivos" }
            FileChooser().run {
                title = "Importar Butacas"
                extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
                extensionFilters.add(FileChooser.ExtensionFilter("JSON:", "*.json"))
                extensionFilters.add(FileChooser.ExtensionFilter("XML", "*.xml"))
                showOpenDialog(RoutesManager.activeStage)
            }?.let { file ->
                logger.debug { "Importando butacas" }
                RoutesManager.activeStage.scene.cursor = WAIT
                viewModel.importarButacas(file).onSuccess {
                    showAlertOperacion(
                        title = "Butacas importadas",
                        mensaje = "Se han importado todas las butacas.\nButacas importadas: ${it.size}"
                    )
                }.onFailure { error ->
                    showAlertOperacion(alerta = AlertType.ERROR, title = "Error al importar", mensaje = error.message)
                }
                RoutesManager.activeStage.scene.cursor = DEFAULT
            }

        }

        exportSeatsButton.setOnAction {
            logger.debug { "Exportando butacas al explorador de archivos" }
            FileChooser().run {
                title = "Exportar Butacas"
                extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
                extensionFilters.add(FileChooser.ExtensionFilter("JSON:", "*.json"))
                extensionFilters.add(FileChooser.ExtensionFilter("XML", "*.xml"))
                showSaveDialog(RoutesManager.activeStage)
            }?.let { file ->
                logger.debug { "Exportando butacas" }
                RoutesManager.activeStage.scene.cursor = WAIT
                viewModel.exportarButacas(file).onSuccess {
                    showAlertOperacion(
                        title = "Butacas exportadas",
                        mensaje = "Se han exportado todas las butacas.\nButacas exportadas: ${it}"
                    )
                    }.onFailure { error ->
                        showAlertOperacion(alerta = AlertType.ERROR, title = "Error al exportar", mensaje = error.message)
                    }
                RoutesManager.activeStage.scene.cursor = DEFAULT
            }
        }

        importComplementsButton.setOnAction {
            logger.debug { "Importando complementos desde el explorador de archivos" }
            FileChooser().run {
                title = "Importar Complementos"
                extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
                extensionFilters.add(FileChooser.ExtensionFilter("JSON:", "*.json"))
                extensionFilters.add(FileChooser.ExtensionFilter("XML", "*.xml"))
                showOpenDialog(RoutesManager.activeStage)
            }?.let { file ->
                logger.debug { "Importando complementos" }
                RoutesManager.activeStage.scene.cursor = WAIT
                viewModel.importarComplementos(file).onSuccess {
                    showAlertOperacion(
                        title = "Complementos importados",
                        mensaje = "Se han importado todos los complementos.\nComplementos importados: ${it.size}"
                    )
                }.onFailure { error ->
                    showAlertOperacion(alerta = AlertType.ERROR, title = "Error al importar", mensaje = error.message)
                }
                RoutesManager.activeStage.scene.cursor = DEFAULT
            }
        }

        exportComplementsButton.setOnAction {
            logger.debug { "Exportando complementos al explorador de archivos" }
            FileChooser().run {
                title = "Exportar Complementos"
                extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
                extensionFilters.add(FileChooser.ExtensionFilter("JSON:", "*.json"))
                extensionFilters.add(FileChooser.ExtensionFilter("XML", "*.xml"))
                showSaveDialog(RoutesManager.activeStage)
            }?.let { file ->
                logger.debug { "Exportando complementos" }
                RoutesManager.activeStage.scene.cursor = WAIT
                viewModel.exportarComplementos(file).onSuccess {
                    showAlertOperacion(
                        title = "Complementos exportados",
                        mensaje = "Se han exportado todos los complementos.\nComplementos exportados: ${it}"
                    )
                }.onFailure { error ->
                    showAlertOperacion(alerta = AlertType.ERROR, title = "Error al exportar", mensaje = error.message)
                }
                RoutesManager.activeStage.scene.cursor = DEFAULT
            }
        }

        // TODO -> CONTINUAR DESDE AQUÍ

        importBackUpButton.setOnAction {  }

        backUpButton.setOnAction {
            logger.debug { "Realizando backup y guardando en el explorador de archivos" }
            FileChooser().run {
                title = "Importar Butacas"
                extensionFilters.add(FileChooser.ExtensionFilter("Tipos de archivo:", "*.zip"))
                showSaveDialog(RoutesManager.activeStage)
            }
        }

        exportCineButton.setOnAction { RoutesManager.initExportEstadoCine() }

        exitButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.LOGIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.LOGIN)
        }
        exitMenuButton.setOnAction { RoutesManager.onAppExit() }
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }

        getRaisingButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.OBTENER_RECAUDACION}" }
            RoutesManager.changeScene(view = RoutesManager.View.OBTENER_RECAUDACION)
        }

        updateSeatButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.ACTUALIZAR_BUTACA}" }
            RoutesManager.changeScene(view = RoutesManager.View.ACTUALIZAR_BUTACA)
        }

        showCineButton.setOnAction {
            loginViewModel.state.value.isAdmin = true
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MOSTRAR_ESTADO_CINE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MOSTRAR_ESTADO_CINE)
        }

        complementListButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.LISTADO_COMPLEMENTOS_ADMIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.LISTADO_COMPLEMENTOS_ADMIN) }
        usernameField.text = loginViewModel.state.value.currentAdmin
    }

    private fun showAlertOperacion(
        alerta: AlertType = AlertType.CONFIRMATION,
        title: String = "",
        mensaje: String = ""
    ) {
        Alert(alerta).apply {
            this.title = title
            this.contentText = mensaje
        }.showAndWait()
    }
}