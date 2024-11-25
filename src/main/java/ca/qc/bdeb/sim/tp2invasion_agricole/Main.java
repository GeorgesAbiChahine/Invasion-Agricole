package ca.qc.bdeb.sim.tp2invasion_agricole;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires.Input;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * @author <b style="font-family:'Georgia'; color: #FFDF00; ">Valère Bardon (6231049)</b>
 * @author <b style="font-family:'Georgia'; color: #FFDF00; ">Georges Abi Chahine (2357505)</b>
 * <p>
 * Date : 20241121
 * <p>
 * Ce travail pratique consiste à programmer un jeu nommé <b>"Invasion Agricole"</b>. Dans le jeu,
 * on controle un vaisseau spatial qui a pour mission de capturer des vaches
 * tout en évitant les projectiles lancés par des fermiers qui protègent leur bétail.
 * Le joueur doit gérer les déplacements du vaisseau tout en optimisant l’utilisation du rayon
 * d’enlèvement, qui se décharge progressivement et nécessite un temps de recharge complet
 * lorsqu’il est épuisé. À chaque deux fermiers capturés, une vie est regagnée, jusqu’à un
 * maximum de 4 vies.
 * <p>
 * Le programme est composé de trois scènes principales :
 * <ul>
 *     <li>L’<b>écran d’accueil</b>, offrant les options de démarrer le jeu ou d’obtenir des informations sur le TP.</li>
 *     <li>La <b>scène de jeu</b>, où le jeu se déroule.</li>
 *     <li>L’<b>écran d’information</b>, qui présente les crédits du TP.</li>
 * </ul>
 * <p>
 * La classe {@code Main} sert d'interface qui sert à afficher les différentes scène du jeu (intro,infos, et le jeu).
 * C'est la vue du MVC.
 */

public class Main extends Application {

    public final static int LARGEUR = 900;
    public final static int HAUTEUR = 520;

    private Partie partie;
    private Stage stage;
    private AnimationTimer timer;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        creerSceneIntro();

