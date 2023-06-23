package solution_personelle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Solution 1 de réduction de couleurs
 * <p>Méthode de sélection des couleurs représentatives :</p>
 * <ol>
 *     <li> on regroupe les couleurs par proximité dans l'espace RGB</li>
 *     <li>puis on sélectionne les regroupements de couleurs les plus fréquents</li>
 * </ol>
 */
public class Solution_v1 extends Solution {

    @Override
    public BufferedImage reductionCouleurs(BufferedImage image, int nbCouleurs) {
        // Histogramme qui contient les couleurs de l'image et leur fréquence
        Map<Integer, Integer> histogramme = rassemblerCouleurs(image);
        // Tri décroissant par fréquence des couleurs regroupées
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(histogramme.entrySet());
        entries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // On sélectionne les nbCouleurs premiers regroupements de couleurs
        Color[] couleursRepresentatives = new Color[nbCouleurs];
        // On garde les nbCouleurs couleurs représentatives
        for (int i = 0; i < Math.min(nbCouleurs, entries.size()); i++) {
            couleursRepresentatives[i] = new Color(entries.get(i).getKey());
        }

        BufferedImage imageReduite = remplacerCouleurs(image, couleursRepresentatives);
        return imageReduite;
    }

    public Map<Integer, Integer> rassemblerCouleurs(BufferedImage image) {
        // Regroupement des couleurs de l'image par fréquence
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
        return histogramme;
    }
}