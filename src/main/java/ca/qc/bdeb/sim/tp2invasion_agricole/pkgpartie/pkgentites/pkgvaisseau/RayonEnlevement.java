package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires.Input;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Entite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

/**
 * La classe {@code RayonEnlevement} représente le rayon d'enlèvement du vaisseau.
 * Ce rayon peut s'étendre pour capturer des vaches et des fermiers, mais il est limité
 * par une charge qui se décharge et se recharge au fil du temps.
 */
public class RayonEnlevement extends Entite {

    /**
     * La charge actuelle du rayon, entre 0 (vide) et 1 (plein).
     */
    private double charge = 1;

    /**
     * Indique si le rayon peut être utilisé pour effectuer des enlèvements.
     */
    private boolean peutEnlever = true;

    /**
     * Constructeur de la classe {@code RayonEnlevement}.
     * Initialise le rayon d'enlèvement en fonction de la position et des dimensions du vaisseau.
     *
     * @param vaisseau Le vaisseau auquel le rayon est attaché.
     */
    public RayonEnlevement(Vaisseau vaisseau) {
        super(null, vaisseau.getDimensions()[0], 0, null, new double[]{vaisseau.getPos()[0],
                getYRayon(vaisseau)});
    }

    /**
     * Calcule la position Y du rayon en fonction de la position et de la hauteur de la base du vaisseau.
     *
     * @param vaisseau Le vaisseau auquel le rayon est attaché.
     * @return La position Y du rayon.
     */
    protected static double getYRayon(Vaisseau vaisseau) {
        return (vaisseau.getPos()[1] + vaisseau.getDimensions()[1] - vaisseau.getImageBase().getHeight() / 2);
    }

    public double getCharge() {
        return charge;
    }

    public boolean isPeutEnlever() {
        return peutEnlever;
    }

    /**
     * Met à jour le comportement du rayon en fonction du temps écoulé et des actions du joueur. <p>
     * - Le rayon s'étend si la touche Espace est enfoncée, consommant de la charge. <p>
     * - La charge se recharge lorsque le rayon n'est pas utilisé. <p>
     * - La touche E réinitialise la charge à 100 %.
     *
     * @param deltaTemps Le temps écoulé depuis la dernière mise à jour, en secondes.
     * @param vaisseau   Le vaisseau auquel le rayon est attaché.
     */
    protected void update(double deltaTemps, Vaisseau vaisseau) {
        final double VITESSE_ALLONGEMENT = 150;
        final double VITESSE_DECHARGEMENT = 0.25;
        final double VITESSE_CHARGEMENT = 0.2;

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
        if (charge >= 1 || Input.isOneTimeKeyPressed(KeyCode.E)) {
            charge = 1;
            peutEnlever = true;
        }
    }

    /**
     * Dessine le rayon sur le canvas. <p>
     * - Le rayon est dessiné avec une transparence jaune lorsqu'il est actif. <p>
     * - En mode débogage, un contour jaune est ajouté si le rayon peut enlever.
     *
     * @param contexte Le contexte graphique où le rayon sera dessiné.
     * @param camera   La caméra utilisée pour ajuster la position du rayon.
     */
    @Override
    public void dessiner(GraphicsContext contexte, Camera camera) {
        contexte.setFill(Color.rgb(255, 255, 0, 0.5));
        contexte.fillRect(camera.getXEcran(pos[0]), pos[1], DIMENSIONS[0], DIMENSIONS[1]);

        //TODO VOIR SI ON DOIT OVVERRIDE GERERENMODEDEBOGAGE  (MARCHE PAS FOR SOME REASON)
        if (Partie.getModeDebogage() && peutEnlever) {
            contexte.setStroke(Color.YELLOW);
            contexte.strokeRect(camera.getXEcran(pos[0]), pos[1], DIMENSIONS[0], DIMENSIONS[1]);
        }
    }

    /**
     * Désactive le rayon en réinitialisant sa longueur et en empêchant les enlèvements.
     */
    protected void desactiver() {
        DIMENSIONS[1] = 0;
        charge = 0;
        peutEnlever = false;
    }
}
