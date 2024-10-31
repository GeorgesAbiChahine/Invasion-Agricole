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
    protected double vx;
    protected double vy;


    public Entite(double vx, double vy,
                  double LARGEUR, double HAUTEUR, Image IMAGE, double x, double y) {
        this.HAUTEUR = HAUTEUR;
        this.LARGEUR = LARGEUR;
        this.image = IMAGE;
        this.vy = vy;
        this.vx = vx;
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
        x += deltatemps * vx;
        y += deltatemps * vy;

        x = Math.min(x, Partie.LARGEUR + LARGEUR);
        x = Math.max(0, x);
        y = Math.min(y,Partie.HAUTEUR - HAUTEUR);
        y = Math.max(0,y);
    }


}
