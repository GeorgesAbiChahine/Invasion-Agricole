package ca.qc.bdeb.sim.tp2invasion_agricole;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
      var root = new Pane();
        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Invasion Agricole");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}