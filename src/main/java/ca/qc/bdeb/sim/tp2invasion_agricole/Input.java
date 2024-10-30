package ca.qc.bdeb.sim.tp2invasion_agricole;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class Input {
    private static HashMap<KeyCode,Boolean> touches = new HashMap<>();

    public static boolean isKeyPressed(KeyCode code){
        return touches.getOrDefault(code,false);
    }

    public static void setKeyPressed(KeyCode code, Boolean isPressed){
        touches.put(code,isPressed);
    }
}
