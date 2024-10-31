package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.EntiteAcceleratrice;
import javafx.scene.image.Image;

public class Aimant extends EntiteAcceleratrice {
    public Aimant(double ax, double ay, double vx, double vy, double LARGEUR, double HAUTEUR, Image IMAGE, double x, double y, double vitesseMax) {
        super(ax, ay, vx, vy, LARGEUR, HAUTEUR, IMAGE, x, y, vitesseMax);
    }
}
