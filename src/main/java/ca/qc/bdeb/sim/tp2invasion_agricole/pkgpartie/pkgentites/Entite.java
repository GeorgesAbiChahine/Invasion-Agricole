package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Entite {

    protected Image image;
    protected final double LARGEUR;
    protected final double HAUTEUR;
    protected double x;
    protected double y;

    // v[0] = vx, v[1] = vy
    protected double[] v = new double[2];


    protected double[] position = new double[2];


    public Entite(double vx, double vy,
                  double LARGEUR, double HAUTEUR, Image IMAGE, double x, double y) {
        this.HAUTEUR = HAUTEUR;
        this.LARGEUR = LARGEUR;
        this.image = IMAGE;
        this.v[1] = vy;
        this.v[0] = vx;
        this.y = y;
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public double getLARGEUR() {
        return LARGEUR;
    }

    public double getHAUTEUR() {
        return HAUTEUR;
    }

    public void dessiner(GraphicsContext contexte, Camera camera) {
        contexte.drawImage(image, camera.getXEntiteEcran(this), camera.getYEntiteEcran(this), LARGEUR, HAUTEUR);
    }

    public void update(double deltatemps) {
        updatePosition(deltatemps);
    }

    protected void updatePosition(double deltatemps) {
        x += deltatemps * v[0];
        y += deltatemps * v[1];

        x = Math.min(x, Partie.LARGEUR + LARGEUR);
        x = Math.max(0, x);
        y = Math.min(y,Partie.HAUTEUR - HAUTEUR);
        y = Math.max(0,y);
    }

    //TODO CREER CLASSE PROJECTILES.
    protected static double calculerV(double x, double y, double xVaisseau, double yVaisseau,
                                      int magnitude,int typeVitesse) {
        double dx = xVaisseau - x;
        double dy = yVaisseau - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (typeVitesse == 0) return magnitude * (dx / distance);
        else return magnitude * (dy / distance);
    }
}
