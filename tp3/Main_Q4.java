package tp3;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_Q4 {
    public static void main(String[] args) throws IOException {

        BufferedImage bufferedImage1 = ImageIO.read(new File("./images_etudiants/originale.jpg"));
        BufferedImage bufferedImage2 = new BufferedImage(bufferedImage1.getWidth(),bufferedImage1.getWidth(),BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < bufferedImage1.getWidth(); x++) {
            for (int y = 0; y < bufferedImage1.getHeight(); y++) {

                int pixelColor = bufferedImage1.getRGB(x, y);

                bufferedImage2.setRGB(x, y, pixelColor&0x00ffff);
            }
        }

        ImageIO.write(bufferedImage2, "JPG", new File("./images_etudiants/nouvelleImageVertBleu.jpg"));

    }
}
