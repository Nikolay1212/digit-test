package com.digit.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostService {

    private static final Logger LOGGER = Logger.getLogger(PostService.class.getName());

    public static void main(String[] args) {

        String url = "http://localhost:8081/message";

        URL UrlObj;

        while (true)

        {
            try {
                UrlObj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) UrlObj.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

                Scanner scanner = new Scanner(System.in);
                String message = scanner.nextLine();
                if (message.equals("")) {
                    LOGGER.log(Level.INFO, "Send empty message");
                    sendMessage(url, connection, outputStream, message);
                    connection.disconnect();
                    LOGGER.log(Level.WARNING, "Send empty message");
                    System.exit(0);
                } else {
                    LOGGER.log(Level.INFO, "Send message: " + message);
                    sendMessage(url, connection, outputStream, message);
                    connection.disconnect();
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Something wrong", e);
                e.printStackTrace();
            }
        }
    }

    private static void sendMessage(String url, HttpURLConnection connection, DataOutputStream outputStream, String message) throws IOException {
        String urlPostParameters = "{\"text\": \"" + message + "\"}";
        outputStream.writeBytes(urlPostParameters);
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader inputReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = inputReader.readLine()) != null) {
                response.append(inputLine);
            }
            inputReader.close();
        }
    }
}
