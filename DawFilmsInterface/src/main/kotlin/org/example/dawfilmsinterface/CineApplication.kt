package org.example.dawfilmsinterface

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import org.example.dawfilmsinterface.routes.RoutesManager

class CineApplication : Application() {
    override fun start(stage: Stage) {
        RoutesManager.apply {
            app = this@CineApplication
        }.run {
            initMainStage(stage)
        }
    }
}

fun main() {
    Application.launch(CineApplication::class.java)
}