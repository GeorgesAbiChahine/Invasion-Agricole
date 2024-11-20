package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles;

import javafx.scene.image.Image;

/**
 * La classe {@code Girouette} représente un projectile qui se déplace en direction
 * du centre du vaisseau cible. Ce projectile est initialisé avec une vitesse calculée
 * automatiquement en fonction de la position initiale et de la cible avec une magnitude totale de 500.
 */
public class Girouette extends Projectile {

    /**
     * Constructeur de la classe {@code Girouette}.
     * Initialise une girouette avec sa position de départ, la position centrale du vaisseau cible,
     * et une vitesse calculée pour atteindre sa cible.
     *
     * @param pos               La position initiale de la girouette (tableau de composantes X et Y).
     * @param posVaisseauCentre La position centrale du vaisseau cible (tableau de composantes X et Y).
     */
    public Girouette(double[] pos, double[] posVaisseauCentre) {
        super(null, calculerV(pos, posVaisseauCentre, 500), 53, 55,
                new Image("girouette.png"),
                new double[]{pos[0], pos[1]}, 0);
    }

}
