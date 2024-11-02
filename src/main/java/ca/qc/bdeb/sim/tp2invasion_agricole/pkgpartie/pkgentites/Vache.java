package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.Utilitaire;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Vache extends EntiteAvecGravite {
    private final Image[] vachesImages = {new Image("vache.png"), new Image("vache-droite.png")};

    public Vache() {
        super(genererVitesseVache(), 0, 136, 134,
                new Image("vache.png"), Utilitaire.genererDouble(0, Partie.LARGEUR)
                , Partie.HAUTEUR - 136);
    }

    private static double genererVitesseVache(){
        return (Utilitaire.genererBoolean() ? -1 : 1) * Utilitaire.genererDouble(10,50);
    }

    @Override
    public void dessiner(GraphicsContext contexte, Camera camera) {
        super.dessiner(contexte,camera);
        changerImageVache();
    }

    private void changerImageVache(){
        image = v[0] < 0 ? vachesImages[0] : vachesImages[1];
    }

    @Override
    public void update(double deltatemps) {
        super.update(deltatemps);
        if (x == Partie.LARGEUR + LARGEUR || x == 0){
            v[0] *= -1;
        }
    }
}
