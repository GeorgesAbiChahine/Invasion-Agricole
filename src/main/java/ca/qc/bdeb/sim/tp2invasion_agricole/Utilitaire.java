package ca.qc.bdeb.sim.tp2invasion_agricole;

import java.util.Random;

public class Utilitaire {
    public static double generer(double min, double max) {
        var gen = new Random();
        return gen.nextDouble(min,max);
    }
}
