package org.example.dawfilmsinterface.cine.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Clase controller para la visualización del estado del cine a través de la IU.
 * Gestiona las acciones y eventos relacionados con la visualización del estado del cine en la aplicación.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property backMenuButton Botón para regresar al menú principal dependiendo del tipo de usuario.
 * @property usernameField Etiqueta que muestra el nombre de usuario.
 * @property acercaDeMenuButton Botón de menú para mostrar la información relevante de los desarrolladores.
 * @property backMenuMenuButton Botón de menú para regresar al menú principal dependiendo del tipo de usuario.
 */
class EstadoCineController {
    @FXML
    lateinit var backMenuButton: Button

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    /**
     * Función que inicializa la vista del estado del cine.
     * Asigna las acciones al botón de regreso al menú principal y a los elementos de menú.
     */
    @FXML
    private fun initialize() {
        backMenuButton.setOnAction {
            if (usernameField.text == "admin") {
                logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
                RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
            } else {
                logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
                RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
            }
        }
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            if (usernameField.text == "admin") {
                logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
                RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
            } else {
                logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
                RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
            }
        }
    }
}