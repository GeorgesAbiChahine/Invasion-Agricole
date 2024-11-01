package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.EntiteAvecGravite;
import javafx.scene.image.Image;

public class PotDeFleur extends EntiteAvecGravite {
    public PotDeFleur(double vx, double vy, double x, double y) {
        super(vx, vy, 31, 61, new Image("pot-de-fleurs.png"), x, y);
    }
}
