package org.example.dawfilmsinterface.cine.controllers.loginRegister.recuperarContraseña

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.MenuItem
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import org.example.dawfilmsinterface.cine.viewmodels.RecuperarPasswordViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class EmailContraseñaOlvidadaController: KoinComponent {

    val viewModel: RecuperarPasswordViewModel by inject()

    @FXML
    lateinit var backLoginButton: Button

    @FXML
    lateinit var emailField: TextField

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
        continueButton.setOnAction { continuar() }

        backLoginButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.LOGIN}" }
            stage.close()
        }

        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }

        backLoginMenuButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.LOGIN}" }
            stage.close()
        }

        emailField.setOnKeyPressed { event ->
            if (event.code == KeyCode.ENTER) {
                continueButton.fire()
                event.consume()
            }
        }
    }

    private fun continuar() {
        viewModel.validateUserEmail(emailField.text)
            .onSuccess {
                logger.debug { "Validado email del usuario" }
                showAlertOperacion(alerta = Alert.AlertType.INFORMATION,
                    title="Email validado",
                    mensajeEncabezado = "Email correcto, se ha procedido a enviar un código de recuperación al email facilitado.",
                    mensajePie = "Si no lo recibe en breve, revise su bandeja de spam.")
                RoutesManager.initCodigoRecuperarPass()
                stage.close()
            }.onFailure {
                logger.debug { "Intento de acceso incorrecto" }
                showAlertOperacion(alerta = Alert.AlertType.ERROR,
                    title = "Error en la recuperación de contraseña.",
                    mensajeEncabezado = "Error, el email no existe.")
            }
    }


    private fun showAlertOperacion(
        alerta: Alert.AlertType = Alert.AlertType.CONFIRMATION,
        title: String = "",
        mensajeEncabezado: String = "",
        mensajePie:String = ""
    ) {
        Alert(alerta).apply {
            this.title = title
            this.headerText=mensajeEncabezado
            this.contentText = mensajePie
        }.showAndWait()
    }


}