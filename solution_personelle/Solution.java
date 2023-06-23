package solution_personelle;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Classe abstraite représentant une solution au problème de réduction de couleurs
 */
public abstract class Solution {

    /**
     * Réduit le nombre de couleurs d'une image
     * @param image L'image à réduire
     * @param nbCouleurs Le nombre de couleurs que l'on souhaite conserver
     * @return L'image réduite
     */
    public abstract BufferedImage reductionCouleurs(BufferedImage image, int nbCouleurs);

    /**
     * Génère un histogramme des couleurs de l'image qui contient les couleurs de l'image (rgb) et leur fréquence
     * @param image l'image dont on veut générer l'histogramme
     * @return un histogramme des couleurs de l'image
     */
    public abstract Map<Integer, Integer> rassemblerCouleurs(BufferedImage image);

    /**
     * Permet de calculer la distance entre deux couleurs
     * @param origine Couleur d'origine
     * @param destination Couleur ciblée
     * @return La distance entre les deux couleurs
     */
    public static double calculDistance(Color origine, Color destination){
        return Math.pow(origine.getRed() - destination.getRed(), 2)
                + Math.pow(origine.getGreen() - destination.getGreen(), 2)
                + Math.pow(origine.getBlue() - destination.getBlue(), 2);
    }

    /**
     * Transforme les couleurs d'une image par les couleurs les plus proches données
     * @param image L'image à transformer
     * @param couleursRepresentatives Les couleurs à utiliser
     * @return L'image transformée
     */
    public static BufferedImage remplacerCouleurs(BufferedImage image, Color[] couleursRepresentatives){
        int width = image.getWidth();
        int height = image.getHeight();
        // Remplacement des couleurs des pixels par les couleurs représentatives les plus proches
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
