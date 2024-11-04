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
    private final double HATEUR_MINIMALE = Partie.DIMENSIONS[1] * 0.4;
    // Séparé en deux pour dimensions horizontale et verticale, puis en encore en deux pour la direction (+, -)
    private final KeyCode[][] TOUCHES_DE_CONTROLE = {{KeyCode.RIGHT, KeyCode.LEFT}, {KeyCode.DOWN, KeyCode.UP}};

    private int nbVies = 4;
    public Vaisseau() {
        // Largeur 100, Hauteur 140
        super(new double[]{0,0},new double[]{0,0}, 100, 140, new Image("extraterrestre.png"),
                 new double[]{(double) Main.LARGEUR / 2 - (double) 100 / 2, 100}, 600);
    }

    @Override
    public void dessiner(GraphicsContext contexte, Camera camera) {
        double largeurTete = 66;
        double hauteurTete = 81;
        double largeurBase = 176;
        double hauteurBase = 41;

        // Dessin de la tete
        contexte.drawImage(image, camera.getXEcran(pos[0]) + DIMENSIONS[0] / 2 - largeurTete / 2,
                camera.getYEcran(pos[1]) + DIMENSIONS[1] - hauteurBase - hauteurTete);
        // Dessin de l'oval
        contexte.setFill(Color.rgb(255, 255, 0, 0.6));
        contexte.fillOval(camera.getXEcran(pos[0]), camera.getYEcran(pos[1]), DIMENSIONS[0], DIMENSIONS[1]);
        // Dessin de la base
        contexte.drawImage(imageBase, camera.getXEcran(pos[0]) + DIMENSIONS[0] / 2 - largeurBase / 2,
                camera.getYEcran(pos[1]) + DIMENSIONS[1] - hauteurBase);
    }

    @Override
    public void update(double deltatemps) {
        super.update(deltatemps);

        // Gestion de l'accélération
        for (int i = 0; i < TOUCHES_DE_CONTROLE.length; i++) {
            int direction = 0;
            if (Input.isKeyPressed(TOUCHES_DE_CONTROLE[i][0]))
                direction++;
            if (Input.isKeyPressed(TOUCHES_DE_CONTROLE[i][1]))
                direction--; // Si les deux touches sont pressées, directionX = 0
            gererChangementsAcceleration(i, direction);
        }

        // Gestion du bug lors du changment de direction de la vitesse
        int[] directionsPrecendentes = {getDirection(v[0]), getDirection(v[1])};
        updatePosition(deltatemps);
        for (int j = 0; j < directionsPrecendentes.length; j++) {
            if (directionsPrecendentes[j] != getDirection(v[j]))
                v[j] = 0;
        }

        imageBase = (Math.abs(a[0]) == ACCELERATION_BASE || Math.abs(a[1]) == ACCELERATION_BASE) ?
                    new Image("base-vaisseau-on.png") : new Image("base-vaisseau-off.png");
    }

    @Override
    protected void updatePosition(double deltatemps) {
        super.updatePosition(deltatemps);
        pos[1] = Math.min(pos[1], HATEUR_MINIMALE);
    }

    /**
     * Fonction qui gère l'accélération. Accélère ou ralentit le vaisseau selon les paramètres
     * @param indexAcceleration 0 : en x, 1 : en y
     * @param multiplicateur -1 ou 1 dépendant de la direction, et 0 si aucune touche n'est pressée
     */
    private void gererChangementsAcceleration(int indexAcceleration, int multiplicateur) {
        // Si une direction est spécifiée, on accélère dans la direction
        if (multiplicateur != 0) {
            a[indexAcceleration] = multiplicateur * ACCELERATION_BASE;
        } else { // Sinon, on accélère dans la direction opposée pour ralentir
            double vitesse = (indexAcceleration == 0) ? v[0] : v[1];
            int directionVitesse = getDirection(vitesse);
            a[indexAcceleration] = directionVitesse * RALENTISSEMENT;
        }
    }

    private int getDirection(double v) {
        return ((v > 0) ? 1 : -1);
    }


}
