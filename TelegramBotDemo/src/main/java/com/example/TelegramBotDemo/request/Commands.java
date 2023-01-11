package com.example.TelegramBotDemo.request;

import com.example.TelegramBotDemo.config.BotConfig;
import com.example.TelegramBotDemo.service.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Commands extends TelegramBot {
    static SendMessage message = new SendMessage();


    public Commands(BotConfig config) {
        super(config);
    }

    public void startCommand(long chatId, String name) {
        String answer = "Welcome to Nail Salon, " + name + "!";
        sendMessage(chatId, answer);
    }

    public void buttonsForMenu (long chatId, String textToSend) {
        SendMessage message2 = new SendMessage();
        message2.setChatId(String.valueOf(chatId));
        message2.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Сменить язык");
        row.add("Портфолио");
        row.add("Очистить запись");

        keyboardRows.add(row);

        row = new KeyboardRow();

        row.add("Сделать запись на ноготочки");
        row.add("Информация о салоне");
        row.add("ПрайсЛист");
        row.add("Посмотреть мои записи");

        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        message2.setReplyMarkup(keyboardMarkup);


        try {
            execute(message2);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }


    }


    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);


        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendPhoto(long chatId) {
        SendPhoto sendPhoto1 = new SendPhoto();
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto1.setChatId(String.valueOf(chatId));
        sendPhoto.setChatId(String.valueOf(chatId));
        sendPhoto.setPhoto(new InputFile(new File(("data/mani.jpeg"))));
        sendPhoto1.setPhoto(new InputFile(new File("data/nat2.jpeg")));
        try {
            execute(sendPhoto);
            execute(sendPhoto1);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void SendMapDirections(long chatId) {
        SendPhoto mapDirections = new SendPhoto();
        mapDirections.setChatId(String.valueOf(chatId));
        mapDirections.setPhoto(new InputFile(new File("data/loca2.png")));
        try {
            execute(mapDirections);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void Prices(long chatId) {
        Parsers parsers = new Parsers();
        sendMessage(chatId, parsers.getAllPrices());
    }

    public void nailsAppointment(long chatId) {


        message.setChatId(String.valueOf(chatId));
        message.setText("Какой тип маникюра вы предпочитаете?");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var apparatButton = new InlineKeyboardButton();

        apparatButton.setText("Аппаратный");
        apparatButton.setCallbackData("APPARAT_BUTTON");

        var combiButton = new InlineKeyboardButton();

        combiButton.setText("Комбинированный");
        combiButton.setCallbackData("COMBI_BUTTON");

        rowsInLine.add(Collections.singletonList(apparatButton));
        rowsInLine.add(Collections.singletonList(combiButton));

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeLanguage(long chatId, String textToSend) {
        SendMessage message2 = new SendMessage();
        message2.setChatId(String.valueOf(chatId));
        message2.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("\uD83C\uDDF7\uD83C\uDDFARussian\uD83C\uDDF7\uD83C\uDDFA");
        row.add("\uD83C\uDDFA\uD83C\uDDF8English\uD83C\uDDFA\uD83C\uDDF8");

        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        message2.setReplyMarkup(keyboardMarkup);


        try {
            execute(message2);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }



    }

    public void clearAppointmentsForUser (long chatId, List<String> appointments, Update update) {

        try {
            int indexForDelete = 0;
            for (int i = 0; i < appointments.size(); i++) {
                if (appointments.get(i).contains(String.valueOf(update.getMessage().getChat().getId()))) {
                    indexForDelete = i;
                }
            }
            appointments.remove(indexForDelete);
            sendMessage(chatId, "ваши записи удалены");
        } catch (Exception e){
                sendMessage(chatId, "У вас нет записей");
        }
    }




    public void checkAppointmentsForUser (long chatId, List<String> appointments, Update update) {

        int indexForShow = 0;
        String chat = String.valueOf(update.getMessage().getChatId());
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).contains(chat)) {
                indexForShow = i;
                sendMessage(chatId, appointments.get(indexForShow));
            }
        }
    }



    public void buttonsForOwner (long chatId, String textToSend) {

        SendMessage message2 = new SendMessage();
        message2.setChatId(String.valueOf(chatId));
        message2.setText(textToSend);


        ReplyKeyboardMarkup keyboardMarkupForOwner = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRowsForOwner = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Показать записи");
        row.add("Очистить записи");

        keyboardRowsForOwner.add(row);

        keyboardMarkupForOwner.setKeyboard(keyboardRowsForOwner);
        message2.setReplyMarkup(keyboardMarkupForOwner);

        try {
            execute(message2);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }


}