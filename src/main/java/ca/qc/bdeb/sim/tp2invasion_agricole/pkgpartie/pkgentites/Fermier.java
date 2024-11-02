package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import ca.qc.bdeb.sim.tp2invasion_agricole.Utilitaire;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles.Aimant;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles.Girouette;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles.PotDeFleur;
import javafx.scene.image.Image;


public class Fermier extends Entite {
    public Fermier() {
        // Largeur : 83, Hauteur : 185.
        super(0, 0, 83, 185, choisirImageAleatoire(),
                Utilitaire.genererDouble(0, Partie.LARGEUR), Partie.HAUTEUR - 185);
    }

    private static Image choisirImageAleatoire() {
        String nomImage = Utilitaire.genererBoolean() ? "fermier.png" : "fermiere.png";
        return new Image(nomImage);
    }

    private void lancer(Camera camera, Vaisseau vaisseau) {
        if (camera.getXEntiteEcran(this) > 0 && camera.getXEntiteEcran(this) < Main.LARGEUR) {
            int rnd = (int) Utilitaire.genererDouble(0, 3);
            Entite projectile;
            switch (rnd) {
                case 0:
                    projectile = new Aimant(x, y, 300);
                    break;
                case 1:
                    projectile = new Girouette(x, y, vaisseau.x, vaisseau.y);
                    break;
                case 2:
                    projectile = new PotDeFleur(0, 0, x, y);
                    break;
            }
        }
    }
}
