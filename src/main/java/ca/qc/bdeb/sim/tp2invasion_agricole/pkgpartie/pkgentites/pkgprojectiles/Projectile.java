package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.EntiteAcceleratrice;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import javafx.scene.image.Image;

/**
 * La classe abstraite {@code Projectile} représente les projectiles génériques
 * dans le jeu. Ces projectiles sont lancés par les fermiers pour causer des dégâts au vaisseau.
 */
public abstract class Projectile extends EntiteAcceleratrice {

    /**
     * Constructeur de la classe {@code Projectile}.
     *
     * @param a          L'accélération du projectile (tableau de composantes X et Y).
     * @param v          La vitesse initiale du projectile (tableau de composantes X et Y).
     * @param LARGEUR    La largeur du projectile.
     * @param HAUTEUR    La hauteur du projectile.
     * @param IMAGE      L'image représentant le projectile.
     * @param pos        La position initiale du projectile (tableau de composantes X et Y).
     * @param vitesseMax La vitesse maximale que le projectile peut atteindre.
     */
    public Projectile(double[] a, double[] v, double LARGEUR, double HAUTEUR, Image IMAGE, double[] pos, double vitesseMax) {
        super(a, v, LARGEUR, HAUTEUR, IMAGE, pos, vitesseMax);
    }

    /**
     * Calcule la vitesse requise pour diriger un projectile vers une position cible.
     * La méthode utilise la distance entre la position actuelle et la cible pour normaliser
     * la direction et applique une magnitude définie.
     *
     * @param pos         La position actuelle du projectile (tableau de composantes X et Y).
     * @param posVaisseau La position cible (tableau de composantes X et Y).
     * @param magnitude   La magnitude de la vitesse.
     * @return Un tableau contenant les composantes X et Y de la vitesse calculée.
     */
    protected static double[] calculerV(double[] pos, double[] posVaisseau, int magnitude) {
        double[] delta = new double[2];
        for (int i = 0; i < pos.length; i++) delta[i] = posVaisseau[i] - pos[i];

        double distance = Math.sqrt(delta[0] * delta[0] + delta[1] * delta[1]);
        return new double[]{magnitude * (delta[0] / distance), magnitude * (delta[1] / distance)};
    }

    @Override
    protected void gererLimites(int axe, double extremite) {
        if (pos[axe] < (-DIMENSIONS[axe]) || pos[axe] > extremite) aSupprimer = true;
    }

    public void gererAttaqueSurVaisseau(Vaisseau vaisseau) {
        if (estEnCollisionAvec(vaisseau)) {
            vaisseau.prendDegats();
            aSupprimer = true;
        }
    }
}
