package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgdecor;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.Utilitaire;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Decor {

    public final double SOL_HAUTEUR = 60;
    private final ArrayList<ObjetsDecor> OBJETS_DECOR = new ArrayList<>();

    public Decor() {
        ajouterEtoiles();
        ajouterImagesDecors();
    }


    public void dessiner(GraphicsContext contexte, Camera camera){
        contexte.setFill(Color.web("#1a1a1a"));
        contexte.fillRect(0,0,Partie.DIMENSIONS[0],Partie.DIMENSIONS[1]);
        contexte.setFill(Color.web("#225500"));
        contexte.fillRect(0,Partie.DIMENSIONS[1] - SOL_HAUTEUR, Partie.DIMENSIONS[0], SOL_HAUTEUR);

        for (var objetsDecor : OBJETS_DECOR) {
            objetsDecor.dessiner(contexte,camera);
        }
    }

    private void ajouterEtoiles() {
        double grandeurEtoile = Utilitaire.genererDouble(8, 15);
        for (int i = 0; i < 100; i++) {
            OBJETS_DECOR.add(new Etoile(Utilitaire.genererDouble(0, Partie.DIMENSIONS[0]),
                    Utilitaire.genererDouble(0, Partie.DIMENSIONS[1] / 2),
                    grandeurEtoile));
        }
    }

    private void ajouterImagesDecors() {
        final double GRANGE_LARGEUR = 113;
        final double GRANGE_HAUTEUR = 147;
        final double TRACTEUR_LARGEUR = 89;
        final double TRACTEUR_HAUTEUR = 55;

        double x = Utilitaire.genererDouble(0, 800);
        int index = Utilitaire.genererBoolean() ? 0 : 1;
        while (x < Partie.DIMENSIONS[0]) {
            if (index % 2 == 0) {
                OBJETS_DECOR.add(new ImagesDecor(x, Partie.DIMENSIONS[1] - SOL_HAUTEUR - GRANGE_HAUTEUR,
                        GRANGE_LARGEUR, GRANGE_HAUTEUR,new Image("grange.png")));
            } else {
                OBJETS_DECOR.add(new ImagesDecor(x, Partie.DIMENSIONS[1] - SOL_HAUTEUR - TRACTEUR_HAUTEUR,
                        TRACTEUR_LARGEUR, TRACTEUR_HAUTEUR, new Image("tracteur.png")));
            }
            index++;
            x += Utilitaire.genererDouble(500, 800);
        }
    }
}
