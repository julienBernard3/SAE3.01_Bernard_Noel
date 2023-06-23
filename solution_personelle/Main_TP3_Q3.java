package solution_personelle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.Color;


public class Main_TP3_Q3 {
    public static void main(String[] args) throws IOException {
        String imagePath;
        int nbCouleurs;
        if (args.length != 2) {
            System.out.println("Veuillez spécifier 2 arguments :");
            System.out.println("    - le chemin vers l'image à traiter ('images_etudiants/copie.png' par default)");
            System.out.println("    - le nombre de couleurs à conserver");
            imagePath = "images_etudiants/copie.png";
            nbCouleurs = 20;
        }
        else {
            imagePath = args[0];
            try {
                nbCouleurs = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Le nombre de couleurs doit être un entier");
                return;
            }
        }
        // on récupère l'extension de l'image
        String extension = imagePath.substring(imagePath.lastIndexOf('.') + 1);
        // Chargement image
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.out.println("L'image n'a pas pu être chargée, vérifiez le chemin spécifié (et qu'il contient bien l'extension)");
            System.out.println("Chemin spécifié : " + imagePath);
            return;
        }

        // Méthode 1
        Solution solution_v1 = new Solution_v1();
        // Calculs sur image
        BufferedImage nImageV1 = solution_v1.reductionCouleurs(image, nbCouleurs);
        //Sauvegarde image
        ImageIO.write(nImageV1, extension.toUpperCase(), new File(imagePath+"ReduiteV1_"+nbCouleurs+"."+extension));

        // Méthode 2
        Solution solution_v2 = new Solution_v2();
        BufferedImage nImagev2 =  solution_v2.reductionCouleurs(image, nbCouleurs);
        ImageIO.write(nImagev2, extension.toUpperCase(), new File(imagePath+"ReduiteV2_"+nbCouleurs+"."+extension));

        // Méthode 3

        Solution solution_v3 = new Solution_v3();
        BufferedImage nImagev3 =  solution_v3.reductionCouleurs(image, nbCouleurs);
        ImageIO.write(nImagev3, extension.toUpperCase(), new File(imagePath+"ReduiteV3_"+nbCouleurs+"."+extension));

        // Méthode 4
        Solution solution_v4 = new Solution_v4();
        BufferedImage nImagev4 =  solution_v4.reductionCouleurs(image, nbCouleurs);
        ImageIO.write(nImagev4, extension.toUpperCase(), new File(imagePath+"ReduiteV4_"+nbCouleurs+"."+extension));
    }


    //Méthode calcul sur image

    //Calcul histogramme
    //Selection des couleurs représentatives
    //Remplacement des autres couleurs par la couleur représentative la + proche

}