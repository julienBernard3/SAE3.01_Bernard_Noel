package tp3;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main_Q5b {
    public static void main(String[] args) throws IOException {

        Color[] couleurs = new Color[5];
        couleurs[0] = Color.GREEN;
        couleurs[1] = Color.YELLOW;
        couleurs[2] = Color.WHITE;
        couleurs[3] = Color.ORANGE;
        couleurs[4] = Color.PINK;


        BufferedImage bufferedImage1 = ImageIO.read(new File("./images_etudiants/originale.jpg"));
        BufferedImage bufferedImage2 = new BufferedImage(bufferedImage1.getWidth(),bufferedImage1.getWidth(),BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < bufferedImage1.getWidth(); x++) {
            for (int y = 0; y < bufferedImage1.getHeight(); y++) {

                Color pixelColor = new Color(bufferedImage1.getRGB(x, y));

                // Choix de la couleur la plus proche dans le tableau
                Color closestColor = couleurs[0];
                //Initialisation du premier écart à partir de la premiere couleur
                double minDistance = Math.pow(pixelColor.getRed() - closestColor.getRed(), 2)
                        + Math.pow(pixelColor.getGreen() - closestColor.getGreen(), 2)
                        + Math.pow(pixelColor.getBlue() - closestColor.getBlue(), 2);


                //Pour toutes les autres couleurs
                for (int i = 1; i < couleurs.length; i++) {
                    //On regarde la différence de distance entre la prochaine couleur et la couleur du pixel
                    double distance = Math.pow(pixelColor.getRed() - couleurs[i].getRed(), 2)
                            + Math.pow(pixelColor.getGreen() - couleurs[i].getGreen(), 2)
                            + Math.pow(pixelColor.getBlue() - couleurs[i].getBlue(), 2);
                    //On regarde de quelle couleur le pixel est le plus proche
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestColor = couleurs[i];
                    }
                }

                bufferedImage2.setRGB(x, y, closestColor.getRGB());
            }
        }

        ImageIO.write(bufferedImage2, "JPG", new File("./images_etudiants/nouvelleImageTransfoVertJauneBlancOrangeRose.jpg"));

    }
}
