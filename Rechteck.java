import java.awt.*;

/**
 * Beschreiben Sie hier die Klasse Rechteck.
 *
 * @author Yannic Yu, Vivian Bär
 * @version 17.05.2021
 */

public class Rechteck extends Figur {
    private int breite;
    private int laenge;

    Rechteck() {
    }

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

    public void ausgabeAttribute() {
        System.out.println("Position: x = " + getX() + ", y = " + getY() +
                "\nBreite: " + getBreite() + " px" +
                "\nLänge: " + getLaenge() + " px" +
                "\nBezeichnung: " + getBezeichnung() +
                "\nColor: " + getColor());
    }

    public boolean ueberlappt(Rechteck r) {
        // aktuelles Rechteck = R1
        // Rechteck r = R2
        int dx = r.getX() - getX(); // Abstandsvektor x-Richtung
        int dy = r.getY() - getY(); // Abstandsvektor y-Richtung

        /* So kann man sich den code auch vorstellen:
        if (dx <= laenge && dx >= -r.getLaenge()) {
            if (dy <= breite && dy >= -r.getBreite()) {
                return true;
            } else return false;
        } else return false;
         */

        // längeR1 <= dx <= -längeR2, breiteR1 <= dy <= -breiteR2 ... gibt wahr oder falsch aus
        // wahr -> Überlappung, falsch -> keine Überlappung
        return laenge >= dx && dx >= -r.getLaenge() && breite >= dy && dy >= -r.getBreite();
    }
}