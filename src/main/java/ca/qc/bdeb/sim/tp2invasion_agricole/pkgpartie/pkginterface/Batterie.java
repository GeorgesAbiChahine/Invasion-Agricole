package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkginterface;


import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Batterie extends ObjetsInterface{
    private final Image IMAGE = new Image("batterie.png");

    public Batterie(double x, double y, double LARGEUR) {
        super(x, y, LARGEUR);
    }

    public void dessiner(GraphicsContext contexte, Vaisseau vaisseau) {
        final double HAUTEUR = 45;

        contexte.setStroke(Color.YELLOW);
        contexte.strokeRect(X, Y, LARGEUR, HAUTEUR);

        if (vaisseau.getRayonEnlevement().getCharge() >= 0.2) contexte.setFill(Color.YELLOW);
        else contexte.setFill(Color.ORANGE);
        if (!vaisseau.getRayonEnlevement().isPeutEnlever()) contexte.setFill(Color.RED);

        contexte.fillRect(X, Y, LARGEUR * vaisseau.getRayonEnlevement().getCharge(), HAUTEUR);
        contexte.drawImage(IMAGE, X + 5, Y + 5);
    }
}
