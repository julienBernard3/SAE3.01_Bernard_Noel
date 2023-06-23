package solution_personelle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Solution 2 de réduction de couleurs
 * <p>Méthode de sélection des couleurs représentatives :</p>
 * <ol>
 *     <li> on tri les couleurs dans l'espace RGB</li>
 *     <li> on sélectionne de façon régulière les couleurs dans la liste triée en prenant en compte la fréquence</li>
 * </ol>
 */
public class Solution_v2 extends Solution{
    @Override
    public BufferedImage reductionCouleurs(BufferedImage image, int nbCouleurs) {
        // Histogramme qui contient les couleurs de l'image et leur fréquence
        Map<Integer, Integer> histogramme = rassemblerCouleurs(image);
        // Tri des couleurs par RGB
        TreeMap<Integer, Integer> sortedMap = new TreeMap<>(histogramme);

        int tailleEchantillon = image.getWidth() * image.getHeight();
        // Valeur de l'intervalle de sélection de couleurs
        int divisionTaille = tailleEchantillon / nbCouleurs;

        // Tableau qui stocke les divisions / intervalles
        int[] divisions = new int[nbCouleurs];
        int sommeFrequences = 0;
        int divisionCourrante = 0;

        // On récupère les couleurs représentatives par intervalle selon la fréquence
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
