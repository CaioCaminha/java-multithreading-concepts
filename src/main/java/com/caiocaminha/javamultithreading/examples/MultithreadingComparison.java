package com.caiocaminha.javamultithreading.examples;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.concurrent.atomic.AtomicInteger;

public class MultithreadingComparison {
    public static final String SOURCE_FILE = "./resources/image.jpg";
    public static final String DESTINATION_FILE = "./out/image.jpg";

    public static void main(String[] args) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        System.out.println(
                new AtomicInteger(19).hashCode()
        );

    }


    public static class HashCodeExample {
        public static  int hashCodeCalculator(Object o) {
            int result = 1;

            return 31 * result + (o == null ? 0 : o.hashCode());
        }
    }

    public static void recolorImage(
            BufferedImage originalImage,
            BufferedImage resultImage,
            int leftCorner,
            int topCorner,
            int width,
            int height
    ) {

    }

    //this example is bullshit - forget about it

    public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
        int rgb = originalImage.getRGB(x, y);

        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed;
        int newGreen;
        int newBlue;

        if(isShadeOfGray(red, green, blue)) {
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green + 80);
            newBlue = Math.max(0, blue + 20);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }

        int newRgb = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(resultImage, x, y, newRgb);
    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }


    public static boolean isShadeOfGray(int red, int green, int blue) {
        return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
    }

    public static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0;

        /**
         * |= OR ASSIGN operator is equals to rgb = rgb | blue
         * << shift green's bits 8 bits to the left
         * I have no idea why this is needed
         */
        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;
        rgb |= 0xFF000000;

        return rgb;
    }

    public static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }

    public static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    public static int getBlue(int rgb) {
        return (rgb & 0x000000FF);
    }
}
