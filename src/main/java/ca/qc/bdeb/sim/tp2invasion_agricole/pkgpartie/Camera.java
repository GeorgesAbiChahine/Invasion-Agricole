package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie;

import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;

/**
 * La classe {@code Camera} gère la position de la caméra dans le jeu.
 * Elle suit le vaisseau pour s'assurer que ce dernier reste visible à l'écran
 * tout en respectant les limites du terrain.
 */
public class Camera {

    /**
     * Position actuelle de la caméra sur l'axe X.
     * Cette position est utilisée pour calculer les coordonnées visibles à l'écran.
     */
    private double xEcran = 0;

    /**
     * Calcule la position d'un objet par rapport à l'écran en fonction de la position actuelle de la caméra.
     *
     * @param x La position réelle de l'objet sur l'axe X.
     * @return La position de l'objet relative à l'écran.
     */
    public double getXEcran(Double x) {
        return x - xEcran;
    }

    /**
     * Met à jour la position de la caméra pour suivre le vaisseau.
     * La caméra ajuste sa position uniquement lorsque le vaisseau s'approche des limites gauche ou droite
     * de l'écran, tout en respectant les limites du terrain.
     *
     * @param vaisseau Le vaisseau que la caméra doit suivre.
     */
    public void update(Vaisseau vaisseau) {
        // Déplacement de la caméra vers la droite si le vaisseau dépasse l'extrémité droite de l'écran.
        if (getXEcran(vaisseau.getPos()[0]) + vaisseau.getDimensions()[0] > vaisseau.EXTREMITE_DROITE
                && (xEcran + Main.LARGEUR) < Partie.DIMENSIONS[0]) {
            xEcran += (getXEcran(vaisseau.getPos()[0]) + vaisseau.getDimensions()[0]) - vaisseau.EXTREMITE_DROITE;
        }
        // Déplacement de la caméra vers la gauche si le vaisseau dépasse l'extrémité gauche de l'écran.
        if (getXEcran(vaisseau.getPos()[0]) < vaisseau.EXTREMITE_GAUCHE && xEcran > 0) {
            xEcran -= vaisseau.EXTREMITE_GAUCHE - (getXEcran(vaisseau.getPos()[0]));
        }

    }
}
