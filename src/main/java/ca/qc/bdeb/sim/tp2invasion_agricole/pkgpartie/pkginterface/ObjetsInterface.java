package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkginterface;

/**
 * La classe abstraite {@code ObjetsInterface} représente un élément générique
 * de l'interface utilisateur dans le jeu. Les objets dérivés de cette classe
 * partagent des propriétés communes telles que leur position et leur largeur.
 */
public abstract class ObjetsInterface {
    protected final double X;
    protected final double Y;
    protected final  double LARGEUR;

    /**
     * Constructeur de la classe {@code ObjetsInterface}.
     *
     * @param x       La position X de l'objet.
     * @param y       La position Y de l'objet.
     * @param LARGEUR La largeur de l'objet.
     */
    public ObjetsInterface(double x, double y, double LARGEUR) {
        X = x;
        Y = y;
        this.LARGEUR = LARGEUR;
    }
}
