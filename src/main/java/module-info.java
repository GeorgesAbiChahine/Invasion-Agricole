module ca.qc.bdeb.sim.tp2invasion_agricole {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jconsole;
    requires jdk.compiler;


    opens ca.qc.bdeb.sim.tp2invasion_agricole to javafx.fxml;
    exports ca.qc.bdeb.sim.tp2invasion_agricole;
    exports ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie;
    opens ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie to javafx.fxml;
    opens ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites to javafx.fxml;
    exports ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites;
    exports ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgentitesabsorbable;
    opens ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgentitesabsorbable to javafx.fxml;
    exports ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau;
    opens ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau to javafx.fxml;
    exports ca.qc.bdeb.sim.tp2invasion_agricole.pkgvisuel.pkgdecor;
    opens ca.qc.bdeb.sim.tp2invasion_agricole.pkgvisuel.pkgdecor to javafx.fxml;
    exports ca.qc.bdeb.sim.tp2invasion_agricole.pkgvisuel.pkginterface;
    opens ca.qc.bdeb.sim.tp2invasion_agricole.pkgvisuel.pkginterface to javafx.fxml;
    exports ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires;
    opens ca.qc.bdeb.sim.tp2invasion_agricole.pkgutilitaires to javafx.fxml;
}