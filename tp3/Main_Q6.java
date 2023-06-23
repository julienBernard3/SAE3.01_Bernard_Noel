package tp3;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main_Q6 {
    public static void main(String[] args) {
        // Chemin vers l'image originale
        String originalImagePath = "./images_etudiants/originale.jpg";

        // Tableau contenant les chemins vers les images à comparer
        String[] imagePaths = {
                "./images_etudiants/coul_2.png",
                "./images_etudiants/coul_3.png",
                "./images_etudiants/coul_5.png",
                "./images_etudiants/coul_10.png",
                "./images_etudiants/coul_20.png",
                "./images_etudiants/copie.png",
                "./images_etudiants/copie_pixels.png",
                "./images_etudiants/copie_nb.png",
                "./images_etudiants/copie_rouge.png",
                "./images_etudiants/copie_vert_bleu.png",
                "./images_etudiants/copie_proche_YG.png",
                "./images_etudiants/copie_proche_YGW.png",
                "./images_etudiants/copie_proche_YGWO.png",
                "./images_etudiants/copie_proche_YGWOP.png"
        };

        try {
            // Chargement de l'image originale
            BufferedImage originalImage = ImageIO.read(new File(originalImagePath));

            // Boucle pour comparer les distances avec chaque image
            for (String imagePath : imagePaths) {
                // Chargement de l'image à comparer
                BufferedImage image = ImageIO.read(new File(imagePath));

                // Calcul de la distance entre les deux images
                long distance = Distance.distance(originalImage, image);

                // Affichage de la distance
                System.out.println("distance(" + originalImagePath + ", " + imagePath + ") = " + distance);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
