module org.example.dawfilmsinterface {
    requires javafx.controls;
    requires javafx.fxml;

    requires kotlin.stdlib;

    requires logging.jvm;
    requires org.slf4j;

    requires runtime.jvm;
    requires sqlite.driver;
    requires java.sql;

    requires kotlinx.serialization.core;
    requires kotlinx.serialization.json;

    requires kotlin.result.jvm;

    requires koin.core.jvm;

    requires open;

    opens org.example.dawfilmsinterface to javafx.fxml;
    exports org.example.dawfilmsinterface;

    opens org.example.dawfilmsinterface.cine.controllers to javafx.fxml;
    exports org.example.dawfilmsinterface.cine.controllers;
}