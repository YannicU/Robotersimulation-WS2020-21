import java.util.Comparator;
import java.util.Map;

/**
 * Beschreiben Sie hier die Klasse Punkt.
 *
 * @author Yannic Yu
 * @version 29.04.2021
 */
public class Punkt implements Comparator<Punkt> {
    Map<Punkt, Double> base;
    private int x = 0;
    private int y = 0;

    Punkt() {
    }

    Punkt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Punkt(Map<Punkt, Double> base) { // geklaut und verstehe ich noch nicht
        this.base = base;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void bewegeUm(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public double getAbstand(Punkt andererPunkt) {
        return Math.sqrt(Math.pow((andererPunkt.getX() - getX()), 2)
            + Math.pow((andererPunkt.getY() - getY()), 2));
    }

    public void ausgabeAttribute() {
        System.out.println("x = " + getX() + ", y = " + getY());
    }

    // vorsicht geklaut! Verstehe ich auch nur so halb:
    @Override
    public int compare(Punkt punkt1, Punkt punkt2) {
        if (base.get(punkt1) >= base.get(punkt2)) {
            return 1;
        } else {
            return -1;
        } // returning 0 would merge keys
    }
}
