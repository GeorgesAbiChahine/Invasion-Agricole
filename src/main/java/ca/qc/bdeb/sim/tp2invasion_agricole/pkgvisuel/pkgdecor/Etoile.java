package ca.qc.bdeb.sim.tp2invasion_agricole.pkgvisuel.pkgdecor;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * La classe {@code Etoile} représente une étoile dans le décor du jeu.
 * Ces étoiles sont dessinées comme des symboles textuels (*) avec une taille et une position spécifiques.
 */
public class Etoile extends ObjetDecor {
    /**
     * Caractère utilisé pour représenter l'étoile.
     */
    protected final String ILLUSTRATION = "*";

    /**
     * Constructeur de la classe {@code Etoile}.
     *
     * @param x      La position X de l'étoile.
     * @param y      La position Y de l'étoile.
     * @param taille La taille de l'étoile (utilisée pour définir sa largeur et hauteur).
     */
    public Etoile(double x, double y, double taille) {
        super(x, y, taille);
    }

    /**
     * Dessine l'étoile sur le canvas.
     *
     * @param contexte Le contexte graphique où l'étoile sera dessinée.
     * @param camera   La caméra utilisée pour ajuster la position de l'étoile en fonction du défilement.
     */
    @Override
    public void dessiner(GraphicsContext contexte, Camera camera) {
        // Définit la police de l'étoile avec une taille correspondant à sa largeur.
        contexte.setFont(Font.font("Arial", LARGEUR));

        // Définit la couleur de l'étoile.
        contexte.setFill(Color.WHITE);

        // Dessine l'étoile à la position ajustée par la caméra.
        contexte.fillText(ILLUSTRATION, camera.getXEcran(x), y, LARGEUR);
    }
}
