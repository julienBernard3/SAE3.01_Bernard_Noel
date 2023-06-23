import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.Color;


public class Main_TP3_Q3 {
    public static void main(String[] args) throws IOException {
        int nbCouleurs = 31;
        String img = "./images_etudiants/animaux/ours";
        String extension = "png";

        BufferedImage image = ImageIO.read(new File(img+"."+extension));


        BufferedImage nImageV1 = reductionCouleurs(image, nbCouleurs);
        ImageIO.write(nImageV1, extension.toUpperCase(), new File(img+"ReduiteV1_"+nbCouleurs+"."+extension));

        BufferedImage nImagev2 =  reductionCouleurs2(image, nbCouleurs);

        ImageIO.write(nImagev2, extension.toUpperCase(), new File(img+"ReduiteV2_"+nbCouleurs+"."+extension));

        BufferedImage nImagev3 =  reductionCouleurs3(image, nbCouleurs);

        ImageIO.write(nImagev3, extension.toUpperCase(), new File(img+"ReduiteV3_"+nbCouleurs+"."+extension));

        BufferedImage nImagev4 =  reductionCouleurs4(image, nbCouleurs);

        ImageIO.write(nImagev4, extension.toUpperCase(), new File(img+"ReduiteV4_"+nbCouleurs+"."+extension));

        //Chargement image

        //Calculs sur image

        //Sauvegarde image


    }


    //Méthode calcul sur image

    //Calcul histogramme
    //Selection des couleurs représentatives
    //Remplacement des autres couleurs par la couleur représentative la + proche




    public static BufferedImage reductionCouleurs(BufferedImage image, int nbCouleurs){
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


        System.out.println(histogramme.size());
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(histogramme.entrySet());

        entries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        Color[] couleursRepresentatives = new Color[nbCouleurs];
        for (int i = 0; i < Math.min(nbCouleurs, entries.size()); i++) {
            couleursRepresentatives[i] = new Color(entries.get(i).getKey());

        }



        // Remplacement des couleurs des pixels
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

        return imageReduite;
    }

    public static double calculDistance(Color origine, Color destination){
        return Math.pow(origine.getRed() - destination.getRed(), 2)
                + Math.pow(origine.getGreen() - destination.getGreen(), 2)
                + Math.pow(origine.getBlue() - destination.getBlue(), 2);
    }




