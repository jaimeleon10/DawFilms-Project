package org.example.dawfilmsinterface.cine.controllers.admin.actualizarButaca

import javafx.fxml.FXML
import javafx.scene.control.*
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()
/**
 * Clase controller para la actualización de las butacas a través de la IU
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property backMenuMenuButton Botón que nos llevara de regreso al menú
 * @property precioSelectedField Campo de texto que nos indicara el precio de la butaca
 * @property ocupacionSelectedField Campo de texto que nos indicara la ocupación de la butaca
 * @property tipoSelectedField Campo de texto que nos indicará el tipo de la butaca
 * @property estadoSelectedField Campo de texto que nos indicará el estado de la butaca
 * @property idSelectedField Campo de texto que nos indicará el id de la butaca
 * @property editButton Botón que nos llevara a la opción de editar butaca
 * @property idFilterComboBox Combo box que nos ayudará a la selección
 * @property ocupacionFilterComboBox Combo box que filtrara las butacas dependiendo de si están ocupadas o no
 * @property tipoFilterComboBox Combo box que filtrara las butacas dependiendo de su tipo
 * @property estadoFilterComboBox Combo box que filtrara las butacas dependiendo de su estado
 * @property backMenuButton Botón que nos devolverá al menu anterior
 * @property usernameField Label que nos mostrará el nombre de usuario
 * @property acercaDeMenuButton Botón de menú que nos mostrará la información relevante de los desarrolladores
 * @property butacaTable Tabla donde se nos mostrará la información relativa a los complementos
 */
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
    lateinit var butacaTable: TableView<Any>

    /**
     * Función que inicializa la vista de actualizar butaca
     * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
    */
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