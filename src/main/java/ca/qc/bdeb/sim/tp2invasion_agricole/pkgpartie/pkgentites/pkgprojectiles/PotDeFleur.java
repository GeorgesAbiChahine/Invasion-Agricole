package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import javafx.scene.image.Image;

/**
 * La classe {@code PotDeFleur} représente un projectile qui se déplace en direction
 * du vaisseau cible, avec une vitesse initiale calculée pour atteindre une position
 * légèrement décalée au-dessus du centre du vaisseau avec une magnitude totale de 1000.
 */
public class PotDeFleur extends Projectile {

    /**
     * Constructeur de la classe {@code PotDeFleur}.
     * Initialise un pot de fleurs avec une position initiale, un vaisseau cible, et une vitesse
     * calculée en fonction de la distance au vaisseau.
     *
     * @param pos      La position initiale du pot de fleurs (tableau de composantes X et Y).
     * @param vaisseau Le vaisseau cible qui influence la trajectoire du pot de fleurs.
     */
    public PotDeFleur(double[] pos, Vaisseau vaisseau) {
        super(new double[]{0, 1000}, calculerV(pos, new double[]{vaisseau.getPosCentre()[0],
                        vaisseau.getPosCentre()[1] - 400}, 1000), 31, 61,
                new Image("pot-de-fleurs.png"), new double[]{pos[0], pos[1]}, 0);
    }


}
