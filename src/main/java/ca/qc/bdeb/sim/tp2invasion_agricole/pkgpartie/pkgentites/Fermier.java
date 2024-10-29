package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.Utilitaire;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgarriereplan.Decor;
import javafx.scene.image.Image;

public class Fermier extends Entite{
    public Fermier() {
        // Largeur : 83, Hauteur : 185.
        super(0, 0, 0, 0, 83, 185,choisirImageAleatoire(),
                Utilitaire.generer(0, Partie.LARGEUR), Partie.HAUTEUR - Decor.SOL_HAUTEUR - 185);
    }

    //TODO : genererboolean() ? true : false
    private static Image choisirImageAleatoire() {
        String nomImage = Utilitaire.generer(0,1) < 0.5 ? "fermier.png" : "fermiere.png";
        return new Image(nomImage);
    }
}
