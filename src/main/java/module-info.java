module com.example.hirnok {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hirnok to javafx.fxml;
    exports com.example.hirnok;
}