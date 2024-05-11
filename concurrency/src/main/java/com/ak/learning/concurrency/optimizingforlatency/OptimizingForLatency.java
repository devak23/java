package com.ak.learning.concurrency.optimizingforlatency;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OptimizingForLatency {
    public static final Path SOURCE_PATH = Paths.get("src/main/resources/many-flowers.jpg");
    public static final Path DEST_PATH = Paths.get("src/main/resources/many-flowers-out.jpg");

    public static void main(String[] args) throws Exception {

        BufferedImage inputImage = ImageIO.read(new File(SOURCE_PATH.toUri()));
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        long start = System.currentTimeMillis();
        colorizeSingleThreaded(inputImage, outputImage);

        File outputFile = new File(DEST_PATH.toUri());
        ImageIO.write(outputImage, "jpg", outputFile);

        long end = System.currentTimeMillis();

        System.out.println("Time taken to colorize and generate with single thread: " + (end-start) + " miliseconds");
    }

    public static void colorizeSingleThreaded(BufferedImage originalImage, BufferedImage newImage) {
        recolorImage(originalImage, newImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }

    public static void recolorImage(BufferedImage originalImage, BufferedImage newImage, int leftCorner, int topCorner, int width, int height) {
        for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++ ) {
          for (int y = topCorner; y < topCorner + height && y < originalImage.getHeight(); y++) {
              recolorPixel(originalImage, newImage, x, y);
          }
        }
    }

    public static void recolorPixel(BufferedImage inputImage, BufferedImage outputImage, int x, int y) {
        int rgb = inputImage.getRGB(x, y);

        int red = getRed(rgb);
        int blue = getBlue(rgb);
        int green = getGreen(rgb);

        int newRed, newBlue, newGreen;
        if (isShadeOfGray(red, blue, green)) {
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 80);
            newBlue = Math.max(0, blue - 20);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }

        int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(outputImage, x, y, newRGB);
    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    public static boolean isShadeOfGray(int red, int green, int blue) {
        return Math.abs(red - green) < 30
                && Math.abs(red - blue) < 30
                && Math.abs(green - blue) < 30;
    }

    public static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0;

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
        return rgb & 0x000000FF;
    }
}
