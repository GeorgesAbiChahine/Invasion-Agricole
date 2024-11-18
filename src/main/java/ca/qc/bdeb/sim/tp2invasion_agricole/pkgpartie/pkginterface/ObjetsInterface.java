package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkginterface;

public abstract class ObjetsInterface {
    protected final double X;
    protected final double Y;
    protected final  double LARGEUR;

    public ObjetsInterface(double x, double y, double LARGEUR) {
        X = x;
        Y = y;
        this.LARGEUR = LARGEUR;
    }
}
