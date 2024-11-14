package ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires;

import java.util.Random;

public class Utilitaire {
    public static double genererDouble(double min, double max) {
        var gen = new Random();
        return gen.nextDouble(min, max);
    }

    public static boolean genererBoolean() {
        var gen = new Random();
        return gen.nextBoolean();
    }
}
