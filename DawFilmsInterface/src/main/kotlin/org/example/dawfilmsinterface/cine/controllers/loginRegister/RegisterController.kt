package org.example.dawfilmsinterface.cine.controllers.loginRegister

import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import org.example.dawfilmsinterface.cine.viewmodels.RegistroViewModel
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDate

private val logger = logging()

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
        /*
        continueButton.setOnAction {
            logger.debug { "Cambiando de escena a ${RoutesManager.View.LOGIN}" }
            RoutesManager.changeScene(view = RoutesManager.View.LOGIN)
        }

         */

        continueButton.setOnAction {
            validarCampos(
                nameField.text,
                surnameField.text,
                emailField.text,
                passwordField.text,
                checkPassField.text,
                dniField.text,
                birthDatePicker.value
            )
        }
    }

    @FXML
    private fun validarCampos(enteredNombre: String, enteredApellido: String, enteredEmail: String, enteredPass: String, enteredConfPass: String, enteredDni: String, selectedDate : LocalDate){
        if (enteredNombre == ""){
            showAlertOperacion(AlertType.ERROR, "Nombre inválido", "El nombre no puede estar vacío")
        }
        if (enteredApellido == ""){
            showAlertOperacion(AlertType.ERROR, "Apellido inválido", "El apellido no puede estar vacío")
        }
        if (enteredDni == ""){
            showAlertOperacion(AlertType.ERROR, "DNI inválido", "El dni no puede estar vacío")
        }
        if (selectedDate > LocalDate.now()){
            showAlertOperacion(AlertType.ERROR, "Fecha inválida", "La fecha no puede ser posterior a hoy")
        }
        if (enteredEmail == ""){
            showAlertOperacion(AlertType.ERROR, "Email inválido", "El email no puede estar vacío")
        }
        if (enteredPass == ""){
            showAlertOperacion(AlertType.ERROR, "Contraseña inválida", "La contraseña no puede estar vacía")
        }
        if (enteredConfPass == "" && enteredConfPass != enteredPass){
            showAlertOperacion(AlertType.ERROR, "Contraseña inválida", "La contraseña debe coincidir con la contraseña")
        }
    }

    @FXML
    private fun showAlertOperacion(
        alerta: AlertType = AlertType.CONFIRMATION,
        title: String = "",
        mensaje: String = ""
    ){
        Alert(alerta).apply {
            this.title = title
            this.contentText =mensaje
        }.showAndWait()
    }
}