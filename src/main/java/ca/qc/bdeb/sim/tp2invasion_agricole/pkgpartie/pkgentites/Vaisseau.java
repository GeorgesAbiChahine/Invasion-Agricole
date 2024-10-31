package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.Input;
import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;



public class Vaisseau extends EntiteAcceleratrice {
    private Image imageBase = new Image("base-vaisseau-off.png");
    private final double ACCELERATION_BASE = 2000;
    private final double RALENTISSEMENT = -500;
    public Vaisseau() {
        // Largeur 100, Hauteur 140
        super(0,0, 0, 0, 100, 140, new Image("extraterrestre.png"),
                (double) Main.LARGEUR / 2 - (double) 100 / 2, 100, 600);
    }

    @Override
    public void dessiner(GraphicsContext contexte, Camera camera) {
        double largeurTete = 66;
        double hauteurTete = 81;
        double largeurBase = 176;
        double hauteurBase = 41;

        // Dessin de la tete
        contexte.drawImage(image, camera.getXEntiteEcran(this) + LARGEUR / 2 - largeurTete / 2,
                camera.getYEntiteEcran(this) + HAUTEUR - hauteurBase - hauteurTete);
        // Dessin de l'oval
        contexte.setFill(Color.rgb(255, 255, 0, 0.6));
        contexte.fillOval(camera.getXEntiteEcran(this), camera.getYEntiteEcran(this), LARGEUR, HAUTEUR);
        // Dessin de la base
        contexte.drawImage(imageBase, camera.getXEntiteEcran(this) + LARGEUR / 2 - largeurBase / 2, camera.getYEntiteEcran(this) + HAUTEUR - hauteurBase);

    }

    @Override
    public void update(double deltatemps) {
        super.update(deltatemps);

        int directionX = 0;
        if (Input.isKeyPressed(KeyCode.RIGHT))
            directionX++;
        if (Input.isKeyPressed(KeyCode.LEFT))
            directionX--; // Si les deux touches sont pressées, directionX = 0

        gererChangementsAcceleration(0, directionX);

        int directionY = 0;
        if (Input.isKeyPressed(KeyCode.DOWN))
            directionY++;
        if (Input.isKeyPressed(KeyCode.UP))
            directionY--;

        gererChangementsAcceleration(1, directionY);

        // Mise a jour de la position
        int directionXAvant = getDirection(vx);
        int directionYAvant = getDirection(vy);

        updatePosition(deltatemps);

        if (directionXAvant != getDirection(vx)) {
            vx = 0;
            //a[0] = 0;
        }
        if (directionYAvant != getDirection(vy)) {
            vy = 0;
            //a[1] = 0;
        }



        imageBase = (Math.abs(a[0]) == ACCELERATION_BASE || Math.abs(a[1]) == ACCELERATION_BASE) ?
                    new Image("base-vaisseau-on.png") : new Image("base-vaisseau-off.png");


    }

    @Override
    protected void updatePosition(double deltatemps) {
        super.updatePosition(deltatemps);
        y = Math.min(y, Partie.HAUTEUR * 0.4);
    }

    /**
     * @param indexAcceleration 0 : en x, 1 : en y
     * @param multiplicateur -1 ou 1 dépendant de la direction, et 0 si aucune touche n'est pressée
     */
    private void gererChangementsAcceleration(int indexAcceleration, int multiplicateur) {
        if (multiplicateur != 0) {
            a[indexAcceleration] = multiplicateur * ACCELERATION_BASE;
        } else {
            double vitesse = (indexAcceleration == 0) ? vx : vy;
            int directionVitesse = getDirection(vitesse);
            a[indexAcceleration] = directionVitesse * RALENTISSEMENT;
        }
    }

    private int getDirection(double v) {
        return ((v > 0) ? 1 : -1);
    }


}
