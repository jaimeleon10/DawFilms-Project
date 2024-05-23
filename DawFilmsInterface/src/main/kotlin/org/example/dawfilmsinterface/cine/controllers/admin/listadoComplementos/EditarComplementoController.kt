package org.example.dawfilmsinterface.cine.controllers.admin.listadoComplementos

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
class EditarComplementoController {
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
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            stage.close()
        }
        saveButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            stage.close()
        }
        cancelButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            stage.close()
        }
        cleanButton.setOnAction {
            priceSpinner.valueFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 25.0, 5.0)
        }
    }
}