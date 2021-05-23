import java.awt.*;
import java.util.Random;

/**
 * Beschreiben Sie hier die Klasse Figur.
 *
 * @author Yannic
 * @version 23.05.2021
 */

abstract class Figur {
    private static final Random ZUFALLSGENERATOR = new Random();
    private Punkt position;
    private String bezeichnung;
    private Color farbe;

    Figur() {
    }

    Figur(Punkt position, String bezeichnung, Color farbe) {
        this.position = position;
        this.bezeichnung = bezeichnung;
        this.farbe = checkFarbe(farbe);
    }

    abstract int minX();

    abstract int minY();

    abstract int maxX();

    abstract int maxY();

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public Punkt getPos() {
        return position;
    }

    public void setPos(Punkt position) {
        this.position = position;
    }

    public void setPos(int x, int y) {
        position.setXY(x, y);
    }

    public void setXY(int x, int y) {
        position.setXY(x, y);
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Color getColor() {
        return farbe;
    }

    public void setColor(Color farbe) {
        this.farbe = checkFarbe(farbe);
    }

    public void bewegeUm(int dx, int dy) {
        position.bewegeUm(dx, dy);
    }

    public void bewegeUm(Punkt verschiebevektor) {
        position.bewegeUm(verschiebevektor.getX(), verschiebevektor.getY());
    }

    private Color checkFarbe(Color farbe) {
        if (farbe == Color.white || farbe.equals(new Color(255, 255, 255))) {
            float r = ZUFALLSGENERATOR.nextFloat();
            float g = ZUFALLSGENERATOR.nextFloat();
            float b = ZUFALLSGENERATOR.nextFloat();
            farbe = new Color(r, g, b);
            System.out.println("Farbe darf nicht weiß sein! Farbe wurde zufällig gewählt: " +
                    "(r = " + r + ", g = " + g + ", b = " + b + ")");
            checkFarbe(farbe); // nur falls zufällige Farbe nochmal Weiß sein sollte
        }
        return farbe;
    }
}
