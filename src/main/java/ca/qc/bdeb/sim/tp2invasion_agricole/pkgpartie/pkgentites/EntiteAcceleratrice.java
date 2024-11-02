package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import javafx.scene.image.Image;

public class EntiteAcceleratrice extends Entite {
    // 0 : ax, 1 : ay
    protected double[] a = new double[2];
    protected final double vitesseMax;

    public EntiteAcceleratrice(double ax, double ay, double vx, double vy, double LARGEUR, double HAUTEUR, Image IMAGE, double x, double y, double vitesseMax) {
        super(vx, vy, LARGEUR, HAUTEUR, IMAGE, x, y);
        this.a[0] = ax;
        this.a[1] = ay;
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
    private void regulerVitesse(int indexVitesse){
        if (v[indexVitesse] > vitesseMax)
            v[indexVitesse] = vitesseMax;
        if (v[indexVitesse] < -vitesseMax)
            v[indexVitesse] = -vitesseMax;
    }

}
