package ca.qc.bdeb.sim.tp2invasion_agricole.pkgvisuel.pkgdecor;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * La classe {@code ImagesDecor} représente un objet décoratif basé sur une image
 * qui est affichée dans l'arrière-plan du jeu.
 * Elle hérite de la classe {@code ObjetsDecor} et utilise les propriétés de position
 * et de dimension pour dessiner une image.
 */
public class ImagesDecor extends ObjetDecor {
    private final double HAUTEUR;
    protected Image image;

    /**
     * Constructeur de la classe {@code ImagesDecor}.
     * @param x     La position en X de l'image.
     * @param y     La position en Y de l'image.
     * @param l     La largeur de l'image.
     * @param h     La hauteur de l'image.
     * @param image L'image à afficher pour cet objet décoratif.
     */
    public ImagesDecor(double x, double y, double l, double h, Image image) {
        super(x, y, l);
        this.HAUTEUR = h;
        this.image = image;
    }

    /**
     * Dessine l'image associée à cet objet décoratif sur le canvas.
     * La position de l'image est ajustée en fonction de la caméra.
     *
     * @param contexte Le contexte graphique où l'image sera dessinée.
     * @param camera   La caméra utilisée pour ajuster la position de l'objet en fonction du déplacement dans le jeu.
     */
    @Override
    public void dessiner(GraphicsContext contexte, Camera camera) {
        contexte.drawImage(image, camera.getXEcran(x), y, LARGEUR, HAUTEUR);
    }
}
