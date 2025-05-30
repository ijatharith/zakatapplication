module com.mycompany.zakatapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.mycompany.zakatapplication to javafx.fxml;
    exports com.mycompany.zakatapplication;
}