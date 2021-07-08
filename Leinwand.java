import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Erstellt eine Leinwand auf der die Zeichenfläche angezeigt wird.
 *
 * @author Vivian Bär, Yannic Yu
 * @version 23.05.2021
 */

public class Leinwand {
    private static Leinwand leinwand;
    private static Color hgFarbe;
    private final JFrame FENSTER;
    public Zeichenflaeche zeichenflaeche;

    /**
     * Konstruktor der Klasse Leinwand
     *
     * @param titel  Titel des Fensters
     * @param laenge Länge des Fensters (width)
     * @param breite Breite des Fensters (hight)
     */
    Leinwand(String titel, int laenge, int breite, Color hgFarbe) {
        Leinwand.hgFarbe = hgFarbe;
        FENSTER = new JFrame();
        FENSTER.getContentPane().setBackground(hgFarbe);
        FENSTER.setTitle(titel);
        FENSTER.setSize(laenge + 17, breite + 40);
        FENSTER.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        FENSTER.setResizable(false);
        FENSTER.setVisible(false);
    }

    public static Leinwand getLeinwand(String titel, int laenge, int breite, Color hgFarbe) {
        if (leinwand == null) {
            leinwand = new Leinwand(titel, laenge, breite, hgFarbe);
        }
        return leinwand;
    }

    public static Color getHgFarbe() {
        return hgFarbe;
    }

    public void zeichnen(ArrayList<Rechteck> hindernisse, Punkt[] poi, Roboter roboter) {
        zeichenflaeche = new Zeichenflaeche();
        zeichenflaeche.repaintFiguren(hindernisse, poi, roboter);
        FENSTER.add(zeichenflaeche);
        FENSTER.setVisible(true);
    }

    /**
     * Verzögert den Fortschritt des Codes um angegebene Millisekunden
     *
     * @param millisekunden Dauer der Verzögerung (in millisekunden)
     */
    public void warten(int millisekunden) {
        try {
            Thread.sleep(millisekunden);
        } catch (Exception e) {
            System.out.println("Exception occured: " + e);
        }
    }

    public void close() {
        FENSTER.dispose();
    }
}