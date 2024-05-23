package org.example.dawfilmsinterface.cine.controllers.cliente

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.control.TableView
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Clase controller para el listado de complementos a través de la IU.
 * Gestiona las acciones y eventos relacionados con la visualización del listado de complementos en la aplicación.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property backMenuButton Botón para regresar al menú principal.
 * @property usernameField Etiqueta que muestra el nombre de usuario.
 * @property acercaDeMenuButton Botón de menú que nos mostrará la información relevante de los desarrolladores.
 * @property backMenuMenuButton Botón de menú para regresar al menú principal.
 * @property complementosTable Tabla que muestra el listado de complementos.
 */
class ListadoComplementosController {
    @FXML
    lateinit var backMenuButton: Button

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    lateinit var complementosTable: TableView<Any>

    /**
     * Función que inicializa la vista del listado de complementos.
     * Asigna las acciones a los botones y elementos de menú.
     * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    @FXML
    private fun initialize() {
        backMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
    }
}