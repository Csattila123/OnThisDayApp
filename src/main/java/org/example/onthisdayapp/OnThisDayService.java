package org.example.onthisdayapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class OnThisDayService {
    private static final String eventURL = "https://en.wikipedia.org/api/rest_v1/feed/onthisday/events/01/16";

    private static JSONArray getAllEvents() {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(eventURL)).header("User-Agent", "OnThisDayStudentApp/1.0").GET().build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject responseJSON = new JSONObject(response.body());

            return new JSONArray(responseJSON.getJSONArray("events"));
        } catch (URISyntaxException use) {
            System.err.println(use.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } catch (InterruptedException ie) {
            System.err.println(ie.getMessage());
        }

        throw new RuntimeException("Hibás API kérés!");
    }

    private static WikiEvent getRandomEvent() {
        JSONArray events = getAllEvents();

        JSONObject randomJSON = events.getJSONObject(new Random().nextInt(events.length()));

        String desc = randomJSON.getString("text");
        String title = randomJSON.getJSONArray("pages").getJSONObject(0).getJSONObject("titles").getString("normalized");

        String thumbnail = "";

        if (randomJSON.getJSONArray("pages").getJSONObject(0).has("thumbnail")) {
            thumbnail = randomJSON.getJSONArray("pages").getJSONObject(0).getJSONObject("thumbnail").getString("source");
        }

        return new WikiEvent(title, desc, thumbnail);
    }

    private static String getTranslateURL(String text) {
        text = text.replaceAll(" ", "\s");

        return "https://api.mymemory.translated.net/get?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) + "&langpair=en%7Chu";
    }

    public static WikiEvent getTranslatedWikiEvent() {
        WikiEvent englishEvent = getRandomEvent();

        try {
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(getTranslateURL(englishEvent.getTitle()))).GET().build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject responseJSON = new JSONObject(response.body());

            String translatedTitle = responseJSON.getJSONObject("responseData").get("translatedText").toString();

            request = HttpRequest.newBuilder().uri(new URI(getTranslateURL(englishEvent.getDesc()))).GET().build();
            client = HttpClient.newHttpClient();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseJSON = new JSONObject(response.body());

            String translatedDesc = responseJSON.getJSONObject("responseData").get("translatedText").toString();

            return new WikiEvent(translatedTitle, translatedDesc, englishEvent.getThumbnail());

        } catch (URISyntaxException use) {
            System.err.println(use.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } catch (InterruptedException ie) {
            System.err.println(ie.getMessage());
        }

        throw new RuntimeException("Hibás API kérés!");
    }
}
