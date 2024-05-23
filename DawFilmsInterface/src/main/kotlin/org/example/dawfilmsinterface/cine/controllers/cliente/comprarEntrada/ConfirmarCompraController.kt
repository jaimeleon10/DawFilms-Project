package org.example.dawfilmsinterface.cine.controllers.cliente.comprarEntrada

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.control.TableView
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

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
class ConfirmarCompraController {
    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var butacasTable: TableView<Any>

    @FXML
    lateinit var complementosTable: TableView<Any>

    @FXML
    lateinit var cancelarCompraButton: Button

    @FXML
    lateinit var confirmarCompraButton: Button

    @FXML
    lateinit var cantidadButacasLabel: Label

    @FXML
    lateinit var cantidadComplementosLabel: Label

    /**
     * Función que inicializa la vista de confirmación de compra.
     * Asigna las acciones a los botones y elementos de menú.
     * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    @FXML
    private fun initialize() {
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
        confirmarCompraButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
        cancelarCompraButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
    }
}