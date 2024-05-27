package org.example.dawfilmsinterface.cine.controllers.cliente.comprarEntrada

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import org.example.dawfilmsinterface.cine.viewmodels.LoginViewModel
import org.example.dawfilmsinterface.locale.toDefaultDateString
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.cine.viewmodels.CarritoViewModel
import org.example.dawfilmsinterface.cine.viewmodels.ConfirmarCompraViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDate

private val logger = logging()

/**
 * Clase controller para confirmar la compra a través de la IU.
 * Gestiona las acciones y eventos relacionados con la confirmación y cancelación de compras en la aplicación.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property acercaDeMenuButton Botón de menú que nos mostrará la información relevante de los desarrolladores.
 * @property backMenuMenuButton Botón de menú para regresar al menú principal.
 * @property usernameField Etiqueta que muestra el nombre de usuario.
 * @property butacasTable Tabla que muestra las butacas seleccionadas para la compra.
 * @property complementosTable Tabla que muestra los complementos seleccionados para la compra.
 * @property cancelarCompraButton Botón para cancelar la compra y regresar al menú anterior.
 * @property confirmarCompraButton Botón para confirmar la compra y finalizar el proceso.
 * @property cantidadButacasLabel Etiqueta que muestra la cantidad de butacas seleccionadas.
 * @property cantidadComplementosLabel Etiqueta que muestra la cantidad de complementos seleccionados.
 */
class ConfirmarCompraController: KoinComponent {
    val viewModel: ConfirmarCompraViewModel by inject()

    val carritoViewModel: CarritoViewModel by inject()

    val loginViewModel: LoginViewModel by inject()

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var cancelarCompraButton: Button

    @FXML
    lateinit var confirmarCompraButton: Button

    @FXML
    lateinit var cantidadButacasLabel: Label

    @FXML
    lateinit var cantidadComplementosLabel: Label

    @FXML
    lateinit var butacasTable: TableView<Butaca>

    @FXML
    lateinit var precioButacasColumn: TableColumn<Butaca, String>

    @FXML
    lateinit var columnaButacasColumn: TableColumn<Butaca, String>

    @FXML
    lateinit var filaButacasColumn: TableColumn<Butaca, String>

    @FXML
    lateinit var tipoButacasColumn: TableColumn<Butaca, String>

    @FXML
    lateinit var complementosTable: TableView<Pair<Complemento, Int>>

    @FXML
    lateinit var cantidadComplementosColumn: TableColumn<Pair<Complemento, Int>, String>

    @FXML
    lateinit var precioComplementosColumn: TableColumn<Pair<Complemento, Int>, String>

    @FXML
    lateinit var nombreComplementosColumn: TableColumn<Pair<Complemento, Int>, String>

    @FXML
    lateinit var fechaLabel: Label

    @FXML
    lateinit var precioTotalLabel: Label

    /**
     * Función que inicializa la vista de confirmación de compra.
     * Asigna las acciones a los botones y elementos de menú.
     * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    @FXML
    private fun initialize() {
        logger.debug { "Inicializando ConfirmarCompraController FXML" }

        initDefaultValues()

        initEventos()
    }

    private fun initDefaultValues() {
        viewModel.deleteLastValues()

        viewModel.updateToComplementosList(carritoViewModel.state.value.listadoComplementosSeleccionados)
        complementosTable.items = FXCollections.observableArrayList(viewModel.state.value.complementos.toList())
        nombreComplementosColumn.setCellValueFactory { cellData ->
            val nombre = cellData.value.first.nombre
            SimpleStringProperty(nombre)
        }
        precioComplementosColumn.setCellValueFactory { cellData ->
            val precio = cellData.value.first.precio.toString()
            SimpleStringProperty(precio)
        }
        cantidadComplementosColumn.setCellValueFactory { cellData ->
            val cantidad = cellData.value.second
            SimpleStringProperty(cantidad.toString())
        }
        complementosTable.columns.forEach {
            it.isResizable = false
            it.style = "-fx-font-size: 15; -fx-alignment: CENTER;"
            it.isReorderable = false
        }

        viewModel.updateToButacasList(carritoViewModel.state.value.listadoButacasSeleccionadas)
        butacasTable.items = FXCollections.observableArrayList(viewModel.state.value.butacas)
        tipoButacasColumn.cellValueFactory = PropertyValueFactory("tipoButaca")
        filaButacasColumn.setCellValueFactory { cellData ->
            val indice = cellData.value.fila
            val filaLetra = filaALetra(indice)
            SimpleStringProperty(filaLetra)
        }
        columnaButacasColumn.setCellValueFactory { cellData ->
            val indice = cellData.value.columna + 1
            SimpleStringProperty(indice.toString())
        }
        precioButacasColumn.setCellValueFactory { cellData ->
            val precio = cellData.value.tipoButaca.precio
            SimpleStringProperty(precio.toString())
        }
        butacasTable.columns.forEach {
            it.isResizable = false
            it.style = "-fx-font-size: 15; -fx-alignment: CENTER;"
            it.isReorderable = false
        }

        cantidadButacasLabel.text = "Butacas seleccionadas: ${viewModel.state.value.butacas.size}"
        cantidadComplementosLabel.text = "Complementos seleccionados: ${viewModel.state.value.complementos.size}"
        fechaLabel.text = "Fecha de compra: ${LocalDate.now().toDefaultDateString()}"
        var total = 0.0
        viewModel.state.value.butacas.forEach { total += it.tipoButaca.precio }
        viewModel.state.value.complementos.toList().forEach { total += (it.first.precio * it.second) }
        precioTotalLabel.text = "Precio total: $total €"
        usernameField.text = loginViewModel.state.value.currentCliente.nombre
    }

    private fun initEventos() {
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
        confirmarCompraButton.setOnAction {
            viewModel.realizarCompra(loginViewModel.state.value.currentCliente)
            viewModel.imprimirHtml(loginViewModel.state.value.currentCliente.email)
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
        cancelarCompraButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
    }

    private fun filaALetra(fila: Int): String {
        return when (fila) {
            0 -> "A"
            1 -> "B"
            2 -> "C"
            3 -> "D"
            else -> "E"
        }
    }
}