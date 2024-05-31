package org.example.dawfilmsinterface.cine.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.control.ToggleButton
import javafx.scene.image.ImageView
import org.example.dawfilmsinterface.cine.viewmodels.LoginViewModel
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.cine.viewmodels.EstadoCineViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
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
class EstadoCineController: KoinComponent {
    val viewModel: EstadoCineViewModel by inject()

    val loginViewModel: LoginViewModel by inject()

    @FXML
    lateinit var e7Button: ToggleButton

    @FXML
    lateinit var d7Button: ToggleButton

    @FXML
    lateinit var c7Button: ToggleButton

    @FXML
    lateinit var b7Button: ToggleButton

    @FXML
    lateinit var a7Button: ToggleButton

    @FXML
    lateinit var e6Button: ToggleButton

    @FXML
    lateinit var d6Button: ToggleButton

    @FXML
    lateinit var c6Button: ToggleButton

    @FXML
    lateinit var b6Button: ToggleButton

    @FXML
    lateinit var a6Button: ToggleButton

    @FXML
    lateinit var e5Button: ToggleButton

    @FXML
    lateinit var d5Button: ToggleButton

    @FXML
    lateinit var c5Button: ToggleButton

    @FXML
    lateinit var b5Button: ToggleButton

    @FXML
    lateinit var a5Button: ToggleButton

    @FXML
    lateinit var e4Button: ToggleButton

    @FXML
    lateinit var d4Button: ToggleButton

    @FXML
    lateinit var c4Button: ToggleButton

    @FXML
    lateinit var b4Button: ToggleButton

    @FXML
    lateinit var a4Button: ToggleButton

    @FXML
    lateinit var e3Button: ToggleButton

    @FXML
    lateinit var d3Button: ToggleButton

    @FXML
    lateinit var c3Button: ToggleButton

    @FXML
    lateinit var b3Button: ToggleButton

    @FXML
    lateinit var a3Button: ToggleButton

    @FXML
    lateinit var e2Button: ToggleButton

    @FXML
    lateinit var d2Button: ToggleButton

    @FXML
    lateinit var c2Button: ToggleButton

    @FXML
    lateinit var b2Button: ToggleButton

    @FXML
    lateinit var a2Button: ToggleButton

    @FXML
    lateinit var e1Button: ToggleButton

    @FXML
    lateinit var d1Button: ToggleButton

    @FXML
    lateinit var c1Button: ToggleButton

    @FXML
    lateinit var b1Button: ToggleButton

    @FXML
    lateinit var a1Button: ToggleButton

    @FXML
    lateinit var backMenuButton: Button

    @FXML
    lateinit var usernameField: Label

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var backMenuMenuButton: MenuItem

    val botonesButacas: MutableList<ToggleButton> = mutableListOf()

    /**
     * Función que inicializa la vista del estado del cine.
     * Asigna las acciones al botón de regreso al menú principal y a los elementos de menú.
     */
    @FXML
    private fun initialize() {

        logger.debug { "Inicializando EstadoCineController FXML" }

        initDefaultValues()

        initEventos()
    }

    private fun initDefaultValues() {
        botonesButacas.addAll(
            listOf(
                a1Button, a2Button, a3Button, a4Button, a5Button, a6Button, a7Button,
                b1Button, b2Button, b3Button, b4Button, b5Button, b6Button, b7Button,
                c1Button, c2Button, c3Button, c4Button, c5Button, c6Button, c7Button,
                d1Button, d2Button, d3Button, d4Button, d5Button, d6Button, d7Button,
                e1Button, e2Button, e3Button, e4Button, e5Button, e6Button, e7Button
            )
        )

        for (boton in botonesButacas) {
            viewModel.state.value.id = boton.id.substring(0,2).uppercase()
            viewModel.iconoPorDefecto()

            if (viewModel.state.value.tipoButaca == TipoButaca.VIP) boton.style = "-fx-opacity: 1; -fx-border-color: #ffbd2e; -fx-border-width: 3; -fx-border-radius: 5; -fx-background-radius: 5; -fx-background-color: lightgray;"
            else boton.style = "-fx-opacity: 1; -fx-border-color: lightgray; -fx-border-width: 3; -fx-border-radius: 5; -fx-background-radius: 5; -fx-background-color: lightgray;"

            if (viewModel.state.value.estadoButaca == EstadoButaca.MANTENIMIENTO || viewModel.state.value.estadoButaca == EstadoButaca.FUERASERVICIO || viewModel.state.value.ocupacionButaca == OcupacionButaca.OCUPADA || viewModel.state.value.ocupacionButaca == OcupacionButaca.ENRESERVA) {
                boton.isDisable = true
            }

            val newIcon = ImageView(viewModel.state.value.icono)
            newIcon.fitWidth = 24.0
            newIcon.fitHeight = 24.0
            boton.graphic = newIcon
        }

        if (loginViewModel.state.value.isAdmin) usernameField.text = loginViewModel.state.value.currentAdmin
        else usernameField.text = loginViewModel.state.value.currentCliente.nombre
    }

    private fun initEventos() {
        backMenuButton.setOnAction {
            if (usernameField.text == "Admin") {
                logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
                RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
            } else {
                logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
                RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
            }
        }
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            if (usernameField.text == "Admin") {
                logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
                RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
            } else {
                logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
                RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
            }
        }
    }
}