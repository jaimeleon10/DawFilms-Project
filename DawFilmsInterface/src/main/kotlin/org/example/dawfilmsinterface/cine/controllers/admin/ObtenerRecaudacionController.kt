package org.example.dawfilmsinterface.cine.controllers.admin

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import org.example.dawfilmsinterface.cine.viewmodels.LoginViewModel
import org.example.dawfilmsinterface.cine.viewmodels.ObtenerRecaudacionViewModel
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.routes.RoutesManager
import org.example.dawfilmsinterface.ventas.services.VentaService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
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
 * @property acercaDeMenuButton Botón de menú que nos mostrará la información relevante de los desarrolladores.
 * @property backMenuMenuButton Botón de menú para regresar al menú principal.
 * @property productosTable Tabla que muestra los productos y su recaudación.
 */
class ObtenerRecaudacionController : KoinComponent {

    private val viewModel : ObtenerRecaudacionViewModel by inject()

    private val loginViewModel : LoginViewModel by inject()

    private val ventaService : VentaService by inject()

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
    lateinit var productoColumnTable: TableColumn<Producto, String>

    @FXML
    lateinit var fechaColumnTable: TableColumn<Producto, String>

    @FXML
    lateinit var cantidadColumnTable: TableColumn<Producto, Int>

    @FXML
    lateinit var precioColumnTable: TableColumn<Producto, Double>

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
    }

    @FXML
    private fun initDefaultValues(){
        /*
        precioColumnTable.setCellValueFactory { cellData ->
            val precio = cellData.value
            SimpleDoubleProperty(precio)
        }

         */

        tipoProductoFilterComboBox.items = FXCollections.observableArrayList(viewModel.state.value.typesProducto)

        productosTable.items = FXCollections.observableArrayList(ventaService.getAllLineas())

        usernameField.text = loginViewModel.state.value.currentAdmin
    }

    @FXML
    private fun initBindings() {

    }

    @FXML
    private fun initEvents() {
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