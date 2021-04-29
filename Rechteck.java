import java.awt.Color;

/**
 * Beschreiben Sie hier die Klasse Rechteck.
 *
 * @author Yannic Yu
 * @version 29.04.2021
 */

public class Rechteck {
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

    private Punkt getPosition() {
        return position;
    }

    public int getPositionX() {
        return position.getX();
    }

    public int getPositionY() {
        return position.getY();
    }

    public int getLaenge() {
        return laenge;
    }

    public int getBreite() {
        return breite;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public String getColor() {
        return "red = " + farbe.getRed() + ", green = " + farbe.getGreen() + ", blue = " + farbe.getBlue();
    }

    private void setPosition(Punkt position) {
        this.position = position;
    }

    private void setPosition(int x, int y) {
        position.setXY(x, y);
    }

    private void setLaenge(int laenge) {
        this.laenge = laenge;
    }

    private void setBreite(int breite) {
        this.breite = breite;
    }

    private void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    private void setColor(Color farbe) {
        checkFarbe(farbe);
    }

    private void checkFarbe(Color farbe) {
        if (farbe == Color.white) {
            System.out.println("Farbe darf nicht weiß sein! Farbe wurde automatisch als grau fesgelegt");
            this.farbe = Color.gray;
        } else {
            this.farbe = farbe;
        }
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
        int dx = r.getPositionX() - getPositionX();
        int dy = r.getPositionY() - getPositionY();
        if (getPositionX() <= r.getPositionX() && getPositionY() <= r.getPositionY()) { // I. Quadrant
            if (dx <= laenge) {
                return dy <= r.breite;
            } else {
                return false;
            }
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