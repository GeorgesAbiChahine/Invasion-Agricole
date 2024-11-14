package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgentitesabsorbable;

import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Entite;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import javafx.scene.image.Image;

public abstract class EntiteAbsorbable extends Entite {
    protected boolean estEnEnlevement = false;


    public EntiteAbsorbable(double[] v, double LARGEUR, double HAUTEUR, Image IMAGE, double[] pos) {
        super(v, LARGEUR, HAUTEUR, IMAGE, pos);
    }

    @Override
    protected void updatePosition(double deltatemps) {
        super.updatePosition(deltatemps);

        final double GRAVITE = 1000;
        if (estEnEnlevement) v[1] = -100;
        else if (!toucheLeSol()) v[1] += GRAVITE * deltatemps;
    }

    private boolean toucheLeSol() {
        return pos[1] + DIMENSIONS[1] >= Main.HAUTEUR;
    }

    public void gererEnlevement(Vaisseau vaisseau) {
        estEnEnlevement = !vaisseau.isEstMort() && estEnCollisionAvec(vaisseau.getRayonEnlevement());

        if (!vaisseau.isEstMort() && estEnCollisionAvec(vaisseau)) {
            aSupprimer = true;
            gererAbsorptionParVaisseau(vaisseau);
        }
    }

    protected abstract void gererAbsorptionParVaisseau(Vaisseau vaisseau);
}
