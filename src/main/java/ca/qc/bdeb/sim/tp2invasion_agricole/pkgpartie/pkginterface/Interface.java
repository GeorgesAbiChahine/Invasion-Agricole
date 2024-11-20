package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkginterface;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Entite;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

/**
 * La classe {@code Interface} représente l'interface utilisateur du jeu.
 * Elle affiche des informations essentielles telles que la batterie du vaisseau,
 * une mini-carte, le nombre de points, et les vies restantes.
 */
public class Interface {

    /**
     * La batterie affichée dans l'interface, représentant la charge du rayon d'enlèvement du vaisseau.
     */
    private final Batterie BATTERIE = new Batterie(5,5,200);

    /**
     * La mini-carte affichée dans l'interface, représentant les vaches et le vaisseau visibles sur le terrain.
     */
    private final MiniCarte MINI_CARTE = new MiniCarte(5,60,200);

    /**
     * Dessine l'interface utilisateur complète sur le canvas.
     *
     * @param contexte Le contexte graphique où l'interface sera dessinée.
     * @param vaisseau Le vaisseau dont les informations (batterie, points, vies) sont affichées.
     * @param entites  La liste des entités présentes sur le terrain, utilisée pour la mini-carte.
     */
    public void dessiner(GraphicsContext contexte, Vaisseau vaisseau, ArrayList<Entite> entites) {
        BATTERIE.dessiner(contexte, vaisseau);
        MINI_CARTE.dessiner(contexte, vaisseau, entites);

        contexte.setTextAlign(TextAlignment.LEFT);
        contexte.setTextBaseline(VPos.BASELINE);

        dessinerPoints(contexte, vaisseau);

        dessinerVies(contexte, vaisseau);
    }


    /**
     * Dessine le nombre de points du joueur dans l'interface.
     * Une image représentant une mini-vache est affichée à côté du score.
     *
     * @param contexte Le contexte graphique où les points seront affichés.
     * @param vaisseau Le vaisseau dont le nombre de points est à afficher.
     */
    private void dessinerPoints(GraphicsContext contexte, Vaisseau vaisseau) {
        contexte.drawImage(new Image("mini-vache.png"), 220, 5);
        contexte.setFill(Color.WHITE);
        contexte.setFont(Font.font("Arial", 45));
        contexte.fillText(Integer.toString(vaisseau.getNombrePoints()), 275, 45);
    }


    /**
     * Dessine le nombre de vies du joueur dans l'interface.
     * Une icône représentant un alien est affichée à côté du nombre de vies.
     * Si le vaisseau est mort, le texte s'affiche en rouge.
     * Si le vaisseau est invincible, un smiley ":)" est affiché à la place.
     *
     * @param contexte Le contexte graphique où les vies seront affichées.
     * @param vaisseau Le vaisseau dont le nombre de vies est à afficher.
     */
    private void dessinerVies(GraphicsContext contexte, Vaisseau vaisseau) {
        contexte.drawImage(new Image("icone.png"), 315, 0);
        if (vaisseau.isEstMort()) contexte.setFill(Color.RED);
        if (vaisseau.isEstInvincible()) contexte.fillText(":)", 380, 45);
        else contexte.fillText(Integer.toString(vaisseau.getNombreVies()), 380, 45);
    }
}
