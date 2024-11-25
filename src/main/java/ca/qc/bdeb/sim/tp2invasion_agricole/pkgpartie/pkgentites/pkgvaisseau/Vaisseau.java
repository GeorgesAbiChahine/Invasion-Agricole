package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires.Input;
import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.EntiteAcceleratrice;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires.Utilitaire;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;


/**
 * La classe {@code Vaisseau} représente le vaisseau contrôlé par le joueur.
 * Elle gère les mouvements du vaisseau, l'absorption des entités, ainsi que les événements de vie ou de mort du joueur.
 */
public class Vaisseau extends EntiteAcceleratrice {
    // Constante utilisée par le constructeur et pour calculer la position initiale
    private final static int LARGEUR_VAISSEAU = 100;
    private final double ACCELERATION_BASE = 2000;
    private final double HAUTEUR_MINIMALE = Partie.DIMENSIONS[1] * 0.6;
    public final double EXTREMITE_DROITE = (Main.LARGEUR * 0.7);
    public final double EXTREMITE_GAUCHE = (Main.LARGEUR * 0.3);

    private final int NB_VIES_MAX = 4;

    // Séparé en deux pour dimensions horizontale et verticale, puis en encore en deux pour la direction (+, -)
    private final KeyCode[][] TOUCHES_DE_CONTROLE = {{KeyCode.RIGHT, KeyCode.LEFT}, {KeyCode.DOWN, KeyCode.UP}};

    private Image imageBase = new Image("base-vaisseau-off.png");
    private final RayonEnlevement RAYON_ENLEVEMENT = new RayonEnlevement(this);

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

    /**
     * @return Un tableau contenant les coordonnées x et y de départ.
     */
    private static double[] initialiserPosition() {
        return new double[]{(double) Main.LARGEUR / 2 - (double) LARGEUR_VAISSEAU / 2, 100};
    }

    /**
     * @return {@code true} si le vaisseau est mort et hors de l'écran, {@code false} sinon.
     */
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

    /**
     * @return L'image de base utilisée pour la partie inférieure du vaisseau.
     */
    public Image getImageBase() {
        return imageBase;
    }

    public RayonEnlevement getRayonEnlevement() {
        return RAYON_ENLEVEMENT;
    }

    /**
     * Réinitialise la position du vaisseau à sa position de départ, et remet sa vitesse et son accélération à 0.
     * Relache les touches de mouvement pour éviter un bug, où si la touche n'est pas relâchée avant la fin de la
     * mort du vaisseau, le programme garde la touche comme pressée même si ce n'est plus le cas.
     */
    public void reinitialiser() {
        a[0] = 0;
        a[1] = 0;
        v[0] = 0;
        v[1] = 0;
        for (var touchesAxe : TOUCHES_DE_CONTROLE) {
            for (var touche : touchesAxe) Input.setKeyPressed(touche, false);
        }
        pos = initialiserPosition();
    }

    /**
     * Obtient la direction de la vitesse.
     *
     * @param v La vitesse à évaluer.
     * @return 1 si positif, -1 si négatif, 0 si nul.
     */
    private int getDirection(double v) {
        if (v == 0) return 0;
        return ((v > 0) ? 1 : -1);
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

        // Gestion des lignes de limites (caméra et hauteur minimale)
        if (Partie.getModeDebogage()) {
            contexte.setStroke(Color.GRAY);
            contexte.strokeLine(0, HAUTEUR_MINIMALE, Main.LARGEUR, HAUTEUR_MINIMALE);
            contexte.strokeLine(EXTREMITE_GAUCHE, 0, EXTREMITE_GAUCHE, Main.HAUTEUR);
            contexte.strokeLine(EXTREMITE_DROITE, 0, EXTREMITE_DROITE, Main.HAUTEUR);
        }
    }

    @Override
    public void update(double deltatemps) {
        // Si le vaisseau est mort, gérer sa mort et éviter toute autre logique
        if (gererMort()) {
            updatePosition(deltatemps); // Continuer à mettre à jour sa position (chute hors de l'écran)
            return;
        }

        // Gestion de l'accélération
        for (int i = 0; i < TOUCHES_DE_CONTROLE.length; i++) {
            int direction = 0; // Valeur par défaut : aucune direction
            if (Input.isKeyPressed(TOUCHES_DE_CONTROLE[i][0])) direction++; // Touches pour aller à droite ou en bas
            if (Input.isKeyPressed(TOUCHES_DE_CONTROLE[i][1])) direction--; // Touches pour aller à gauche ou en haut
            // Si les deux touches sont pressées, directionX = 0
            gererChangementsAcceleration(i, direction);
        }

        updatePositionEnGerantLeBugDAcceleration(deltatemps);

        // Changer l'image de la base si une touche est appuyée pour accélérer le vaisseau
        imageBase = (Math.abs(a[0]) == ACCELERATION_BASE || Math.abs(a[1]) == ACCELERATION_BASE) ?
                new Image("base-vaisseau-on.png") : new Image("base-vaisseau-off.png");

        RAYON_ENLEVEMENT.update(deltatemps, this);

        // Gestion des commandes spéciales
        if (Input.isOneTimeKeyPressed(KeyCode.W) && nombreVies < NB_VIES_MAX) nombreVies++;
        if (Input.isOneTimeKeyPressed(KeyCode.Q)) estInvincible = !estInvincible;
    }

