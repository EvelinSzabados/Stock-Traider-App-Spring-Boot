package com.codecool.stock;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * "https://financialmodelingprep.com/api/v3/stock/real-time-price/%s";
 * Stock price service that gets prices from Yahoo.
 **/

@Service
public class StockAPIService {

    @Value("${stock.url}")
    private String apiPath;

    private RemoteURLReader remoteURLReader;

    /** Get stock price from iex and return as a double
     *  @param symbol Stock symbol, for example "aapl"
     **/

    public StockAPIService(RemoteURLReader reader){
        this.remoteURLReader = reader;

    }
    public double getPrice(String symbol) throws IOException, JSONException {
        String url = String.format(apiPath, symbol);
        String result = remoteURLReader.readFromUrl(url);
        JSONObject json = new JSONObject(result);
        String price = json.get("price").toString();
        return Double.parseDouble(price);
    }

    /** Buys a share of the given stock at the current price. Returns false if the purchase fails */
    public boolean buy(String symbol) {
        // Stub. No need to implement this.
        return true;
    }
}