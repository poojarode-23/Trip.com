package com.example.APIService;

import com.example.APIService.APIService;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class DataUrl {

    private static final String API_KEY = "668ea0b0d51ad599145126ufjee9607";

    public static double[] getCoordinates(String address) throws Exception {
        // Encode the address
        System.out.println(address + " getCoordinates called!!!");
        String encodedAddress = URLEncoder.encode(address, "UTF-8");
        String url = String.format(
                "https://geocode.maps.co/search?q=%s&api_key=%s",
                encodedAddress, API_KEY);

        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
        httpClient.setRequestMethod("GET");

        int responseCode = httpClient.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // Print the response
            System.out.println("Response: " + response.toString());

            JSONArray obj = new JSONArray(response.toString());
            if (obj.length() > 0) {
                JSONObject location = obj.getJSONObject(0);
                double lat = location.getDouble("lat");
                double lon = location.getDouble("lon");

                System.out.println("=======================================================");
                System.out.println(location);
                System.out.println("=======================================================");
                System.out.println(location.getString("display_name"));
                System.out.println(lat);
                System.out.println(lon);

                return new double[]{lat, lon};
            } else {
                System.out.println("No location data found");
                return null;
            }
        } else {
            System.out.println("HTTP error code: " + responseCode);
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(httpClient.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder();
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine);
            }
            errorReader.close();
            System.out.println("Error response: " + errorResponse.toString());
        }
        return null;
    }

    public static String getLocationName(double lat, double lon) throws Exception {
        String url = String.format(
                "https://geocode.maps.co/reverse?lat=%f&lon=%f&api_key=%s",
                lat, lon, API_KEY);

        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
        httpClient.setRequestMethod("GET");

        int responseCode = httpClient.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject obj = new JSONObject(response.toString());
            if (obj.has("display_name")) {
                return obj.getString("display_name");
            } else {
                System.out.println("No location name found");
                return null;
            }
        } else {
            System.out.println("HTTP error code: " + responseCode);
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(httpClient.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder();
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine);
            }
            errorReader.close();
            System.out.println("Error response: " + errorResponse.toString());
        }
        return null;
    }
}
