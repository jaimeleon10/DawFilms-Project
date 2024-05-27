package org.example.dawfilmsinterface.cine.controllers.cliente

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import org.example.dawfilmsinterface.cine.viewmodels.LoginViewModel
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.cine.viewmodels.ListadoComplementosViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Clase controller para el listado de complementos a través de la IU.
 * Gestiona las acciones y eventos relacionados con la visualización del listado de complementos en la aplicación.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property backMenuButton Botón para regresar al menú principal.
 * @property usernameField Etiqueta que muestra el nombre de usuario.
 * @property acercaDeMenuButton Botón de menú que nos mostrará la información relevante de los desarrolladores.
 * @property backMenuMenuButton Botón de menú para regresar al menú principal.
 * @property complementosTable Tabla que muestra el listado de complementos.
 */
class ListadoComplementosController: KoinComponent {
    val viewModel: ListadoComplementosViewModel by inject()

    val loginViewModel: LoginViewModel by inject()

    @FXML
    lateinit var backMenuButton: Button

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    lateinit var complementosTable: TableView<Complemento>

    @FXML
    lateinit var stockColumn: TableColumn<Complemento, String>

    @FXML
    lateinit var precioColumn: TableColumn<Complemento, String>

    @FXML
    lateinit var nombreColumn: TableColumn<Complemento, String>

    /**
     * Función que inicializa la vista del listado de complementos.
     * Asigna las acciones a los botones y elementos de menú.
     * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    @FXML
    private fun initialize() {
        logger.debug { "Inicializando ListadoComplementosController FXML" }

        initDefaultValues()

        initBindings()

        initEvents()

    }

    private fun initBindings() {
        viewModel.state.addListener { _, _, newValue ->
            logger.debug { "Actualizando datos de la vista" }
            if (complementosTable.items != newValue.complementos){
                complementosTable.items = FXCollections.observableArrayList(newValue.complementos)
            }
        }
    }

    private fun initDefaultValues() {
        viewModel.loadAllComplementos()

        complementosTable.items = FXCollections.observableArrayList(viewModel.state.value.complementos)
        complementosTable.columns.forEach {
            it.isResizable = false
            it.isReorderable = false
        }
        complementosTable.columns[1].style = "-fx-font-size: 15; -fx-alignment: CENTER;"
        complementosTable.columns[2].style = "-fx-font-size: 15; -fx-alignment: CENTER;"

        nombreColumn.cellValueFactory = PropertyValueFactory("nombre")
        precioColumn.cellValueFactory = PropertyValueFactory("precio")
        stockColumn.cellValueFactory = PropertyValueFactory("stock")
    }

    private fun initEvents() {
        backMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
        usernameField.text = loginViewModel.state.value.currentCliente.nombre
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
    }
}