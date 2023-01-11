package com.example.TelegramBotDemo.model;

import java.util.List;

public class PriceModel {
    List<PriceListManicure> services;
    String name;

    @Override
    public String toString() {
        return "services=" + services;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PriceListManicure> getPriceList() {
        return services;
    }

    public void setPriceList(List<PriceListManicure> priceList) {
        this.services = priceList;
    }
}
