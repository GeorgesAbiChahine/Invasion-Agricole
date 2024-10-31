package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import javafx.scene.image.Image;

public abstract class EntiteAvecGravite extends Entite{
    private final double GRAVITE = 1000;
    public EntiteAvecGravite( double vx, double vy, double LARGEUR, double HAUTEUR, Image IMAGE, double x, double y) {
        super(vx, vy, LARGEUR, HAUTEUR, IMAGE, x, y);
    }

    @Override
    protected void updatePosition(double deltatemps) {
        super.updatePosition(deltatemps);

        if (!toucheLeSol())
            vy += GRAVITE * deltatemps;
    }

    private boolean toucheLeSol() {
        return y + HAUTEUR >= Main.HAUTEUR;
    }
}
