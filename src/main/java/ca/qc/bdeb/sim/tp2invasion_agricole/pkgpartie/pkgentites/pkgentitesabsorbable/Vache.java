package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgentitesabsorbable;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires.Utilitaire;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * La classe {@code Vache} représente une vache dans le jeu qui se déplace horizontalement dans le jeu et peut être
 * absorbée par un vaisseau. Lorsqu'elle est absorbée, elle ajoute des points au joueur.
 */
public class Vache extends EntiteAbsorbable {

    /**
     * La vitesse originale de la vache, utilisée pour restaurer sa vitesse après un échec enlèvement.
     */
    private double vitesseOriginale = v[0];
    private static final Image[] VACHE_IMAGES = {new Image("vache.png"), new Image("vache-droite.png")};


    /**
     * Constructeur de la classe {@code Vache}.
     * Initialise une vache avec une position et une vitesse aléatoires sur l'axe X.
     * La vache commence par défaut sur le sol.
     */
    public Vache() {
        super(new double[]{genererVitesseVache(), 0}, 136, 134,
                VACHE_IMAGES[0], new double[]{Utilitaire.genererDouble(0, Partie.DIMENSIONS[0])
                        , Partie.DIMENSIONS[1] - 136});
    }

    private static double genererVitesseVache() {
        return (Utilitaire.genererBoolean() ? -1 : 1) * Utilitaire.genererDouble(10, 50);
    }

    @Override
    public void dessiner(GraphicsContext contexte, Camera camera) {
        super.dessiner(contexte, camera);
        changerImageVache();
    }

    private void changerImageVache() {
        if (!estEnEnlevement && v[0] != 0) image = v[0] < 0 ? VACHE_IMAGES[0] : VACHE_IMAGES[1];
    }

    /**
     * Met à jour la position et le comportement de la vache en fonction du temps écoulé.
     * Si la vache est en cours d'enlèvement, sa vitesse est mise à zéro.
     * Si elle atteint les bords de l'écran, elle inverse sa direction.
     *
     * @param deltatemps Le temps écoulé depuis la dernière mise à jour, en secondes.
     */
    @Override
    public void update(double deltatemps) {
        super.update(deltatemps);
        if (estEnEnlevement) {
            v[0] = 0;
        } else v[0] = vitesseOriginale;
        if (pos[0] == Partie.DIMENSIONS[0] - DIMENSIONS[0] || pos[0] == 0) {
            v[0] *= -1;
            vitesseOriginale = v[0];
        }
    }

    /**
     * Gère l'absorption de la vache par le vaisseau.
     * Lorsqu'une vache est absorbée, elle ajoute un point au score du joueur.
     *
     * @param vaisseau Le vaisseau qui absorbe la vache.
     */
    @Override
    protected void gererAbsorptionParVaisseau(Vaisseau vaisseau) {
        vaisseau.ajouterPoint();
    }
}
