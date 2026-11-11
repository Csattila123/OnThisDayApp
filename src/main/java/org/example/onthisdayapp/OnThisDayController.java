package org.example.onthisdayapp;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Month;

public class OnThisDayController {

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private ComboBox<String> dayComboBox;

    @FXML
    private Label title;

    @FXML
    private Label text;

    @FXML
    private Label year;

    @FXML
    private ImageView thumbnail;

    @FXML
    public void initialize() {
        for (int i = 1; i <= 12; i++) {
            monthComboBox.getItems().add(String.format("%02d", i));
        }

        monthComboBox.setOnAction(event -> updateDays());

        monthComboBox.setValue("01");
        updateDays();
    }

    private void updateDays() {
        String selectedMonth = monthComboBox.getValue();
        if (selectedMonth == null) return;

        int month = Integer.parseInt(selectedMonth);
        int maxDays = Month.of(month).maxLength();

        dayComboBox.getItems().clear();
        for (int i = 1; i <= maxDays; i++) {
            dayComboBox.getItems().add(String.format("%02d", i));
        }
        dayComboBox.setValue("01");
    }

    @FXML
    protected void getTranslatedWikiEvent() {
        String selectedMonth = monthComboBox.getValue();
        String selectedDay = dayComboBox.getValue();

        WikiEvent event = OnThisDayService.getTranslatedWikiEvent(selectedMonth, selectedDay);

        title.setText(event.getTitle());
        title.setVisible(true);
        title.setManaged(true);

        text.setText(event.getDesc());
        text.setVisible(true);
        text.setManaged(true);

        year.setText(String.valueOf(event.getYear()));
        year.setVisible(true);
        year.setManaged(true);


        if (event.getThumbnail() != null && !event.getThumbnail().isEmpty()) {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(event.getThumbnail()))
                        .header("User-Agent", "OnThisDayStudentApp/1.0")
                        .build();

                HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
                Image img = new Image(new ByteArrayInputStream(response.body()));

                thumbnail.setImage(img);
                thumbnail.setVisible(true);
                thumbnail.setManaged(true);
            } catch (Exception e) {
                e.printStackTrace();
                thumbnail.setImage(null);
                thumbnail.setVisible(false);
                thumbnail.setManaged(false);
            }
        } else {
            thumbnail.setImage(null);
            thumbnail.setVisible(false);
            thumbnail.setManaged(false);
        }
    }
}