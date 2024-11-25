package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgentitesabsorbable;

import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Entite;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import javafx.scene.image.Image;

/**
 * La classe abstraite {@code EntiteAbsorbable} représente une entité du jeu qui peut être absorbée par le vaisseau.
 * Cette classe hérite de {@code Entite} et ajoute des comportements spécifiques liés à l'absorption.
 * Les entités absorbables interagissent avec la gravité et peuvent être enlevées par le vaisseau.
 */
public abstract class EntiteAbsorbable extends Entite {
    /**
     * Indique si l'entité est actuellement en cours d'enlèvement par un vaisseau.
     */
    protected boolean estEnEnlevement = false;

    /**
     * Constructeur de la classe {@code EntiteAbsorbable}.
     *
     * @param v       La vitesse de l'entité (composantes X et Y).
     * @param LARGEUR La largeur de l'entité.
     * @param HAUTEUR La hauteur de l'entité.
     * @param IMAGE   L'image associée à l'entité.
     * @param pos     La position initiale de l'entité (composantes X et Y).
     */
    public EntiteAbsorbable(double[] v, double LARGEUR, double HAUTEUR, Image IMAGE, double[] pos) {
        super(v, LARGEUR, HAUTEUR, IMAGE, pos);
    }

    /**
     * Met à jour la position de l'entité en fonction du temps écoulé.
     * Applique les effets de la gravité lorsque l'entité n'est pas au sol ou en cours d'enlèvement.
     *
     * @param deltatemps Le temps écoulé depuis la dernière mise à jour, en secondes.
     */
    @Override
    protected void updatePosition(double deltatemps) {
        super.updatePosition(deltatemps);
        final double GRAVITE = 1000;

        if (estEnEnlevement) v[1] = -100;
        else if (!toucheLeSol()) v[1] += GRAVITE * deltatemps;
    }

    private boolean toucheLeSol() {
        return pos[1] + DIMENSIONS[1] >= Main.HAUTEUR;
    }

    /**
     * Gère l'enlèvement de l'entité par le vaisseau.
     * Si l'entité est en collision avec le rayon d'enlèvement du vaisseau, elle est enlevée.
     * Si l'entité entre en collision avec le vaisseau, elle est absorbée.
     *
     * @param vaisseau Le vaisseau qui absorbe l'entité.
     */
    public void gererEnlevement(Vaisseau vaisseau) {
        estEnEnlevement = !vaisseau.isEstMort() && estEnCollisionAvec(vaisseau.getRayonEnlevement());

        if (!vaisseau.isEstMort() && estEnCollisionAvec(vaisseau)) {
            aSupprimer = true;
            gererAbsorptionParVaisseau(vaisseau);
        }
    }

    /**
     * Méthode abstraite gérant les effets spécifiques lorsque l'entité est absorbée par le vaisseau.
     *
     * @param vaisseau Le vaisseau qui absorbe l'entité.
     */
    protected abstract void gererAbsorptionParVaisseau(Vaisseau vaisseau);
}
