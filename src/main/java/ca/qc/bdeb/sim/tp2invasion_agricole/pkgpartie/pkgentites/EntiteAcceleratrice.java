package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import javafx.scene.image.Image;

public class EntiteAcceleratrice extends Entite {
    // 0 : ax, 1 : ay
    protected double[] a;
    protected final double vitesseMax; // Vitesse maximale que l'entité peut atteindre, 0 si non applicable

    public EntiteAcceleratrice(double[] a, double[] v, double LARGEUR, double HAUTEUR, Image IMAGE, double[] pos, double vitesseMax) {
        super(v, LARGEUR, HAUTEUR, IMAGE, pos);
        this.a = a;
        this.vitesseMax = vitesseMax;
    }

    @Override
    protected void updatePosition(double deltatemps) {
        super.updatePosition(deltatemps);
        v[0] += a[0] * deltatemps;
        v[1] += a[1] * deltatemps;

        regulerVitesse(0);
        regulerVitesse(1);
    }

    private void regulerVitesse(int indexVitesse) {
        if (vitesseMax == 0) return;

        if (v[indexVitesse] > vitesseMax)
            v[indexVitesse] = vitesseMax;
        if (v[indexVitesse] < -vitesseMax)
            v[indexVitesse] = -vitesseMax;
    }

}
