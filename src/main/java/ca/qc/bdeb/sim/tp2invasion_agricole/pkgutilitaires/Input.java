package ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class Input {
    private static final HashMap<KeyCode, Boolean> TOUCHES = new HashMap<>();

    public static boolean isKeyPressed(KeyCode code) {
        return TOUCHES.getOrDefault(code, false);
    }

    public static void setKeyPressed(KeyCode code, Boolean isPressed) {
        TOUCHES.put(code, isPressed);
    }
}
