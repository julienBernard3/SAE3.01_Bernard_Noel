package tp3;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_Q3 {
    public static void main(String[] args) throws IOException {

        BufferedImage bufferedImage1 = ImageIO.read(new File("./images_etudiants/originale.jpg"));
        BufferedImage bufferedImage2 = new BufferedImage(bufferedImage1.getWidth(),bufferedImage1.getWidth(),BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < bufferedImage1.getWidth(); x++) {
            for (int y = 0; y < bufferedImage1.getHeight(); y++) {

                Color pixelColor = new Color(bufferedImage1.getRGB(x, y));
                int[] rgb = Utilitaire.extractRGB(pixelColor.getRGB());

                int grayLevel = (rgb[0] + rgb[1] + rgb[2]) / 3;

                Color grayColor = new Color(grayLevel, grayLevel, grayLevel);

                bufferedImage2.setRGB(x, y, grayColor.getRGB());
            }
        }

        ImageIO.write(bufferedImage2, "JPG", new File("./images_etudiants/nouvelleImageGris.jpg"));

    }
}
