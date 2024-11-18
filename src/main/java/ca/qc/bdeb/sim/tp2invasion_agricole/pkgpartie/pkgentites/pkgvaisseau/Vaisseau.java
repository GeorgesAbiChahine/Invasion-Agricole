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

    private final static int LARGEUR_VAISSEAU = 100;
    private final double ACCELERATION_BASE = 2000;
    private final double HAUTEUR_MINIMALE = Partie.DIMENSIONS[1] * 0.6;
    public final double EXTREMITE_DROITE = (Main.LARGEUR * 0.7);
    public final double EXTREMITE_GAUCHE = (Main.LARGEUR * 0.3);
    // Séparé en deux pour dimensions horizontale et verticale, puis en encore en deux pour la direction (+, -)
    private final KeyCode[][] TOUCHES_DE_CONTROLE = {{KeyCode.RIGHT, KeyCode.LEFT}, {KeyCode.DOWN, KeyCode.UP}};
    private Image imageBase = new Image("base-vaisseau-off.png");

    private final RayonEnlevement RAYON_ENLEVEMENT = new RayonEnlevement(this);
    private final int NB_VIES_MAX = 4;

    protected boolean estInvincible = false;
    private int nombreVies = 4;
    private int nombrePoints = 0;
    private int nombrePersonnesAbsorbees = 0;
    private boolean estMort = false;
    private boolean estMortEtSortiDeLEcran = false;

    public Vaisseau() {
        // Note : largeur = 100
        super(null, null, LARGEUR_VAISSEAU, 140, new Image("extraterrestre.png"),
                initialiserPosition(), 600);
    }

    private static double[] initialiserPosition() {
        return new double[]{(double) Main.LARGEUR / 2 - (double) LARGEUR_VAISSEAU / 2, 100};
    }

    public boolean isEstMortEtSortiDeLEcran() {
        return estMortEtSortiDeLEcran;
    }

    public boolean isEstMort() {
        return estMort;
    }

    public boolean isEstInvincible() {
        return estInvincible;
    }

    public int getNombreVies() {
        return nombreVies;
    }

    public int getNombrePoints() {
        return nombrePoints;
    }

    public Image getImageBase() {
        return imageBase;
    }

    private int getDirection(double v) {
        if (v == 0) return 0;
        return ((v > 0) ? 1 : -1);
    }

    public void reinitialiserPosition() {
        pos = initialiserPosition();
    }

    @Override
    public void dessiner(GraphicsContext contexte, Camera camera) {
        RAYON_ENLEVEMENT.dessiner(contexte, camera);

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
        if (gererMort()) {
            updatePosition(deltatemps);
            return;
        }

        // Gestion de l'accélération
        for (int i = 0; i < TOUCHES_DE_CONTROLE.length; i++) {
            int direction = 0;
            if (Input.isKeyPressed(TOUCHES_DE_CONTROLE[i][0])) direction++;
            if (Input.isKeyPressed(TOUCHES_DE_CONTROLE[i][1])) direction--;
            // Si les deux touches sont pressées, directionX = 0
            gererChangementsAcceleration(i, direction);
        }

        // Gestion du bug lors du changment de direction de la vitesse
        int[] directionsPrecedentes = {getDirection(v[0]), getDirection(v[1])};

        updatePosition(deltatemps);

        for (int j = 0; j < directionsPrecedentes.length; j++) {
            if (directionsPrecedentes[j] != 0 && directionsPrecedentes[j] != getDirection(v[j])) v[j] = 0;
        }

        imageBase = (Math.abs(a[0]) == ACCELERATION_BASE || Math.abs(a[1]) == ACCELERATION_BASE) ?
                new Image("base-vaisseau-on.png") : new Image("base-vaisseau-off.png");

        RAYON_ENLEVEMENT.update(deltatemps, this);

        if (Input.isOneTimeKeyPressed(KeyCode.W) && nombreVies < NB_VIES_MAX) nombreVies++;
        if (Input.isOneTimeKeyPressed(KeyCode.Q)) estInvincible = !estInvincible;
    }

    @Override
    protected void updatePosition(double deltatemps) {
        super.updatePosition(deltatemps);
        if (!estMort) pos[1] = Math.min(pos[1], HAUTEUR_MINIMALE - DIMENSIONS[1]);
    }

    /**
     * Fonction qui gère l'accélération. Accélère ou ralentit le vaisseau selon les paramètres
     *
     * @param indexAcceleration 0 : en x, 1 : en y
     * @param multiplicateur    -1 ou 1 dépendant de la direction, et 0 si aucune touche n'est pressée
     */
    private void gererChangementsAcceleration(int indexAcceleration, int multiplicateur) {
        final double RALENTISSEMENT = -500;

        // Si une direction est spécifiée, on accélère dans la direction
        if (multiplicateur != 0) {
            a[indexAcceleration] = multiplicateur * ACCELERATION_BASE;
        } else { // Sinon, on accélère dans la direction opposée pour ralentir
            double vitesse = v[indexAcceleration];
            int directionVitesse = getDirection(vitesse);
            a[indexAcceleration] = directionVitesse * RALENTISSEMENT;
        }
    }


    public RayonEnlevement getRayonEnlevement() {
        return RAYON_ENLEVEMENT;
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
        if (!estMort) super.gererLimites(axe, extremite);
    }

    private boolean gererMort() {
        if (!estMort) return false;
        v[0] = -100;
        v[1] = 100;
        RAYON_ENLEVEMENT.desactiver();
        // Lorsqu'il dépasse l'écran, on le met mort et qui dépasse l'écran
        if (pos[1] > Main.HAUTEUR) estMortEtSortiDeLEcran = true;
        return true;
    }
}
