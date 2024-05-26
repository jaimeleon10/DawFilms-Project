package org.example.dawfilmsinterface.cine.controllers.cliente.comprarEntrada

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.control.ToggleButton
import org.example.dawfilmsinterface.productos.viewmodels.SeleccionarButacaViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import javafx.scene.image.*
import org.example.dawfilmsinterface.cine.viewModels.LoginViewModel
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.productos.viewmodels.CarritoViewModel

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
class SeleccionButacasController: KoinComponent {
    val viewModel: SeleccionarButacaViewModel by inject()

    val viewModelLogin: LoginViewModel by inject()

    val carritoViewModel: CarritoViewModel by inject()

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

    val botonesButacas: MutableList<ToggleButton> = mutableListOf()
    var contadorButacasSeleccionadas = 0

    /**
     * Función que inicializa la vista de selección de butacas.
     * Asigna las acciones a los botones y elementos de menú.
     * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
     * @since 1.0.0
     */
    @FXML
    private fun initialize() {
        logger.debug { "Inicializando ActualizarButacaController FXML" }

        viewModel.state.set(SeleccionarButacaViewModel.ButacaSeleccionadaState())

        initDefaultValues()

        initEventos()
    }

    private fun initDefaultValues() {
        botonesButacas.addAll(
            listOf(
                butacaA1Button, butacaA2Button, butacaA3Button, butacaA4Button, butacaA5Button, butacaA6Button, butacaA7Button,
                butacaB1Button, butacaB2Button, butacaB3Button, butacaB4Button, butacaB5Button, butacaB6Button, butacaB7Button,
                butacaC1Button, butacaC2Button, butacaC3Button, butacaC4Button, butacaC5Button, butacaC6Button, butacaC7Button,
                butacaD1Button, butacaD2Button, butacaD3Button, butacaD4Button, butacaD5Button, butacaD6Button, butacaD7Button,
                butacaE1Button, butacaE2Button, butacaE3Button, butacaE4Button, butacaE5Button, butacaE6Button, butacaE7Button
            )
        )

        for (boton in botonesButacas) {
            viewModel.state.value.id = boton.id.substring(6, boton.id.length - 6)
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
    }

    private fun initEventos() {
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backMenuMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
        continueButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.SELECCION_COMPLEMENTOS}" }
            RoutesManager.changeScene(view = RoutesManager.View.SELECCION_COMPLEMENTOS)
        }
        usernameField.text = viewModelLogin.state.value.currentCliente.nombre

        for (boton in botonesButacas) {
            boton.setOnAction {
                if (!boton.isDisable) {
                    changeButtonIcon(boton, boton.isSelected)
                    if (carritoViewModel.state.value.listadoButacasSeleccionadas.isEmpty()) selectedButacasLabel.text = "Butacas seleccionadas: "
                    else selectedButacasLabel.text = "Butacas seleccionadas: ${carritoViewModel.state.value.listadoButacasSeleccionadas}"
                }
            }
        }
    }

    private fun changeButtonIcon(boton: ToggleButton, seleccionado: Boolean) {
        if (seleccionado) {
            if (contadorButacasSeleccionadas in 0..4) {
                viewModel.state.value.id = boton.id.substring(6, boton.id.length - 6)
                viewModel.cambiarIcono()
                val newIcon = ImageView(viewModel.state.value.icono)
                newIcon.fitWidth = 24.0
                newIcon.fitHeight = 24.0
                boton.graphic = newIcon
                carritoViewModel.state.value.listadoButacasSeleccionadas.add(boton.id.substring(6, boton.id.length - 6))
                contadorButacasSeleccionadas += 1
            } else {
                boton.isSelected = false
            }
        } else {
            val newIcon = ImageView(Image(RoutesManager.getResourceAsStream("icons/butacaSinSeleccionar.png")))
            newIcon.fitWidth = 24.0
            newIcon.fitHeight = 24.0
            boton.graphic = newIcon
            carritoViewModel.state.value.listadoButacasSeleccionadas.remove(boton.id.substring(6, boton.id.length - 6))
            contadorButacasSeleccionadas -= 1
        }
    }
}