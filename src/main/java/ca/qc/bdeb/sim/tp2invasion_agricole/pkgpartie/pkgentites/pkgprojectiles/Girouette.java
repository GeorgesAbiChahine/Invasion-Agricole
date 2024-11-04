package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Entite;
import javafx.scene.image.Image;

public class Girouette extends Projectile {

    public Girouette(double[] pos , double[] posVaisseauCentre) {
        super(new double[]{0, 0}, calculerV(pos, posVaisseauCentre,500), 53, 55,
                new Image("girouette.png"),
                new double[]{pos[0],pos[1]},0);
    }

}
