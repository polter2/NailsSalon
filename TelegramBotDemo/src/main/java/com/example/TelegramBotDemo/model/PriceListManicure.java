package com.example.TelegramBotDemo.model;

public class PriceListManicure {
    String name;
    int price;

    public PriceListManicure(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "priceListManicure{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
