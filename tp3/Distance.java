package tp3;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Distance {
    public static long distance(BufferedImage image1, BufferedImage image2) {
        int width = image1.getWidth();
        int height = image1.getHeight();

        long totalDistance = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color1 = new Color(image1.getRGB(x, y));
                Color color2 = new Color(image2.getRGB(x, y));

                long redDiff = color1.getRed() - color2.getRed();
                long greenDiff = color1.getGreen() - color2.getGreen();
                long blueDiff = color1.getBlue() - color2.getBlue();

                totalDistance += redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff;
            }
        }

        return totalDistance;
    }
}


