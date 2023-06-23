package tp3;

public class Utilitaire {
    public static int[] extractRGB(int rgb) {
        int[] colors = new int[3];

        // Extraction de la couleur bleue
        colors[0] = rgb & 0xFF;

        // Extraction de la couleur verte
        colors[1] = (rgb >> 8) & 0xFF;

        // Extraction de la couleur rouge
        colors[2] = (rgb >> 16) & 0xFF;

        return colors;
    }

}
