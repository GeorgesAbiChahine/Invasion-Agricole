package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import javafx.scene.image.Image;

public class PotDeFleur extends Projectile {
    public PotDeFleur(double[] pos, Vaisseau vaisseau) {
        super(new double[]{0,1000},calculerV(pos,new double[]{vaisseau.getPosCentre()[0],
                        vaisseau.getPosCentre()[1] - 400},1000), 31, 61,
                new Image("pot-de-fleurs.png"),new double[]{pos[0],pos[1]},0);
    }


}
