package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires.Input;
import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.EntiteAcceleratrice;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;


public class Vaisseau extends EntiteAcceleratrice {
    private Image imageBase = new Image("base-vaisseau-off.png");
    private final double ACCELERATION_BASE = 2000;
    private final double RALENTISSEMENT = -500;
    private final double HAUTEUR_MINIMALE = Partie.DIMENSIONS[1] * 0.6;
    public final double EXTREMITE_DROITE = (Main.LARGEUR * 0.7);
    public final double EXTREMITE_GAUCHE = (Main.LARGEUR * 0.3);
    // Séparé en deux pour dimensions horizontale et verticale, puis en encore en deux pour la direction (+, -)
    private final KeyCode[][] TOUCHES_DE_CONTROLE = {{KeyCode.RIGHT, KeyCode.LEFT}, {KeyCode.DOWN, KeyCode.UP}};

    private final RayonEnlevement rayonEnlevement = new RayonEnlevement(this);
    private final int NB_VIES_MAX = 4;
    protected boolean estInvincible = false;
    private int nombreVies = 4;
    private int nombrePoints = 0;
    private int nombrePersonnesAbsorbees = 0;
    private boolean estMort = false;
    private boolean estMortEtSortiDeLEcran = false;

    public Vaisseau() {
        // Largeur 100, Hauteur 140
        super(new double[]{0, 0}, new double[]{0, 0}, 100, 140, new Image("extraterrestre.png"),
                new double[]{(double) Main.LARGEUR / 2 - (double) 100 / 2, 100}, 600);
    }

    public int getNombreVies() {
        return nombreVies;
    }

    public int getNombrePoints() {
        return nombrePoints;
    }

    public boolean isEstInvincible() {
        return estInvincible;
    }

    @Override
    public void dessiner(GraphicsContext contexte, Camera camera) {
        rayonEnlevement.dessiner(contexte, camera);

        // Dessin de la tete
        contexte.drawImage(image, camera.getXEcran(pos[0]) + DIMENSIONS[0] / 2 - image.getWidth() / 2,
                pos[1] + DIMENSIONS[1] - imageBase.getHeight() - image.getHeight());
        // Dessin de l'oval
        contexte.setFill(Color.rgb(255, 255, 0, 0.6));
        contexte.fillOval(camera.getXEcran(pos[0]), pos[1], DIMENSIONS[0], DIMENSIONS[1]);
        // Dessin de la base
        contexte.drawImage(imageBase, camera.getXEcran(pos[0]) + DIMENSIONS[0] / 2 - imageBase.getWidth() / 2,
                pos[1] + DIMENSIONS[1] - imageBase.getHeight());

        gererDessinDebogage(contexte, camera);
    }

    @Override
    public void gererDessinDebogage(GraphicsContext contexte, Camera camera) {
        super.gererDessinDebogage(contexte, camera);

        // Gestion des lignes de limites
        if (Partie.getModeDebogage()) {
            contexte.setStroke(Color.GRAY);
            contexte.strokeLine(0, HAUTEUR_MINIMALE, Main.LARGEUR, HAUTEUR_MINIMALE);
            contexte.strokeLine(EXTREMITE_GAUCHE, 0, EXTREMITE_GAUCHE, Main.HAUTEUR);
            contexte.strokeLine(EXTREMITE_DROITE, 0, EXTREMITE_DROITE, Main.HAUTEUR);
        }
    }

    @Override
    public void update(double deltatemps) {
        if (estMort) {
            gererMort();
            updatePosition(deltatemps);
            return;
        }

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
            if (directionsPrecendentes[j] != 0 && directionsPrecendentes[j] != getDirection(v[j]))
                v[j] = 0;
        }

        imageBase = (Math.abs(a[0]) == ACCELERATION_BASE || Math.abs(a[1]) == ACCELERATION_BASE) ?
                new Image("base-vaisseau-on.png") : new Image("base-vaisseau-off.png");
        rayonEnlevement.update(deltatemps, this);

        //TODO VOIR SI L ENDROI EST GOOD
        if (Input.isKeyPressed(KeyCode.W) && nombreVies < NB_VIES_MAX) {
            nombreVies++;
            Input.setKeyPressed(KeyCode.W, false);
        }
        if (Input.isKeyPressed(KeyCode.Q)) {
            estInvincible = !estInvincible;
            Input.setKeyPressed(KeyCode.Q, false);
        }
    }

    @Override
    protected void updatePosition(double deltatemps) {
        super.updatePosition(deltatemps);
        if (!estMort)
            pos[1] = Math.min(pos[1], HAUTEUR_MINIMALE - DIMENSIONS[1]);
    }

    /**
     * Fonction qui gère l'accélération. Accélère ou ralentit le vaisseau selon les paramètres
     *
     * @param indexAcceleration 0 : en x, 1 : en y
     * @param multiplicateur    -1 ou 1 dépendant de la direction, et 0 si aucune touche n'est pressée
     */
    private void gererChangementsAcceleration(int indexAcceleration, int multiplicateur) {
        // Si une direction est spécifiée, on accélère dans la direction
        if (multiplicateur != 0) {
            a[indexAcceleration] = multiplicateur * ACCELERATION_BASE;
        } else { // Sinon, on accélère dans la direction opposée pour ralentir
            double vitesse = v[indexAcceleration];
            int directionVitesse = getDirection(vitesse);
            a[indexAcceleration] = directionVitesse * RALENTISSEMENT;
        }
    }

    private int getDirection(double v) {
        if (v == 0)
            return 0;
        return ((v > 0) ? 1 : -1);
    }

    public Image getImageBase() {
        return imageBase;
    }

    public RayonEnlevement getRayonEnlevement() {
        return rayonEnlevement;
    }

    public void ajouterPersonneAbsorbee() {
        nombrePersonnesAbsorbees++;
        if (nombrePersonnesAbsorbees % 2 == 0 && nombreVies < NB_VIES_MAX) nombreVies++;
    }

    public void prendDegats() {
        if (!estInvincible && !estMort) nombreVies--;
        if (nombreVies == 0) estMort = true;
    }

    public void ajouterPoint() {
        nombrePoints++;
    }

    @Override
    protected void gererLimites(int axe, double extremite) {
        if (!estMort)
            super.gererLimites(axe, extremite);
    }

    public boolean isEstMortEtSortiDeLEcran() {
        return estMortEtSortiDeLEcran;
    }

    public boolean isEstMort() {
        return estMort;
    }

    private void gererMort() {
        v[0] = -100;
        v[1] = 100;
        // Lorsqu'il dépasse l'écran, on le met mort et qui dépasse l'écran
        if (pos[1] > Main.HAUTEUR) {
            estMortEtSortiDeLEcran = true;
        }
    }
}
