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

public class CommandsENG extends TelegramBot {
    public CommandsENG(BotConfig config) {
        super(config);
    }

    public void sendMessageENG(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);


        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendPhotoENG(long chatId) {
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

    public void SendMapDirectionsENG(long chatId) {
        SendPhoto mapDirections = new SendPhoto();
        mapDirections.setChatId(String.valueOf(chatId));
        mapDirections.setPhoto(new InputFile(new File("data/loca2.png")));
        try {
            execute(mapDirections);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void PricesENG(long chatId) {
        Parsers parsers = new Parsers();
        sendMessageENG(chatId, parsers.getAllPrices());
    }

    public void nailsAppointmentENG(long chatId) {


        Commands.message.setChatId(String.valueOf(chatId));
        Commands.message.setText("What type of manicure would you like??");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var apparatButton = new InlineKeyboardButton();

        apparatButton.setText("Apparat");
        apparatButton.setCallbackData("APPARAT_BUTTON_ENG");

        var combiButton = new InlineKeyboardButton();

        combiButton.setText("Combine");
        combiButton.setCallbackData("COMBI_BUTTON_ENG");

        rowsInLine.add(Collections.singletonList(apparatButton));
        rowsInLine.add(Collections.singletonList(combiButton));

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);
        Commands.message.setReplyMarkup(markupInLine);
        try {
            execute(Commands.message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    public void clearAppointmentsForUserENG(long chatId, List<String> appointments, Update update) {
        try {
        int indexForDelete = 0;
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).contains(String.valueOf(update.getMessage().getChat().getId()))) {
                indexForDelete = i;
            }
        }
        appointments.remove(indexForDelete);
        sendMessageENG(chatId, "Your appointments were removed");
        } catch (Exception e) {
            sendMessageENG(chatId, "You have no appointments yet");
        }


    }



//    public void checkAppointmentsForUserENG(long chatId, List<String> appointments, Update update) {
//
//        int indexForShow = 0;
//        for (int i = 0; i < appointments.size(); i++) {
//            if (appointments.get(i).contains(String.valueOf(update.getMessage().getChat().getId()))) {
//                indexForShow = i;
//            }
//            sendMessageENG(chatId, appointments.get(indexForShow));
//        }
//
//    }






    public void changeLanguageENG(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Change language");
        row.add("Portfolio");
        row.add("Check my appointments");

        keyboardRows.add(row);

        row = new KeyboardRow();

        row.add("Make an appointment for manicure");
        row.add("Information about salon");
        row.add("PriceList");
        row.add("Clear my appointments");

        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
