package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgdecor;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Etoile extends ObjetsDecor {
    protected final String ILLUSTRATION = "*";

    public Etoile(double x, double y,double taille) {
        super(x, y, taille, taille);
    }

    //TODO Largeur pour taille est bizarre
    @Override
    public void dessiner(GraphicsContext contexte, Camera camera) {
        //TODO VERIFIER SETFONT
        contexte.setFont(Font.font("Arial", LARGEUR));
        contexte.setFill(Color.WHITE);
        contexte.fillText(ILLUSTRATION, camera.getXEcran(x), y, LARGEUR);
    }
}
