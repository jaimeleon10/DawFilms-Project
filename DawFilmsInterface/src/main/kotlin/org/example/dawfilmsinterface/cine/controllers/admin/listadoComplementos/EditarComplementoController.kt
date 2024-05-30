package org.example.dawfilmsinterface.cine.controllers.admin.listadoComplementos

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.image.ImageView
import javafx.stage.Stage
import org.example.dawfilmsinterface.cine.viewmodels.GestionComplementosViewModel
import org.example.dawfilmsinterface.cine.viewmodels.GestionComplementosViewModel.TipoOperacion.*
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.logger.MESSAGE
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Clase controller para la edición de las butacas a través de la IU
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @see org.example.dawfilmsinterface.productos.models.complementos
 * @property categoriaComboBox Combo box que nos indicara la categoria del complemento
 * @property precioSpinner Spinner para la seleccion del precio del complemento
 * @property stockSpinner Spinner para la seleccion del stock del complemento
 * @property nombreField Campo de texto que nos indicara el nombre del complemento.
 * @property idField Campo de texto que nos indicará el id del complemento
 * @property saveButton Botón que nos guardara los cambios realizados
 * @property cleanButton Botón que eliminara toda la información de los campos
 * @property cancelButton Botón para cancelar la edición y volver a la vista anterior
 * @property acercaDeMenuButton Botón de menú que nos mostrará la información relevante de los desarrolladores
 * @property backMenuMenuButton Botón que nos llevara de regreso al menú
 * @property tituloLabel Label que nos indica la accion que estamos realizando
 */
class EditarComplementoController : KoinComponent {
    private val viewModel: GestionComplementosViewModel by inject()

    @FXML
    lateinit var categoriaComboBox: ComboBox<Any>

    @FXML
    lateinit var priceSpinner: Spinner<Double>

    @FXML
    lateinit var stockSpinner: Spinner<Int>

    @FXML
    lateinit var nombreField: TextField

    @FXML
    lateinit var idField: TextField

    @FXML
    lateinit var saveButton: Button

    @FXML
    lateinit var cleanButton: Button

    @FXML
    lateinit var cancelButton: Button

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    lateinit var tituloLabel: Label

    @FXML
    lateinit var imagenImage : ImageView

    @FXML
    lateinit var disponibleComboBox: ComboBox<String>

    private lateinit var stage: Stage
    fun setStage(stage: Stage) {
        this.stage = stage
    }
    /**
     * Función que inicializa la vista de editar los complementos
     * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */

    @FXML
    private fun initialize() {
        logger.debug { "Inicializando EditarComplementoController FXML en Modo: ${viewModel.state.value.tipoOperacion}" }

        initDefaultValues()

        initEventos()
    }

    private fun initDefaultValues() {
        logger.debug { "InitValues" }

        viewModel.loadTypes()

        idField.text = viewModel.state.value.complemento.id
        nombreField.text = viewModel.state.value.complemento.nombre

        categoriaComboBox.items = FXCollections.observableList(viewModel.state.value.typesCategoria)
        categoriaComboBox.value = viewModel.state.value.complemento.categoria

        disponibleComboBox.items = FXCollections.observableArrayList(viewModel.state.value.availability)
        disponibleComboBox.value = if (viewModel.state.value.complemento.isDeleted) "NO" else "SI"

        stockSpinner.valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500, viewModel.state.value.complemento.stock)
        priceSpinner.valueFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(1.00, 25.00, viewModel.state.value.complemento.precio, 0.5)

        imagenImage.image = viewModel.state.value.complemento.imagen
    }

    private fun initEventos() {
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            stage.close()
        }
        saveButton.setOnAction {
            onGuardarAction()
            stage.close()
        }
        cancelButton.setOnAction { onCancelarAction() }
        cleanButton.setOnAction { onLimpiarAction() }
    }

    private fun onGuardarAction(){
        logger.debug { "Guardando complemento" }

        viewModel.updateDataComplementoOperacion(
            id = idField.text,
            nombre = nombreField.text,
            precio = priceSpinner.value,
            stock = stockSpinner.value,
            categoria = categoriaComboBox.value.toString(),
            imagen = imagenImage.image,
            isDeleted = disponibleComboBox.value == "NO"
        )

        if (nombreField.text == null || nombreField.text.isEmpty()) {
            showAlertOperacion(
                AlertType.ERROR,
                title = "Complemento no salvado",
                header = "El nombre no puede estar vacío"
            )
        } else {
            when (viewModel.state.value.tipoOperacion) {
                NUEVO -> viewModel.createComplemento()
                EDITAR -> viewModel.editarComplemento()
            }
        }


    }

    private fun onCancelarAction() {
        logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
        stage.close()
    }

    private fun onLimpiarAction() {
        logger.debug { "onLimpiarAction" }
        limpiarFormulario()
    }

    private fun limpiarFormulario() {
        logger.debug { "limpiarFormulario" }
        nombreField.clear()
        priceSpinner.valueFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 100.0, 1.0, 0.5)
        stockSpinner.valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500,1)
        categoriaComboBox.selectionModel.selectLast()
        disponibleComboBox.selectionModel.selectFirst()
    }

    private fun showAlertOperacion(
        alerta: AlertType = AlertType.CONFIRMATION,
        title: String = "",
        header: String = "",
        message: String = ""
    ) {
        val alert = Alert(alerta)
        alert.title = title
        alert.headerText = header
        alert.contentText = message
        alert.showAndWait()
    }
}