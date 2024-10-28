package ca.qc.bdeb.sim.tp2invasion_agricole;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static int LARGEUR = 900;
    public static int HAUTEUR = 520;
    @Override
    public void start(Stage stage) throws IOException {
      var root = new Pane();
        Scene scene = creerSceneJeu(root);

        stage.setTitle("Invasion Agricole");
        stage.setScene(scene);
        stage.show();
    }

    private Scene creerSceneJeu(Pane root){
        var scene = new Scene(root, LARGEUR, HAUTEUR);
        var canvas = new Canvas(LARGEUR,HAUTEUR);


        GraphicsContext contexte = canvas.getGraphicsContext2D();



        return scene;
    }

    public static void main(String[] args) {
        launch();
    }
}