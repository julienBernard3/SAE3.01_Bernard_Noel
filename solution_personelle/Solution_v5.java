package solution_personelle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.TreeMap;

/**
 * Solution 4 de réduction de couleurs
 * <p>Méthode de sélection des couleurs représentatives :</p>
 * <ol>
 *     <li> on regroupe et tri les couleurs par proximité dans l'espace RGB</li>
 *     <li> on sélectionne de façon régulière les couleurs</li>
 * </ol>
 */
public class Solution_v5 extends Solution{
    @Override
    public BufferedImage reductionCouleurs(BufferedImage image, int nbCouleurs) {

        // Regroupement des couleurs par fréquence
        // On utilise une TreeMap pour trier les couleurs
        Map<Integer, Integer> histogramme = new TreeMap<>();
        int width = image.getWidth();
        int height = image.getHeight();

        double binSize = 20.00000; // Taille de chaque intervalle de couleur (en binaire) (<256)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color colorPixel = new Color(image.getRGB(x, y));

                // Permet de regrouper les couleurs par intervalle
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

        int tailleEchantillon = image.getWidth() * image.getHeight();
        // Valeur de l'intervalle de sélection de couleurs
        int divisionTaille = tailleEchantillon / nbCouleurs;

        // Tableau qui stocke les divisions / intervalles
        int[] divisions = new int[nbCouleurs];
        int sommeFrequences = 0;
        int divisionCourrante = 0;

        // On récupère les couleurs représentatives par intervalle selon la fréquence
        for (Map.Entry<Integer, Integer> entry : histogramme.entrySet()) {
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
        // On garde les nbCouleurs couleurs représentatives
        for (int i = 0; i < nbCouleurs; i++) {
            couleursRepresentatives[i] = new Color(divisions[i]);
        }

        BufferedImage imageReduite = remplacerCouleurs(image, couleursRepresentatives);
        return imageReduite;
    }
}
