package org.example.dawfilmsinterface.routes

import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.WindowEvent
import org.example.dawfilmsinterface.cine.controllers.AcercaDeController
import org.example.dawfilmsinterface.cine.controllers.admin.ExportarEstadoCineController
import org.example.dawfilmsinterface.cine.controllers.admin.actualizarButaca.EditarButacaController
import org.example.dawfilmsinterface.cine.controllers.admin.listadoComplementos.EditarComplementoController
import org.example.dawfilmsinterface.cine.controllers.loginRegister.recuperarContraseña.CodigoContraseñaOlvidadaController
import org.example.dawfilmsinterface.cine.controllers.loginRegister.recuperarContraseña.EmailContraseñaOlvidadaController
import org.example.dawfilmsinterface.cine.controllers.loginRegister.recuperarContraseña.NuevaContraseñaOlvidadaController
import org.lighthousegames.logging.logging
import java.io.InputStream
import java.net.URL
import java.util.*

private val logger = logging()

object RoutesManager {
    private lateinit var mainStage: Stage
    private lateinit var _activeStage: Stage
    val activeStage: Stage
        get() = _activeStage
    lateinit var app: Application

    enum class View(val fxml: String) {
        ACERCA_DE("views/acercaDe-view.fxml"),
        CONFIRMAR_COMPRA("views/confirmarCompra-view.fxml"),
        EDITAR_COMPLEMENTO("views/editComplemento-view.fxml"),
        EDITAR_BUTACA("views/editSeat-view.fxml"),
        MOSTRAR_ESTADO_CINE("views/estadoCine-view.fxml"),
        EXPORTAR_ESTADO_CINE("views/exportarCine-view.fxml"),
        CODIGO_CONTRASENA_OLVIDADA("views/forgotPassCode-view.fxml"),
        EMAIL_CONTRASENA_OLVIDADA("views/forgotPassEmail-view.fxml"),
        NUEVA_CONTRASENA_OLVIDADA("views/forgotPassNewPass-view.fxml"),
        OBTENER_RECAUDACION("views/getRaising-view.fxml"),
        LISTADO_COMPLEMENTOS_ADMIN("views/listadoComplementosAdmin-view.fxml"),
        LISTADO_COMPLEMENTOS_CLIENTE("views/listadoComplementosCliente-view.fxml"),
        LOGIN("views/login-view.fxml"),
        MENU_CINE_ADMIN("views/menuCineAdmin-view.fxml"),
        MENU_CINE_CLIENTE("views/menuCineCliente-view.fxml"),
        PANTALLA_DE_CARGA("views/pantallaDeCarga-view.fxml"),
        REGISTRO("views/register-view.fxml"),
        SELECCION_BUTACAS("views/seleccionButacas-view.fxml"),
        SELECCION_COMPLEMENTOS("views/seleccionComplementos-view.fxml"),
        ACTUALIZAR_BUTACA("views/updateSeat-view.fxml")
    }

    init {
        logger.debug { "Inicializando RoutesManager" }
        Locale.setDefault(Locale.forLanguageTag("es-ES"))
    }

