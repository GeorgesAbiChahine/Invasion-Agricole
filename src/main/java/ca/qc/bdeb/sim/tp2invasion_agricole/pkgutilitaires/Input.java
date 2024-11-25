package ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

/**
 * La classe {@code Input} gère les entrées clavier dans le jeu.
 * Elle permet de suivre l'état des touches (appuyées ou non) et de déterminer
 * si une touche a été appuyée une seule fois.
 */
public class Input {

    private static final HashMap<KeyCode, Boolean> TOUCHES = new HashMap<>();

    public static boolean isKeyPressed(KeyCode code) {
        return TOUCHES.getOrDefault(code, false);
    }

    public static void setKeyPressed(KeyCode code, Boolean isPressed) {
        TOUCHES.put(code, isPressed);
    }

    /**
     * Vérifie si une touche a été appuyée une seule fois.
     * Après avoir détecté l'appui, l'état de la touche est immédiatement remis à {@code false}.
     *
     * @param code Le code de la touche à vérifier.
     * @return {@code true} si la touche a été appuyée une seule fois, sinon {@code false}.
     */
    public static boolean isOneTimeKeyPressed(KeyCode code) {
        boolean touchePressee = TOUCHES.getOrDefault(code, false);
        if (touchePressee) setKeyPressed(code, false);
        return touchePressee;
    }
}
