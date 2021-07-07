import java.awt.*;

/**
 * Erzeugt Rechtecke die als Hindernisse gesehen werden.
 * Erbt die Eigenschaften der Klasse Figur.
 *
 * @author Vivian Bär, Yannic Yu
 * @version 17.05.2021
 */

public class Rechteck extends Figur {
    private int breite;
    private int laenge;

    /**
     * 1. Konstruktor der Klasse Rechteck
     */
    Rechteck() {
        super();
    }

    /**
     * 2. Konstruktor der Klasse Rechteck
     *
     * @param position    Objekt der Klasse Punkt das die x-y-Koordinaten besitzt
     * @param laenge      Länge des Rechteckes (in x-Richtung)
     * @param breite      Breite des Rechteckes (in y-Richtung)
     * @param bezeichnung Bezeichnung des Rechtecks
     * @param farbe       Farbe des Rechtecks
     */
    Rechteck(Punkt position, int laenge, int breite, String bezeichnung, Color farbe) {
        super(position, bezeichnung, farbe); // ruft den Konstruktor der Superklasse "Figur"
        this.laenge = laenge;
        this.breite = breite;
    }

    int minX() {
        return getX();
    }

    int minY() {
        return getY();
    }

    int maxX() {
        return getX() + getLaenge();
    }

    int maxY() {
        return getY() + getBreite();
    }

    public int getLaenge() {
        return laenge;
    }

    public int getBreite() {
        return breite;
    }

    /**
     * Ausgabe der Daten des Rechtecks auf der Konsole
     */
    public void ausgabeAttribute() {
        System.out.println("Position: x = " + getX() + ", y = " + getY() +
                "\nBreite: " + getBreite() + " px" +
                "\nLänge: " + getLaenge() + " px" +
                "\nBezeichnung: " + getBezeichnung() +
                "\nColor: " + getColor());
    }
}