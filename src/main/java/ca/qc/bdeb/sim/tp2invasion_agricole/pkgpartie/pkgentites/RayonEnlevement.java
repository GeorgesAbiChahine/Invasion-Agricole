package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.Input;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class RayonEnlevement {
    private final double VITESSE_ALLONGEMENT = 150;
    private final double VITESSE_DECHARGEMENT = 0.25;
    private final double VITESSE_CHARGEMENT = 0.2;
    private double charge = 1;
    private double hauteurRayon = 0;

    protected void update(double deltaTemps) {
        if (Input.isKeyPressed(KeyCode.SPACE) && charge > 0) {
            hauteurRayon += VITESSE_ALLONGEMENT * deltaTemps;
            charge -= VITESSE_DECHARGEMENT * deltaTemps;
        } else {
            hauteurRayon = 0;

        }
    }

    protected void draw(GraphicsContext contexte, Camera camera, double[] posVaisseau, double[] dimensionsVaisseau, double hauteurBaseVaisseau) {
        contexte.setFill(Color.rgb(255, 255, 0, 0.5));
        contexte.fillRect(camera.getXEcran(posVaisseau[0]), posVaisseau[1] + (dimensionsVaisseau[1] - hauteurBaseVaisseau / 2), dimensionsVaisseau[0], hauteurRayon);
    }
}
