import java.awt.Color;
import java.util.Random;

/**
 * Beschreiben Sie hier die Klasse Rechteck.
 *
 * @author Yannic Yu
 * @version 29.04.2021
 */

public class Rechteck {
    private static final Random ZUFALLSGENERATOR = new Random();
    private Punkt position;
    private int breite = 0;
    private int laenge = 0;
    private String bezeichnung;
    private Color farbe;

    Rechteck() {
    }

    Rechteck(Punkt position, int laenge, int breite, String bezeichnung, Color farbe) {
        this.position = position;
        this.laenge = laenge;
        this.breite = breite;
        this.bezeichnung = bezeichnung;
        checkFarbe(farbe);
    }

    private void checkFarbe(Color farbe) {
        if (farbe == Color.white || farbe.equals(new Color(255, 255, 255))) {
            float r = ZUFALLSGENERATOR.nextFloat();
            float g = ZUFALLSGENERATOR.nextFloat();
            float b = ZUFALLSGENERATOR.nextFloat();
            this.farbe = new Color(r, g, b);
            checkFarbe(this.farbe);
            System.out.println("Farbe darf nicht weiß sein! Farbe wurde zufällig gewählt: " +
                "(r = " + this.farbe.getRed() + ", g = " + this.farbe.getGreen() + ", b = " + this.farbe.getBlue());
        } else {
            this.farbe = farbe;
        }
    }

    public int getPositionX() {
        return position.getX();
    }

    public int getPositionY() {
        return position.getY();
    }

    public void setPosition(Punkt position) {
        this.position = position;
    }

    public void setPosition(int x, int y) {
        position.setXY(x, y);
    }

    public int getLaenge() {
        return laenge;
    }

    public void setLaenge(int laenge) {
        this.laenge = laenge;
    }

    public int getBreite() {
        return breite;
    }

    public void setBreite(int breite) {
        this.breite = breite;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getColor() {
        return "red = " + farbe.getRed() + ", green = " + farbe.getGreen() + ", blue = " + farbe.getBlue();
    }

    public void setColor(Color farbe) {
        checkFarbe(farbe);
    }

    public void bewegeUm(int dx, int dy) {
        position.bewegeUm(dx, dy);
    }

    public void bewegeUm(Punkt verschiebevektor) {
        position.bewegeUm(verschiebevektor.getX(), verschiebevektor.getY());
    }

    public void ausgabeAttribute() {
        System.out.println("Position: x = " + getPositionX() + ", y = " + getPositionY() +
            "\nBreite: " + getBreite() + " px" +
            "\nLänge: " + getLaenge() + " px" +
            "\nBezeichnung: " + getBezeichnung() +
            "\nColor: " + getColor());
    }

    public boolean ueberlappt(Rechteck r) {
        // zu erzeugende Rechteck = R1, Linke obere Ecke liegt im Koordinaten Ursprung
        // Rechteck r = R2
        int dx = r.getPositionX() - getPositionX();
        int dy = r.getPositionY() - getPositionY();
        if (getPositionX() <= r.getPositionX() && getPositionY() <= r.getPositionY()) { // I. Quadrant
            // Das Prinzip:
            if (dx <= laenge) { // Wenn der x-Abstand von R1 zu R2 kleiner ist als die Länge von R1
                return dy <= r.breite; // Wenn der y-Abstand von R1 zu R2 kleiner ist als die Breite von R2
            } else {
                // Wenn im I. Quadrant der x-Abstand von R1 zu R2 größer ist als die Länge von R1,
                // werden sie sich nie schneiden
                return false;
            }
            // Selbe Denkweise in den anderen Quadranten.
        } else if (getPositionX() >= r.getPositionX() && getPositionY() <= r.getPositionY()) { // II. Quadrant
            if (dx <= r.laenge) {
                return dy <= r.breite;
            } else {
                return false;
            }
        } else if (getPositionX() >= r.getPositionX() && getPositionY() >= r.getPositionY()) { // III. Quadrant
            if (dy <= breite) {
                return dx <= r.laenge;
            } else {
                return false;
            }
        } else if (getPositionX() <= r.getPositionX() && getPositionY() >= r.getPositionY()) { // IV. Quadrant
            if (dy <= breite) {
                return dx <= laenge;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}