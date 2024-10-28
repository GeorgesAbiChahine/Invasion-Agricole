package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;

import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import com.sun.tools.jconsole.JConsoleContext;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Vaisseau extends Entite {
    private Image imageBase = new Image("base-vaisseau-off.png");
    public Vaisseau() {
        // Largeur 100, Hauteur 140
        super(0, 0, 0, 0, 100, 140, new Image("extraterrestre.png"),
                (double) Main.LARGEUR / 2 - (double) 100 / 2, 100);
    }

    @Override
    public void dessiner(GraphicsContext contexte) {
        double largeurTete = 66;
        double hauteurTete = 81;
        double largeurBase = 176;
        double hauteurBase = 41;

        // Dessin de la tete
        contexte.drawImage(IMAGE, x + LARGEUR / 2 - largeurTete / 2, y + HAUTEUR - hauteurBase - hauteurTete);
        // Dessin de l'oval
        contexte.setFill(Color.rgb(255, 255, 0, 0.6));
        contexte.fillOval(x, y, LARGEUR, HAUTEUR);
        // Dessin de la base
        contexte.drawImage(imageBase, x + LARGEUR / 2 - largeurBase / 2, y + HAUTEUR - hauteurBase);

    }

    @Override
    public void updatePosition(double deltatemps) {
        super.updatePosition(deltatemps);
    }
}
