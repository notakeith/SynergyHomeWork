package org.example.functions;

import org.example.commands.AppBotCommand;

public class FilterOperations {
    @AppBotCommand(name = "grayScale", description = "greyScale filter", showInKeyboard = true)
    public static float[] grayScale(float[] rgb) {
        final float mean = (rgb[0] + rgb[1] + rgb[2]) / 3;
        rgb[0] = mean;
        rgb[1] = mean;
        rgb[2] = mean;
        return rgb;
    }

    @AppBotCommand(name = "onlyRed", description = "onlyRed filter", showInKeyboard = true)
    public static float[] onlyRed(float[] rgb) {
        rgb[1] = 0;
        rgb[2] = 0;
        return rgb;
    }
    @AppBotCommand(name = "onlyGreen", description = "onlyGreen filter", showInKeyboard = true)
    public static float[] onlyGreen(float[] rgb) {
        rgb[0] = 0;
        rgb[2] = 0;
        return rgb;
    }
    @AppBotCommand(name = "onlyBlue", description = "onlyBlue filter", showInKeyboard = true)
    public static float[] onlyBlue(float[] rgb) {
        rgb[0] = 0;
        rgb[1] = 0;
        return rgb;
    }

    @AppBotCommand(name = "increaseBrightness", description = "increaseBrightness filter", showInKeyboard = true)
    public static float[] increaseBrightness(float[] rgb) {
        rgb[0] = Math.min(1, rgb[0] * 2);
        rgb[1] = Math.min(1, rgb[1] * 2);
        rgb[2] = Math.min(1, rgb[2] * 2);
        return rgb;
    }

    @AppBotCommand(name = "invertColors", description = "invertColors filter", showInKeyboard = true)
    public static float[] invertColors(float[] rgb) {
        rgb[0] = 1 - rgb[0];
        rgb[1] = 1 - rgb[1];
        rgb[2] = 1 - rgb[2];
        return rgb;
    }

    @AppBotCommand(name = "sepia", description = "sepia filter", showInKeyboard = true)
    public static float[] sepia(float[] rgb) {
        rgb[0] = Math.min(1.0f, (float) (rgb[0] * 0.393 + rgb[1] * 0.769 + rgb[2] * 0.189));
        rgb[1] = Math.min(1.0f, (float) (rgb[0] * 0.349 + rgb[1] * 0.686 + rgb[2] * 0.168));
        rgb[2] = Math.min(1.0f, (float) (rgb[0] * 0.272 + rgb[1] * 0.534 + rgb[2] * 0.131));
        return rgb;
    }

    @AppBotCommand(name = "blackAndWhiteWithHighlight", description = "blackAndWhiteWithHighlight filter", showInKeyboard = true)
    public static float[] blackAndWhiteWithHighlight(float[] rgb) {
        final float threshold = 0.5f; // Пороговое значение для выделения

        float mean = (rgb[0] + rgb[1] + rgb[2]) / 3;
        if (mean > threshold) {
            rgb[0] = 1;
            rgb[1] = 1;
            rgb[2] = 1;
        } else {
            rgb[0] = 0;
            rgb[1] = 0;
            rgb[2] = 0;
        }

        return rgb;
    }
    @AppBotCommand(name = "increaseContrast", description = "increaseContrast filter", showInKeyboard = true)
    public static float[] increaseContrast(float[] rgb) {
        float mean = (rgb[0] + rgb[1] + rgb[2]) / 3;
        rgb[0] = Math.min(1.0f, Math.max(0, (float) (mean + (rgb[0] - mean) * 2)));
        rgb[1] = Math.min(1.0f, Math.max(0, (float) (mean + (rgb[1] - mean) * 2)));
        rgb[2] = Math.min(1.0f, Math.max(0, (float) (mean + (rgb[2] - mean) * 2)));
        return rgb;
    }

}
