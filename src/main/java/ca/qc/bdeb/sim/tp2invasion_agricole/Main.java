package ca.qc.bdeb.sim.tp2invasion_agricole;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
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

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        creerSceneIntro();


        stage.setResizable(false);
        stage.setTitle("Invasion Agricole");
        stage.getIcons().add(new Image("icone.png"));
        stage.show();
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

    private void creerSceneIntro() {
        var root = new StackPane();
        var scene = new Scene(root, LARGEUR, HAUTEUR);

        var imageIntro = new ImageView(new Image("intro.png"));
        root.getChildren().add(imageIntro);

        var vBoxBoutons = new VBox(20);
        vBoxBoutons.setAlignment(Pos.CENTER);

        var jouer = new Button("Jouer!");
        jouer.setPrefSize(70, 30);
        vBoxBoutons.getChildren().add(jouer);

        var infos = new Button("Infos");
        infos.setPrefSize(60, 30);
        vBoxBoutons.getChildren().add(infos);

        root.getChildren().add(vBoxBoutons);

        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) Platform.exit();
        });
        jouer.setOnAction(event -> creerSceneJeu());
        infos.setOnAction(event -> creerSceneInfos());
        stage.setScene(scene);
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


        VBox vBoxTextes = new VBox(40);
        vBoxTextes.setAlignment(Pos.CENTER);

        Text titre = new Text("Invasion Agricole");
        titre.setFont(Font.font("Arial", 36));
        titre.setFill(Color.WHITE);

        Text auteurs = new Text("Par Georges Abi Chahine \n et Valère Bardon");
        auteurs.setFill(Color.WHITE);
        auteurs.setFont(Font.font("Arial", 20));

        Text description = new Text("Travail remis à Nicolas Hurtubise et Salwa Mbarek.\n" +
                "Graphismes adaptés de https://game-icons.net et de https://openclipart.org/.\n" +
                "Développé dans le cadre du cours " +
                "420-203-RE - Développement de programmes dans un environnement graphique,\n" +
                "au Collège de Bois-de-Boulogne.");
        description.setFill(Color.WHITE);
        description.setFont(Font.font("Arial", 14));
        description.setTextAlignment(TextAlignment.CENTER);

        Button retourButton = new Button("Retour");
        retourButton.setOnAction(event -> creerSceneIntro());

        vBoxTextes.getChildren().addAll(titre, auteurs, description, retourButton);

        VBox vBoxTout = new VBox(20);
        vBoxTout.setAlignment(Pos.TOP_CENTER);
        vBoxTout.getChildren().addAll(ligneImages, vBoxTextes);

        root.getChildren().add(vBoxTout);

        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) creerSceneIntro();
        });
        stage.setScene(scene);
    }

    private void initialiserBoucle(GraphicsContext contexte) {
        partie.genererPartie();
        AnimationTimer timer = new AnimationTimer() {
            long dernierTemps = System.nanoTime();

            @Override
            public void handle(long now) {
                double deltaTime = (now - dernierTemps) * 1e-9;
                partie.update(deltaTime);
                partie.dessiner(contexte);
                dernierTemps = now;
            }
        };
        timer.start();
    }

    private void gererEvenements(Pane root) {
        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) creerSceneIntro();
            else Input.setKeyPressed(e.getCode(), true);
        });

        root.setOnKeyReleased(e -> Input.setKeyPressed(e.getCode(), false));
    }

    public static void main(String[] args) {
        launch();
    }
}