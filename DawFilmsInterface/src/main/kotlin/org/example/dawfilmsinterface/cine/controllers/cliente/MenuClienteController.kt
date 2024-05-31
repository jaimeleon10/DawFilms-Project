package org.example.dawfilmsinterface.cine.controllers.cliente

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import org.example.dawfilmsinterface.cine.viewmodels.LoginViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Clase controller para el menú del cliente a través de la IU.
 * Gestiona las acciones y eventos relacionados con las diferentes funcionalidades del menú del cliente en la aplicación.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property usernameField Etiqueta que muestra el nombre de usuario.
 * @property complementsListButton Botón para acceder al listado de complementos disponibles.
 * @property exitMenuButton Botón de menú para salir de la aplicación.
 * @property acercaDeMenuButton Botón de menú que nos mostrará la información relevante de los desarrolladores.
 * @property exitButton Botón para cerrar la sesión y regresar a la vista de login.
 * @property showCinemaButton Botón para mostrar el estado del cine.
 * @property buyTicketButton Botón para comprar entradas.
 */
class MenuClienteController: KoinComponent {

    val loginViewModel: LoginViewModel by inject()

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var complementsListButton: Button

    @FXML
    lateinit var exitMenuButton: MenuItem

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var exitButton: Button

    @FXML
    lateinit var showCinemaButton: Button

    @FXML
    lateinit var buyTicketButton: Button

    /**
     * Función que inicializa la vista del menú del cliente.
     * Asigna las acciones a los botones y elementos de menú.
     * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    @FXML
    private fun initialize() {
        usernameField.text = loginViewModel.state.value.currentCliente.nombre
        exitButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.LOGIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.LOGIN)
        }
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        exitMenuButton.setOnAction { RoutesManager.onAppExit() }
        buyTicketButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.SELECCION_BUTACAS}" }
            RoutesManager.changeScene(view = RoutesManager.View.SELECCION_BUTACAS)
        }
        complementsListButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.LISTADO_COMPLEMENTOS_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.LISTADO_COMPLEMENTOS_CLIENTE)
        }
        showCinemaButton.setOnAction {
            loginViewModel.state.value.isAdmin = false
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MOSTRAR_ESTADO_CINE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MOSTRAR_ESTADO_CINE)
        }
    }
}