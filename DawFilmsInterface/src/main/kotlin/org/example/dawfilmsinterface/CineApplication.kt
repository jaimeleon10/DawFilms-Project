package org.example.dawfilmsinterface

import javafx.application.Application
import javafx.stage.Stage
import org.example.dawfilmsinterface.di.appModule
import org.example.dawfilmsinterface.routes.RoutesManager
import org.koin.core.context.startKoin
import java.time.LocalDateTime

class CineApplication : Application() {

    init {
        println(LocalDateTime.now().toString())
        startKoin {
            printLogger()
            modules(appModule)
        }
    }

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