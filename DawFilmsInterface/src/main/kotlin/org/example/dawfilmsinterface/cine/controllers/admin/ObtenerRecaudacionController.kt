package org.example.dawfilmsinterface.cine.controllers.admin

import javafx.fxml.FXML
import javafx.scene.control.*
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Clase controller para obtener la recaudación a través de la IU.
 * Gestiona las acciones y eventos relacionados con la visualización y filtrado de la recaudación en la aplicación.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property totalRecaudacionField Campo de texto que muestra la recaudación total.
 * @property tipoProductoFilterComboBox Combo box para filtrar los productos por tipo.
 * @property dateFilterDatePicker Selector de fecha para filtrar la recaudación por fecha.
 * @property backMenuButton Botón para regresar al menú anterior.
 * @property usernameField Etiqueta que muestra el nombre de usuario.
 * @property acercaDeMenuButton Botón de menú que muestra información "Acerca de".
 * @property backMenuMenuButton Botón de menú para regresar al menú principal.
 * @property productosTable Tabla que muestra los productos y su recaudación.
 */
class ObtenerRecaudacionController {
    @FXML
    lateinit var totalRecaudacionField: TextField

    @FXML
    lateinit var tipoProductoFilterComboBox: ComboBox<Any>

    @FXML
    lateinit var dateFilterDatePicker: DatePicker

    @FXML
    lateinit var backMenuButton: Button

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    lateinit var productosTable: TableView<Any>

    /**
     * Función que inicializa la vista de obtención de recaudación.
     * Asigna las acciones a los botones y elementos de menú.
     * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    @FXML
    private fun initialize() {
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
        }
        backMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
        }
    }
}