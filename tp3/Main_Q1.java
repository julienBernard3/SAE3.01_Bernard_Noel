package tp3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_Q1 {
    public static void main(String[] args) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File("./images_etudiants/originale.jpg"));
        ImageIO.write(bufferedImage, "JPG", new File("./images_etudiants/nouvelleImage.jpg"));
    }
}