        stage.setResizable(false);
        stage.setTitle("Invasion Agricole");
        stage.getIcons().add(new Image("icone.png"));
        stage.show();
    }

    /**
     * Crée la scène d'introduction avec des options pour commencer une partie ou afficher les informations sur le TP.
     */
    private void creerSceneIntro() {
        var root = new StackPane();
        var scene = new Scene(root, LARGEUR, HAUTEUR);

        var imageIntro = new ImageView(new Image("intro.png"));
        root.getChildren().add(imageIntro);

        var vBoxBoutons = new VBox(20);
        vBoxBoutons.setAlignment(Pos.CENTER);

        var jouer = creerBouton("Jouer!", 70, event -> creerSceneJeu());
        var infos = creerBouton("Infos", 60, event -> creerSceneInfos());

        vBoxBoutons.getChildren().addAll(jouer, infos);
        root.getChildren().add(vBoxBoutons);

        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) Platform.exit();
        });
        stage.setScene(scene);
    }

    /**
     * Crée un bouton personnalisé.
     *
     * @param texte   Le texte affiché sur le bouton.
     * @param largeur La largeur du bouton.
     * @param action  L'action déclenchée lorsque le bouton est cliqué.
     * @return Un bouton configuré.
     */
    private Button creerBouton(String texte, int largeur, EventHandler<ActionEvent> action) {
        Button bouton = new Button(texte);
        bouton.setPrefSize(largeur, 30);
        bouton.setOnAction(action);
        return bouton;
    }

    /**
     * Crée la scène principale du jeu et initialise la partie.
     */
    private void creerSceneJeu() {
        partie = new Partie();
        var root = new Pane();
        var scene = new Scene(root, LARGEUR, HAUTEUR);

        var canvas = new Canvas(LARGEUR, HAUTEUR);
        canvas.setFocusTraversable(true);
        root.getChildren().add(canvas);

        GraphicsContext contexte = canvas.getGraphicsContext2D();

        initialiserBoucle(contexte);
        gererEvenements(root);

        stage.setScene(scene);
    }

    /**
     * Gère les événements clavier pour la scène de jeu.
     *
     * @param root Le conteneur principal de la scène.
     */
    private void gererEvenements(Pane root) {
        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                creerSceneIntro();
                timer.stop();
            } else Input.setKeyPressed(e.getCode(), true);
        });

        root.setOnKeyReleased(e -> Input.setKeyPressed(e.getCode(), false));
    }


    /**
     * Crée la scène d'informations avec des détails sur le jeu et ses créateurs.
     */
    private void creerSceneInfos() {
        var root = new StackPane();
        var scene = new Scene(root, LARGEUR, HAUTEUR);

        // Gérer le fond de l'écran
        root.setBackground(new Background(new BackgroundFill(Color.web("#1a1a1a"), null, null)));

        HBox ligneImages = creerLigneImages();

        VBox vBoxTextesEtRetour = creerVBoxTextes();

        // Vbox final
        VBox vBoxTout = new VBox(20);
        vBoxTout.setAlignment(Pos.TOP_CENTER);
        vBoxTout.getChildren().addAll(ligneImages, vBoxTextesEtRetour);

        root.getChildren().add(vBoxTout);

        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) creerSceneIntro();
        });
        stage.setScene(scene);
    }

    /**
     * Initialise la boucle principale d'animation.
     *
     * @param contexte Le contexte graphique utilisé pour dessiner la scène.
     */
    private void initialiserBoucle(GraphicsContext contexte) {
        partie.genererPartie();
        timer = new AnimationTimer() {
            long dernierTemps = System.nanoTime();

            @Override
            public void handle(long now) {
                double deltaTime = (now - dernierTemps) * 1e-9;
                partie.update(deltaTime);
                partie.dessiner(contexte);
                dernierTemps = now;
                if (partie.getPartieEstTermine()) {
                    creerSceneIntro();
                    timer.stop();
                }
            }
        };
        timer.start();
    }


    /**
     * Crée une ligne d'images alternant entre une vache et un alien pour la scène d'informations.
     * Les images sont ajoutées jusqu'à remplir la largeur de la scène.
     *
     * @return Un conteneur horizontal contenant les images alignées au centre.
     */
    private HBox creerLigneImages() {
        HBox ligneImages = new HBox();
        ligneImages.setAlignment(Pos.CENTER);

        double x = 0;
        int index = 0;
        Image imageVache = new Image("mini-vache.png");
        Image imageAlien = new Image("icone.png");

        while (x < LARGEUR) {
            var imageAMettre = (index % 2 == 0) ? imageVache : imageAlien;
            double largeurImage = imageAMettre.getWidth();
            if (x + largeurImage > LARGEUR) break;
            ligneImages.getChildren().add(new ImageView(imageAMettre));
            x += largeurImage;
            index++;
        }

        return ligneImages;
    }

    /**
     * Crée une boîte verticale contenant le titre, les auteurs, une description, et un bouton "Retour".
     * Cette boîte est utilisée dans la scène d'informations pour afficher les détails du projet.
     *
     * @return Une boîte verticale avec les textes et le bouton alignés au centre.
     */
    private VBox creerVBoxTextes() {
        VBox vBoxTextesEtRetour = new VBox(20);
        vBoxTextesEtRetour.setAlignment(Pos.CENTER);

        Text titre = creerTextePourInfos("Invasion Agricole", 50);

        HBox auteur1 = creerHBoxAuteur("Par", "Georges Abi Chahine");
        HBox auteur2 = creerHBoxAuteur("Et", "Valère Bardon");

        Text description = creerTextePourInfos("Travail remis à Nicolas Hurtubise et Salwa Mbarek. " +
                "Graphismes adaptés de https://game-icons.net et de https://openclipart.org/. " +
                "Développé dans le cadre du cours " +
                "420-203-RE - Développement de programmes dans un environnement graphique, " +
                "au Collège de Bois-de-Boulogne.", 18);
        TextFlow descriptionFlow = new TextFlow(description);
        //On a trouvé - 150 par essai erreur en comparant avec l'image de l'énoncé du TP.
        descriptionFlow.setMaxWidth(LARGEUR - 150);
        descriptionFlow.setTextAlignment(TextAlignment.JUSTIFY);

        Button retourButton = new Button("Retour");
        retourButton.setOnAction(event -> creerSceneIntro());

        vBoxTextesEtRetour.getChildren().addAll(titre, auteur1, auteur2, descriptionFlow, retourButton);
        return vBoxTextesEtRetour;
    }


    /**
     * Crée un objet texte stylisé avec une police Arial et une couleur blanche.
     * Cette méthode est utilisée pour créer les différents textes dans la scène d'informations.
     *
     * @param texte  Le contenu textuel.
     * @param taille La taille de la police.
     * @return Un objet {@code Text} configuré.
     */
    private Text creerTextePourInfos(String texte, double taille) {
        Text objetTexte = new Text(texte);
        objetTexte.setFont(Font.font("Arial", taille));
        objetTexte.setFill(Color.WHITE);
        return objetTexte;
    }

    /**
     * Crée un conteneur horizontal pour afficher un préfixe (comme "Par") suivi d'un nom d'auteur.
     *
     * @param prefixe Le préfixe textuel (par exemple, "Par" ou "Et").
     * @param auteur  Le nom de l'auteur.
     * @return Un conteneur horizontal contenant le préfixe et l'auteur.
     */
    private HBox creerHBoxAuteur(String prefixe, String auteur) {
        Text textePrefixe = creerTextePourInfos(prefixe, 20);
        Text texteAuteur = creerTextePourInfos(auteur, 35);
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(textePrefixe, texteAuteur);
        return hBox;
    }

    public static void main(String[] args) {
        launch();
    }
}