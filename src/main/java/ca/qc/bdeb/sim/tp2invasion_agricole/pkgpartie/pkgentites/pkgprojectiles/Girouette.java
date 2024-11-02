package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Entite;
import javafx.scene.image.Image;

public class Girouette extends Entite {

    public Girouette(double x, double y, double xVaisseau, double yVaisseau) {
        super(calculerV(x, y, xVaisseau, yVaisseau,500,0),
                calculerV(x, y, xVaisseau, yVaisseau,500,1),
                53, 55,
                new Image("girouette.png"),
                x, y);
    }



}
