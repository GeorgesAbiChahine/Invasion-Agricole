package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires.Input;
import ca.qc.bdeb.sim.tp2invasion_agricole.Main;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgvisuel.pkgdecor.Decor;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.*;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgentitesabsorbable.EntiteAbsorbable;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgentitesabsorbable.Fermier;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgentitesabsorbable.Vache;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgprojectiles.Projectile;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgvisuel.pkginterface.Interface;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;


public class Partie {
    public static final double[] DIMENSIONS = {Main.LARGEUR * 4, Main.HAUTEUR};
    private final Vaisseau VAISSEAU = new Vaisseau();
    private final ArrayList<Entite> VACHES = new ArrayList<>();
    private final ArrayList<Entite> FERMIERS = new ArrayList<>();
    private final ArrayList<Entite> PROJECTILES = new ArrayList<>();
    private final ArrayList<ArrayList<Entite>> ENTITES = new ArrayList<>();

    private static boolean enDebogage = false;
    private int niveauActuel = 1;
    private double opaciteFinNiveau = 0;
    private Decor arrierePlan = new Decor();
    private Camera camera = new Camera();
    private Interface interfaceDuNiveau = new Interface();
    private boolean finNiveau = false;

    public static boolean getModeDebogage() {
        return enDebogage;
    }

    public void genererPartie() {
        opaciteFinNiveau = 0;
        finNiveau = false;
        arrierePlan = new Decor();
        VAISSEAU.reinitialiserPosition();
        camera = new Camera();
        interfaceDuNiveau = new Interface();
        genererEntites();
    }

    public void dessiner(GraphicsContext contexte) {
        arrierePlan.dessiner(contexte, camera);
        VAISSEAU.dessiner(contexte, camera);
        dessinerEntites(contexte);
        interfaceDuNiveau.dessiner(contexte, VAISSEAU, VACHES);
        gererAnimationFinNiveau(contexte);
    }

    public void update(double deltaTemps) {
        if (Input.isOneTimeKeyPressed(KeyCode.I) || !resteDesVaches()) finNiveau = true;
        if (gererFinNiveau(deltaTemps)) return;

        gererDebug();
        VAISSEAU.update(deltaTemps);
        camera.update(VAISSEAU);
        updateEntites(deltaTemps);
        gererCollisions();
    }

    private void genererEntites() {
        ENTITES.clear();
        ENTITES.add(FERMIERS);
        ENTITES.add(VACHES);
        ENTITES.add(PROJECTILES);

        genererVaches();
        genererFermiers();
    }


    private void genererFermiers() {
        FERMIERS.clear();
        int nbFermiers = 3 + 3 * (niveauActuel - 1);
        for (int i = 0; i < nbFermiers; i++) FERMIERS.add(new Fermier());
    }

    private void genererVaches() {
        VACHES.clear();
        int nbVaches = 5 + 2 * niveauActuel;
        for (int i = 0; i < nbVaches; i++) VACHES.add(new Vache());
    }

    private void tenterGenererProjectiles(Fermier fermier, Double deltaTemps) {
        Projectile projectile = fermier.tenterCreerProjectile(camera, VAISSEAU, deltaTemps);
        if (projectile != null) PROJECTILES.add(projectile);
    }

    private void updateEntites(double deltaTemps) {
        for (var sousListeEntite : ENTITES) {
            for (int i = 0; i < sousListeEntite.size(); i++) {
                // Update tout
                sousListeEntite.get(i).update(deltaTemps);

                // Update les projectiles
                if (sousListeEntite.get(i) instanceof Fermier fermier) {
                    tenterGenererProjectiles(fermier, deltaTemps);
                }

                // Update les éléments à supprimer
                if (sousListeEntite.get(i).isASupprimer()) {
                    sousListeEntite.remove(i);
                    i--;
                }
            }

        }
    }

    private void dessinerEntites(GraphicsContext contexte) {
        for (var sousListeEntite : ENTITES) {
            for (var entite : sousListeEntite) entite.dessiner(contexte, camera);
        }
    }

    private void gererDebug() {
        if (Input.isOneTimeKeyPressed(KeyCode.D)) enDebogage = !enDebogage;
    }

    public boolean getPartieEstTermine() {
        return VAISSEAU.isEstMortEtSortiDeLEcran();
    }

    private void gererCollisions() {
        for (var sousListeEntite : ENTITES) {
            for (var entite : sousListeEntite) {
                if (entite instanceof EntiteAbsorbable entiteAbsorbable) entiteAbsorbable.gererEnlevement(VAISSEAU);
                else if (entite instanceof Projectile projectile) projectile.gererAttaqueSurVaisseau(VAISSEAU);
            }
        }
    }

    private boolean resteDesVaches() {
        return (!VACHES.isEmpty());
    }

    private boolean gererFinNiveau(double deltaTemps) {
        final double tempsAnimationFin = 2.5;
        if (finNiveau) {
            opaciteFinNiveau += (1 / tempsAnimationFin) * deltaTemps;
            opaciteFinNiveau = Math.min(1, opaciteFinNiveau);
            return true;
        } else return false;
    }

    public void gererAnimationFinNiveau(GraphicsContext contexte) {
        if (finNiveau) {
            contexte.setFill(Color.rgb(0, 0, 0, opaciteFinNiveau));
            contexte.fillRect(0, 0, Main.LARGEUR, Main.HAUTEUR);
            contexte.setFill(Color.WHITE);
            contexte.setTextAlign(TextAlignment.CENTER);
            contexte.setTextBaseline(VPos.CENTER);
            contexte.fillText(
                    "Niveau " + niveauActuel + " complété",
                    (double) Main.LARGEUR / 2,
                    (double) Main.HAUTEUR / 2
            );
        }

        if (opaciteFinNiveau == 1) {
            niveauActuel++;
            genererPartie();
        }
    }

}
