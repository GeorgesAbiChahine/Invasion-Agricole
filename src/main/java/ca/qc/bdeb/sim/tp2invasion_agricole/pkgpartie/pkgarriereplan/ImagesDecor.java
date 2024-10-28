package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgarriereplan;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class ImagesDecor extends ObjetsDecor {
    protected Image image;

    public ImagesDecor(double x, double y, double l, double h, Image image) {
        super(x, y, l, h);
        this.image = image;
    }

    @Override
    public void dessiner(GraphicsContext contexte) {
        contexte.drawImage(image, x, y, LARGEUR, HAUTEUR);
    }
}
