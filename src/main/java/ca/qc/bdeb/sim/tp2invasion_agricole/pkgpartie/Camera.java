package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie;

import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;

public class Camera {
    private double xEcran = 0;

    public double getXEcran(Double x){
        return x - xEcran;
    }

    public void update(Vaisseau vaisseau){
        if (getXEcran(vaisseau.getPos()[0]) + vaisseau.getDimensions()[0] > vaisseau.EXTREMITE_DROITE && (xEcran + Main.LARGEUR) < Partie.DIMENSIONS[0]){
            xEcran += (getXEcran(vaisseau.getPos()[0]) + vaisseau.getDimensions()[0]) - vaisseau.EXTREMITE_DROITE;
        }
        if (getXEcran(vaisseau.getPos()[0]) < vaisseau.EXTREMITE_GAUCHE && xEcran > 0){
            xEcran -= vaisseau.EXTREMITE_GAUCHE - (getXEcran(vaisseau.getPos()[0]));
        }

    }
}
