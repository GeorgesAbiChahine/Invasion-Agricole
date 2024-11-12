package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkginterface;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Entite;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgentitesabsorbable.Vache;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class MiniCarte {
    private double X = 5;
    private double Y = 60;
    private double LARGEUR = 200;

    public void dessiner(GraphicsContext contexte,Vaisseau vaisseau, ArrayList<Entite> entites){
        contexte.setStroke(Color.WHITE);
        contexte.strokeLine(X,Y,LARGEUR,Y);

        contexte.setFill(Color.WHITE);
        for (var entite : entites) {
            if (entite instanceof Vache vache) {
                contexte.fillOval(LARGEUR * vache.getPos()[0] / Partie.DIMENSIONS[0], Y - 5, 10, 10);
            }
        }
        contexte.setFill(Paint.valueOf("#08ff2c"));
        contexte.fillOval(LARGEUR * vaisseau.getPos()[0] / Partie.DIMENSIONS[0], Y - 5, 10, 10);

    }
}
