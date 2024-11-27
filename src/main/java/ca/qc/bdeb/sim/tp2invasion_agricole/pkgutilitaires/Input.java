package ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

/**
 * La classe {@code Input} gère les entrées clavier dans le jeu.
 * Elle permet de suivre l'état des touches (appuyées ou non) et de déterminer
 * si une touche a été appuyée une seule fois.
 */
public class Input {

    private static final HashMap<KeyCode, Touche> TOUCHES = new HashMap<>();

    public static boolean isKeyPressed(KeyCode code) {
        Touche touche = TOUCHES.get(code);
        if (touche == null) return false;
        return touche.isAPPUYEE();
    }

    public static void setKeyPressed(KeyCode code, Boolean isPressed) {
            TOUCHES.put(code, new Touche(isPressed));
    }

    public static void setOneTimeKeyPressed(KeyCode code, Boolean isPressed) {
        TOUCHES.put(code, new Touche(isPressed, true));
    }

    /**
     * Vérifie si une touche a été appuyée une seule fois.
     * Après avoir détecté l'appui, l'état de la touche est immédiatement remis à {@code false}.
     *
     * @param code Le code de la touche à vérifier.
     * @return {@code true} si la touche a été appuyée une seule fois, sinon {@code false}.
     */
    public static boolean isOneTimeKeyPressed(KeyCode code) {
        Touche touche = TOUCHES.get(code);
        if (touche == null) return false;

        boolean touchePressee = touche.isAppuyeeAvecDelai();
        if (touchePressee) setOneTimeKeyPressed(code, true);
        return touchePressee;
    }
}
