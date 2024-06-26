package com.ak.learning.concurrency.optimizingforlatency;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ColorizeMultiThreaded {
    public static final Path SOURCE_PATH = Paths.get("src/main/resources/many-flowers.jpg");
    public static final Path DEST_PATH = Paths.get("src/main/resources/many-flowers-multi-out.jpg");

    public static void main(String[] args) throws Exception {

        BufferedImage inputImage = ImageIO.read(new File(SOURCE_PATH.toUri()));
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        long start = System.currentTimeMillis();
        colorizeMultiThreaded(inputImage, outputImage, 4);

        File outputFile = new File(DEST_PATH.toUri());
        ImageIO.write(outputImage, "jpg", outputFile);

        long end = System.currentTimeMillis();

        System.out.println("Time taken to colorize and generate with single thread: " + (end-start) + " miliseconds");
    }

    private static void colorizeMultiThreaded(BufferedImage inputImage, BufferedImage outputImage, int numberOfThreads) throws InterruptedException {
        List<Thread> threads = new ArrayList<>(10);
        int width = inputImage.getWidth();
        int height = inputImage.getHeight() /numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadMultiplier = i;

            Thread thread = new Thread(() -> {
               int leftCorner = 0;
               int topCorner = height * threadMultiplier;
               recolorImage(inputImage, outputImage, leftCorner, topCorner, width, height);
            });
            threads.add(thread);
        }

        for(Thread t : threads) {
            t.start();
        }
        for (Thread t: threads) {
            t.join();
        }
    }


    public static void recolorImage(BufferedImage originalImage, BufferedImage newImage, int leftCorner, int topCorner, int width, int height) {
        for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++ ) {
          for (int y = topCorner; y < topCorner + height && y < originalImage.getHeight(); y++) {
              recolorPixel(originalImage, newImage, x, y);
          }
        }
    }

    public static void recolorPixel(BufferedImage inputImage, BufferedImage outputImage, int x, int y) {
        // get the value of each pixel at x and y
        int rgb = inputImage.getRGB(x, y);

        int red = getRed(rgb);
        int blue = getBlue(rgb);
        int green = getGreen(rgb);

        int newRed, newBlue, newGreen;
        if (isShadeOfGray(red, blue, green)) {
            newRed = Math.min(255, red + 10); // we increase the value of RED by 10 units to make it closer to purple.
            // However, we do want to curb it to not cross 255 at any given point.
            newGreen = Math.max(0, green - 80); // we reduce the green
            newBlue = Math.max(0, blue - 20); // and we reduce the blue

            // These values are by trial-and-error. To turn the pixel into purple, the red is increased and green is
            // significantly decreased to give some shade of purple. But looking at the color of the flower, the shade
            // of the purple is bit more on the red side than on the blue side. So reduce the blue side and increase
            // the red side.
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

    /**
     * Takes in a combination of red, blue and green and determines if the pixel is a shade of gray
     * @param red - red color component
     * @param green - green color component
     * @param blue - blue color component
     * @return returns if the given rgb input is a shade of gray
     */
    public static boolean isShadeOfGray(int red, int green, int blue) {
        // Check if all the colors have the similar color intensity. In other words, if no component is stronger than
        // the other, then the color is a perfect mix of red, green and blue we know it's a shade of gray. The arbitrary
        // distance used below (30) to determine their proximity indicates the kind of gray we can tolerate as gray
        // without deeming it to be dark enough to be called a shade of black
        return Math.abs(red - green) < 30
                && Math.abs(red - blue) < 30
                && Math.abs(green - blue) < 30;
    }

    /**
     * This will take individual color components and build a compound rgb value from it
     * @param red - the red component of the color
     * @param green - the green component of the color
     * @param blue - the blue component of the color
     * @return a compound color by applying all the 3 colors.
     */
    public static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0; // so we take a basic "no color"

        rgb |= blue; // we apply the blue component to the extreme right to its place in the color.
        rgb |= green << 8; // followed by 'OR-ing' the green component shifting it 8 bits to the left.
        rgb |= red << 16; // followed by 'OR-ing' the red component shifting it 16 bits to the left.

        rgb |= 0xFF000000; // we should set the alpha value to the highest value to make the pixel opaque. FF=255

        return rgb;
    }

    /**
     * Takes an rgb value and returns the red component value from it.
     * @param rgb - input color
     * @return an int indicating the red color
     */
    public static int getRed(int rgb) {
        // This is achieved by masking the alpha, green and blue and then shifting out 2 bytes to the right.
        return (rgb & 0x00FF0000) >> 16;
    }

    /**
     * Takes an RGB value and extracts only the green component value from it.
     * @param rgb input color
     * @return an int indicating the green color
     */
    public static int getGreen(int rgb) {
        // This is achieved by applying a bitmask out the alpha, red and blue components leaving out only the middle
        // byte. Since the green is the middle byte, we need to shift it 8
        return (rgb & 0x0000FF00) >> 8;
    }

    /**
     * Takes an RGB value and extracts only the Blue value from it.
     * @param rgb input color
     * @return an int indicating the blue color
     */
    public static int getBlue(int rgb) {
        // This is achieved by applying a bit-mask to the pixel making all the other components zero except the last byte.
        return rgb & 0x000000FF;
    }
}

/**
 * Color basics -
 * An image is nothing more than a 2-dimensional collection of Pixels. The color of a pixel can be encoded in different
 * ways. A few frequently used groups of pixel color encoding are:
 * - Y'UV - Luma (brightness), and 2 chroma (color) components
 * - RGB - Red, Green, Blue
 * - HSL and HSV - Hue, Saturation, Lightness/Brightness CIE XYZ
 * - Device independent Red, Green and Blue
 *
 * A,R,G,B stands for
 * A = Alpha
 * R = Red
 * G = Green
 * B = Blue
 *
 * Each component is represented by 1 byte (8 bits) so the value of each component is in the range of 0
 * (0x in hexadecimal) and 255 (0xFF in hexadecimal). Since we have 4 bytes, we can store the entire color of a pixel
 * in a variable of type int
 * Using the RGB components you can achieve any color by using 1 component of the color and keeping others to a minimum.
 * If we keep all the components colors the same, you get varying shades of gray varying from White to Black. So looking
 * at the many-flowers.jpg file, you can see a shades of gray and that is how we are going to fina areas that we want to
 * recolor.
 */
