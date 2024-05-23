package org.example.dawfilmsinterface.cine.controllers.admin.listadoComplementos

import javafx.fxml.FXML
import javafx.scene.control.*
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Clase controller para la administración de complementos a través de la IU.
 * Gestiona las acciones y eventos relacionados con la vista de administración de complementos en la aplicación.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @see org.example.dawfilmsinterface.productos.models.complementos
 * @property backMenuButton Botón para regresar al menú anterior.
 * @property deleteButton Botón para eliminar un complemento.
 * @property editButton Botón para editar un complemento.
 * @property addButton Botón para añadir un nuevo complemento.
 * @property stockSelectedField Campo de texto para el stock del complemento seleccionado.
 * @property precioSelectedField Campo de texto para el precio del complemento seleccionado.
 * @property nombreSelectedField Campo de texto para el nombre del complemento seleccionado.
 * @property idSelectedField Campo de texto para el ID del complemento seleccionado.
 * @property usernameField Etiqueta que muestra el nombre de usuario.
 * @property acercaDeMenuButton Elemento de menú para la opción "Acerca de".
 * @property backMenuMenuButton Elemento de menú para regresar al menú anterior.
 * @property complementosTable Tabla que muestra los complementos.
 */
class ListadoComplementosAdminController {
    @FXML
    lateinit var backMenuButton: Button

    @FXML
    lateinit var deleteButton: Button

    @FXML
    lateinit var editButton: Button

    @FXML
    lateinit var addButton: Button

    @FXML
    lateinit var stockSelectedField: TextField

    @FXML
    lateinit var precioSelectedField: TextField

    @FXML
    lateinit var nombreSelectedField: TextField

    @FXML
    lateinit var idSelectedField: TextField

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    @FXML
    lateinit var complementosTable: TableView<Any>

    /**
     * Función que inicializa la vista de administración de complementos.
     * Asigna las acciones a los botones y elementos de menú.
     * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    @FXML
    private fun initialize() {
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
        }
        addButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.EDITAR_COMPLEMENTO}" }
            RoutesManager.initEditarComplemento("AÑADIR COMPLEMENTO")
        }
        editButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.EDITAR_COMPLEMENTO}" }
            RoutesManager.initEditarComplemento("EDITAR COMPLEMENTO")
        }
        backMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
        }
    }
}