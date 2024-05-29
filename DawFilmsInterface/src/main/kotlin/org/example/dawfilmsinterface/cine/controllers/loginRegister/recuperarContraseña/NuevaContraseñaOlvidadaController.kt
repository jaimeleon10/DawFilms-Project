package org.example.dawfilmsinterface.cine.controllers.loginRegister.recuperarContraseña

import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.MenuItem
import javafx.scene.control.PasswordField
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import org.example.dawfilmsinterface.cine.viewmodels.RecuperarPasswordViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class NuevaContraseñaOlvidadaController: KoinComponent {

    val viewModel: RecuperarPasswordViewModel by inject()

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
        continueButton.setOnAction { nuevoPassword() }

        backLoginButton.setOnAction { stage.close() }

        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }

        backLoginMenuButton.setOnAction { stage.close() }

        newPassField.setOnKeyPressed { event ->
            if (event.code == KeyCode.ENTER) {
                continueButton.fire()
                event.consume()
            }
        }

        checkNewPassField.setOnKeyPressed { event ->
            if (event.code == KeyCode.ENTER) {
                continueButton.fire()
                event.consume()
            }
        }
    }

    private fun nuevoPassword() {
        if(!validateFields()) {
            showAlertOperacion(alerta = Alert.AlertType.ERROR,
                title = "Error en la recuperación de contraseña",
                mensajeEncabezado = "Los campos de contraseña y repetir contraseña no son válidos.",
                mensajePie = "Deben coincidir y no estar vacíos"
            )
            return
        } else {
            viewModel.validateUserPassword(newPassField.text).onSuccess {
                logger.debug { "Formato password correcto, actualizando usuario" }
                viewModel.updatePassword().onSuccess {
                    showAlertOperacion(alerta = Alert.AlertType.INFORMATION,
                        title="Contraseña correcta",
                        mensajeEncabezado = "Contraseña actualizada.")
                }.onFailure {
                    showAlertOperacion(alerta = Alert.AlertType.ERROR,
                        title = "Error en la recuperación de contraseña",
                        mensajeEncabezado = "La contraseña no se pudo actualizar")
                }
                stage.close()
            }.onFailure {
                logger.debug { "Formato de password incorrecto" }
                showAlertOperacion(alerta = Alert.AlertType.ERROR,
                    title = "Error en la recuperación de contraseña",
                    mensajeEncabezado = "La contraseña no es válida",
                    mensajePie = "La contraseña debe tener 5 caracteres o mas, y contener al menos un número, una letra (al menos una mayúscula)"
                )
            }
        }
    }

    private fun validateFields(): Boolean {
        if (checkNewPassField.text == "" || newPassField.text == "") return false
        if (checkNewPassField.text != newPassField.text) return false
        return true
    }

    private fun showAlertOperacion(
        alerta: Alert.AlertType = Alert.AlertType.ERROR,
        title: String = "",
        mensajeEncabezado: String = "",
        mensajePie:String=""
    ) {
        Alert(alerta).apply {
            this.title = title
            this.headerText = mensajeEncabezado
            this.contentText = mensajePie
        }.showAndWait()
    }

}