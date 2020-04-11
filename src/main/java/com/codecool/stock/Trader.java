package com.codecool.stock;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Business logic for stock trading
 **/
@Component
public class Trader {

    private StockAPIService stockService;
    private Logger logger;

    public Trader(StockAPIService stockService, Logger logger) {
        this.stockService = stockService;
        this.logger = logger;
    }

    /**
     * Checks the price of a stock, and buys it if the price is not greater than the bid amount.
     *
     * @return whether any stock was bought
     */
    public boolean buy(String symbol, double bid) throws IOException, JSONException {
        double price = stockService.getPrice(symbol);

        boolean result;
        if (price <= bid) {
            result = true;
            stockService.buy(symbol);
            logger.log("Purchased " + symbol + " stock at $" + bid + ", since its higher that the current price ($" + price + ")");
        } else {
            logger.log("Bid for " + symbol + " was $" + bid + " but the stock price is $" + price + ", no purchase was made.");
            result = false;
        }
        return result;
    }

}
