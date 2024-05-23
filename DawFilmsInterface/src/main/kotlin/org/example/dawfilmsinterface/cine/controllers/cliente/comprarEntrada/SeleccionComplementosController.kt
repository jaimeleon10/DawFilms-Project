package org.example.dawfilmsinterface.cine.controllers.cliente.comprarEntrada

import javafx.fxml.FXML
import javafx.scene.control.*
import org.example.dawfilmsinterface.routes.RoutesManager
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
class SeleccionComplementosController {
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
    lateinit var quantityComplementSpinner: Spinner<Double>

    @FXML
    lateinit var complementosTable: TableView<Any>

    @FXML
    lateinit var selectedComplementosLabel: Label

    @FXML
    lateinit var continueButton: Button

    @FXML
    lateinit var selectedComplementosQuantityLabel: Label

    /**
     * Función que inicializa la vista de selección de complementos.
     * Asigna las acciones a los botones y elementos de menú.
     * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    @FXML
    private fun initialize() {
        continueButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.CONFIRMAR_COMPRA}" }
            RoutesManager.changeScene(view = RoutesManager.View.CONFIRMAR_COMPRA)
        }
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }

    }
}