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

/**
 * Clase controller para la pantalla de recuperación de contraseña olvidada.
 * Gestiona las acciones y eventos relacionados con la introducción y validación del código de recuperación.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property viewModel ViewModel que gestiona el estado y la lógica de la recuperación de contraseña.
 * @property backLoginButton Botón para regresar a la pantalla de inicio de sesión.
 * @property codeField Campo de texto para ingresar el código de recuperación.
 * @property continueButton Botón para continuar con la validación del código.
 * @property backLoginMenuButton Menú de opción para regresar a la pantalla de inicio de sesión.
 * @property acercaDeMenuButton Menú de opción para mostrar información sobre los desarrolladores.
 */
class CodigoContraseñaOlvidadaController :KoinComponent {

    val viewModel: RecuperarPasswordViewModel by inject()

    @FXML
    lateinit var backLoginButton: Button

    @FXML
    lateinit var codeField: TextField

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

        codeField.requestFocus()

        if (viewModel.state.value.restoreCode==""){

            codeField.text = viewModel.generateRestoreCode()
            viewModel.state.value.restoreCode = codeField.text
        }
        else{
            codeField.text = viewModel.state.value.restoreCode
        }

        codeField.setOnKeyPressed { event ->
            if (event.code == KeyCode.ENTER) {
                continueButton.fire()
                event.consume()
            }
        }

        continueButton.setOnAction { continuar() }

        backLoginButton.setOnAction {
            viewModel.state.value.restoreCode = ""
            RoutesManager.initEmailRecuperarPass()
            stage.close()
        }

        acercaDeMenuButton.setOnAction { RoutesManager.initAcercaDeStage() }

        backLoginMenuButton.setOnAction {
            viewModel.state.value.restoreCode = ""
            stage.close() }
    }

    private fun continuar() {
        viewModel.validateRestoredCode(codeField.text)
            .onSuccess {
                logger.debug { "Validado código de recuperación" }
                RoutesManager.initNuevaRecuperarPass()
                stage.close()
            }.onFailure {
                logger.debug { "Código de recuperación incorrecto" }
                showAlertOperacion(alerta = Alert.AlertType.ERROR,
                    "Error en la recuperación de contraseña",
                    "Error, el código de recuperación es incorrecto, por favor introdúzcalo de nuevo.")
            }
    }
    private fun showAlertOperacion(
        alerta: Alert.AlertType = Alert.AlertType.CONFIRMATION,
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