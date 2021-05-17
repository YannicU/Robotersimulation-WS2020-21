import java.awt.*;

public class Kreis extends Figur {
    private int durchmesser;

    Kreis() {
    }

    Kreis(Punkt position, int durchmesser, String bezeichnung, Color farbe) {
        super(position, bezeichnung, farbe);
        this.durchmesser = durchmesser;
    }

    int minX() {
        return 0;
    }

    int minY() {
        return 0;
    }

    int maxX() {
        return 0;
    }

    int maxY() {
        return 0;
    }

    public int getDurchmesser() {
        return durchmesser;
    }

    public void setDurchmesser(int durchmesser) {
        this.durchmesser = durchmesser;
    }
}
