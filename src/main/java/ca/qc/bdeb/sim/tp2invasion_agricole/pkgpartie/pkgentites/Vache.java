package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.Utilitaire;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Vache extends Entite{
    private final Image[] vachesImages = {new Image("vache.png"), new Image("vache-droite.png")};

    public Vache() {
        super(0, 0, genererVitesseVache(), 0, 136, 134,
                new Image("vache.png"), Utilitaire.generer(0, Partie.LARGEUR)
                , Partie.HAUTEUR - 136);
    }

    private static double genererVitesseVache(){
        return (Utilitaire.generer(0,1) < 0.5 ? -1 : 1) * Utilitaire.generer(0.1,0.5);
    }

    //TODO VERIFIER QUE C'EST BIEN FAIT.
    @Override
    public void dessiner(GraphicsContext contexte) {
        super.dessiner(contexte);
        genererImageVache();
    }

    private void genererImageVache(){
        image = vx < 0 ? vachesImages[0] : vachesImages[1];
    }

    //TODO: Voir si on doit override update ou updatePosition
    @Override
    public void updatePosition(double deltatemps) {
        super.updatePosition(deltatemps);
        if (x == Partie.LARGEUR + LARGEUR || x == 0){
            vx *= -1;
        }
    }
}
