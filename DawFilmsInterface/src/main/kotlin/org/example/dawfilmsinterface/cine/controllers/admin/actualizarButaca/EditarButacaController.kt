package org.example.dawfilmsinterface.cine.controllers.admin.actualizarButaca

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.Stage
import org.example.dawfilmsinterface.cine.viewmodels.GestionButacaViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import org.example.dawfilmsinterface.cine.viewmodels.GestionButacaViewModel.TipoOperacion.EDITAR
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca

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

    val viewModel: GestionButacaViewModel by inject()

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

        initValues()

        initEventos()
    }

    @FXML
    private fun initValues() {
        logger.debug { "InitValues" }
        idSelectedField.text = viewModel.state.value.butaca.id
        idSelectedField.isEditable = false

        estadoComboBox.items = FXCollections.observableList(viewModel.state.value.typesEstado)
        estadoComboBox.value = viewModel.state.value.butaca.estado

        tipoComboBox.items = FXCollections.observableList(viewModel.state.value.typesTipo)
        tipoComboBox.value = viewModel.state.value.butaca.tipo

        ocupacionComboBox.items = FXCollections.observableList(viewModel.state.value.typesOcupacion)
        ocupacionComboBox.value = viewModel.state.value.butaca.ocupacion

        imagenImage.image = if (viewModel.state.value.butaca.tipo == "VIP") Image(RoutesManager.getResourceAsStream("icons/butacaSeleccionadaVIP.png")) else Image(RoutesManager.getResourceAsStream("icons/butacaSeleccionada.png"))
    }

    @FXML
    private fun initEventos(){
        saveButton.setOnAction {
            onGuardarAction()
            stage.close()
        }
        cancelButton.setOnAction {onCancelarAction()}
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        cleanButton.setOnAction { onLimpiarAction() }
        backMenuMenuButton.setOnAction {
            "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}"
            stage.close()
        }
    }

    @FXML
    private fun onGuardarAction(){
        logger.debug { "onGuardarAction" }

        viewModel.updateDataButacaOperacion(
            estado = estadoComboBox.value,
            tipo = tipoComboBox.value,
            ocupacion = ocupacionComboBox.value,
            precio = 5.0,
            imagen = viewModel.state.value.butaca.imagen
        )


        if (viewModel.state.value.tipoOperacion == EDITAR){
            viewModel.editarButaca()
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
        estadoComboBox.selectionModel.selectFirst()
        tipoComboBox.selectionModel.selectFirst()
        ocupacionComboBox.selectionModel.selectFirst()
    }
}