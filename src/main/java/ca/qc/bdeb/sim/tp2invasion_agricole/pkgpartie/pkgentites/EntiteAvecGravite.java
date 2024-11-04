package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import javafx.scene.image.Image;

public abstract class EntiteAvecGravite extends Entite{
    private final double GRAVITE = 1000;
    public EntiteAvecGravite(double[] v, double LARGEUR, double HAUTEUR, Image IMAGE, double[] pos) {
        super(v, LARGEUR, HAUTEUR, IMAGE, pos);
    }

    @Override
    protected void updatePosition(double deltatemps) {
        super.updatePosition(deltatemps);

        if (!toucheLeSol())
            v[1] += GRAVITE * deltatemps;
    }

    private boolean toucheLeSol() {
        return pos[1] + DIMENSIONS[1] >= Main.HAUTEUR;
    }
}
