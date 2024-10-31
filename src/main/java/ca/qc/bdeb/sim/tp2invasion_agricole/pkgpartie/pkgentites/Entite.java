package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Entite {
    private final double GRAVITE = 1000;
    protected Image image;
    protected final double LARGEUR;
    protected final double HAUTEUR;
    protected double x;
    protected double y;
    protected double vx;
    protected double vy;
    protected double ax;
    protected double ay;


    public Entite(double ax, double ay, double vx, double vy,
                  double LARGEUR, double HAUTEUR, Image IMAGE, double x, double y) {
        this.HAUTEUR = HAUTEUR;
        this.LARGEUR = LARGEUR;
        this.image = IMAGE;
        this.ay = ay;
        this.ax = ax;
        this.vy = vy;
        this.vx = vx;
        this.y = y;
        this.x = x;
    }

    public void dessiner(GraphicsContext contexte) {
        contexte.drawImage(image, x, y, LARGEUR, HAUTEUR);
    }

    public void update(double deltatemps) {
        updatePosition(deltatemps);
    }

    protected void updatePosition(double deltatemps) {
        vx += deltatemps * ax;
        vy += deltatemps * ay;

        x += deltatemps * vx;
        y += deltatemps * vy;

        x = Math.min(x, Partie.LARGEUR + LARGEUR);
        x = Math.max(0, x);
        y = Math.min(y,Partie.HAUTEUR - HAUTEUR);
        y = Math.max(0,y);
    }


}
