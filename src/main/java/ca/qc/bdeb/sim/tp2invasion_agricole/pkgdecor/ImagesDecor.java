package ca.qc.bdeb.sim.tp2invasion_agricole.pkgdecor;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImagesDecor extends ObjetsDecor {
    protected Image image;

    public ImagesDecor(double x, double y, double l, double h, Image image) {
        super(x, y, l, h);
        this.image = image;
    }

    @Override
    public void dessiner(GraphicsContext contexte, Camera camera) {
        contexte.drawImage(image, camera.getXEcran(x), y, LARGEUR, HAUTEUR);
    }
}
