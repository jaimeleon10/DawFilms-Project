package org.example.dawfilmsinterface.cine.controllers.admin.actualizarButaca

import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.Stage
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

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
class EditarButacaController {

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
        saveButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            stage.close()
        }
        cancelButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            stage.close()
        }
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}"
            stage.close()
        }
        cleanButton.setOnAction {
            precioSpinner.valueFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 25.0, 5.0)
        }
    }
}