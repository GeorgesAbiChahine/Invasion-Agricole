package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.Utilitaire;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import javafx.scene.image.Image;

public class Fermier extends Entite{
    public Fermier() {
        // Largeur : 83, Hauteur : 185.
        super(0, 0, 83, 185,choisirImageAleatoire(),
                Utilitaire.genererDouble(0, Partie.LARGEUR), Partie.HAUTEUR - 185);
    }

    private static Image choisirImageAleatoire() {
        String nomImage = Utilitaire.genererBoolean() ? "fermier.png" : "fermiere.png";
        return new Image(nomImage);
    }
}
