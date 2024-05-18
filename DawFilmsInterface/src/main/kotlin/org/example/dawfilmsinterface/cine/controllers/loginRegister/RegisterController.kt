package org.example.dawfilmsinterface.cine.controllers.loginRegister

import javafx.fxml.FXML
import javafx.scene.control.*
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class RegisterController {
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
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        volverLoginMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.LOGIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.LOGIN)
        }
        backLoginButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.LOGIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.LOGIN)
        }
        continueButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.LOGIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.LOGIN)
        }
    }
}