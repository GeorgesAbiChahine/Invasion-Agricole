package ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkginterface;

import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.Entite;
import ca.qc.bdeb.sim.tp2invasion_agricole.pkgpartie.pkgentites.pkgvaisseau.Vaisseau;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class Interface {
    private final Batterie BATTERIE = new Batterie();
    private final MiniCarte MINI_CARTE = new MiniCarte();

    public void dessiner(GraphicsContext contexte, Vaisseau vaisseau, ArrayList<Entite> entites) {
        BATTERIE.dessiner(contexte, vaisseau);
        MINI_CARTE.dessiner(contexte, vaisseau, entites);

        contexte.drawImage(new Image("mini-vache.png"), 220, 5);

        contexte.setTextAlign(TextAlignment.LEFT);
        contexte.setTextBaseline(VPos.BASELINE);

        contexte.setFill(Color.WHITE);
        contexte.setFont(Font.font("Arial", 45));
        contexte.fillText(Integer.toString(vaisseau.getNombrePoints()), 275, 45);

        contexte.drawImage(new Image("icone.png"), 315, 0);

        if (vaisseau.isEstMort()) contexte.setFill(Color.RED);
        if (vaisseau.isEstInvincible()) contexte.fillText(":)", 380, 45);
        else contexte.fillText(Integer.toString(vaisseau.getNombreVies()), 380, 45);
    }
}
