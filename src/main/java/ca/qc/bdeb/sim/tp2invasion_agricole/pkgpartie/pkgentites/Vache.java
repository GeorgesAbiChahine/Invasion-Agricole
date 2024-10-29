package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.Utilitaire;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgarriereplan.Decor;
import javafx.scene.image.Image;

public class Vache extends Entite{
    public Vache() {
        super(0, 0, genererVitesseVache(), 0, 136, 134,
                new Image("vache.png"), Utilitaire.generer(0, Partie.LARGEUR)
                , Partie.HAUTEUR - Decor.SOL_HAUTEUR - 136);
    }

    private static double genererVitesseVache(){
        return (Utilitaire.generer(0,1) < 0.5 ? -1 : 1) * Utilitaire.generer(10,25);
    }
}
