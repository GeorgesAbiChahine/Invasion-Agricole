package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires.Input;
import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgdecor.Decor;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.*;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgentitesabsorbable.EntiteAbsorbable;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgentitesabsorbable.Fermier;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgentitesabsorbable.Vache;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles.Projectile;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkginterface.Interface;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;


public class Partie {
    public static final double[] DIMENSIONS = {Main.LARGEUR * 4, Main.HAUTEUR};
    private int niveauActuel = 1;
    private final Decor ARRIERE_PLAN = new Decor();
    private Vaisseau vaisseau = new Vaisseau();
    private final ArrayList<Entite> ENTITES = new ArrayList<>();
    private final Camera CAMERA = new Camera();
    private static boolean debogage = false;

    private Interface anInterface = new Interface();

    public void genererPartie() {
        genererEntites();
    }

    public void dessiner(GraphicsContext contexte) {
        ARRIERE_PLAN.dessiner(contexte, CAMERA);
        vaisseau.dessiner(contexte, CAMERA);
        dessinerEntites(contexte);
        anInterface.dessiner(contexte, vaisseau, ENTITES);
    }

    public void update(double deltaTemps) {
        gererDebug();
        vaisseau.update(deltaTemps);
        CAMERA.update(vaisseau);
        updateEntites(deltaTemps);
        gererCollisions();
    }

    private void genererEntites() {
        ENTITES.addAll(genererFermiers());
        ENTITES.addAll(genererVaches());
    }

    private ArrayList<Fermier> genererFermiers() {
        ArrayList<Fermier> fermiers = new ArrayList<>();
        int nbFermiers = 3 + 3 * (niveauActuel - 1);
        for (int i = 0; i < nbFermiers; i++) {
            fermiers.add(new Fermier());
        }
        return fermiers;
    }

    private ArrayList<Vache> genererVaches() {
        ArrayList<Vache> vaches = new ArrayList<>();
        int nbVaches = 5 + 2 * niveauActuel;
        for (int i = 0; i < nbVaches; i++) {
            vaches.add(new Vache());
        }
        return vaches;
    }

    private void tenterGenererProjectiles(Fermier fermier, Double deltaTemps) {
        Projectile projectile = fermier.tenterCreerProjectile(CAMERA, vaisseau, deltaTemps);
        if (projectile != null)
            ENTITES.add(projectile);
    }

    private void updateEntites(double deltaTemps) {
        for (int i = 0; i < ENTITES.size(); i++) {
            // Update tout
            ENTITES.get(i).update(deltaTemps);

            // Update les projectiles
            if (ENTITES.get(i) instanceof Fermier fermier) {
                tenterGenererProjectiles(fermier, deltaTemps);
            }

            // Update les éléments à supprimer
            if (ENTITES.get(i).isASupprimer()) {
                ENTITES.remove(i);
                i--;
            }
        }
    }

    private void dessinerEntites(GraphicsContext contexte) {
        for (var entite : ENTITES) {
            entite.dessiner(contexte, CAMERA);
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
        }
        return false;
    }

    public static boolean getModeDebogage() {
        return debogage;
    }

    private void gererCollisions() {
        for (var entite : ENTITES) {
            if (!vaisseau.isEstMort()) {
                if (entite instanceof EntiteAbsorbable entiteAbsorbable) entiteAbsorbable.gererEnlevement(vaisseau);
                else if (entite instanceof Projectile projectile) projectile.gererAttaqueSurVaisseau(vaisseau);
            }
        }
    }
}
