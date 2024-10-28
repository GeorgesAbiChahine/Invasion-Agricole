package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgarriereplan;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Etoile extends ObjetsDecor {
    protected final String ILLUSTRATION = "*";

    public Etoile(double x, double y,double taille) {
        super(x, y, taille, taille);
    }

    //TODO Largeur pour taille est bizarre
    @Override
    public void dessiner(GraphicsContext contexte) {
        contexte.setFill(Color.WHITE);
        contexte.fillText(ILLUSTRATION, x, y, LARGEUR);
    }
}
