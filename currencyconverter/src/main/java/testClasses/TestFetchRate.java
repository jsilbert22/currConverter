package testClasses;

import currencyConverter.backend.FetchRate;

public class TestFetchRate {
    public static void main(String[] args) {
        FetchRate req = new FetchRate("EUR", "USD", 25.0);

        double newRate = req.getRate();
        System.out.println("new rate: " + newRate);
    }
}
