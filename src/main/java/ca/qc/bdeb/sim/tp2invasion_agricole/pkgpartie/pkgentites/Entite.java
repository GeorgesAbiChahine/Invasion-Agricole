package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Entite {
    protected boolean aSupprimer = false; // Est-ce que l'entité est à supprimer du tableau

    protected Image image;
    protected final double[] DIMENSIONS;

    // pos[0] = x, pos[1] = y
    protected  double[] pos = new double[2];

    // v[0] = vx, v[1] = vy
    protected double[] v = new double[2];



    public Entite(double[] v,
                  double LARGEUR, double HAUTEUR, Image IMAGE, double[] pos) {
        this.DIMENSIONS = new double[]{LARGEUR, HAUTEUR};
        this.image = IMAGE;
        this.v = v;
        this.pos = pos;
    }

    public double[] getPos() {
        return pos;
    }

    public double[] getDimensions() {
        return DIMENSIONS;
    }

    public double[] getPosCentre() {
        double[] posCentre = new double[2];
        for (int i = 0; i < posCentre.length; i++) posCentre[i] = pos[i] + DIMENSIONS[i];
        return posCentre;
    }

    public void dessiner(GraphicsContext contexte, Camera camera) {
        contexte.drawImage(image, camera.getXEcran(pos[0]), camera.getYEcran(pos[1]), DIMENSIONS[0], DIMENSIONS[1]);
    }

    public void update(double deltatemps) {
        updatePosition(deltatemps);
    }

    protected void updatePosition(double deltatemps) {
        for (int i = 0; i < pos.length; i++) {
            pos[i] += deltatemps * v[i];
            double extremite = Partie.DIMENSIONS[i] - DIMENSIONS[i];
            gererLimites(i, extremite);
        }
    }

    protected void gererLimites(int axe, double extremite) {
        pos[axe]  = Math.min(pos[axe], extremite);
        pos[axe]  = Math.max(axe, pos[axe]);
    }

    public boolean isASupprimer() {
        return aSupprimer;
    }


}