    fun initMainStage(stage: Stage) {
        logger.debug { "Inicializando ventana: pantalla de carga" }

        val fxmlLoader = FXMLLoader(getResource(View.PANTALLA_DE_CARGA.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 920.0, 680.0)

        stage.apply {
            stage.title = "DawFilms"
            stage.isResizable = false
            stage.icons.add(Image(getResourceAsStream("icons/logoCine.png")))
            stage.setOnCloseRequest { onAppExit(event = it) }
            stage.scene = scene
        }.show().also {
            mainStage = stage
            _activeStage = stage
        }
    }

    fun initAcercaDeStage() {
        logger.debug { "Inicializando AcercaDeStage" }

        val fxmlLoader = FXMLLoader(getResource(View.ACERCA_DE.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 802.0, 590.0)
        val stage = Stage()
        stage.title = "Acerca de DawFilms"
        stage.scene = scene
        stage.initOwner(mainStage)
        stage.initModality(Modality.WINDOW_MODAL)
        stage.isResizable = false

        stage.show()

        val controller = fxmlLoader.getController<AcercaDeController>()
        controller.setStage(stage)
    }

    fun initEmailRecuperarPass() {
        logger.debug { "Inicializando RecuperarPassStage" }

        val fxmlLoader = FXMLLoader(getResource(View.EMAIL_CONTRASENA_OLVIDADA.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 690.0, 400.0)
        val stage = Stage()
        stage.title = "Recuperar contraseña"
        stage.scene = scene
        stage.initOwner(mainStage)
        stage.initModality(Modality.WINDOW_MODAL)
        stage.isResizable = false

        stage.show()

        val controller = fxmlLoader.getController<EmailContraseñaOlvidadaController>()
        controller.setStage(stage)
    }

    fun initCodigoRecuperarPass() {
        logger.debug { "Inicializando RecuperarPassStage" }

        val fxmlLoader = FXMLLoader(getResource(View.CODIGO_CONTRASENA_OLVIDADA.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 690.0, 400.0)
        val stage = Stage()
        stage.title = "Recuperar contraseña"
        stage.scene = scene
        stage.initOwner(mainStage)
        stage.initModality(Modality.WINDOW_MODAL)
        stage.isResizable = false

        stage.show()

        val controller = fxmlLoader.getController<CodigoContraseñaOlvidadaController>()
        controller.setStage(stage)
    }

    fun initNuevaRecuperarPass() {
        logger.debug { "Inicializando RecuperarPassStage" }

        val fxmlLoader = FXMLLoader(getResource(View.NUEVA_CONTRASENA_OLVIDADA.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 690.0, 400.0)
        val stage = Stage()
        stage.title = "Recuperar contraseña"
        stage.scene = scene
        stage.initOwner(mainStage)
        stage.initModality(Modality.WINDOW_MODAL)
        stage.isResizable = false

        stage.show()

        val controller = fxmlLoader.getController<NuevaContraseñaOlvidadaController>()
        controller.setStage(stage)
    }

    fun initExportEstadoCine() {
        logger.debug { "Inicializando ExportCineStage" }

        val fxmlLoader = FXMLLoader(getResource(View.EXPORTAR_ESTADO_CINE.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 690.0, 310.0)
        val stage = Stage()
        stage.title = "Exportar estado del cine"
        stage.scene = scene
        stage.initOwner(mainStage)
        stage.initModality(Modality.WINDOW_MODAL)
        stage.isResizable = false

        stage.show()

        val controller = fxmlLoader.getController<ExportarEstadoCineController>()
        controller.setStage(stage)
    }

    fun initEditarButaca() {
        logger.debug { "Inicializando EditarButacaStage" }

        val fxmlLoader = FXMLLoader(getResource(View.EDITAR_BUTACA.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 530.0, 530.0)
        val stage = Stage()
        stage.title = "Editando butaca"
        stage.scene = scene
        stage.initOwner(mainStage)
        stage.initModality(Modality.WINDOW_MODAL)
        stage.isResizable = false

        stage.show()

        val controller = fxmlLoader.getController<EditarButacaController>()
        controller.setStage(stage)
    }

    fun initEditarComplemento(titulo: String) {
        logger.debug { "Inicializando EditarComplementoStage" }

        val fxmlLoader = FXMLLoader(getResource(View.EDITAR_COMPLEMENTO.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 530.0, 530.0)
        val stage = Stage()
        stage.title = "Editando complemento"
        stage.scene = scene
        stage.initOwner(mainStage)
        stage.initModality(Modality.WINDOW_MODAL)
        stage.isResizable = false

        stage.show()

        val controller = fxmlLoader.getController<EditarComplementoController>()
        controller.setStage(stage)
        controller.tituloLabel.text = titulo
    }

    fun changeScene(
        myStage: Stage = activeStage,
        view: View,
        width: Double = 920.0,
        height: Double = 680.0,
    ) {
        logger.debug { "Cambiando de escena a ${view.fxml}" }
        val parentRoot = FXMLLoader.load<Pane>(this.getResource(view.fxml))
        val scene = Scene(parentRoot, width, height)
        myStage.scene = scene
    }

    fun getResource(resource: String): URL {
        return app::class.java.getResource(resource)
            ?: throw RuntimeException("No se ha encontrado el recurso: $resource")
    }

    fun getResourceAsStream(resource: String): InputStream {
        return app::class.java.getResourceAsStream(resource)
            ?: throw RuntimeException("No se ha encontrado el recurso como stream: $resource")
    }

    fun onAppExit(
        title: String = "Saliendo de DawFilms...",
        headerText: String = "¿Estás seguro de que quieres salir de DawFilms?",
        contentText: String = "Si sales, se cerrará la aplicación y perderás todos los datos no guardados",
        event: WindowEvent? = null
    ) {
        logger.debug { "Cerrando la aplicación" }
        Alert(Alert.AlertType.CONFIRMATION).apply {
            this.title = title
            this.headerText = headerText
            this.contentText = contentText
        }.showAndWait().ifPresent { opcion ->
            if (opcion == ButtonType.OK) {
                Platform.exit()
            } else {
                event?.consume()
            }
        }
    }
}