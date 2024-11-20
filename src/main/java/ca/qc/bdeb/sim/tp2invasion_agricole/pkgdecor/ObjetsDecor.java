package ca.qc.bdeb.sim.tp2invasion_agricole.pkgdecor;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import javafx.scene.canvas.GraphicsContext;

/**
 * La classe abstraite {@code ObjetsDecor} représente un élément décoratif générique dans l'arrière-plan du jeu.
 * Cette classe sert de base pour tous les objets décoratifs qui doivent être dessinés à l'écran,
 * comme les étoiles ou les images spécifiques (tracteurs, granges, etc.).
 */
public abstract class ObjetsDecor {
    protected double x;
    protected double y;
    protected final double LARGEUR;
    protected final double HAUTEUR;

    /**
     * Constructeur de la classe {@code ObjetsDecor}.
     *
     * @param x La position en X de l'objet.
     * @param y La position en Y de l'objet.
     * @param l La largeur de l'objet.
     * @param h La hauteur de l'objet.
     */
    public ObjetsDecor(double x, double y, double l, double h) {
        this.x = x;
        this.y = y;
        this.LARGEUR = l;
        this.HAUTEUR = h;
    }

    /**
     * Méthode abstraite pour dessiner l'objet décoratif sur le canvas.
     *
     * @param contexte Le contexte graphique où l'objet sera dessiné.
     * @param camera   La caméra utilisée pour ajuster la position de l'objet en fonction du déplacement dans le jeu.
     */
    public abstract void dessiner(GraphicsContext contexte, Camera camera);
}
