import java.awt.*;

/**
 * Beschreiben Sie hier die Klasse Kreis.
 *
 * @author Yannic
 * @version 23.05.2021
 */

public class Kreis extends Figur {
    private int durchmesser;

    Kreis() {
    }

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

    public int getDurchmesser() {
        return durchmesser;
    }

    public void setDurchmesser(int durchmesser) {
        this.durchmesser = durchmesser;
    }
}
