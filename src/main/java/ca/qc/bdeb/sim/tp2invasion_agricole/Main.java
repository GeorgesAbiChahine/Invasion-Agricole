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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

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
    private void creerSceneIntro() {
        var root = new StackPane();
        var scene = new Scene(root, LARGEUR, HAUTEUR);

        var imageIntro = new ImageView(new Image("intro.png"));
        root.getChildren().add(imageIntro);

        var vBoxBoutons = new VBox(20);
        vBoxBoutons.setAlignment(Pos.CENTER);

        var jouer = creerBouton("Jouer!", 70, event -> creerSceneJeu());
        var infos = creerBouton("Infos", 60, event -> creerSceneInfos());

        vBoxBoutons.getChildren().addAll(jouer,infos);
        root.getChildren().add(vBoxBoutons);

        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) Platform.exit();
        });
        stage.setScene(scene);
    }
    private Button creerBouton(String texte, int largeur, EventHandler<ActionEvent> action) {
        Button bouton = new Button(texte);
        bouton.setPrefSize(largeur, 30);
        bouton.setOnAction(action);
        return bouton;
    }

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
    private void gererEvenements(Pane root) {
        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                creerSceneIntro();
                timer.stop();
            }
            else Input.setKeyPressed(e.getCode(), true);
        });

        root.setOnKeyReleased(e -> Input.setKeyPressed(e.getCode(), false));
    }
    private void creerSceneInfos() {
        var root = new StackPane();
        var scene = new Scene(root, LARGEUR, HAUTEUR);

        var canvas = new Canvas(LARGEUR, HAUTEUR);
        var contexte = canvas.getGraphicsContext2D();
        contexte.setFill(Color.web("#1a1a1a"));
        contexte.fillRect(0, 0, LARGEUR, HAUTEUR);
        root.getChildren().add(canvas);


        HBox ligneImages = new HBox(5);
        ligneImages.setAlignment(Pos.CENTER);

        double x = 0;
        int index = 0;
        while (x < LARGEUR) {
            if (index % 2 == 0) {
                var extraterrestre = new Image("icone.png");
                ligneImages.getChildren().add(new ImageView(extraterrestre));
                x += extraterrestre.getWidth();
            } else {
                var vache = new Image("mini-vache.png");
                ligneImages.getChildren().add(new ImageView(vache));
                x += vache.getWidth();
            }
            index++;
        }

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



    private void initialiserBoucle(GraphicsContext contexte) {
//        if (timer != null) {
//            timer.stop();
//        }

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

    private Text creerTextePourInfos(String texte, double taille) {
        Text objetTexte = new Text(texte);
        objetTexte.setFont(Font.font("Arial", taille));
        objetTexte.setFill(Color.WHITE);
        return objetTexte;
    }

    private HBox creerHBoxAuteur(String prefixe, String auteur) {
        Text textePrefixe = creerTextePourInfos(prefixe, 20);
        Text texteAuteur = creerTextePourInfos(auteur, 35);
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(textePrefixe, texteAuteur);
        return hbox;
    }

    public static void main(String[] args) {
        launch();
    }
}