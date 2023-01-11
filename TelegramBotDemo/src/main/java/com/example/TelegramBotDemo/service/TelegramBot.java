package com.example.TelegramBotDemo.service;

import com.example.TelegramBotDemo.config.BotConfig;
import com.example.TelegramBotDemo.request.Commands;
import com.example.TelegramBotDemo.request.CommandsENG;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {


    BotConfig config;
    long owner = 1019130800;
    long chatId;
    String messageText = null;
    String sendOwner = "";
    List<String> appointments = new ArrayList<>();


    public TelegramBot(BotConfig config) {
        this.config = config;
    }


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText() && !(update.getMessage().getChatId() == owner)) {
            messageText = update.getMessage().getText();
            chatId = update.getMessage().getChatId();
            Commands commands = new Commands(config);
            CommandsENG commandsENG = new CommandsENG(config);

            switch (messageText) {


                case "/start":

                    commands.startCommand(chatId, update.getMessage().getChat().getFirstName());
                    commands.changeLanguage(chatId, "Please, choose language to start!");


                    break;

                case "Сделать запись на ноготочки":


                    commands.nailsAppointment(chatId);

                    break;

                case "\uD83C\uDDF7\uD83C\uDDFARussian\uD83C\uDDF7\uD83C\uDDFA":

                    commands.buttonsForMenu(chatId, "Вы выбрали русский язык");


                    break;

                case "Портфолио":

                    commands.sendMessage(chatId, "Портфолио Мастер маникюра:");
                    commands.sendPhoto(chatId);
                    break;

                case "Информация о салоне":

                    commands.SendMapDirections(chatId);
                    commands.sendMessage(chatId, "Москва, улица Гагарина, Кв.123"
                            + "\nНомер телефона: +88005553555 - Галя Мастер маникюра");
                    break;

                case "Сменить язык":

                    commands.changeLanguage(chatId, "Выберите язык");

                    break;



                case "ПрайсЛист":

                    commands.Prices(chatId);
                    break;

                case "Очистить запись":

                    commands.clearAppointmentsForUser(chatId, appointments, update);

                    break;


                case "Посмотреть мои записи":

                       commands.checkAppointmentsForUser(chatId, appointments, update);

                    break;


                default:
                    regexNum(update);
                    break;


                case "Check my appointments":


                    commands.checkAppointmentsForUser(update.getMessage().getChat().getId(), appointments, update);



                    break;


                case "Clear my appointments":

                    commandsENG.clearAppointmentsForUserENG(chatId, appointments, update);
                    break;


                case "PriceList":

                    commandsENG.PricesENG(chatId);


                    break;

                case "Information about salon":

                    commandsENG.SendMapDirectionsENG(chatId);
                    commands.sendMessage(chatId, "Moscow, Gagarin Street, Apt.123"
                            + "\nPhone Number: +88005553555 - Galya Manicure");
                    break;

                case "Make an appointment for manicure":

                    commandsENG.nailsAppointmentENG(chatId);

                    break;


                case "Portfolio":
                    commands.sendMessage(chatId, "Portfolio of Manicure Master");
                    commandsENG.sendPhotoENG(chatId);
                    break;

                case "\uD83C\uDDFA\uD83C\uDDF8English\uD83C\uDDFA\uD83C\uDDF8":

                    commandsENG.changeLanguageENG(chatId, "You changed language to English\uD83C\uDDFA\uD83C\uDDF8");


                    break;





                case "Change language":

                    commands.changeLanguage(chatId, "Choose language");


            }
        } else if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getChatId() == owner) {
            Commands commands = new Commands(config);
            String message = update.getMessage().getText();
            switch (message) {
                case "/start":
                    commands.sendMessage(owner, "Здравствуйте");
                    commands.buttonsForOwner(owner, "Вы вошли в режим владельца бота");
                    break;

                case "Показать записи":
                    for (String a : appointments) {
                        commands.sendMessage(owner, a);
                    }
                    break;

                case "Очистить записи":
                    sendOwner = "";
                    appointments.clear();

                    break;
            }

        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId2 = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.equals("APPARAT_BUTTON")) {
                String text = "Введите ваше имя:" +
                        "\nВведите номер телефона:" +
                        "\nПример:  Ivan +421999999999" + "\n"
                        + "\nПодсказка: Имя и телефон должны вводится через пробел" +
                        "\nНомер телефона должен начинаться с +421 и 9 цифр";
                EditMessageText message = new EditMessageText();
                message.setChatId(String.valueOf(chatId2));
                message.setText(text);
                message.setMessageId((int) messageId);
                sendOwner += "\n" + "**Appointment for Hardware manicure**";
                try {
                    execute(message);
                } catch (TelegramApiException e) {

                }
            } else if (callbackData.equals("COMBI_BUTTON")) {
                String text = "" +
                        "Введите ваше имя:" +
                        "\nВведите номер телефона:" +
                        "\nПример:  Ivan +421999999999" + "\n"
                        + "\nПодсказка: Имя и телефон должны вводится через пробел" +
                        "\nНомер телефона должен начинаться с +421 и 9 цифр";
                EditMessageText message = new EditMessageText();
                message.setChatId(String.valueOf(chatId2));
                message.setText(text);
                message.setMessageId((int) messageId);
                sendOwner += "\n" + "**Appointment for Combined manicure**";
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }

            } else if (callbackData.equals("APPARAT_BUTTON_ENG")) {
                String text = "Enter your first name:" +
                        "\nEnter your phone number:" +
                        "\nExample:  Ivan +421999999999" + "\n"
                        + "\nHint: You should use space button after after name to enter phone number" +
                        "\nPhone number should start with +421 and 9 numbers";
                EditMessageText message = new EditMessageText();
                message.setChatId(String.valueOf(chatId2));
                message.setText(text);
                message.setMessageId((int) messageId);
                sendOwner += "\n" + "**RECEIPT**" + "\n" + "\n" + "Appointment for apparat manicure" + "\n";
                try {
                    execute(message);
                } catch (TelegramApiException e) {

                }
            } else if (callbackData.equals("COMBI_BUTTON_ENG")) {
                String text = "" +
                        "Enter your first name:" +
                        "\nEnter your phone number:" +
                        "\nExample:  Ivan +421999999999" + "\n"
                        + "\nHint: You should use space button after after name to enter phone number" +
                        "\nPhone number should start with +421 and 9 numbers";
                EditMessageText message = new EditMessageText();
                message.setChatId(String.valueOf(chatId2));
                message.setText(text);
                message.setMessageId((int) messageId);

                sendOwner += "\n" +  "**RECEIPT**"
                        + "\n" + "\n" + "Your appointment for combine manicure" + "\n";
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    public void regexNum(Update update) {
        String regex = "^([a-zа-я A-ZА-Я]{3,15}) (\\+421\\d{9}$)";
        Commands commands = new Commands(config);
        messageText = messageText.trim();
        if (messageText.matches(regex)) {
            commands.sendMessage(chatId, "Thank you! Nail Master will contact you soon!" + "\n"
                    + "Спасибо! Мастер маникюра скоро с вами свяжется!");
            appointments.add(sendOwner  + "\n" + " **information about your appointment:**" + "\n" + "**RECEIPT**"
                    + "\n"
                    + "\n" + update.getMessage().getText() + "\n" + "Your receipt number (PLEASE SAVE IT): "
                    + update.getMessage().getChatId() + "\n" + "If have any questions about your appointment -" +
                    "please call service support ' +88005553555' ");


        } else {
            commands.sendMessage(chatId, "Invalid phone number or name!" + "\n"
                    + "Неверный номер телефона или имя!");
        }
    }
}