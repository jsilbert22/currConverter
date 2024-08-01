package currencyConverter.backend;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FetchRate {
    // Setting URL
    private final String start_url_str = "https://v6.exchangerate-api.com/v6/c8e21c862cab585f5df1c206/latest/";
    private String startCurrency;
    private String endCurrency;
    private double conversionAmount;

    public FetchRate(String startCurrency, String endCurrency, double conversionAmount){
        this.startCurrency = startCurrency;
        this.endCurrency = endCurrency;
        this.conversionAmount = conversionAmount;
    }

    /**
     * public method for getting the value of the converted currency
     * @return double
     */
    public double getRate()  {
        try {
            Map<String, Double> conversionMap = getConversionMap(startCurrency);

            double conversionRate = conversionMap.get(endCurrency);
            double convertedAmount = conversionAmount*conversionRate;

            System.out.println(conversionAmount + " of " + startCurrency + " to " + endCurrency +
                    " is " + convertedAmount);

            return convertedAmount;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    /**
     * API GET request to get the most recent currency conversion rates map
     * @param startCurrency starting currency
     * @return Map of the rates from the starting currency
     */
    private Map<String, Double> getConversionMap(String startCurrency){
        try {
            // Setting URL
            String url_str = start_url_str + startCurrency;
            Map<String, Double> conversionMap;

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

                conversionMap = new HashMap<>();

                for (String currency : currencies) {
                    double rate = conversionRates.get(currency).getAsDouble();
                    conversionMap.put(currency, rate);
                }
            } else {
                System.out.println("Failed to get conversion rates.");
                return new HashMap<>();
            }

            return conversionMap;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new HashMap<>();

    }




}
