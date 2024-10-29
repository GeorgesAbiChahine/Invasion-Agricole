package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie;

import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgarriereplan.Decor;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Fermier;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Vache;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Vaisseau;
import javafx.scene.canvas.GraphicsContext;


public class Partie {
        public static final double LARGEUR = Main.LARGEUR * 4;
        public static final double HAUTEUR = Main.HAUTEUR;
        private Decor arrierePlan = new Decor();
        private Vaisseau vaisseau = new Vaisseau();
        private Fermier fermier1 = new Fermier();
        private Vache vache = new Vache();

        public void dessiner(GraphicsContext contexte, double deltaTemps) {
vaisseau.updatePosition(deltaTemps);
fermier1.updatePosition(deltaTemps);
vache.updatePosition(deltaTemps);
                arrierePlan.dessiner(contexte);
                vaisseau.dessiner(contexte);
                fermier1.dessiner(contexte);
                vache.dessiner(contexte);
        }
}
