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
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;


public class Partie {
    public static final double[] DIMENSIONS = {Main.LARGEUR * 4, Main.HAUTEUR};
    private int niveauActuel = 1;
    private int nombreTotalVaches = 0;
    private double opaciteFinNiveau = 0;
    private Decor arrierePlan = new Decor();
    private Vaisseau vaisseau = new Vaisseau();
    private final ArrayList<Entite> ENTITES = new ArrayList<>();
    private Camera camera = new Camera();
    private static boolean enDebogage = false;
    private Interface interfaceDuNiveau = new Interface();
    private boolean finNiveau = false;

    public void genererPartie() {
        opaciteFinNiveau = 0;
        finNiveau = false;
        arrierePlan = new Decor();
        vaisseau.reinitialiserPosition();
        ENTITES.clear();
        camera = new Camera();
        interfaceDuNiveau = new Interface();
        genererEntites();
    }

    public void dessiner(GraphicsContext contexte) {
        arrierePlan.dessiner(contexte, camera);
        vaisseau.dessiner(contexte, camera);
        dessinerEntites(contexte);
        interfaceDuNiveau.dessiner(contexte, vaisseau, ENTITES);
        gererAnimationFinNiveau(contexte);
    }

    // TODO ONE TIME KEY PRESS
    public void update(double deltaTemps) {
        if (Input.isKeyPressed(KeyCode.I)) {
            Input.setKeyPressed(KeyCode.I, false);
            finNiveau = true;
        }

        if (vaisseau.getNombrePoints() == nombreTotalVaches) finNiveau = true;

        gererDebug();
        vaisseau.update(deltaTemps);
        camera.update(vaisseau);
        updateEntites(deltaTemps);
        gererCollisions();
        gererFinNiveau(deltaTemps);
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
        nombreTotalVaches += vaches.size();
        return vaches;
    }

    private void tenterGenererProjectiles(Fermier fermier, Double deltaTemps) {
        Projectile projectile = fermier.tenterCreerProjectile(camera, vaisseau, deltaTemps);
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
            entite.dessiner(contexte, camera);
        }
    }

    private void gererDebug() {
        if (gererTouchesDebug()) {
            enDebogage = !enDebogage;
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
        return enDebogage;
    }

    public boolean getPartieEstTermine(){
        return vaisseau.isEstMortEtSortiDeLEcran();
    }

    private void gererCollisions() {
        for (var entite : ENTITES) {
            if (entite instanceof EntiteAbsorbable entiteAbsorbable) entiteAbsorbable.gererEnlevement(vaisseau);
            else if (entite instanceof Projectile projectile) projectile.gererAttaqueSurVaisseau(vaisseau);
        }
    }

    private void gererFinNiveau(double deltaTemps) {
        final double tempsAnimationFin = 2.5;
        if (finNiveau) opaciteFinNiveau += (1/ tempsAnimationFin) * deltaTemps;
        opaciteFinNiveau = Math.min(1, opaciteFinNiveau);
    }

    // TODO FREEZE LORS DE LANIMATION
    public void gererAnimationFinNiveau(GraphicsContext contexte) {
        if (finNiveau) {
            contexte.setFill(Color.rgb(0, 0, 0, opaciteFinNiveau));
            contexte.fillRect(0, 0, Main.LARGEUR, Main.HAUTEUR);
            contexte.setFill(Color.WHITE);
            contexte.setTextAlign(TextAlignment.CENTER);
            contexte.setTextBaseline(VPos.CENTER);
            contexte.fillText(
                    "Niveau " + niveauActuel + " complété",
                    Main.LARGEUR / 2,
                    Main.HAUTEUR / 2
            );
        }

        if (opaciteFinNiveau == 1) {
            niveauActuel++;
            genererPartie();
        }
    }

}
