package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Entite;
import javafx.scene.image.Image;

public class Aimant extends Entite {
    public Aimant(double ax, double ay, double vx, double vy, Image IMAGE, double x, double y) {
            super(ax, 0, vx, vy, 42, 40, new Image("aimant.png"), x, y);
    }
}
