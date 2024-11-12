package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkginterface;


import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Batterie {
    private final Image IMAGE = new Image("batterie.png");
    private final double X = 5;
    private final double Y = 5;
    private final double LARGEUR = 200;
    private final double HAUTEUR = 45;

    public void dessiner(GraphicsContext contexte, Vaisseau vaisseau) {
        contexte.setStroke(Color.YELLOW);
        contexte.strokeRect(X, Y, LARGEUR, HAUTEUR);

        if (vaisseau.getRayonEnlevement().getCharge() >= 0.2) contexte.setFill(Color.YELLOW);
        else contexte.setFill(Color.ORANGE);
        if (!vaisseau.getRayonEnlevement().isPeutEnlever()) contexte.setFill(Color.RED);

        contexte.fillRect(X, Y, LARGEUR * vaisseau.getRayonEnlevement().getCharge(), HAUTEUR);
        contexte.drawImage(IMAGE, X + 5, Y + 5);
    }
}
