package solution_personelle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Solution 3 de réduction de couleurs
 * <p>Méthode de sélection des couleurs représentatives :</p>
 * <ol>
 *     <li> on tri les couleurs dans l'espace RGB</li>
 *     <li> on sélectionne de façon régulière les couleurs</li>
 * </ol>
 */
public class Solution_v3 extends Solution{
    @Override
    public BufferedImage reductionCouleurs(BufferedImage image, int nbCouleurs) {
        // Histogramme qui contient les couleurs de l'image et leur fréquence
        Map<Integer, Integer> histogramme = rassemblerCouleurs(image);

        // Tri des couleurs par RGB
        TreeMap<Integer, Integer> sortedMap = new TreeMap<>(histogramme);

        // On récupère la taille totale de l'échantillon (la somme des fréquences)
        int tailleEchantillon = sortedMap.size();
        // Taille des divisions / intervalles
        int ecartDivisions = tailleEchantillon / nbCouleurs;

        // Tableau qui stocke les divisions
        int[] divisions = new int[nbCouleurs];
        int sommeDivisionCourrante = 0;
        int indexDivisionCourante = 0;

        // On récupère les couleurs représentatives par intervalle
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
        // On garde les nbCouleurs couleurs représentatives
        for (int i = 0; i < nbCouleurs; i++) {
            couleursRepresentatives[i] = new Color(divisions[i]);
        }

        BufferedImage imageReduite = remplacerCouleurs(image, couleursRepresentatives);
        return imageReduite;
    }

    @Override
    public Map<Integer, Integer> rassemblerCouleurs(BufferedImage image) {
        Map<Integer, Integer> histogramme = new HashMap<>();
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int colorPixel = image.getRGB(x, y);
                histogramme.put(colorPixel, histogramme.getOrDefault(colorPixel, 0) + 1);
            }
        }
        return histogramme;
    }
}
