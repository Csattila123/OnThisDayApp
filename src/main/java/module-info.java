module org.example.onthisdayapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens org.example.onthisdayapp to javafx.fxml;
    exports org.example.onthisdayapp;
}