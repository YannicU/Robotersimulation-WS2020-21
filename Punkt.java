/**
 * Klasse Punkt speichert die Koordinaten der verschiedenen Objekte.
 *
 * @author Vivian Bär, Yannic Yu
 * @version 11.05.2021
 */
public class Punkt {
    private int x = 0;
    private int y = 0;

    /**
     * 1. Konstruktor der Klasse Punkt
     */
    Punkt() {
    }

    /**
     * 2. Konstruktor der Klasse Punkt
     *
     * @param x Wert von x
     * @param y Wert von y
     */
    Punkt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gibt den x-Wert des Punktes zurück
     *
     * @return Wert von x
     */
    public int getX() {
        return x;
    }

    /**
     * Gibt den y-Wert des Punktes zurück
     *
     * @return Wert von y
     */
    public int getY() {
        return y;
    }

    /**
     * Setzt die x- und y-Werte des Punktes
     *
     * @param x Wert von x
     * @param y Wert von y
     */
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * bewegt x um dx und y um dy
     *
     * @param dx Wert um den x bewegt werden soll
     * @param dy Wert um den y bewegt werden soll
     */
    public void bewegeUm(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
     * Gibt den Abstand zwischen zwei Punkten zurück
     *
     * @param andererPunkt Der Punkt zu dem der Abstand berechnet werden soll
     * @return Abstand zwischen den Punkten
     */
    public double getAbstand(Punkt andererPunkt) {
        return Math.sqrt(Math.pow((andererPunkt.getX() - getX()), 2)
                + Math.pow((andererPunkt.getY() - getY()), 2));
    }

    /**
     * Gibt die Koordinaten des Punktes auf der Konsole aus
     */
    public void ausgabeAttribute() {
        System.out.println("x = " + getX() + ", y = " + getY());
    }
}
