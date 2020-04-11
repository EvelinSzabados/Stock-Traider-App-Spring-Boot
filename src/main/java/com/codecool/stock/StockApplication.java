package com.codecool.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.InputMismatchException;
import java.util.Scanner;

@SpringBootApplication
public class StockApplication {

    @Autowired
    Logger logger;
    @Autowired
    RemoteURLReader remoteURLReader;
    @Autowired
    Trader trader;
    @Autowired
    StockAPIService stockAPIService;

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);

    }

    @PostConstruct
    public void start() {

        String url = "https://financialmodelingprep.com/api/v3/stock/real-time-price/%s";
        stockAPIService = new StockAPIService(remoteURLReader);
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter a stock symbol (for example aapl):");
        String symbol = keyboard.nextLine();
        System.out.println("Enter the maximum price you are willing to pay: ");
        double price;
        try {
            price = keyboard.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Enter a number");
            return;
        }

        try {
            boolean purchased = trader.buy(symbol, price);
            if (purchased) {
                logger.log("Purchased stock!");
            } else {
                logger.log("Couldn't buy the stock at that price.");
            }
        } catch (Exception e) {
            logger.log("There was an error while attempting to buy the stock: " + e.getMessage());
        }
    }
}
