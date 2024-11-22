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


/**
 * La classe {@code Partie} représente une partie du jeu "Invasion Agricole".
 * Cette classe gère le déroulement d'un niveau, y compris la génération des entités,
 * les mises à jour des états, les collisions, et l'affichage graphique.
 * C'est le modèle dans le MVC.
 */
public class Partie {
    // Dimensions du niveau, à ne pas confondre avec les dimensions du Canvas
    public static final double[] DIMENSIONS = {Main.LARGEUR * 4, Main.HAUTEUR};
    private final Vaisseau VAISSEAU = new Vaisseau();
    private final ArrayList<Entite> VACHES = new ArrayList<>();
    private final ArrayList<Entite> FERMIERS = new ArrayList<>();
    private final ArrayList<Entite> PROJECTILES = new ArrayList<>();
    // ArrayList contenant les ArrayList de toutes les entités, afin de centraliser toutes les entités et faciliter leurs
    // mises à jour et dessin
    private final ArrayList<ArrayList<Entite>> ENTITES = new ArrayList<>();

    // Indique si le mode déboogage est activé
    private static boolean enDebogage = false;
    private int niveauActuel = 1;
    // Variable utilisée pour gérer l'animation de fin de niveau, dans laquelle un écran noir s'opacifie de plus en plus
    private double opaciteFinNiveau = 0;
    private Decor arrierePlan = new Decor();
    private Camera camera = new Camera();
    private Interface interfaceDuNiveau = new Interface();
    private boolean finNiveau = false;

    public static boolean getModeDebogage() {
        return enDebogage;
    }

    /**
     * Initialise les éléments d'une nouvelle partie ou d'un nouveau niveau.
     * Réinitialise les entités, le vaisseau, la caméra, et l'interface.
     */
    public void genererPartie() {
        opaciteFinNiveau = 0;
        finNiveau = false;
        arrierePlan = new Decor();
        VAISSEAU.reinitialiser();
        camera = new Camera();
        interfaceDuNiveau = new Interface();
        genererEntites();
    }

    /**
     * Dessine l'arrière-plan, le vaisseau, les entités, et l'interface sur le contexte graphique.
     * Si une animation de fin de niveau est en cours, elle est également dessinée.
     *
     * @param contexte Le contexte graphique où le décor sera dessiné.
     */
    public void dessiner(GraphicsContext contexte) {
        arrierePlan.dessiner(contexte, camera);
        VAISSEAU.dessiner(contexte, camera);
        dessinerEntites(contexte);
        interfaceDuNiveau.dessiner(contexte, VAISSEAU, VACHES);
        gererAnimationFinNiveau(contexte);
    }

    /**
     * Met à jour l'état du jeu en fonction du temps écoulé.
     * Gère le débogage, les mouvements du vaisseau, les entités,
     * les collisions et la fin de niveau.
     *
     * @param deltaTemps Le temps écoulé depuis la dernière mise à jour, en secondes.
     */
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

    /**
     * Tente de générer un projectile tiré par un fermier.
     * Si le projectile est créé, il est ajouté à la liste des projectiles.
     *
     * @param fermier    Le fermier tentant de créer un projectile.
     * @param deltaTemps Le temps écoulé depuis la dernière mise à jour.
     */
    private void tenterGenererProjectiles(Fermier fermier, Double deltaTemps) {
        Projectile projectile = fermier.tenterCreerProjectile(camera, VAISSEAU, deltaTemps);
        if (projectile != null) PROJECTILES.add(projectile);
    }

    /**
     * Met à jour l'état de toutes les entités du jeu.
     * Supprime les entités marquées pour suppression.
     *
     * @param deltaTemps Le temps écoulé depuis la dernière mise à jour.
     */
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

    /**
     * Dessine toutes les entités présentes dans le niveau sur le contexte graphique.
     *
     * @param contexte Le contexte graphique sur lequel dessiner.
     */
    private void dessinerEntites(GraphicsContext contexte) {
        for (var sousListeEntite : ENTITES) {
            for (var entite : sousListeEntite) entite.dessiner(contexte, camera);
        }
    }

    /**
     * Gère l'activation ou la désactivation du mode débogage.
     * Le mode débogage peut être activé/désactivé en appuyant sur la touche "D".
     */
    private void gererDebug() {
        if (Input.isOneTimeKeyPressed(KeyCode.D)) enDebogage = !enDebogage;
    }

    /**
     * Vérifie si la partie est terminée, c'est-à-dire si le vaisseau est mort et sorti de l'écran.
     *
     * @return {@code true} si la partie est terminée, {@code false} sinon.
     */
    public boolean getPartieEstTermine() {
        return VAISSEAU.isEstMortEtSortiDeLEcran();
    }

    /**
     * Gère les collisions entre le vaisseau et les différentes entités.
     * Les entités absorbables peuvent être enlevées par le vaisseau,
     * tandis que les projectiles peuvent infliger des dégâts au vaisseau.
     */
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

    /**
     * Gère la transition de fin de niveau.
     * Augmente l'opacité de l'animation de fin de niveau jusqu'à 1, et déclenche la génération
     * d'une nouvelle partie lorsque l'animation est terminée.
     *
     * @param deltaTemps Le temps écoulé depuis la dernière mise à jour, en secondes.
     * @return {@code true} si la fin de niveau est en cours, {@code false} sinon.
     */
    private boolean gererFinNiveau(double deltaTemps) {
        final double tempsAnimationFin = 2.5;
        if (finNiveau) {
            opaciteFinNiveau += (1 / tempsAnimationFin) * deltaTemps;
            opaciteFinNiveau = Math.min(1, opaciteFinNiveau);
            return true;
        } else return false;
    }

    /**
     * Gère l'animation visuelle de la fin de niveau.
     * Affiche un écran de transition avec un texte indiquant que le niveau a été complété.
     * Une fois l'animation terminée, un nouveau niveau est généré.
     *
     * @param contexte Le contexte graphique sur lequel dessiner l'animation.
     */
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
