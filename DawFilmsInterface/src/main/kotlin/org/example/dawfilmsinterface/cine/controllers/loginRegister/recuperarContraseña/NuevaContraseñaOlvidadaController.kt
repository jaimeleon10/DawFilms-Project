package org.example.dawfilmsinterface.cine.controllers.loginRegister.recuperarContraseña

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.MenuItem
import javafx.scene.control.PasswordField
import javafx.stage.Stage
import org.example.dawfilmsinterface.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class NuevaContraseñaOlvidadaController {
    @FXML
    lateinit var backLoginButton: Button

    @FXML
    lateinit var checkNewPassField: PasswordField

    @FXML
    lateinit var newPassField: PasswordField

    @FXML
    lateinit var continueButton: Button

    @FXML
    lateinit var backLoginMenuButton: MenuItem

    @FXML
    lateinit var acercaDeMenuButton: MenuItem

    // Utilizar viewModel cuando se implemente
    private lateinit var stage: Stage
    fun setStage(stage: Stage) {
        this.stage = stage
    }

    @FXML
    private fun initialize() {
        continueButton.setOnAction { stage.close() }
        backLoginButton.setOnAction {
            RoutesManager.initCodigoRecuperarPass()
            stage.close()
        }
        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }
        backLoginMenuButton.setOnAction { stage.close() }
    }
}