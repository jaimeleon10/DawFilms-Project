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
    requires net.devrieze.xmlutil.serialization;

    opens org.example.dawfilmsinterface to javafx.fxml;
    exports org.example.dawfilmsinterface;

    opens org.example.dawfilmsinterface.cine.controllers to javafx.fxml;
    exports org.example.dawfilmsinterface.cine.controllers;

    opens org.example.dawfilmsinterface.cine.controllers.loginRegister to javafx.fxml;
    exports org.example.dawfilmsinterface.cine.controllers.loginRegister;

    opens org.example.dawfilmsinterface.cine.controllers.loginRegister.recuperarContraseña to javafx.fxml;
    exports org.example.dawfilmsinterface.cine.controllers.loginRegister.recuperarContraseña;

    opens org.example.dawfilmsinterface.cine.controllers.admin to javafx.fxml;
    exports org.example.dawfilmsinterface.cine.controllers.admin;

    opens org.example.dawfilmsinterface.cine.controllers.admin.actualizarButaca to javafx.fxml;
    exports org.example.dawfilmsinterface.cine.controllers.admin.actualizarButaca;

    opens org.example.dawfilmsinterface.cine.controllers.admin.listadoComplementos to javafx.fxml;
    exports org.example.dawfilmsinterface.cine.controllers.admin.listadoComplementos;

    opens org.example.dawfilmsinterface.cine.controllers.cliente to javafx.fxml;
    exports org.example.dawfilmsinterface.cine.controllers.cliente;

    opens org.example.dawfilmsinterface.cine.controllers.cliente.comprarEntrada to javafx.fxml;
    exports org.example.dawfilmsinterface.cine.controllers.cliente.comprarEntrada;
}