package ca.qc.bdeb.sim.tp2invasion_agricole.pkgvisuel.pkgdecor;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgvisuel.ObjetVisuel;
import javafx.scene.canvas.GraphicsContext;

public abstract class ObjetDecor extends ObjetVisuel {
    public ObjetDecor(double x, double y, double l) {
        super(x, y, l);
    }

    /**
     * Méthode abstraite pour dessiner l'objet décoratif sur le canvas.
     *
     * @param contexte Le contexte graphique où l'objet sera dessiné.
     * @param camera   La caméra utilisée pour ajuster la position de l'objet en fonction du déplacement dans le jeu.
     */
    public abstract void dessiner(GraphicsContext contexte, Camera camera);
}
