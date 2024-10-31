package ca.qc.bdeb.sim.tp2invasion_agricole;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Entite;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Vaisseau;

public class Camera {
    private double xEcran = 0;
    private double yEcran = 0;

    public double getXEntiteEcran(Entite entite){
        return entite.getX() - xEcran;
    }

    public double getYEntiteEcran(Entite entite) {
        return entite.getY() - yEcran;
    }

    public void bougerCamera(Vaisseau vaisseau){
        if (getXEntiteEcran(vaisseau) > (Main.LARGEUR * 0.7) && (xEcran + Main.LARGEUR) < Partie.LARGEUR){
            xEcran += (getXEntiteEcran(vaisseau) + vaisseau.getLARGEUR()) - (Main.LARGEUR * 0.7);
        }
        if (getXEntiteEcran(vaisseau) < (Main.LARGEUR * 0.3) && xEcran > 0){
            xEcran -= (getXEntiteEcran(vaisseau) + vaisseau.getLARGEUR()) - (Main.LARGEUR * 0.3);
        }

    }
}
