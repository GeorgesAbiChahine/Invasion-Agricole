package ca.qc.bdeb.sim.tp2invasion_agricole.pkgvisuel.pkgdecor;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires.Utilitaire;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * La classe {@code Decor} gère les éléments graphiques fixes de l'arrière-plan dans le jeu,
 * tels que le sol, les étoiles et les objets décoratifs (tracteurs et granges).
 * Ces éléments ne sont pas interactifs, mais contribuent à l'ambiance visuelle du jeu.
 */
public class Decor {
    public final double HAUTEUR_SOL = 60;

    private final ArrayList<ObjetDecor> OBJETS_DECOR = new ArrayList<>();

    /**
     * Constructeur de la classe {@code Decor}.
     * Initialise les objets décoratifs en ajoutant des étoiles et des images spécifiques
     * comme des tracteurs et des granges dans l'arrière-plan.
     */
    public Decor() {
        ajouterEtoiles();
        ajouterImagesDecors();
    }


    /**
     * Dessine le décor sur le canvas.
     * Cette méthode gère la couleur de fond, la représentation du sol,
     * ainsi que tous les objets décoratifs ajoutés dans la liste {@code OBJETS_DECOR}.
     *
     * @param contexte Le contexte graphique où le décor sera dessiné.
     * @param camera   La caméra utilisée pour ajuster la position des objets en fonction du déplacement.
     */
    public void dessiner(GraphicsContext contexte, Camera camera) {
        //Dessine le fond noir.
        contexte.setFill(Color.web("#1a1a1a"));
        contexte.fillRect(0, 0, Partie.DIMENSIONS[0], Partie.DIMENSIONS[1]);

        //Dessine le sol vert.
        contexte.setFill(Color.web("#225500"));
        contexte.fillRect(0, Partie.DIMENSIONS[1] - HAUTEUR_SOL, Partie.DIMENSIONS[0], HAUTEUR_SOL);

        // Dessine tous les objets décoratifs.
        for (var objetsDecor : OBJETS_DECOR) objetsDecor.dessiner(contexte, camera);
    }

    /**
     * Ajoute des étoiles dans le décor.
     * Les étoiles sont placées aléatoirement dans la partie supérieure de l'écran.
     */
    private void ajouterEtoiles() {
        double grandeurEtoile = Utilitaire.genererDouble(8, 15);
        for (int i = 0; i < 100; i++) {
            OBJETS_DECOR.add(new Etoile(Utilitaire.genererDouble(0, Partie.DIMENSIONS[0]),
                    Utilitaire.genererDouble(0, Partie.DIMENSIONS[1] / 2),
                    grandeurEtoile));
        }
    }

    /**
     * Ajoute des images décoratives telles que des tracteurs et des granges
     * dans le décor du jeu. Ces images sont placées aléatoirement avec des
     * intervalles variés.
     * Les images sont générées en alternance (tracteur ou grange) le long de l'axe X.
     */
    private void ajouterImagesDecors() {
        final Image IMAGE_TRACTEUR = new Image("tracteur.png");
        final Image IMAGE_GRANGE = new Image("grange.png");

        double x = Utilitaire.genererDouble(0, 800);
        int index = Utilitaire.genererBoolean() ? 0 : 1;
        while (x < Partie.DIMENSIONS[0]) {
            if (index % 2 == 0) {
                ajouterImageDansListe(IMAGE_GRANGE, x);
            } else {
                ajouterImageDansListe(IMAGE_TRACTEUR, x);
            }
            index++;
            x += Utilitaire.genererDouble(500, 800);
        }
    }

    private void ajouterImageDansListe(Image image, double x) {
        OBJETS_DECOR.add(new ImagesDecor(x, Partie.DIMENSIONS[1] - HAUTEUR_SOL - image.getHeight(),
                image.getWidth(), image.getHeight(), image));
    }
}
