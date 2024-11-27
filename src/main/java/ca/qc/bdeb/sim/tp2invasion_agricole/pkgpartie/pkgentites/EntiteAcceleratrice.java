package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import javafx.scene.image.Image;

/**
 * La classe {@code EntiteAcceleratrice} représente une entité qui peut subir des accélérations
 * et dont la vitesse est limitée par une valeur maximale configurable.
 */
public class EntiteAcceleratrice extends Entite {
    protected double[] a;

    // Vitesse maximale que l'entité peut atteindre, 0 si non applicable.
    protected final double vitesseMax;

    /**
     * Constructeur de la classe {@code EntiteAcceleratrice}.
     *
     * @param a          L'accélération de l'entité (tableau de composantes X et Y).
     *                   Si {@code null}, l'accélération est initialisée à 0 sur les deux axes.
     * @param v          La vitesse initiale de l'entité (tableau de composantes X et Y).
     * @param LARGEUR    La largeur de l'entité.
     * @param HAUTEUR    La hauteur de l'entité.
     * @param IMAGE      L'image représentant l'entité.
     * @param pos        La position initiale de l'entité (tableau de composantes X et Y).
     * @param vitesseMax La vitesse maximale que l'entité peut atteindre.
     */
    public EntiteAcceleratrice(double[] a, double[] v, double LARGEUR, double HAUTEUR, Image IMAGE, double[] pos, double vitesseMax) {
        super(v, LARGEUR, HAUTEUR, IMAGE, pos);
        this.a = (a == null) ? new double[]{0, 0} : a;
        this.vitesseMax = vitesseMax;
    }

    /**
     * Met à jour la position de l'entité en fonction de l'accélération et du temps écoulé.
     * Cette méthode ajuste également la vitesse pour respecter la limite maximale.
     *
     * @param deltatemps Le temps écoulé depuis la dernière mise à jour, en secondes.
     */
    @Override
    protected void updatePosition(double deltatemps) {
        super.updatePosition(deltatemps);
        v[0] += a[0] * deltatemps;
        v[1] += a[1] * deltatemps;

        regulerVitesse(0);
        regulerVitesse(1);
    }

    /**
     * Régule la vitesse de l'entité sur un axe donné pour s'assurer qu'elle ne dépasse pas la vitesse maximale.
     *
     * @param indexVitesse L'index de l'axe à réguler (0 pour X, 1 pour Y).
     */
    protected void regulerVitesse(int indexVitesse) {
        if (vitesseMax == 0) return;

        if (v[indexVitesse] > vitesseMax) v[indexVitesse] = vitesseMax;
        if (v[indexVitesse] < -vitesseMax) v[indexVitesse] = -vitesseMax;
    }

}
