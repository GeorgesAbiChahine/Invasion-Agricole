package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class Entite {
    protected boolean aSupprimer = false; // Est-ce que l'entité est à supprimer du tableau

    protected Image image;
    protected final double[] DIMENSIONS;

    // pos[0] = x, pos[1] = y
    protected double[] pos;

    // v[0] = vx, v[1] = vy
    protected double[] v;


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
        for (int i = 0; i < posCentre.length; i++) posCentre[i] = pos[i] + DIMENSIONS[i] / 2;
        return posCentre;
    }

    public void dessiner(GraphicsContext contexte, Camera camera) {
        contexte.drawImage(image, camera.getXEcran(pos[0]), pos[1], DIMENSIONS[0], DIMENSIONS[1]);
        gererDessinDebogage(contexte, camera);
    }

    public void gererDessinDebogage(GraphicsContext contexte, Camera camera) {
        if (Partie.getModeDebogage()) {
            contexte.setStroke(Color.YELLOW);
            contexte.strokeRect(camera.getXEcran(pos[0]), pos[1], DIMENSIONS[0], DIMENSIONS[1]);
        }
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
        pos[axe] = Math.min(pos[axe], extremite);
        pos[axe] = Math.max(axe, pos[axe]);
    }

    public boolean isASupprimer() {
        return aSupprimer;
    }

    public boolean estEnCollisionAvec(Entite entite) {
        double[] posEntite = entite.getPos();
        double[] dimensionsEntite = entite.getDimensions();
        return ((pos[0] < posEntite[0] + dimensionsEntite[0]) &&
                (pos[0] + DIMENSIONS[0] > posEntite[0]) &&
                (pos[1] < posEntite[1] + dimensionsEntite[1]) &&
                (pos[1] + DIMENSIONS[1] > posEntite[1]));
    }
}
