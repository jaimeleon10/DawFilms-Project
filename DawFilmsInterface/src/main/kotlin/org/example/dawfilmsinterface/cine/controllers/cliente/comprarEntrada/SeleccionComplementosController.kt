package org.example.dawfilmsinterface.cine.controllers.cliente.comprarEntrada

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.ImageView
import org.example.dawfilmsinterface.cine.viewModels.LoginViewModel
import org.example.dawfilmsinterface.productos.mappers.toModel
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.viewmodels.CarritoViewModel
import org.example.dawfilmsinterface.productos.viewmodels.SeleccionarComplementoViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Clase controller para la selección de complementos a través de la IU.
 * Gestiona las acciones y eventos relacionados con la selección de complementos en la aplicación.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property addComplementButton Botón para añadir un complemento.
 * @property complementField Campo de texto para ingresar el nombre del complemento.
 * @property usernameField Etiqueta que muestra el nombre de usuario.
 * @property acercaDeMenuButton Botón de menú que nos mostrará la información relevante de los desarrolladores.
 * @property backMenuMenuButton Botón de menú para regresar al menú principal.
 * @property quantityComplementSpinner Selector de cantidad para los complementos.
 * @property complementosTable Tabla que muestra los complementos seleccionados.
 * @property selectedComplementosLabel Etiqueta que muestra los complementos seleccionados.
 * @property continueButton Botón para continuar con el proceso de compra.
 * @property selectedComplementosQuantityLabel Etiqueta que muestra la cantidad de complementos seleccionados.
 */
class SeleccionComplementosController: KoinComponent {
    val viewModel: SeleccionarComplementoViewModel by inject()

    val viewModelLogin: LoginViewModel by inject()

    val carritoViewModel: CarritoViewModel by inject()

    @FXML
    lateinit var addComplementButton: Button

    @FXML
    lateinit var complementField: TextField

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    lateinit var quantityComplementSpinner: Spinner<Int>

    @FXML
    lateinit var complementosTable: TableView<Complemento>

    @FXML
    lateinit var selectedComplementosLabel: Label

    @FXML
    lateinit var continueButton: Button

    @FXML
    lateinit var selectedComplementosQuantityLabel: Label

    @FXML
    lateinit var stockColumn: TableColumn<Complemento, String>

    @FXML
    lateinit var precioColumn: TableColumn<Complemento, String>

    @FXML
    lateinit var nombreColumn: TableColumn<Complemento, String>

    @FXML
    lateinit var imagenImage: ImageView

    @FXML
    lateinit var removeComplementButton: Button

    var complementosSeleccionados = 0

    /**
     * Función que inicializa la vista de selección de complementos.
     * Asigna las acciones a los botones y elementos de menú.
     * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    @FXML
    private fun initialize() {
        logger.debug { "Inicializando SeleccionComplementosController FXML" }

        initDefaultValues()

        initBindings()

        initEventos()
    }

    private fun initBindings() {
        logger.debug { "Inicializando bindings"}

        complementField.textProperty().bind(viewModel.state.map { it.complemento.nombre })
        imagenImage.imageProperty().bind(viewModel.state.map { it.complemento.icono })
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
        complementosTable.columns.forEach { it.isResizable = false }
        complementosTable.columns[1].style = "-fx-font-size: 15; -fx-alignment: CENTER;"
        complementosTable.columns[2].style = "-fx-font-size: 15; -fx-alignment: CENTER;"

        nombreColumn.cellValueFactory = PropertyValueFactory("nombre")
        precioColumn.cellValueFactory = PropertyValueFactory("precio")
        stockColumn.cellValueFactory = PropertyValueFactory("stock")
    }

    private fun initEventos() {
        continueButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.CONFIRMAR_COMPRA}" }
            RoutesManager.changeScene(view = RoutesManager.View.CONFIRMAR_COMPRA)
        }
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
        usernameField.text = viewModelLogin.state.value.currentCliente.nombre
        complementosTable.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { onTableSelected(newValue) }
        }
        addComplementButton.setOnAction { onAñadirAction() }
        removeComplementButton.setOnAction { onEliminarAction() }
    }

    private fun onAñadirAction() {
        logger.debug { "Añadiendo complemento seleccionado" }

        if (quantityComplementSpinner.value > 0) {
            if (carritoViewModel.state.value.listadoComplementosSeleccionados.containsKey(viewModel.state.value.complemento.nombre)) {
                logger.debug { "Reemplazando cantidad seleccionada del complemento ${viewModel.state.value.complemento.nombre}" }
                carritoViewModel.state.value.listadoComplementosSeleccionados[viewModel.state.value.complemento.nombre] =
                    quantityComplementSpinner.value
            } else {
                logger.debug { "Añadiendo complemento seleccionado al listado" }
                carritoViewModel.state.value.listadoComplementosSeleccionados.put(
                    viewModel.state.value.complemento.nombre,
                    quantityComplementSpinner.value
                )
                complementosSeleccionados += 1
            }
        }

        selectedComplementosQuantityLabel.text = "Complementos seleccionados: $complementosSeleccionados"
        selectedComplementosLabel.text = carritoViewModel.state.value.listadoComplementosSeleccionados.entries.joinToString(",") { " ${it.key}: ${it.value}" }
    }

    private fun onEliminarAction() {
        logger.debug { "Eliminando complemento seleccionado" }

        if (carritoViewModel.state.value.listadoComplementosSeleccionados.containsKey(viewModel.state.value.complemento.nombre)) {
            logger.debug { "Eliminando complemento ${viewModel.state.value.complemento.nombre}" }
            carritoViewModel.state.value.listadoComplementosSeleccionados.remove(viewModel.state.value.complemento.nombre)
            complementosSeleccionados -= 1
        }

        selectedComplementosQuantityLabel.text = "Complementos seleccionados: $complementosSeleccionados"
        if (carritoViewModel.state.value.listadoComplementosSeleccionados.isEmpty()) selectedComplementosLabel.text = ""
        else selectedComplementosLabel.text = carritoViewModel.state.value.listadoComplementosSeleccionados.entries.joinToString(",") { " ${it.key}: ${it.value}" }
    }

    private fun onTableSelected(newValue: Complemento) {
        logger.debug { "onTablaSelected: $newValue" }
        viewModel.updateComplementoSeleccionado(newValue)
        quantityComplementSpinner.valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(0, viewModel.state.value.complemento.toModel().stock, 1)
    }
}