package com.example.TelegramBotDemo.request;

import com.example.TelegramBotDemo.model.PriceListManicure;
import com.example.TelegramBotDemo.model.PriceModel;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parsers {
    public PriceModel parseGson() {
        Gson gson = new Gson();
        FileReader reader;

        {
            try {
                reader = new FileReader("serv.json");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        PriceModel priceModel = gson.fromJson(reader, PriceModel.class);
        return priceModel;
    }

    public static PriceModel parseJson() {
        Parsers parser = new Parsers();
        PriceModel priceModel = parser.parseGson();
        return priceModel;
    }


    private static Document getPageUSD() throws IOException {
        String url = "https://www.forbes.com/advisor/money-transfer/currency-converter/eur-usd/";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    private static Document getPageUAH() throws IOException {
        String url = "https://www.forbes.com/advisor/money-transfer/currency-converter/eur-uah/";

        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    private static Pattern pattern = Pattern.compile("\\d{1,3}.\\d{2}");

    private static String getCurrencyFromString(String stringCurrency) throws Exception {
        Matcher matcher = pattern.matcher(stringCurrency);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Не удалось вывести курс");
    }

    public double getCurrencyRateUAH() {
        Document pageUAH;
        try {
            pageUAH = getPageUAH();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Element euroHrivnya = pageUAH.select("div[class=result-box-c1-c2]").first();


        try {
            return Double.parseDouble(getCurrencyFromString(String.valueOf(euroHrivnya)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public double getCurrencyRateUSD() {
        Document pageUSD;
        try {
            pageUSD = getPageUSD();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Element euroDollar = pageUSD.select("div[class=result-box-c1-c2]").first();
        try {
            return Double.parseDouble(getCurrencyFromString(String.valueOf(euroDollar)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String getAllPrices() {
        Parsers parsers = new Parsers();
        String euro;
        String uah;
        String usd;
        double x = 0;
        double y = 0;
        String pri = "";
        for (PriceListManicure a : parseJson().getPriceList()) {
            x = Double.parseDouble(String.valueOf(a.getPrice() * parsers.getCurrencyRateUSD()));
            y = Double.parseDouble(String.valueOf(a.getPrice() * parsers.getCurrencyRateUAH()));
            euro = String.valueOf(a.getPrice());
            uah = String.valueOf(y);
            usd = String.valueOf(x);
            Pattern pattern = Pattern.compile("\\d{1,9}.\\d{2}");
            Matcher matcher = pattern.matcher(usd);
            Matcher matcher1 = pattern.matcher(uah);

            if (matcher.find()) {
               usd = matcher.group();
            }
            if (matcher1.find()){
                uah = matcher1.group();
            }
            pri += "\n" + a.getName() +"\n" + euro + " EUR " +  "\n" + uah + " UAH " + "\n" + usd + " USD " + "\n";
        }
        return pri;
    }
}

