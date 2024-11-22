package ca.qc.bdeb.sim.tp2invasion_agricole.pkgvisuel.pkginterface;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Entite;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgvisuel.ObjetVisuel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

/**
 * La classe {@code MiniCarte} représente une mini-carte affichée dans l'interface utilisateur.
 * Elle permet de visualiser la position des vaches et du vaisseau sur le terrain.
 */
public class MiniCarte extends ObjetVisuel {

    /**
     * Constructeur de la classe {@code MiniCarte}.
     *
     * @param x       La position X de la mini-carte.
     * @param y       La position Y de la mini-carte.
     * @param LARGEUR La largeur de la mini-carte.
     */
    public MiniCarte(double x, double y, double LARGEUR) {
        super(x, y, LARGEUR);
    }

    /**
     * Dessine la mini-carte sur le canvas.
     * Cette méthode trace une ligne blanche pour représenter l'axe principal de la carte
     * et affiche des points correspondant aux positions des vaches et du vaisseau.
     * <p>
     * - Les vaches sont représentées par des cercles blancs.
     * <p>
     * - Le vaisseau est représenté par un cercle vert.
     *
     * @param contexte Le contexte graphique où la mini-carte sera dessinée.
     * @param vaisseau Le vaisseau dont la position est affichée sur la mini-carte.
     * @param vaches  La liste des vaches à inclure dans la mini-carte.
     */
    public void dessiner(GraphicsContext contexte, Vaisseau vaisseau, ArrayList<Entite> vaches) {
        // Dessine l'axe principal de la mini-carte.
        contexte.setStroke(Color.WHITE);
        contexte.strokeLine(x, y, LARGEUR, y);

        // Dessine les vaches sur la mini-carte.
        contexte.setFill(Color.WHITE);
        for (var vache : vaches) {
                contexte.fillOval(LARGEUR * vache.getPos()[0] / Partie.DIMENSIONS[0], y - 5, 10, 10);
        }

        // Dessine le vaisseau sur la mini-carte.
        contexte.setFill(Paint.valueOf("#08ff2c"));
        contexte.fillOval(LARGEUR * vaisseau.getPos()[0] / Partie.DIMENSIONS[0], y - 5, 10, 10);

    }
}
