package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import ca.qc.bdeb.sim.tp2invasion_agricole.Utilitaire;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles.Aimant;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles.Girouette;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles.PotDeFleur;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles.Projectile;
import javafx.scene.image.Image;


public class Fermier extends Entite {
    private double tempsPourLancer = Utilitaire.genererDouble(0,2);

    public Fermier() {
        // Largeur : 83, Hauteur : 185.
        super(new double[]{0, 0}, 83, 185, choisirImageAleatoire(),
                new double[]{Utilitaire.genererDouble(0, Partie.DIMENSIONS[0]), Partie.DIMENSIONS[1] - 185});
    }

    private static Image choisirImageAleatoire() {
        String nomImage = Utilitaire.genererBoolean() ? "fermier.png" : "fermiere.png";
        return new Image(nomImage);
    }

    public Projectile tenterCreerProjectile(Camera camera, Vaisseau vaisseau, double deltaTemps) {
        if (camera.getXEcran(pos[0]) > 0 && camera.getXEcran(pos[0]) + DIMENSIONS[0] < Main.LARGEUR) {
            tempsPourLancer += deltaTemps;
            if (tempsPourLancer >= 2) {
                tempsPourLancer = 0;
                int rnd = (int) Utilitaire.genererDouble(0, 3);
                switch (rnd) {
                    case 0:
                        return new Aimant(pos, vaisseau.pos, 300);
                    case 1:
                        return new Girouette(pos, vaisseau.getPosCentre());
                    case 2:
                        return new PotDeFleur(pos, vaisseau);
                }
            }
        }
        return null;
    }
}
