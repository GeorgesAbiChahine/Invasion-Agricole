package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgarriereplan;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.Utilitaire;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Decor {

    public final double SOL_HAUTEUR = 60;
    private final ArrayList<ObjetsDecor> OBJETS_DECOR = new ArrayList<>();

    public Decor() {
        ajouterEtoiles();
        ajouterImagesDecors();
    }


    public void dessiner(GraphicsContext contexte){
        contexte.setFill(Color.web("#1a1a1a"));
        contexte.fillRect(0,0,Partie.LARGEUR,Partie.HAUTEUR);
        contexte.setFill(Color.web("#225500"));
        contexte.fillRect(0,Partie.HAUTEUR - SOL_HAUTEUR, Partie.LARGEUR, SOL_HAUTEUR);

        for (var objetsDecor : OBJETS_DECOR) {
            objetsDecor.dessiner(contexte);
        }

    }

    private void ajouterEtoiles() {
        double grandeurEtoile = Utilitaire.genererDouble(8, 15);
        for (int i = 0; i < 100; i++) {
            OBJETS_DECOR.add(new Etoile(Utilitaire.genererDouble(0, Partie.LARGEUR),
                    Utilitaire.genererDouble(0, Partie.HAUTEUR / 2), // TODO : Changer Main pour Partie
                    grandeurEtoile));
        }
    }

    private void ajouterImagesDecors() {
        final double GRANGE_LARGEUR = 113;
        final double GRANGE_HAUTEUR = 147;
        final double TRACTEUR_LARGEUR = 89;
        final double TRACTEUR_HAUTEUR = 55;

        double x = Utilitaire.genererDouble(0, 800);
        int index = Utilitaire.genererDouble(0,1) < 0.5 ? 0 : 1;
        while (x < Partie.LARGEUR) {
            if (index % 2 == 0) {
                OBJETS_DECOR.add(new Grange(x, Partie.HAUTEUR - SOL_HAUTEUR - GRANGE_HAUTEUR,
                        GRANGE_LARGEUR, GRANGE_HAUTEUR));
            } else {
                OBJETS_DECOR.add(new Tracteur(x, Partie.HAUTEUR - SOL_HAUTEUR - TRACTEUR_HAUTEUR,
                        TRACTEUR_LARGEUR, TRACTEUR_HAUTEUR));
            }
            index++;
            x += Utilitaire.genererDouble(500, 800);
        }
    }
}
