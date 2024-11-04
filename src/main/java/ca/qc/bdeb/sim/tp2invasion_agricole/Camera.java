package ca.qc.bdeb.sim.tp2invasion_agricole;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Vaisseau;

public class Camera {
    private double xEcran = 0;
    private double yEcran = 0; //TODO ENLEVER VARIABLE INUTILE

    public double getXEcran(Double x){
        return x - xEcran;
    }

    //TODO ENLEVER FONCTION INUTILE
    public double getYEcran(Double y) {
        return y - yEcran;
    }

    public void bougerCamera(Vaisseau vaisseau){
        double EXTREMITE_DROITE = (Main.LARGEUR * 0.7);
        if (getXEcran(vaisseau.getPos()[0]) + vaisseau.getDimensions()[0] > EXTREMITE_DROITE && (xEcran + Main.LARGEUR) < Partie.DIMENSIONS[0]){
            xEcran += (getXEcran(vaisseau.getPos()[0]) + vaisseau.getDimensions()[0]) - EXTREMITE_DROITE;
        }
        double EXTREMITE_GAUCHE = (Main.LARGEUR * 0.3);
        if (getXEcran(vaisseau.getPos()[0]) < EXTREMITE_GAUCHE && xEcran > 0){
            xEcran -= EXTREMITE_GAUCHE - (getXEcran(vaisseau.getPos()[0]));
        }

    }
}
