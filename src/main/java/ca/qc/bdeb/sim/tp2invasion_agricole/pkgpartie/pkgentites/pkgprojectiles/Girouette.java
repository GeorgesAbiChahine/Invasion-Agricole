package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Entite;
import javafx.scene.image.Image;

public class Girouette extends Entite {
    public Girouette(double vx, double vy, double x, double y) {
        super(vx, vy, 53, 55, new Image("girouette.png"), x, y);
    }
}
