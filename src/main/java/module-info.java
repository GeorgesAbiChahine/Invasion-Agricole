module ca.qc.bdeb.sim.tp2invasion_agricole {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jconsole;
    requires jdk.compiler;


    opens ca.qc.bdeb.sim.tp2invasion_agricole to javafx.fxml;
    exports ca.qc.bdeb.sim.tp2invasion_agricole;
    exports ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgarriereplan;
    opens ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgarriereplan to javafx.fxml;
    exports ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie;
    opens ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie to javafx.fxml;
}