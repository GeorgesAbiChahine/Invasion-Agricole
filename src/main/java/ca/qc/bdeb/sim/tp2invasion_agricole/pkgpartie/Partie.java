package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie;

import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgarriereplan.Decor;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Vaisseau;
import javafx.scene.canvas.GraphicsContext;


public class Partie {
        public static final double LARGEUR = Main.LARGEUR * 4;
        public static final double HAUTEUR = Main.HAUTEUR;
        private Decor arrierePlan = new Decor();
        private Vaisseau vaisseau = new Vaisseau();

        public void dessiner(GraphicsContext contexte) {
                arrierePlan.dessiner(contexte);
                vaisseau.dessiner(contexte);
        }
}
