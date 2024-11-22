package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Classe abstraite {@code Entite} représentant une entité de base dans le jeu.
 * Une entité possède une position, une vitesse, une dimension et une image.
 */
public abstract class Entite {
    protected boolean aSupprimer = false; // Est-ce que l'entité est à supprimer du tableau

    protected Image image;
    protected final double[] DIMENSIONS;

    // pos[0] = x, pos[1] = y
    protected double[] pos;

    // v[0] = vx, v[1] = vy
    protected double[] v;

    /**
     * Constructeur de la classe {@code Entite}.
     *
     * @param v       La vitesse initiale de l'entité (tableau de composantes X et Y).
     *                Si {@code null}, la vitesse est initialisée à 0 sur les deux axes.
     * @param LARGEUR La largeur de l'entité.
     * @param HAUTEUR La hauteur de l'entité.
     * @param IMAGE   L'image représentant l'entité.
     * @param pos     La position initiale de l'entité (tableau de composantes X et Y).
     */
    public Entite(double[] v, double LARGEUR, double HAUTEUR, Image IMAGE, double[] pos) {
        this.DIMENSIONS = new double[]{LARGEUR, HAUTEUR};
        this.image = IMAGE;
        this.v = (v == null) ? new double[]{0, 0} : v;
        this.pos = pos;
    }


    public double[] getPos() {
        return pos;
    }

    public double[] getDimensions() {
        return DIMENSIONS;
    }

    /**
     * Calcule et retourne la position centrale de l'entité.
     *
     * @return Un tableau contenant les composantes X et Y de la position centrale.
     */
    public double[] getPosCentre() {
        double[] posCentre = new double[2];
        for (int i = 0; i < posCentre.length; i++) posCentre[i] = pos[i] + DIMENSIONS[i] / 2;
        return posCentre;
    }

    public void dessiner(GraphicsContext contexte, Camera camera) {
        contexte.drawImage(image, camera.getXEcran(pos[0]), pos[1], DIMENSIONS[0], DIMENSIONS[1]);
        gererDessinDebogage(contexte, camera);
    }

    /**
     * Gère l'affichage des bordures de débogage autour de l'entité si le mode débogage est activé.
     *
     * @param contexte Le contexte graphique utilisé pour le dessin.
     * @param camera   La caméra utilisée pour ajuster la position de l'entité.
     */
    public void gererDessinDebogage(GraphicsContext contexte, Camera camera) {
        if (Partie.getModeDebogage()) {
            contexte.setStroke(Color.YELLOW);
            contexte.strokeRect(camera.getXEcran(pos[0]), pos[1], DIMENSIONS[0], DIMENSIONS[1]);
        }
    }

    /**
     * Met à jour l'entité en fonction du temps écoulé.
     * Cette fonction va être "Overridden" par ses sous classes pour gérer leur logique indépendante.
     *
     * @param deltatemps Le temps écoulé depuis la dernière mise à jour, en secondes.
     */
    public void update(double deltatemps) {
        updatePosition(deltatemps);
    }

    /**
     * Met à jour la position de l'entité en fonction de sa vitesse et du temps écoulé.
     *
     * @param deltatemps Le temps écoulé depuis la dernière mise à jour, en secondes.
     */
    protected void updatePosition(double deltatemps) {
        for (int i = 0; i < pos.length; i++) {
            pos[i] += deltatemps * v[i];
            double extremite = Partie.DIMENSIONS[i] - DIMENSIONS[i];
            gererLimites(i, extremite);
        }
    }

    /**
     * Gère les limites de position de l'entité pour s'assurer qu'elle reste dans les bornes du jeu.
     *
     * @param axe       L'axe à vérifier (0 pour X, 1 pour Y).
     * @param extremite La position maximale permise sur cet axe.
     */
    protected void gererLimites(int axe, double extremite) {
        pos[axe] = Math.min(pos[axe], extremite);
        pos[axe] = Math.max(axe, pos[axe]);
    }

    /**
     * Indique si l'entité est marquée pour suppression.
     *
     * @return {@code true} si l'entité doit être supprimée, sinon {@code false}.
     */
    public boolean isASupprimer() {
        return aSupprimer;
    }

    /**
     * Vérifie si l'entité est en collision avec une autre entité.
     *
     * @param entite L'autre entité à vérifier.
     * @return {@code true} si les entités sont en collision, sinon {@code false}.
     */
    public boolean estEnCollisionAvec(Entite entite) {
        double[] posEntite = entite.getPos();
        double[] dimensionsEntite = entite.getDimensions();
        return ((pos[0] < posEntite[0] + dimensionsEntite[0]) &&
                (pos[0] + DIMENSIONS[0] > posEntite[0]) &&
                (pos[1] < posEntite[1] + dimensionsEntite[1]) &&
                (pos[1] + DIMENSIONS[1] > posEntite[1]));
    }
}
