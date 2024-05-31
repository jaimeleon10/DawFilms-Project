package org.example.dawfilmsinterface.cine.controllers.loginRegister

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.fxml.FXML
import javafx.scene.control.*
import org.example.dawfilmsinterface.cine.viewmodels.LoginViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import javafx.scene.input.KeyCode
import java.util.*

private val logger = logging()

/**
 * Clase controller para la pantalla de inicio de sesión.
 * Gestiona las acciones y eventos relacionados con el inicio de sesión del usuario.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property viewModel ViewModel que gestiona el estado y la lógica del inicio de sesión.
 * @property continueButton Botón para continuar con el inicio de sesión.
 * @property exitMenuButton Menú de opción para salir de la aplicación.
 * @property acercaDeMenuButton Menú de opción para mostrar información sobre los desarrolladores.
 * @property registerButton Botón para ir a la pantalla de registro.
 * @property forgotPassButton Botón para ir a la pantalla de recuperación de contraseña.
 * @property userNameField Campo de texto para ingresar el nombre de usuario.
 * @property passwordField Campo de contraseña para ingresar la contraseña.
 */
class LoginController: KoinComponent {

    val viewModel: LoginViewModel by inject()

    @FXML
    lateinit var continueButton: Button

    @FXML
    lateinit var exitMenuButton: MenuItem

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    @FXML
    lateinit var registerButton: Button

    @FXML
    lateinit var forgotPassButton: Button

    @FXML
    lateinit var userNameField: TextField

    @FXML
    lateinit var passwordField: PasswordField

    @FXML
    private fun initialize(){
        userNameField.requestFocus()
        userNameField.setOnKeyPressed { event ->
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
        continueButton.setOnAction { cargarMenu() }
        exitMenuButton.setOnAction { RoutesManager.onAppExit() }
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        registerButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.REGISTRO}" }
            RoutesManager.changeScene(view = RoutesManager.View.REGISTRO)
        }
        forgotPassButton.setOnAction { RoutesManager.initEmailRecuperarPass() }
    }

    private fun cargarMenu() {
        if (userNameField.text == "admin" && passwordField.text == "admin") {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_ADMIN}" }
            viewModel.changeAdmin()
            viewModel.state.value.isAdmin = true
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
        } else {
            val encryptedPassword = encodeBase64(passwordField.text)
            viewModel.validarCliente(userNameField.text, encryptedPassword).onSuccess {
                logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
                RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
            }.onFailure {
                logger.debug { "Intento de acceso incorrecto" }
                showAlertOperacion(alerta = Alert.AlertType.ERROR,"Error en el acceso, usuario no válido", it.message)
            }
        }
    }
    private fun encodeBase64(input: String): String {
        val encoder = Base64.getEncoder()
        val encodedBytes = encoder.encode(input.toByteArray(Charsets.UTF_8))
        return String(encodedBytes, Charsets.UTF_8)
    }

    private fun showAlertOperacion(
        alerta: Alert.AlertType = Alert.AlertType.CONFIRMATION,
        title: String = "",
        mensaje: String = ""
    ) {
        Alert(alerta).apply {
            this.title = title
            this.contentText = mensaje
        }.showAndWait()
    }
}