    private void updatePositionEnGerantLeBugDAcceleration(double deltatemps) {
        // Sauvegarder la direction actuelle des vitesses pour détection de changement de direction
        int[] directionsPrecedentes = {getDirection(v[0]), getDirection(v[1])};

        // Mettre à jour la position du vaisseau en fonction de la physique (accélération, vitesse, etc.)
        updatePosition(deltatemps);

        // Annuler toute vitesse si un changement de direction est détecté à cause du ralentissement pour éviter un bug
        for (int j = 0; j < directionsPrecedentes.length; j++) {
            if (directionsPrecedentes[j] != 0 && directionsPrecedentes[j] != getDirection(v[j])) v[j] = 0;
        }
    }

    @Override
    protected void updatePosition(double deltatemps) {
        super.updatePosition(deltatemps);
        // Empêcher le vaisseau de descendre en dessous d'une hauteur minimale définie, sauf s'il est mort
        if (!estMort) pos[1] = Math.min(pos[1], HAUTEUR_MINIMALE - DIMENSIONS[1]);
    }

    /**
     * Gère l'accélération du vaisseau en fonction de l'axe et de la direction indiqués.
     * Si une direction est spécifiée, le vaisseau accélère dans cette direction.
     * Si aucune direction n'est donnée, il ralentit dans la direction opposée à sa vitesse actuelle.
     *
     * @param indexAcceleration 0 pour l'axe X, 1 pour l'axe Y.
     * @param multiplicateur    -1 pour une direction négative, 1 pour une direction positive,
     *                          ou 0 si aucune touche n'est pressée.
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

    /**
     * Ajoute un fermier absorbé par le vaisseau. Après chaque deux absorptions, une vie est ajoutée
     * au vaisseau, si le nombre maximum de vies n'est pas atteint.
     */
    public void ajouterPersonneAbsorbee() {
        nombrePersonnesAbsorbees++;
        if (nombrePersonnesAbsorbees % 2 == 0 && nombreVies < NB_VIES_MAX) nombreVies++;
    }

    public void prendDegats() {
        if (!estInvincible && !estMort) nombreVies--;
        if (nombreVies == 0) estMort = true;
    }

    /**
     * Augmente le score du vaisseau en ajoutant un point au compteur de points, appelée lorsqu'une vache est absorbée.
     */
    public void ajouterPoint() {
        nombrePoints++;
    }

    @Override
    protected void gererLimites(int axe, double extremite) {
        // S'il est mort, on ignore et il peut dépasser les limites
        if (!estMort) super.gererLimites(axe, extremite);
    }

    /**
     * Gère l'état de mort du vaisseau. Une fois mort, le vaisseau se déplace dans une
     * trajectoire spécifique (en chute libre simulée) et désactive son rayon d'enlèvement.
     * Si le vaisseau dépasse l'écran, il est marqué comme "mort et sorti de l'écran".
     *
     * @return {@code true} si le vaisseau est mort, {@code false} sinon.
     */
    private boolean gererMort() {
        // S'il n'est pas mort, on ignore
        if (!estMort) return false;

        // Si le vaisseau vient de mourir, on définit les nouvelles vitesses
        if (v[1] != 100 && Math.abs(v[0]) != 100) {
            // Le vaisseau tombe vers la droite ou la gauche aléatoirement
            int directionAleatoire = (Utilitaire.genererBoolean()) ? 1 : -1;
            a[0] = 0;
            a[1] = 0;
            v[0] = directionAleatoire * 100;
            v[1] = 100;
        }

        // On désactive le rayon d'enlèvement
        RAYON_ENLEVEMENT.desactiver();

        // Lorsqu'il dépasse l'écran, on le met "mort et sorti de l'écran"
        if (pos[1] > Main.HAUTEUR) estMortEtSortiDeLEcran = true;
        return true;
    }
}
