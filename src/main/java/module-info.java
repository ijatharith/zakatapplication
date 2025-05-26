module com.mycompany.zakatapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mycompany.zakatapplication to javafx.fxml;
    exports com.mycompany.zakatapplication;
}