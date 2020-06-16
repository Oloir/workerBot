import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot{
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {

//        Conn.Conn();
////      Conn.CreateDB();
//        Conn.WriteDB();
//        Conn.ReadDB();
//        Conn.CloseDB();


//        System.getProperties().put("proxySet", "true");
//        System.getProperties().put("socksProxyHost", "127.0.0.1");
//        System.getProperties().put("socksProxyPort", "9150");


        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        }
        catch (TelegramApiException e){
            e.printStackTrace();

        }
    }

//test2


    public void sendMsg(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try{
            setButtons(sendMessage);
            execute(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }


    public void onUpdateReceived(Update update) {

          Connection conn;
          Statement statmt;
          ResultSet resSet;

        conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:quotes");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ArrayList<String> List1 = new ArrayList<String>();
        Message message = update.getMessage();
        if(message !=null && message.hasText()){
            switch (message.getText()){
                case "Дай цитату!":
                    Quote QQ = new Quote();
                    try {
                        sendMsg(message, QQ.getQuote());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Спасибо!":
                    sendMsg(message, "Пожалуйста!");
                    break;
                default:
                    sendMsg(message, "Записал!");

                    String newQuote = null;
                    newQuote = message.getText();

                    String sql = "insert into quotes (" +
                            "text) values (?)";
                    try (PreparedStatement statement = conn.prepareStatement(sql)) {
                        statement.setString(1, newQuote);
                        statement.execute();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }










//                    FileWriter FW = null;
//                    try {
//                        FW = new FileWriter("Library", true);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        FW.write("\n" + message.getText());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        FW.flush();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        FW.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    List1.add(message.getText());
//                    break;

            }
        }
    }

// Новая клавиатура

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowsList = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();

        firstRow.add(new KeyboardButton("Дай цитату!"));

        keyboardRowsList.add(firstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
    }

//  Конфиг

    public String getBotUsername() {
        return "QueBotBot";
    }

    public String getBotToken() {
        return "943087428:AAEoLmSLXJfsTHbkA-wRIEmhoGKb-8SPrxI";
    }
}