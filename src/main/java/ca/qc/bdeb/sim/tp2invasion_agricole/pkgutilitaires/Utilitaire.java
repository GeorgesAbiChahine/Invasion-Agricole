package ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires;

import java.util.Random;

/**
 * La classe {@code Utilitaire} fournit des méthodes statiques pour générer des valeurs aléatoires,
 * telles que des doubles dans une plage donnée ou des valeurs booléennes.
 * Elle est utilisée dans plusieurs parties du jeu pour introduire des éléments aléatoires.
 */
public class Utilitaire {

    /**
     * Génère un nombre aléatoire de type {@code double} compris entre deux bornes (inclusivement).
     *
     * @param min La borne minimale (incluse).
     * @param max La borne maximale (exclue).
     * @return Un nombre aléatoire de type {@code double} entre {@code min} et {@code max}.
     */
    public static double genererDouble(double min, double max) {
        var gen = new Random();
        return gen.nextDouble(min, max);
    }

    /**
     * Génère une valeur booléenne aléatoire.
     *
     * @return {@code true} ou {@code false}, choisi aléatoirement.
     */
    public static boolean genererBoolean() {
        var gen = new Random();
        return gen.nextBoolean();
    }
}
