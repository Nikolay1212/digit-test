package com.digit.test;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetService {

    private static final int REQUEST_FREQUENCY = 1000;

    private static final Logger LOGGER = Logger.getLogger(GetService.class.getName());

    private static final String URL = "http://localhost:8081/message/last";

    public static void main(String[] args) {

        while (true) {
            URL url;
            try {
                Thread.sleep(REQUEST_FREQUENCY);
                url = new URL(URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        String inputLine;
                        StringBuilder content = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                        String tempMessage = content.toString();
                        JSONObject json = new JSONObject(tempMessage);
                        if (json.get("text").equals("")) {
                            LOGGER.log(Level.WARNING, "Get Empty message");
                            System.exit(0);
                        } else {
                            LOGGER.log(Level.INFO, "Get message");
                            System.out.println(json.get("text").toString());
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
