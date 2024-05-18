package org.example.dawfilmsinterface.cine.controllers.loginRegister

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.MenuItem
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class LoginController {
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
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_ADMIN)
        } else {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.MENU_CINE_CLIENTE}" }
            RoutesManager.changeScene(view = RoutesManager.View.MENU_CINE_CLIENTE)
        }
    }
}