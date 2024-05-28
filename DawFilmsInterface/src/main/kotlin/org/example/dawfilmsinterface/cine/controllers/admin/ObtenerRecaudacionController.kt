package org.example.dawfilmsinterface.cine.controllers.admin

import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import org.example.dawfilmsinterface.cine.viewmodels.LoginViewModel
import org.example.dawfilmsinterface.cine.viewmodels.ObtenerRecaudacionViewModel
import org.example.dawfilmsinterface.locale.toDefaultDateString
import org.example.dawfilmsinterface.routes.RoutesManager
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDate

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
 * @property acercaDeMenuButton Botón de menú que nos mostrará la información relevante de los desarrolladores.
 * @property backMenuMenuButton Botón de menú para regresar al menú principal.
 * @property productosTable Tabla que muestra los productos y su recaudación.
 */
class ObtenerRecaudacionController : KoinComponent {

    private val viewModel : ObtenerRecaudacionViewModel by inject()

    private val loginViewModel : LoginViewModel by inject()

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

    @FXML
    lateinit var productoColumnTable: TableColumn<LineaVenta, String>

    @FXML
    lateinit var fechaColumnTable: TableColumn<LineaVenta, String>

    @FXML
    lateinit var cantidadColumnTable: TableColumn<LineaVenta, Int>

    @FXML
    lateinit var precioColumnTable: TableColumn<LineaVenta, Double>

    /**
     * Función que inicializa la vista de obtención de recaudación.
     * Asigna las acciones a los botones y elementos de menú.
     * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    @FXML
    private fun initialize() {
        logger.debug { "Inicializando Obtener Recaudacion Controller" }

        initDefaultValues()
        initEvents()
        initBindings()
        actualizarTotal()
    }

    @FXML
    private fun initDefaultValues(){
        productoColumnTable.setCellValueFactory { cellData ->
            SimpleObjectProperty(cellData.value.obtenerIdProducto())
        }
        fechaColumnTable.setCellValueFactory { cellData ->
            SimpleObjectProperty(cellData.value.createdAt.toDefaultDateString())
        }
        cantidadColumnTable.cellValueFactory = PropertyValueFactory("cantidad")
        precioColumnTable.setCellValueFactory { cellData ->
            SimpleObjectProperty(cellData.value.precio)
        }

        actualizarTotal()

        tipoProductoFilterComboBox.items = FXCollections.observableArrayList(viewModel.state.value.typesProducto)
        tipoProductoFilterComboBox.selectionModel.selectFirst()

        productosTable.items = FXCollections.observableArrayList(viewModel.state.value.lineasVentas)
        productosTable.columns.forEach { it.isResizable = false }
        productosTable.columns.forEach { it.isReorderable = false }

        usernameField.text = loginViewModel.state.value.currentAdmin
    }

    @FXML
    private fun actualizarTotal(){
        val total = productosTable.items.sumOf { (it as LineaVenta).precio * it.cantidad }
        totalRecaudacionField.text = total.toString()
    }

    @FXML
    private fun initBindings() {
        viewModel.state.addListener { _, _, newValue ->
            logger.debug { "Actualizando datos de la vista" }
            productosTable.items = FXCollections.observableArrayList(newValue.lineasVentas)
            actualizarTotal()
        }
    }

    @FXML
    private fun initEvents() {
        tipoProductoFilterComboBox.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let {onComboSelected(newValue.toString())}
        }

        dateFilterDatePicker.valueProperty().addListener{ _, _, newValue ->
            newValue?.let {onDateSelected(newValue)}
        }

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

    private fun onDateSelected(newDate : LocalDate){
        logger.debug { "onDateSelected : $newDate" }
        filterDataTable()
    }

    private fun onComboSelected(newValue: String) {
        logger.debug { "onComboSelected: $newValue"}
        filterDataTable()
    }

    private fun filterDataTable(){
        logger.debug { "filterDataTable" }
        val tipoProducto = tipoProductoFilterComboBox.value.toString()
        val selectedDate = dateFilterDatePicker.value

        val filteredList = viewModel.lineasFilteredList(tipoProducto).filter { lineaVenta ->
            selectedDate == null || lineaVenta.createdAt == selectedDate
        }

        productosTable.items = FXCollections.observableArrayList(filteredList)

        actualizarTotal()
    }
}