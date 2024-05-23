package org.example.dawfilmsinterface.cine.controllers.cliente.comprarEntrada

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.control.ToggleButton
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Clase controller para la selección de butacas a través de la IU.
 * Gestiona las acciones y eventos relacionados con la selección de butacas en la aplicación.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property usernameField Etiqueta que muestra el nombre de usuario.
 * @property acercaDeMenuButton Botón de menú que nos mostrará la información relevante de los desarrolladores.
 * @property backMenuMenuButton Botón de menú para regresar al menú principal.
 * @property selectedButacasLabel Etiqueta que muestra las butacas seleccionadas.
 * @property continueButton Botón para continuar con el proceso de compra.
 * @property butacaE7Button Botón de selección para la butaca E7.
 * @property butacaD7Button Botón de selección para la butaca D7.
 * @property butacaC7Button Botón de selección para la butaca C7.
 * @property butacaB7Button Botón de selección para la butaca B7.
 * @property butacaA7Button Botón de selección para la butaca A7.
 * @property butacaE6Button Botón de selección para la butaca E6.
 * @property butacaD6Button Botón de selección para la butaca D6.
 * @property butacaC6Button Botón de selección para la butaca C6.
 * @property butacaB6Button Botón de selección para la butaca B6.
 * @property butacaA6Button Botón de selección para la butaca A6.
 * @property butacaE5Button Botón de selección para la butaca E5.
 * @property butacaD5Button Botón de selección para la butaca D5.
 * @property butacaC5Button Botón de selección para la butaca C5.
 * @property butacaB5Button Botón de selección para la butaca B5.
 * @property butacaA5Button Botón de selección para la butaca A5.
 * @property butacaE4Button Botón de selección para la butaca E4.
 * @property butacaD4Button Botón de selección para la butaca D4.
 * @property butacaC4Button Botón de selección para la butaca C4.
 * @property butacaB4Button Botón de selección para la butaca B4.
 * @property butacaA4Button Botón de selección para la butaca A4.
 * @property butacaE3Button Botón de selección para la butaca E3.
 * @property butacaD3Button Botón de selección para la butaca D3.
 * @property butacaC3Button Botón de selección para la butaca C3.
 * @property butacaB3Button Botón de selección para la butaca B3.
 * @property butacaA3Button Botón de selección para la butaca A3.
 * @property butacaE2Button Botón de selección para la butaca E2.
 * @property butacaD2Button Botón de selección para la butaca D2.
 * @property butacaC2Button Botón de selección para la butaca C2.
 * @property butacaB2Button Botón de selección para la butaca B2.
 * @property butacaA2Button Botón de selección para la butaca A2.
 * @property butacaE1Button Botón de selección para la butaca E1.
 * @property butacaD1Button Botón de selección para la butaca D1.
 * @property butacaC1Button Botón de selección para la butaca C1.
 * @property butacaB1Button Botón de selección para la butaca B1.
 * @property butacaA1Button Botón de selección para la butaca A1.
 */
class SeleccionButacasController {
    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    lateinit var selectedButacasLabel: Label

    @FXML
    lateinit var continueButton: Button

    @FXML
    lateinit var butacaE7Button: ToggleButton

    @FXML
    lateinit var butacaD7Button: ToggleButton

    @FXML
    lateinit var butacaC7Button: ToggleButton

    @FXML
    lateinit var butacaB7Button: ToggleButton

    @FXML
    lateinit var butacaA7Button: ToggleButton

    @FXML
    lateinit var butacaE6Button: ToggleButton

    @FXML
    lateinit var butacaD6Button: ToggleButton

    @FXML
    lateinit var butacaC6Button: ToggleButton

    @FXML
    lateinit var butacaB6Button: ToggleButton

    @FXML
    lateinit var butacaA6Button: ToggleButton

    @FXML
    lateinit var butacaE5Button: ToggleButton

    @FXML
    lateinit var butacaD5Button: ToggleButton

    @FXML
    lateinit var butacaC5Button: ToggleButton

    @FXML
    lateinit var butacaB5Button: ToggleButton

    @FXML
    lateinit var butacaA5Button: ToggleButton

    @FXML
    lateinit var butacaE4Button: ToggleButton

    @FXML
    lateinit var butacaD4Button: ToggleButton

    @FXML
    lateinit var butacaC4Button: ToggleButton

    @FXML
    lateinit var butacaB4Button: ToggleButton

    @FXML
    lateinit var butacaA4Button: ToggleButton

    @FXML
    lateinit var butacaE3Button: ToggleButton

    @FXML
    lateinit var butacaD3Button: ToggleButton

    @FXML
    lateinit var butacaC3Button: ToggleButton

    @FXML
    lateinit var butacaB3Button: ToggleButton

    @FXML
    lateinit var butacaA3Button: ToggleButton

    @FXML
    lateinit var butacaE2Button: ToggleButton

    @FXML
    lateinit var butacaD2Button: ToggleButton

    @FXML
    lateinit var butacaC2Button: ToggleButton

    @FXML
    lateinit var butacaB2Button: ToggleButton

    @FXML
    lateinit var butacaA2Button: ToggleButton

    @FXML
    lateinit var butacaE1Button: ToggleButton

    @FXML
    lateinit var butacaD1Button: ToggleButton

    @FXML
    lateinit var butacaC1Button: ToggleButton

    @FXML
    lateinit var butacaB1Button: ToggleButton

    @FXML
    lateinit var butacaA1Button: ToggleButton
    /**
     * Función que inicializa la vista de selección de butacas.
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
        continueButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.SELECCION_COMPLEMENTOS}" }
            RoutesManager.changeScene(view = RoutesManager.View.SELECCION_COMPLEMENTOS)
        }
    }
}