package ca.qc.bdeb.sim.tp2invasion_agricole;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.Partie;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
//TODO: CHANGER l et h ET AUTRES CONSTANTES.
public class Main extends Application {
    public final static int LARGEUR = 900;
    public final static int HAUTEUR = 520;
    private final Partie partie = new Partie();
    @Override
    public void start(Stage stage) throws IOException {
        var root = new Pane();
        stage.setResizable(false);
        Scene scene = creerSceneJeu(root);

        stage.setTitle("Invasion Agricole");
        stage.getIcons().add(new Image("icone.png"));
        stage.setScene(scene);
        stage.show();
    }

    private Scene creerSceneJeu(Pane root){
        var scene = new Scene(root, LARGEUR, HAUTEUR);
        var canvas = new Canvas(LARGEUR,HAUTEUR);
        root.getChildren().add(canvas);

        GraphicsContext contexte = canvas.getGraphicsContext2D();

        initialiserBoucle(contexte);

        return scene;
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
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch();
    }
}