    public static BufferedImage reductionCouleurs2(BufferedImage image, int nbCouleurs){
        Map<Integer, Integer> histogramme = new HashMap<>();
        int width = image.getWidth();
        int height = image.getHeight();


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int colorPixel = image.getRGB(x, y);

                histogramme.put(colorPixel, histogramme.getOrDefault(colorPixel, 0) + 1);
            }
        }


        // Créez une TreeMap en utilisant un comparateur pour trier par clé
        TreeMap<Integer, Integer> sortedMap = new TreeMap<>(histogramme);


        // Calculez la taille totale de l'échantillon (la somme des fréquences)
        int tailleEchantillon = 0;
        for (int frequency : sortedMap.values()) {
            tailleEchantillon += frequency;
        }

        // Calculez la taille des divisions
        int divisionTaille = tailleEchantillon / nbCouleurs;

        // Créez un tableau pour stocker les divisions
        int[] divisions = new int[nbCouleurs];
        int sommeFrequences = 0;
        int divisionCourrante = 0;

        // Parcourez les entrées de la map triée pour trouver les valeurs correspondantes aux divisions
        for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
            int value = entry.getKey();
            int frequency = entry.getValue();
            sommeFrequences += frequency;

            if (sommeFrequences >= divisionTaille * (divisionCourrante + 1)) {
                divisions[divisionCourrante] = value;
                divisionCourrante++;
            }

            if (divisionCourrante >= nbCouleurs) {
                break; // On peut sortir de la boucle après avoir calculé les divisions
            }
        }

        Color[] couleursRepresentatives = new Color[nbCouleurs];
        // Affichez les valeurs des divisions
        for (int i = 0; i < nbCouleurs; i++) {
            couleursRepresentatives[i] = new Color(divisions[i]);
        }

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

        return imageReduite;
    }





    public static BufferedImage reductionCouleurs3(BufferedImage image, int nbCouleurs){
        Map<Integer, Integer> histogramme = new HashMap<>();
        int width = image.getWidth();
        int height = image.getHeight();


        int binSize = 36; // Taille de chaque intervalle de couleur (en binaire) (<256)
        System.out.println(binSize);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int colorPixel = image.getRGB(x, y);

                histogramme.put(colorPixel, histogramme.getOrDefault(colorPixel, 0) + 1);
            }
        }


        // Créez une TreeMap en utilisant un comparateur pour trier par clé
        TreeMap<Integer, Integer> sortedMap = new TreeMap<>(histogramme);


        // Calculez la taille totale de l'échantillon (la somme des fréquences)
        int tailleEchantillon = sortedMap.size();

        // Calculez la taille des divisions
        int ecartDivisions = tailleEchantillon / nbCouleurs;

        // Créez un tableau pour stocker les divisions
        int[] divisions = new int[nbCouleurs];
        int sommeDivisionCourrante = 0;
        int indexDivisionCourante = 0;

        // Parcourez les entrées de la map triée pour trouver les valeurs correspondantes aux divisions
        for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
            int value = entry.getKey();

            sommeDivisionCourrante ++ ;

            if (sommeDivisionCourrante == ecartDivisions ) {
                divisions[indexDivisionCourante] = value;
                indexDivisionCourante++;
                sommeDivisionCourrante = 0;
            }

            if (indexDivisionCourante >= nbCouleurs) {
                break; // On peut sortir de la boucle après avoir calculé les divisions
            }
        }

        Color[] couleursRepresentatives = new Color[nbCouleurs];
        // Affichez les valeurs des divisions
        for (int i = 0; i < nbCouleurs; i++) {
            couleursRepresentatives[i] = new Color(divisions[i]);
        }

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

        return imageReduite;
    }


    public static BufferedImage reductionCouleurs4(BufferedImage image, int nbCouleurs){
        Map<Integer, Integer> histogramme = new HashMap<>();
        int width = image.getWidth();
        int height = image.getHeight();


        int binSize = 2; // Taille de chaque intervalle de couleur (en binaire) (<256)

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color colorPixel = new Color(image.getRGB(x, y));

                // Quantification de la couleur
                // Manipulation d'entier
                int red = (colorPixel.getRed() / binSize) * binSize;
                int green = (colorPixel.getGreen() / binSize) * binSize;
                int blue = (colorPixel.getBlue() / binSize) * binSize;

                // Stockage de la couleur sous forme d'entier
                int quantizedColor = (red << 16) | (green << 8) | blue;

                histogramme.put(quantizedColor, histogramme.getOrDefault(quantizedColor, 0) + 1);
            }
        }


        System.out.println(histogramme.size());
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(histogramme.entrySet());

        entries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));


        TreeMap<Integer, Integer> sortedMap = new TreeMap<>(Comparator.reverseOrder());
        for (Map.Entry<Integer, Integer> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
        }

        // Calculez la taille totale de l'échantillon (la somme des fréquences)
        int tailleEchantillon = sortedMap.size();

        // Calculez la taille des divisions
        int ecartDivisions = tailleEchantillon / nbCouleurs;

        // Créez un tableau pour stocker les divisions
        int[] divisions = new int[nbCouleurs];
        int sommeDivisionCourrante = 0;
        int indexDivisionCourante = 0;

        // Parcourez les entrées de la map triée pour trouver les valeurs correspondantes aux divisions
        for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
            int value = entry.getKey();

            sommeDivisionCourrante ++ ;

            if (sommeDivisionCourrante == ecartDivisions ) {
                divisions[indexDivisionCourante] = value;
                indexDivisionCourante++;
                sommeDivisionCourrante = 0;
            }

            if (indexDivisionCourante >= nbCouleurs) {
                break; // On peut sortir de la boucle après avoir calculé les divisions
            }
        }

        Color[] couleursRepresentatives = new Color[nbCouleurs];
        // Affichez les valeurs des divisions
        for (int i = 0; i < nbCouleurs; i++) {
            couleursRepresentatives[i] = new Color(divisions[i]);
        }

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

        return imageReduite;
    }
}