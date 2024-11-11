package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.Input;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Entite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class RayonEnlevement extends Entite {
    private final double VITESSE_ALLONGEMENT = 150;
    private final double VITESSE_DECHARGEMENT = 0.25;
    private final double VITESSE_CHARGEMENT = 0.2;
    private double charge = 1;
    private boolean peutEnlever = true;

    public RayonEnlevement(Vaisseau vaisseau) {
        super(null, vaisseau.getDimensions()[0], 0, null, new double[]{vaisseau.getPos()[0], getYRayon(vaisseau)});
    }
    protected static double getYRayon(Vaisseau vaisseau) {
        return (vaisseau.getPos()[1] + vaisseau.getDimensions()[1] - vaisseau.getImageBase().getHeight() / 2);
    }

    protected void update(double deltaTemps, Vaisseau vaisseau) {
        pos[0] = vaisseau.getPos()[0];
        pos[1] = getYRayon(vaisseau);

        if (Input.isKeyPressed(KeyCode.SPACE) && charge > 0 && peutEnlever) {
            DIMENSIONS[1] += VITESSE_ALLONGEMENT * deltaTemps;
            charge -= VITESSE_DECHARGEMENT * deltaTemps;
        } else {
            DIMENSIONS[1] = 0;
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

    @Override
    public void dessiner(GraphicsContext contexte, Camera camera) {
        contexte.setFill(Color.rgb(255, 255, 0, 0.5));
        contexte.fillRect(camera.getXEcran(pos[0]), pos[1], DIMENSIONS[0], DIMENSIONS[1]);
        //gererDessinDebogage(contexte, camera);
        if (Partie.getModeDebogage()) {
            contexte.setStroke(Color.YELLOW);
            contexte.strokeRect(camera.getXEcran(pos[0]), pos[1], DIMENSIONS[0], DIMENSIONS[1]);
        }
    }
}
