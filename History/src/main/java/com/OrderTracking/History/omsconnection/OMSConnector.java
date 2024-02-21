package com.OrderTracking.History.omsconnection;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OMSConnector {

    public static OMSConnectionResult establishConnection(String omsEndpoint, String apiKey) {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(omsEndpoint))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .GET()
                .build();

        try {
        
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();

            if (statusCode == 200) {
                return new OMSConnectionResult(true, "Connection successful");
            } else {
                return new OMSConnectionResult(false, "Failed to connect to the external API. Status code: " + statusCode);
            }
        } catch (IOException | InterruptedException e) {
       
            e.printStackTrace();
            return new OMSConnectionResult(false, "An error occurred while connecting to the external API: " + e.getMessage());
        }
    }
}
