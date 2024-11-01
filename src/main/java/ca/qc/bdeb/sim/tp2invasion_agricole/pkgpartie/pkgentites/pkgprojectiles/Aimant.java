package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.EntiteAcceleratrice;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Vaisseau;
import javafx.scene.image.Image;

public class Aimant extends EntiteAcceleratrice {
    private double forceEnX;
    private double forceEnY;
    public Aimant(double x, double y, double vitesseMax) {
        super(0, 0, 0, 0, 42, 40,
                new Image("aimant.png"), x, y, 300);
    }

    private void calculerForceElectrique(Vaisseau vaisseau){
        final double K = 1500;
        double qAimant = 100;
        double qVaisseau = -900;
        double dx = x - vaisseau.getX();
        double dy = y - vaisseau.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double proportionX = dx / distance;
        double proportionY = dy / distance;
        double forceElectrique = (qAimant * qVaisseau * K) / distance;

        forceEnX = forceElectrique * proportionX;
        forceEnY = forceElectrique * proportionY;
    }
}
