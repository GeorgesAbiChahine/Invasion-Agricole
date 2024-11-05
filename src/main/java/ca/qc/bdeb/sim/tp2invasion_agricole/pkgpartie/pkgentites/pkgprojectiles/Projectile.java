package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.EntiteAcceleratrice;
import javafx.scene.image.Image;

public abstract class Projectile extends EntiteAcceleratrice {
    public Projectile(double[] a, double[] v, double LARGEUR, double HAUTEUR, Image IMAGE, double[] pos, double vitesseMax) {
        super(a, v, LARGEUR, HAUTEUR, IMAGE, pos, vitesseMax);
    }

    protected static double[] calculerV(double[] pos, double[] posVaisseau, int magnitude) {
        double[] delta = new double[2];
        for (int i = 0; i < pos.length; i++) delta[i] = posVaisseau[i] - pos[i];

        double distance = Math.sqrt(delta[0] * delta[0] + delta[1]* delta[1]);
        return new double[]{magnitude * (delta[0] / distance), magnitude * (delta[1] / distance)};
    }

    @Override
    protected void gererLimites(int axe, double extremite) {
        if (pos[axe] < (-DIMENSIONS[axe]) || pos[axe] > extremite) aSupprimer = true;
    }
}
