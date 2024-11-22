package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgentitesabsorbable;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires.Utilitaire;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles.Aimant;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles.Girouette;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles.PotDeFleur;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles.Projectile;
import javafx.scene.image.Image;

/**
 * La classe {@code Fermier} représente un fermier ou une fermière dans le jeu.
 * Cette entité est absorbable par un vaisseau, mais elle peut également lancer des projectiles
 * pour se défendre contre le vaisseau.
 * Les fermiers ont des comportements aléatoires en termes de projectiles et d'apparence.
 */
public class Fermier extends EntiteAbsorbable {
    private static final Image[] FERMIER_IMAGES = {new Image("fermier.png"), new Image("fermiere.png")};

    /**
     * Temps écoulé depuis le dernier lancement d'un projectile.
     * Une fois que ce temps atteint 2 secondes, le fermier peut lancer un projectile.
     */
    private double tempsPourLancer = Utilitaire.genererDouble(0, 2);

    /**
     * Constructeur de la classe {@code Fermier}.
     * Initialise un fermier avec des dimensions prédéfinies (Largeur : 83, Hauteur : 185) et une position aléatoire
     * sur l'axe X en bas de l'écran. L'image du fermier est choisie aléatoirement.
     */
    public Fermier() {
        super(null, 83, 185, choisirImageAleatoire(),
                new double[]{Utilitaire.genererDouble(0, Partie.DIMENSIONS[0]), Partie.DIMENSIONS[1] - 185});
    }

    /**
     * Choisit une image aléatoire pour représenter le fermier.
     * Cela peut être soit une image de fermier, soit une image de fermière.
     *
     * @return Une instance de {@code Image} représentant le fermier.
     */
    private static Image choisirImageAleatoire() {
        return Utilitaire.genererBoolean() ? FERMIER_IMAGES[0] : FERMIER_IMAGES[1];
    }

    /**
     * Permet au fermier de tenter de créer un projectile pour attaquer le joueur.
     * <p>
     * Le fermier ne peut lancer un projectile que si :
     * <p>
     * - Il est visible à l'écran (dans la portée de la caméra).
     * <p>
     * - Au moins 2 secondes se sont écoulées depuis le dernier projectile lancé.
     * <p>
     * Les projectiles sont choisis aléatoirement parmi trois types :
     * <p>
     * - Aimant
     * <p>
     * - Girouette
     * <p>
     * - Pot de fleur
     *
     * @param camera     La caméra permettant de déterminer si le fermier est visible à l'écran.
     * @param vaisseau   Le vaisseau que le fermier souhaite attaquer.
     * @param deltaTemps Le temps écoulé depuis la dernière mise à jour, en secondes.
     * @return Une instance de {@code Projectile} si un projectile est créé, sinon {@code null}.
     */
    public Projectile tenterCreerProjectile(Camera camera, Vaisseau vaisseau, double deltaTemps) {
        if (camera.getXEcran(pos[0]) > 0 && camera.getXEcran(pos[0]) + DIMENSIONS[0] < Main.LARGEUR) {
            tempsPourLancer += deltaTemps;
            if (tempsPourLancer >= 2) {
                tempsPourLancer = 0;
                int rnd = (int) Utilitaire.genererDouble(0, 3);
                switch (rnd) {
                    case 0:
                        return new Aimant(pos, vaisseau, 300);
                    case 1:
                        return new Girouette(pos, vaisseau.getPosCentre());
                    case 2:
                        return new PotDeFleur(pos, vaisseau);
                }
            }
        }
        return null;
    }

    /**
     * Gère l'absorption du fermier par le vaisseau.
     * Lorsqu'un fermier est absorbé, le compteur de personnes absorbées dans le vaisseau est incrémenté.
     *
     * @param vaisseau Le vaisseau qui absorbe le fermier.
     */
    @Override
    protected void gererAbsorptionParVaisseau(Vaisseau vaisseau) {
        vaisseau.ajouterPersonneAbsorbee();
    }
}
