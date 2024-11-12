package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgentitesabsorbable;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.Utilitaire;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Vache extends EntiteAbsorbable {
    private double vitesseOriginale = v[0];
    private final Image[] vachesImages = {new Image("vache.png"), new Image("vache-droite.png")};

    public Vache() {
        super(new double[]{genererVitesseVache(), 0}, 136, 134,
                new Image("vache.png"), new double[]{Utilitaire.genererDouble(0, Partie.DIMENSIONS[0])
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
        if (!estEnEnlevement) image = v[0] < 0 ? vachesImages[0] : vachesImages[1];
    }

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

    @Override
    protected void gererAbsorptionParVaisseau(Vaisseau vaisseau) {
        vaisseau.ajouterPoint();
    }
}
