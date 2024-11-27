package ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires;

import javafx.scene.input.KeyCode;

public class Touche {
    private static final double DELAI = Math.pow(10, 9);
    private final boolean APPUYEE;
    private final long MOMENT_DACTIVATION;

    public Touche(boolean APPUYEE) {
        this.APPUYEE = APPUYEE;
        this.MOMENT_DACTIVATION = 0;
    }

    public Touche(boolean APPUYEE, boolean garderMomentDActivation) {
        this.APPUYEE = APPUYEE;
        this.MOMENT_DACTIVATION = (garderMomentDActivation) ? System.nanoTime() : 0;
    }

    public boolean isAPPUYEE() {
        return APPUYEE;
    }

    public boolean isAppuyeeAvecDelai() {
        if (System.nanoTime() - MOMENT_DACTIVATION > DELAI)
            return APPUYEE;
        else return false;
    }
}
