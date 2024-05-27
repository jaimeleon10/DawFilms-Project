package org.example.dawfilmsinterface.cine.controllers.admin.listadoComplementos

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.Alert.AlertType
import org.example.dawfilmsinterface.cine.viewModels.LoginViewModel
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.viewmodels.GestionComplementosViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Clase controller para la administración de complementos a través de la IU.
 * Gestiona las acciones y eventos relacionados con la vista de administración de complementos en la aplicación.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @see org.example.dawfilmsinterface.productos.models.complementos
 * @property backMenuButton Botón para regresar al menú anterior.
 * @property deleteButton Botón para eliminar un complemento.
 * @property editButton Botón para editar un complemento.
 * @property addButton Botón para añadir un nuevo complemento.
 * @property stockSelectedField Campo de texto para el stock del complemento seleccionado.
 * @property precioSelectedField Campo de texto para el precio del complemento seleccionado.
 * @property nombreSelectedField Campo de texto para el nombre del complemento seleccionado.
 * @property idSelectedField Campo de texto para el ID del complemento seleccionado.
 * @property usernameField Etiqueta que muestra el nombre de usuario.
 * @property acercaDeMenuButton Elemento de menú para la opción "Acerca de".
 * @property backMenuMenuButton Elemento de menú para regresar al menú anterior.
 * @property complementosTable Tabla que muestra los complementos.
 */
class ListadoComplementosAdminController : KoinComponent {
    private val viewModel: GestionComplementosViewModel by inject()

    private val loginViewModel : LoginViewModel by inject()

    @FXML
    lateinit var backMenuButton: Button

    @FXML
    lateinit var deleteButton: Button

    @FXML
    lateinit var editButton: Button

    @FXML
    lateinit var addButton: Button

    @FXML
    lateinit var stockSelectedField: TextField

    @FXML
    lateinit var precioSelectedField: TextField

    @FXML
    lateinit var nombreSelectedField: TextField

    @FXML
    lateinit var idSelectedField: TextField

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    lateinit var complementosTable: TableView<Complemento>

    @FXML
    lateinit var nombreColumnTable: TableColumn<Complemento, String>

    @FXML
    lateinit var precioColumnTable: TableColumn<Complemento, String>

    @FXML
    lateinit var stockColumnTable: TableColumn<Complemento, String>

    @FXML
    lateinit var disponibilidadColumnTable: TableColumn<Complemento, String>

    /**
     * Función que inicializa la vista de administración de complementos.
     * Asigna las acciones a los botones y elementos de menú.
     * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    @FXML
    private fun initialize() {
        logger.debug { "Inicializando ListadoComplementosAdminController FXML" }
        initDefaultValues()

        initBindings()

        initEventos()
    }

    private fun initBindings() {
        logger.debug { "Inicializando bindings"}

        idSelectedField.textProperty().bind(viewModel.state.map { it.complemento.id })
        nombreSelectedField.textProperty().bind(viewModel.state.map { it.complemento.nombre })
        precioSelectedField.textProperty().bind(viewModel.state.map { it.complemento.precio.toString() })
        stockSelectedField.textProperty().bind(viewModel.state.map { it.complemento.stock.toString() })

        viewModel.state.addListener { _, _, newValue ->
            logger.debug { "Actualizando datos de la vista" }
            if (complementosTable.items != newValue.complementos){
                complementosTable.items = FXCollections.observableArrayList(newValue.complementos)
            }
        }
    }

    private fun initDefaultValues() {
        logger.debug { "Inicializando valores por defecto" }

        complementosTable.items = FXCollections.observableArrayList(viewModel.state.value.complementos)
        complementosTable.columns.forEach {it.isResizable = false}

        nombreColumnTable.cellValueFactory = PropertyValueFactory("nombre")
        precioColumnTable.cellValueFactory = PropertyValueFactory("precio")
        stockColumnTable.cellValueFactory = PropertyValueFactory("stock")

        usernameField.text = loginViewModel.state.value.currentAdmin
    }

    @FXML
    private fun initEventos() {
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
        }
        addButton.setOnAction {onNuevoAction()}
        editButton.setOnAction {onEditarAction()}
        backMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
        }
        deleteButton.setOnAction { onEliminarAction() }

        complementosTable.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { onTablaSelected(newValue) }
        }
    }

    private fun onEliminarAction(){
        logger.debug { "onEliminarAction" }

        if (complementosTable.selectionModel.selectedItem == null){
            return
        }
        Alert(AlertType.CONFIRMATION).apply {
            title = "¿Eliminar complemento?"
            contentText = "¿Desea eliminar el complemento?"
        }.showAndWait().ifPresent{
            if(it == ButtonType.OK){
                viewModel.eliminarComplemento().onSuccess {
                    logger.debug { "Complemento eliminado correctamente" }
                    showAlertOperacion(
                        alerta = AlertType.INFORMATION,
                        "Complemento eliminado",
                        "Se ha eliminado el complemento"
                    )
                    complementosTable.selectionModel.clearSelection()
                }.onFailure {
                    logger.error { "Error al eliminar el complemento: ${it.message}" }
                    showAlertOperacion(
                        alerta = AlertType.ERROR,
                        "Error al eliminar el complemento",
                        "No se ha eliminado el complemento"
                    )
                }
            }
        }
    }

    private fun onNuevoAction() {
        logger.debug { "Cambiando de escena a ${RoutesManager.View.EDITAR_COMPLEMENTO}" }
        viewModel.changeComplementoOperacion(GestionComplementosViewModel.TipoOperacion.NUEVO)
        RoutesManager.initEditarComplemento("AÑADIR COMPLEMENTO")
    }

    private fun onTablaSelected(newValue: Complemento){
        logger.debug { "onTablaSelected: $newValue" }
        viewModel.updateComplementoSeleccionado(newValue)
    }

    private fun onEditarAction(){
        logger.debug { "Cambiando de escena a ${RoutesManager.View.EDITAR_COMPLEMENTO}" }
        viewModel.changeComplementoOperacion(GestionComplementosViewModel.TipoOperacion.EDITAR)
        RoutesManager.initEditarComplemento("EDITAR COMPLEMENTO")
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