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
    private boolean peutEnlever = true;
    protected double[] pos = new double[2];

    protected void update(double deltaTemps, Vaisseau vaisseau) {
        pos[0] += vaisseau.getPos()[0] - pos[0];
        pos[1] += vaisseau.pos[1] - pos[1]; // vaisseau.getPos()[1] + (vaisseau.getDimensions()[1] - hauteurBaseVaisseau / 2);

        if (Input.isKeyPressed(KeyCode.SPACE) && charge > 0 && peutEnlever) {
            hauteurRayon += VITESSE_ALLONGEMENT * deltaTemps;
            charge -= VITESSE_DECHARGEMENT * deltaTemps;
        } else {
            hauteurRayon = 0;
            charge += VITESSE_CHARGEMENT * deltaTemps;
        }
        if (charge <= 0) {
            peutEnlever = false;
            charge = 0;
        }
        if (charge >= 1) {
            charge = 1;
            peutEnlever = true;
        }
    }

    protected void draw(GraphicsContext contexte, Camera camera, double largeur) {
        contexte.setFill(Color.rgb(255, 255, 0, 0.5));
        contexte.fillRect(camera.getXEcran(pos[1]), pos[1], largeur, hauteurRayon);
    }
}
