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

private val logger = logging()

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
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
        } else {
            val encryptedPassword = encryptPassword(passwordField.text)
            viewModel.validarCliente(userNameField.text, encryptedPassword).onSuccess {
                logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
                RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
            }.onFailure {
                logger.debug { "Intento de acceso incorrecto" }
                showAlertOperacion(alerta = Alert.AlertType.ERROR,"Error en el acceso, usuario no válido", it.message)
            }
        }
    }
    private fun encryptPassword(text: String): String {
        return text
        // TODO aquí llamar a función que encripte nuestra cadena
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