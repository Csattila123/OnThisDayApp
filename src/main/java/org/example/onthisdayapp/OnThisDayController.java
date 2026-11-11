package org.example.onthisdayapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OnThisDayController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}