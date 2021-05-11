
/**
 * Beschreiben Sie hier die Klasse Punkt.
 *
 * @author Yannic Yu
 * @version 11.05.2021
 */
public class Punkt {
    private int x = 0;
    private int y = 0;

    Punkt() {
    }

    Punkt(int x, int y) {
        this.x = x;
        this.y = y;
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
}
