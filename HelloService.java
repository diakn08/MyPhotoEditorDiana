package org.example.miaop2;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class HelloService {

    public static Image grayscale(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        PixelReader reader = image.getPixelReader();
        WritableImage result = new WritableImage(width, height);
        PixelWriter writer = result.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                double gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color grayColor = new Color(gray, gray, gray, color.getOpacity());
                writer.setColor(x, y, grayColor);
            }
        }

        return result;
    }

    public static Image blur(Image inputImage) {
        int width = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();

        PixelReader reader = inputImage.getPixelReader();
        WritableImage outputImage = new WritableImage(width, height);
        PixelWriter writer = outputImage.getPixelWriter();

        int blurRadius = 1;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double r = 0, g = 0, b = 0;
                int count = 0;

                for (int dy = -blurRadius; dy <= blurRadius; dy++) {
                    for (int dx = -blurRadius; dx <= blurRadius; dx++) {
                        int nx = x + dx;
                        int ny = y + dy;

                        if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                            Color color = reader.getColor(nx, ny);
                            r += color.getRed();
                            g += color.getGreen();
                            b += color.getBlue();
                            count++;
                        }
                    }
                }

                r /= count;
                g /= count;
                b /= count;

                writer.setColor(x, y, new Color(r, g, b, 1.0));
            }
        }

        return outputImage;
    }
}


