package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgarriereplan;

import ca.qc.bdeb.sim.tp2invasion_agricole.Camera;
import javafx.scene.canvas.GraphicsContext;

public abstract class ObjetsDecor {
    protected double x;
    protected double y;
    protected final double LARGEUR;
    protected final double HAUTEUR;


    public ObjetsDecor(double x, double y, double l, double h) {
        this.x = x;
        this.y = y;
        this.LARGEUR = l;
        this.HAUTEUR = h;
    }

    public abstract void dessiner(GraphicsContext contexte, Camera camera);
}
