package org.example.dawfilmsinterface.cine.controllers.admin.listadoComplementos

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.image.ImageView
import javafx.stage.Stage
import org.example.dawfilmsinterface.productos.viewmodels.GestionComplementosViewModel.TipoOperacion.EDITAR
import org.example.dawfilmsinterface.productos.viewmodels.GestionComplementosViewModel.TipoOperacion.NUEVO
import org.example.dawfilmsinterface.productos.viewmodels.GestionComplementosViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
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

        initValues()

        initEventos()
    }

    @FXML
    private fun initValues() {
        logger.debug { "InitValues" }
        idField.text = viewModel.state.value.complemento.id
        idField.isEditable = false

        nombreField.text = viewModel.state.value.complemento.nombre

        categoriaComboBox.items = FXCollections.observableList(viewModel.state.value.typesCategoria)
        categoriaComboBox.value = viewModel.state.value.complemento.categoria

        stockSpinner.valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500,20)
        priceSpinner.valueFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 25.0, 3.0)

        imagenImage.image = viewModel.state.value.complemento.imagen
    }

    @FXML
    private fun initEventos() {
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            stage.close()
        }
        saveButton.setOnAction { onGuardarAction() }
        cancelButton.setOnAction { onCancelarAction() }
        cleanButton.setOnAction { onLimpiarAction() }
    }

    @FXML
    private fun onGuardarAction(){
        logger.debug { "onGuardarAction" }

        viewModel.updateDataComplementoOperacion(
            nombre = nombreField.text,
            categoria = categoriaComboBox.value.toString(),
            stock = stockSpinner.value,
            precio = priceSpinner.value,
            imagen = imagenImage.image
        )

        when(viewModel.state.value.tipoOperacion){
            NUEVO -> viewModel.createComplemento()
            EDITAR -> viewModel.editarComplemento()
        }

        if (nombreField.text == null || nombreField.text == "") {
            showAlertOperacion(
                AlertType.ERROR,
                "Complemento no salvado",
                "El nombre no puede estar vacío"
            )
        }
    }

    @FXML
    private fun onCancelarAction() {
        logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
        stage.close()
    }

    @FXML
    private fun onLimpiarAction() {
        logger.debug { "onLimpiarAction" }
        limpiarFormulario()
    }

    @FXML
    private fun limpiarFormulario() {
        logger.debug { "limpiarFormulario" }
        nombreField.clear()
        priceSpinner.valueFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 25.0, 3.0)
        stockSpinner.valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500,20)
        categoriaComboBox.selectionModel.selectFirst()
    }

    @FXML
    private fun showAlertOperacion(
        alerta: AlertType = AlertType.CONFIRMATION,
        title: String = "",
        mensaje: String = ""
    ) {
        val alert = Alert(alerta)
        alert.title = title
        alert.contentText = mensaje
        alert.showAndWait()
    }
}