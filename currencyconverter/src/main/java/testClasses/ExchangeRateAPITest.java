package testClasses;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

public class ExchangeRateAPITest {
    public static void main(String[] args) {
        try {
            // Setting URL
            String url_str = "https://v6.exchangerate-api.com/v6/c8e21c862cab585f5df1c206/latest/USD";

            // Making Request
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET"); // Explicitly setting request method
            request.connect();

            // Convert to JSON using Gson
            Gson gson = new Gson();
            JsonObject jsonobj = gson.fromJson(new InputStreamReader((InputStream) request.getContent()), JsonObject.class);

            // Accessing object
            String req_result = jsonobj.get("result").getAsString();
            System.out.println("Result: " + req_result);

            // Check if the request was successful
            if ("success".equals(req_result)) {
                // Accessing and printing conversion rates
                JsonObject conversionRates = jsonobj.getAsJsonObject("conversion_rates");
                Set<String> currencies = conversionRates.keySet();

                for (String currency : currencies) {
                    double rate = conversionRates.get(currency).getAsDouble();
                    System.out.println(currency + ": " + rate);
                }
            } else {
                System.out.println("Failed to get conversion rates.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
