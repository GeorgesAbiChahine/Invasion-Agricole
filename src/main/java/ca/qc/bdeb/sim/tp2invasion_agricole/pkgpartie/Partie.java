package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie;

import ca.qc.bdeb.sim.tp2invasion_agricole.Camera;
import ca.qc.bdeb.sim.tp2invasion_agricole.Input;
import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgDecor.Decor;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.*;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles.Projectile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;


public class Partie {
    public static final double[] DIMENSIONS = {Main.LARGEUR * 4, Main.HAUTEUR};
    private int niveauActuel = 1;
    private final Decor ARRIERE_PLAN = new Decor();
    private final Vaisseau VAISSEAU = new Vaisseau();
    private final ArrayList<Entite> ENTITES = new ArrayList<>();
    private final Camera CAMERA = new Camera();
    private static boolean debogage = false;

    public void genererPartie(){
        genererEntites();
    }

    public void dessiner(GraphicsContext contexte) {
        ARRIERE_PLAN.dessiner(contexte,CAMERA);
        VAISSEAU.dessiner(contexte, CAMERA);
        dessinerEntites(contexte);
    }
    public void update(double deltaTemps){
        gererDebug();
        VAISSEAU.update(deltaTemps);
        updateEntites(deltaTemps);
        CAMERA.update(VAISSEAU);
    }

    private void genererEntites(){
        ENTITES.addAll(genererFermiers());
        ENTITES.addAll(genererVaches());
    }

    private ArrayList<Fermier> genererFermiers(){
        ArrayList<Fermier> fermiers = new ArrayList<>();
        int nbFermiers = 3 + 3 * (niveauActuel - 1);
        for (int i = 0; i < nbFermiers; i++) {
            fermiers.add(new Fermier());
        }
        return fermiers;
    }
    private ArrayList<Vache> genererVaches(){
        ArrayList<Vache> vaches = new ArrayList<>();
       int nbVaches = 5 + 2 * niveauActuel;
        for (int i = 0; i < nbVaches; i++) {
            vaches.add(new Vache());
        }
        return vaches;
    }
    private void tenterGenererProjectiles(Fermier fermier, Double deltaTemps){
        Projectile projectile = fermier.tenterCreerProjectile(CAMERA, VAISSEAU,deltaTemps);
        if (projectile != null)
            ENTITES.add(projectile);
    }

    private void updateEntites(double deltaTemps){
        for (int i = 0; i < ENTITES.size(); i++) {
            // Update tout
            ENTITES.get(i).update(deltaTemps);

            // Update les projectiles
            if (ENTITES.get(i) instanceof Fermier fermier) {
                tenterGenererProjectiles(fermier,deltaTemps);
            }

            // Update les éléments à supprimer
            if (ENTITES.get(i).isASupprimer()) {
                ENTITES.remove(i);
                i--;
            }
        }
    }
    private void dessinerEntites(GraphicsContext contexte){
        for (var entite : ENTITES) {
            entite.dessiner(contexte,CAMERA);
        }
    }
    private void gererDebug() {
        if (gererTouchesDebug()) {
            debogage = !debogage;
        }
    }

    private boolean gererTouchesDebug() {
        if (Input.isKeyPressed(KeyCode.D)) {
            Input.setKeyPressed(KeyCode.D, false);
            return true;
        } return false;
    }

    public static boolean getModeDebogage() {
        return debogage;
    }

    private void gererCollisions(){
        for (int i = 0; i < ENTITES.size(); i++) {
            if (ENTITES.get(i) instanceof EntiteAbsorbable entiteAbsorbable){
                gererCollisionsRayonEnlevement(entiteAbsorbable);
            }
        }
    }
    private void gererCollisionsRayonEnlevement(EntiteAbsorbable entiteAbsorbable){

    }
}
