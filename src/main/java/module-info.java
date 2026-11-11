module org.example.onthisdayapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.onthisdayapp to javafx.fxml;
    exports org.example.onthisdayapp;
}