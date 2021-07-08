import java.awt.*;

/**
 * Wird verwendet um den Roboter darzustellen.
 * Erbt die Eigenschaften der Klasse Figur.
 *
 * @author Vivian Bär, Yannic Yu
 * @version 23.05.2021
 */

public class Kreis extends Figur {
    private int durchmesser;

    /**
     * 1. Konstruktor der Klasse Kreis
     */
    Kreis() {
        super();
    }

    /**
     * 2. Konstruktor der Klasse Kreis
     *
     * @param position    Objekt der Klasse Punkt das die x-y-Koordinaten besitzt
     * @param durchmesser Durchmesser des Kreises
     * @param bezeichnung Bezeichnung des Kreises
     * @param farbe       Farbe des Kreises
     */
    Kreis(Punkt position, int durchmesser, String bezeichnung, Color farbe) {
        super(position, bezeichnung, farbe);
        this.durchmesser = durchmesser;
    }

    int minX() {
        return getX();
    }

    int minY() {
        return getY();
    }

    int maxX() {
        return getX() + getDurchmesser();
    }

    int maxY() {
        return getY() + getDurchmesser();
    }

    public Punkt getMittelpunkt() {
        return new Punkt(getX() + (getDurchmesser() / 2), getY() + (getDurchmesser() / 2));
    }

    public void setMittelpunkt(Punkt punkt) {
        int x = punkt.getX() - (getDurchmesser() / 2);
        int y = punkt.getY() - (getDurchmesser() / 2);
        setPos(x, y);
    }

    public int getDurchmesser() {
        return durchmesser;
    }

    public void setDurchmesser(int durchmesser) {
        this.durchmesser = durchmesser;
    }
}
