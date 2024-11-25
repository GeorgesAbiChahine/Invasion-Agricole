package ca.qc.bdeb.sim.tp2invasion_agricole.pkgvisuel.pkginterface;


import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgvisuel.ObjetVisuel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * La classe {@code Batterie} représente l'affichage de la barre de batterie du vaisseau
 * dans l'interface utilisateur. Cette barre indique visuellement l'état de charge
 * du rayon d'enlèvement du vaisseau.
 */
public class Batterie extends ObjetVisuel {
    private final Image IMAGE = new Image("batterie.png");

    /**
     * Constructeur de la classe {@code Batterie}.
     *
     * @param x       La position X de la barre de batterie.
     * @param y       La position Y de la barre de batterie.
     * @param LARGEUR La largeur de la barre de batterie.
     */
    public Batterie(double x, double y, double LARGEUR) {
        super(x, y, LARGEUR);
    }

    /**
     * Dessine la barre de batterie sur le canvas. <p>
     * La barre change de couleur en fonction de l'état de charge : <p>
     * - Jaune : charge normale (entre 20% exclusivement et 100% inclusivement). <p>
     * - Orange : charge basse (plus petit ou égale à 20%). <p>
     * - Rouge : le rayon d'enlèvement est désactivé.
     *
     * @param contexte Le contexte graphique où la barre sera dessinée.
     * @param vaisseau Le vaisseau dont l'état de charge est affiché.
     */
    public void dessiner(GraphicsContext contexte, Vaisseau vaisseau) {
        final double HAUTEUR = 45;

        // Dessine le contour de la barre.
        contexte.setStroke(Color.YELLOW);
        contexte.strokeRect(x, y, LARGEUR, HAUTEUR);

        // Change la couleur de la barre en fonction de la charge.
        if (vaisseau.getRayonEnlevement().getCharge() >= 0.2) contexte.setFill(Color.YELLOW);
        else contexte.setFill(Color.ORANGE);
        if (!vaisseau.getRayonEnlevement().isPeutEnlever()) contexte.setFill(Color.RED);

        // Dessine la barre de batterie.
        contexte.fillRect(x, y, LARGEUR * vaisseau.getRayonEnlevement().getCharge(), HAUTEUR);

        // Dessine l'icône de la batterie.
        contexte.drawImage(IMAGE, x + 5, y + 5);
    }
}
