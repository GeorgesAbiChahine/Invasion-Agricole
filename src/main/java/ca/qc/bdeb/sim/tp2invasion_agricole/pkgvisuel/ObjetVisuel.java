package ca.qc.bdeb.sim.tp2invasion_agricole.pkgvisuel;

/**
 * La classe abstraite {@code ObjetVisuel} représente un élément décoratif générique autour du jeu.
 * Cette classe sert de base pour tous les objets décoratifs qui doivent être dessinés à l'écran,
 * comme les étoiles, les images spécifiques (tracteurs, granges, etc.), et les objets de l'interface (minicarte,
 * batterie).
 */
public abstract class ObjetVisuel {
    protected double x;
    protected double y;
    protected final double LARGEUR;

    /**
     * Constructeur de la classe {@code ObjetVisuel}.
     *
     * @param x La position en X de l'objet.
     * @param y La position en Y de l'objet.
     * @param l La largeur de l'objet.
     */
    public ObjetVisuel(double x, double y, double l) {
        this.x = x;
        this.y = y;
        this.LARGEUR = l;
    }
}
