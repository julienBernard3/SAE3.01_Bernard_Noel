package tests;

import solution_personelle.*;
import tp3.Distance;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static solution_SAE.MainCluster.reductionCouleurs;

public class LancementTests {

    public static void main(String[] args) throws IOException {
        String imagePath;
        int nbCouleurs;
        if (args.length != 2) {
            System.out.println("Veuillez spécifier 2 arguments :");
            System.out.println("    - le chemin vers l'image à traiter ('images_etudiants/copie.png' par default)");
            System.out.println("    - le nombre de couleurs à conserver");
            imagePath = "images_etudiants/originale.jpg";
            nbCouleurs = 10;
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

        ArrayList<Solution> solutions = new ArrayList<>();
        solutions.add(new Solution_v1());
        solutions.add(new Solution_v2());
        solutions.add(new Solution_v3());
        solutions.add(new Solution_v4());
        solutions.add(new Solution_v5());

        String tempsEcoule = "";
        String ressemblance = "";
        int i = 1;
        for (Solution solution : solutions) {
            long startTime = System.currentTimeMillis();
            // Calculs sur image
            BufferedImage nImageV1 = solution.reductionCouleurs(image, nbCouleurs);
            //Sauvegarde image
            ImageIO.write(nImageV1, extension.toUpperCase(), new File(imagePath.split("\\.")[0]+"ReduiteV"+i+"_"+nbCouleurs+"."+extension));

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            tempsEcoule += ("Temps écoulé V"+i+" : " + elapsedTime + " millisecondes\n");
            long indiceRessemblance = Distance.distance(image, nImageV1);
            ressemblance += ("Indice de ressemblance V"+i+" : " + indiceRessemblance + "\n");
            i++;
        }

        int nbRepetitions = 30;
        long startTime = System.currentTimeMillis();
        BufferedImage nImageV1 = reductionCouleurs(image, nbCouleurs, nbRepetitions);
        ImageIO.write(nImageV1, extension.toUpperCase(), new File(imagePath+"ReduiteCluster_"+nbCouleurs+"_"+nbRepetitions+"."+extension));
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        long indiceRessemblance = Distance.distance(image, nImageV1);

        System.out.print(tempsEcoule);
        System.out.println("Temps écoulé VKMeans : " + elapsedTime + " millisecondes");
        System.out.print(ressemblance);
        System.out.println("Indice de ressemblance VKMeans : " + indiceRessemblance);
    }
}
