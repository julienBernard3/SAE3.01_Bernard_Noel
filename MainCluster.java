import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.Color;


public class MainCluster {
    public static void main(String[] args) throws IOException {
        int nbCouleurs = 10;
        int nbRepetitions = 30;
        String img = "./images_etudiants/animaux/dauphin_smll";
        String extension = "png";

        BufferedImage image = ImageIO.read(new File(img+"."+extension));


        BufferedImage nImageV1 = reductionCouleurs(image, nbCouleurs, nbRepetitions);
        ImageIO.write(nImageV1, extension.toUpperCase(), new File(img+"ReduiteCluster_"+nbCouleurs+"_"+nbRepetitions+"."+extension));


        //Chargement image

        //Calculs sur image

        //Sauvegarde image


    }


    //Méthode calcul sur image

    //Calcul histogramme
    //Selection des couleurs représentatives
    //Remplacement des autres couleurs par la couleur représentative la + proche




    public static BufferedImage reductionCouleurs(BufferedImage image, int nbCouleurs, int nbRepetitions){
        long startTime = System.currentTimeMillis();
        Map<Integer, Integer> histogramme = new HashMap<>();
        int width = image.getWidth();
        int height = image.getHeight();


        double binSize = 36.00000; // Taille de chaque intervalle de couleur (en binaire) (<256)
        System.out.println(binSize);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color colorPixel = new Color(image.getRGB(x, y));

                // Quantification de la couleur
                // Manipulation d'entier
                double red = (colorPixel.getRed() / binSize);
                double green = (colorPixel.getGreen() / binSize) ;
                double blue = (colorPixel.getBlue() / binSize) ;

                int redInt = (int) (Math.round(red) * binSize);
                int greenInt = (int) (Math.round(green) * binSize);
                int blueInt = (int) (Math.round(blue) * binSize);
                if (redInt>255){
                    redInt = 255;
                }
                if (greenInt>255){
                    greenInt = 255;
                }
                if (blueInt>255){
                    blueInt = 255;
                }

                // Stockage de la couleur sous forme d'entier
                int quantizedColor = (redInt << 16) | (greenInt << 8) | blueInt;

                histogramme.put(quantizedColor, histogramme.getOrDefault(quantizedColor, 0) + 1);
            }
        }


        System.out.println("taille:"+ histogramme.size());
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(histogramme.entrySet());

        entries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        Color[] couleursRepresentatives = new Color[nbCouleurs];




        for (int i = 0; i < Math.min(nbCouleurs, entries.size()); i++) {
            couleursRepresentatives[i] = new Color(entries.get(i).getKey());

        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("Temps écoulé : " + elapsedTime + " millisecondes");

        startTime = System.currentTimeMillis();


        for (int i = 0; i < nbRepetitions; i++) {
            couleursRepresentatives = boucle(height,width,couleursRepresentatives,image,nbCouleurs);
        }

        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;

        System.out.println("Temps écoulé : " + elapsedTime + " millisecondes");

        startTime = System.currentTimeMillis();


        BufferedImage imageReduite = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pixelColor = new Color(image.getRGB(x, y));

                // Choix de la couleur la plus proche dans le tableau
                Color closestColor = couleursRepresentatives[0];
                //Initialisation du premier écart à partir de la premiere couleur
                double minDistance = calculDistance(pixelColor, closestColor);

                //On regarde la différence de distance entre la prochaine couleur et la couleur du pixel
                for (int i = 1; i < couleursRepresentatives.length; i++) {
                    //On regarde la différence de distance entre la prochaine couleur et la couleur du pixel
                    double distance = calculDistance(pixelColor, couleursRepresentatives[i]);

                    //On regarde de quelle couleur le pixel est le plus proche
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestColor = couleursRepresentatives[i];
                    }
                }

                imageReduite.setRGB(x, y, closestColor.getRGB());
            }
        }
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;

        System.out.println("Temps écoulé : " + elapsedTime + " millisecondes");

        return imageReduite;
    }

    public static double calculDistance(Color origine, Color destination){
        return Math.pow(origine.getRed() - destination.getRed(), 2)
                + Math.pow(origine.getGreen() - destination.getGreen(), 2)
                + Math.pow(origine.getBlue() - destination.getBlue(), 2);
    }

    public static Color[] boucle(int height, int width, Color[] couleursRepresentatives, BufferedImage image, int nbCouleurs ){
        Map<Integer, ArrayList<Integer>> histogramme = new HashMap<>();


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pixelColor = new Color(image.getRGB(x, y));

                // Choix de la couleur la plus proche dans le tableau
                Color closestColor = couleursRepresentatives[0];
                //Initialisation du premier écart à partir de la premiere couleur
                double minDistance = calculDistance(pixelColor, closestColor);

                //On regarde la différence de distance entre la prochaine couleur et la couleur du pixel
                for (int i = 1; i < couleursRepresentatives.length; i++) {
                    //On regarde la différence de distance entre la prochaine couleur et la couleur du pixel
                    double distance = calculDistance(pixelColor, couleursRepresentatives[i]);

                    //On regarde de quelle couleur le pixel est le plus proche
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestColor = couleursRepresentatives[i];
                    }
                }

                ArrayList<Integer> temp = histogramme.getOrDefault(closestColor.getRGB(), new ArrayList<>());
                temp.add(image.getRGB(x, y));


                histogramme.put(closestColor.getRGB(), temp);

            }
        }

        Color[] couleursRepresentatives2 = new Color[nbCouleurs];
        int index = 0;
        for (Integer couleurRepres: histogramme.keySet()) {
            ArrayList<Integer> listeCouleurCorrespondantes = histogramme.get(couleurRepres);

            // Calculer la couleur moyenne de la liste correspondante
            int sumRed = 0;
            int sumGreen = 0;
            int sumBlue = 0;
            int count = 0;

            for (Integer couleur: listeCouleurCorrespondantes) {
                Color couleurCorrespondante = new Color(couleur);
                sumRed += couleurCorrespondante.getRed();
                sumGreen += couleurCorrespondante.getGreen();
                sumBlue += couleurCorrespondante.getBlue();
                count++;
            }

            int moyenneRed = sumRed / count;
            int moyenneGreen = sumGreen / count;
            int moyenneBlue = sumBlue / count;

            // Changer la couleur représentative
            couleursRepresentatives2[index] = new Color(moyenneRed, moyenneGreen, moyenneBlue);
            index++;
        }

        return couleursRepresentatives2;
    }


}