package org.example.dawfilmsinterface.cine.controllers.admin.actualizarButaca

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.image.ImageView
import javafx.stage.Stage
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.viewmodels.ActualizarButacaViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import org.example.dawfilmsinterface.productos.viewmodels.ActualizarButacaViewModel.TipoOperacion.EDITAR

private val logger = logging()

/**
 * Clase controller para la edición de las butacas a través de la IU
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @see org.example.dawfilmsinterface.productos.models.butacas
 * @property precioSpinner Spinner para la seleccion del precio de la butaca
 * @property ocupacionComboBox Combo box que nos indicara si la butaca esta ocupada o no
 * @property tipoComboBox Combo box para indicar el tipo de butaca que será
 * @property estadoComboBox Combo box para seleccionar el estado de la butaca
 * @property idSelectedField Campo de texto que nos indicará el id de la butaca
 * @property saveButton Botón que nos guardara los cambios realizados
 * @property cleanButton Botón que eliminara toda la información de los campos
 * @property cancelButton Botón para cancelar la edición y volver a la vista anterior
 * @property acercaDeMenuButton Botón de menú que nos mostrará la información relevante de los desarrolladores
 * @property backMenuMenuButton Botón que nos llevara de regreso al menú
 */
class EditarButacaController : KoinComponent {

    val viewModel: ActualizarButacaViewModel by inject()

    @FXML
    lateinit var precioSpinner: Spinner<Double>

    @FXML
    lateinit var ocupacionComboBox: ComboBox<String>

    @FXML
    lateinit var tipoComboBox: ComboBox<String>

    @FXML
    lateinit var estadoComboBox: ComboBox<String>

    @FXML
    lateinit var idSelectedField: TextField

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
    lateinit var imagenImage : ImageView

    private lateinit var stage: Stage
    fun setStage(stage: Stage) {
        this.stage = stage
    }
    /**
     * Función que inicializa la vista de editar butaca
     * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    @FXML
    private fun initialize() {
        logger.debug { "Inicializando EditarButacaController FXML en Modo: ${viewModel.state.value.tipoOperacion}" }

        idSelectedField.isEditable = false

        initValues()

        initEventos()
    }

    private fun initValues() {
        logger.debug { "InitValues" }
        idSelectedField.text = viewModel.state.value.butaca.id
        estadoComboBox.items = FXCollections.observableList(viewModel.state.value.typesEstado)
        tipoComboBox.items = FXCollections.observableList(viewModel.state.value.typesTipo)
        ocupacionComboBox.items = FXCollections.observableList(viewModel.state.value.typesOcupacion)
        precioSpinner.valueFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 25.0, 5.0)
    }

    private fun initEventos(){
        saveButton.setOnAction { onGuardarAction() }
        cancelButton.setOnAction {onCancelarAction()}
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        cleanButton.setOnAction { onLimpiarAction() }
        backMenuMenuButton.setOnAction {
            "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}"
            stage.close()
        }
    }

    private fun onGuardarAction(){
        logger.debug { "onGuardarAction" }

        viewModel.updateDataButacaOperacion(
            estado = estadoComboBox.value,
            tipo = tipoComboBox.value,
            ocupacion = ocupacionComboBox.value,
            precio = precioSpinner.value,
            imagen = imagenImage.image
        )

        when(viewModel.state.value.tipoOperacion){
            EDITAR -> viewModel.editarButaca()
        }

        logger.debug { "Butaca salvada correctamente" }
        showAlertOperacion(
            AlertType.INFORMATION,
            "Butaca salvada",
            "Butaca salvada"
        )
        stage.close()

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
        estadoComboBox.selectionModel.selectFirst()
        tipoComboBox.selectionModel.selectFirst()
        ocupacionComboBox.selectionModel.selectFirst()
        precioSpinner.valueFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 25.0, 0.0)
    }

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