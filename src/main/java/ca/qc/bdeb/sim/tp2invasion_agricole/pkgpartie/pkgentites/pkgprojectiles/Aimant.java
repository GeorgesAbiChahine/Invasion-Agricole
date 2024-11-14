package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles;

import javafx.scene.image.Image;

public class Aimant extends Projectile {
    private double[] posVaisseau;

    public Aimant(double[] pos, double[] posVaisseau, double vitesseMax) {
        super(new double[]{0, 0}, new double[]{0, 0}, 42, 40,
                new Image("aimant.png"), new double[]{pos[0], pos[1]}, vitesseMax);
        this.posVaisseau = posVaisseau;
    }

    // TODO FIX POSCENTRALE
    private void calculerForceElectrique() {
        final double K = 1500;
        final double Q_AIMANT = 100;
        final double Q_VAISSEAU = -900;

        double[] delta = new double[2];
        for (int i = 0; i < delta.length; i++) delta[i] = pos[i] - posVaisseau[i];

        double distance = Math.sqrt(delta[0] * delta[0] + delta[1] * delta[1]);
        //TODO VERIFIER DISTANCE * DISTANCE
        double forceElectrique = (Q_AIMANT * Q_VAISSEAU * K) / (distance * distance);

        // F = ma, mais m = 1. Donc, F = a
        for (int i = 0; i < delta.length; i++) a[i] = forceElectrique * (delta[i] / distance);
    }

    @Override
    public void update(double deltatemps) {
        super.update(deltatemps);
        calculerForceElectrique();
    }
}
