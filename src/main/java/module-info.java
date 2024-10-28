module ca.qc.bdeb.sim.tp2invasion_agricole {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.qc.bdeb.sim.tp2invasion_agricole to javafx.fxml;
    exports ca.qc.bdeb.sim.tp2invasion_agricole;
}