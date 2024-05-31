package org.example.dawfilmsinterface.cine.controllers.loginRegister

import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.input.KeyCode
import org.example.dawfilmsinterface.cine.viewmodels.RegistroViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDate

private val logger = logging()

/**
 * Clase controller para la pantalla de registro de usuarios.
 * Gestiona las acciones y eventos relacionados con el registro de nuevos usuarios.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property viewModel ViewModel que gestiona el estado y la lógica del registro de usuarios.
 * @property backLoginButton Botón para volver a la pantalla de inicio de sesión.
 * @property birthDatePicker Selector de fecha de nacimiento del usuario.
 * @property emailField Campo de texto para ingresar el correo electrónico del usuario.
 * @property dniField Campo de texto para ingresar el número de identificación del usuario.
 * @property surnameField Campo de texto para ingresar el apellido del usuario.
 * @property checkPassField Campo de contraseña para confirmar la contraseña ingresada.
 * @property continueButton Botón para continuar con el registro.
 * @property volverLoginMenuButton Menú de opción para volver a la pantalla de inicio de sesión.
 * @property acercaDeMenuButton Menú de opción para mostrar información sobre los desarrolladores.
 * @property nameField Campo de texto para ingresar el nombre del usuario.
 * @property passwordField Campo de contraseña para ingresar la contraseña del usuario.
 */
class RegisterController : KoinComponent {
    private val viewModel : RegistroViewModel by inject()

    @FXML
    lateinit var backLoginButton: Button

    @FXML
    lateinit var birthDatePicker: DatePicker

    @FXML
    lateinit var emailField: TextField

    @FXML
    lateinit var dniField: TextField

    @FXML
    lateinit var surnameField: TextField

    @FXML
    lateinit var checkPassField: PasswordField

    @FXML
    lateinit var continueButton: Button

    @FXML
    lateinit var volverLoginMenuButton: MenuItem

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var nameField: TextField

    @FXML
    lateinit var passwordField: PasswordField

    @FXML
    private fun initialize(){
        initValues()
        initEventos()
    }

    @FXML
    private fun initValues() {
        logger.debug { "Inicializando el controlador de registro" }

        birthDatePicker.value = LocalDate.now()
    }

    @FXML
    private fun initEventos() {
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        volverLoginMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.LOGIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.LOGIN)
        }
        backLoginButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.LOGIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.LOGIN)
        }
        nameField.setOnKeyPressed { event ->
            if (event.code == KeyCode.ENTER) {
                continueButton.fire()
                event.consume()
            }
        }
        surnameField.setOnKeyPressed { event ->
            if (event.code == KeyCode.ENTER) {
                continueButton.fire()
                event.consume()
            }
        }
        emailField.setOnKeyPressed { event ->
            if (event.code == KeyCode.ENTER) {
                continueButton.fire()
                event.consume()
            }
        }
        passwordField.setOnKeyPressed { event ->
            if (event.code == KeyCode.ENTER) {
                continueButton.fire()
                event.consume()
            }
        }
        checkPassField.setOnKeyPressed { event ->
            if (event.code == KeyCode.ENTER) {
                continueButton.fire()
                event.consume()
            }
        }
        dniField.setOnKeyPressed { event ->
            if (event.code == KeyCode.ENTER) {
                continueButton.fire()
                event.consume()
            }
        }
        continueButton.setOnAction {
            logger.debug { "Validando campos del registro" }
            if (viewModel.validarCampos(
                    enteredNombre = nameField.text,
                    enteredApellido = surnameField.text,
                    enteredEmail = emailField.text,
                    enteredPass = passwordField.text,
                    enteredConfPass = checkPassField.text,
                    enteredDni = dniField.text.uppercase(),
                    selectedDate = birthDatePicker.value
                )
            ) {
                logger.debug { "Registrando cliente" }
                viewModel.registerCliente()

                logger.debug { "Cambiando de escena a ${RoutesManager.View.LOGIN}" }
                RoutesManager.changeScene(view = RoutesManager.View.LOGIN)
            }
        }
    }
}