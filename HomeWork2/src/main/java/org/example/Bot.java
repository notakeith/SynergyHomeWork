package org.example;

import org.example.functions.FilterOperations;
import org.example.utils.ImageUtils;
import org.example.utils.PhotoMessageUtils;
import org.example.utils.RgbMaster;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    @Override
    public String getBotToken() {
        return "token";
    }

    @Override
    public String getBotUsername() {
        return "name";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String chatId = message.getChatId().toString();
        if (message.hasPhoto()) {
            try {
                ArrayList<String> photoPaths = new ArrayList<>(PhotoMessageUtils.savePhotos(getFilesByMessage(message), getBotToken()));
                for (String path : photoPaths) {
                    processingImage(path, "grayScale");
//                    execute(preparePhotoMessage(path, chatId));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                processingImage("last_recived_image.jpeg", message.getText());
                execute(preparePhotoMessage("last_recived_image.jpeg", chatId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void processingImage(String fileName, String filterName) throws Exception {
        final BufferedImage image = ImageUtils.getImage(fileName);
        final RgbMaster rgbMaster = new RgbMaster(image);
        switch (filterName) {
            case "grayScale":
                rgbMaster.changeImage(FilterOperations::grayScale);
                break;
            case "onlyRed":
                rgbMaster.changeImage(FilterOperations::onlyRed);
                break;
            case "onlyGreen":
                rgbMaster.changeImage(FilterOperations::onlyGreen);
                break;
            case "onlyBlue":
                rgbMaster.changeImage(FilterOperations::onlyBlue);
                break;
            case "increaseBrightness":
                rgbMaster.changeImage(FilterOperations::increaseBrightness);
                break;
            case "invertColors":
                rgbMaster.changeImage(FilterOperations::invertColors);
                break;
            case "sepia":
                rgbMaster.changeImage(FilterOperations::sepia);
                break;
            case "blackAndWhiteWithHighlight":
                rgbMaster.changeImage(FilterOperations::blackAndWhiteWithHighlight);
                break;
            case "increaseContrast":
                rgbMaster.changeImage(FilterOperations::increaseContrast);
                break;
            default:
                break;
        }
        ImageUtils.saveImage(rgbMaster.getImage(), fileName);
    }

    private SendPhoto preparePhotoMessage(String localPath, String chatId) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setReplyMarkup(getKeyboard(FilterOperations.class));
        sendPhoto.setChatId(chatId);
        InputFile newFile = new InputFile();
        newFile.setMedia(new File(localPath));
        sendPhoto.setPhoto(newFile);
        return sendPhoto;
    }


    private List<org.telegram.telegrambots.meta.api.objects.File> getFilesByMessage(Message message) {
        List<PhotoSize> photoSizes = message.getPhoto();
        ArrayList<org.telegram.telegrambots.meta.api.objects.File> files = new ArrayList<>();
        for (PhotoSize photoSize : photoSizes) {
            final String fileId = photoSize.getFileId();
            try {
                files.add(sendApiMethod(new GetFile(fileId)));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return files;
    }

    private ReplyKeyboardMarkup getKeyboard(Class<?> someClass) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();

        Method[] methods = someClass.getDeclaredMethods();
        List<String> methodNames = Arrays.asList(
                "grayScale",
                "onlyRed",
                "onlyGreen",
                "onlyBlue",
                "increaseBrightness",
                "invertColors",
                "sepia",
                "blackAndWhiteWithHighlight",
                "increaseContrast"
        );

        int columnCount = 3;
        int rowIndex = 0;

        for (Method method : methods) {
            String methodName = method.getName();
            if (methodNames.contains(methodName)) {
                if (rowIndex % columnCount == 0) {
                    keyboardRows.add(new KeyboardRow());
                }
                KeyboardButton keyboardButton = new KeyboardButton(methodName);
                keyboardRows.get(rowIndex / columnCount).add(keyboardButton);
                rowIndex++;
            }
        }

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }
}

