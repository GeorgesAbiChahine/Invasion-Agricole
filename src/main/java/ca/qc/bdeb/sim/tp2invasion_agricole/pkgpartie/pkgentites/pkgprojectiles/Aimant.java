package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import javafx.scene.image.Image;

/**
 * La classe {@code Aimant} représente un aimant qui interagit avec le vaisseau après avoir été lancé par un fermier
 * en exerçant une force électrique basée sur la loi de Coulomb.
 */
public class Aimant extends Projectile {
    private final Vaisseau VAISSEAU;

    /**
     * Constructeur de la classe {@code Aimant}.
     *
     * @param pos        La position initiale de l'aimant (tableau de composantes X et Y).
     * @param vaisseau   Le vaisseau cible qui interagit avec l'aimant.
     * @param vitesseMax La vitesse maximale que l'aimant peut atteindre.
     */
    public Aimant(double[] pos, Vaisseau vaisseau, double vitesseMax) {
        super(new double[]{0, 0}, new double[]{0, 0}, 42, 40,
                new Image("aimant.png"), new double[]{pos[0], pos[1]}, vitesseMax);
        this.VAISSEAU = vaisseau;
    }


    /**
     * Calcule la force électrique exercée sur l'aimant en fonction de la loi de Coulomb.
     * La force est calculée à partir des charges de l'aimant et du vaisseau, ainsi que de la distance qui les sépare.
     * La force résultante est appliquée comme accélération sur l'aimant.
     */
    private void calculerForceElectrique() {
        final double K = 1500;
        final double Q_AIMANT = 100;
        final double Q_VAISSEAU = -900;

        double[] delta = new double[2];
        double[] posCentre = getPosCentre();
        for (int i = 0; i < delta.length; i++) delta[i] = posCentre[i] - VAISSEAU.getPosCentre()[i];

        double distance = Math.sqrt(delta[0] * delta[0] + delta[1] * delta[1]);
        double forceElectrique = (Q_AIMANT * Q_VAISSEAU * K) / (distance * distance);

        // F = ma, mais m = 1. Donc, F = a
        for (int i = 0; i < delta.length; i++) a[i] = forceElectrique * (delta[i] / distance);
    }

    /**
     * Met à jour la position et le comportement de l'aimant.
     * Cette méthode appelle {@code calculerForceElectrique()} pour ajuster l'accélération
     * en fonction de la distance et des charges entre l'aimant et le vaisseau.
     *
     * @param deltatemps Le temps écoulé depuis la dernière mise à jour, en secondes.
     */
    @Override
    public void update(double deltatemps) {
        super.update(deltatemps);
        calculerForceElectrique();
    }
}
