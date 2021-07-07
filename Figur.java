import java.awt.*;
import java.util.Random;

/**
 * Beschreiben Sie hier die Klasse Figur.
 *
 * @author Vivian Bär, Yannic Yu
 * @version 23.05.2021
 */

abstract class Figur {
    private static final Random ZUFALLSGENERATOR = new Random();
    private Punkt position;
    private String bezeichnung;
    private Color farbe;

    /**
     * 1. Konstruktor der Klasse Figur
     */
    Figur() {
    }

    /**
     * 2. Konstruktor der Klasse Figur
     *
     * @param position    Objekt der Klasse Punkt das die x-y-Koordinaten besitzt
     * @param bezeichnung Bezeichnung der Figur
     * @param farbe       Farbe der Figur
     */
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

    /**
     * überprüft, ob Farbe weiß ist. Ist sie weiß, dann wird eine neue,
     * zufällige Farbe generiert und zurückgegeben.
     *
     * @param farbe Farbe die zu überprüfen ist
     * @return originale oder zufällige Farbe
     */
    private Color checkFarbe(Color farbe) {
        if (farbe == Color.white || farbe.equals(new Color(255, 255, 255))) {
            float r = ZUFALLSGENERATOR.nextFloat();
            float g = ZUFALLSGENERATOR.nextFloat();
            float b = ZUFALLSGENERATOR.nextFloat();
            farbe = new Color(r, g, b);
            System.out.println("Farbe darf nicht weiß sein! Farbe wurde zufällig gewählt: " +
                    "(r = " + r + ", g = " + g + ", b = " + b + ")");
            checkFarbe(farbe); // falls zufällige Farbe nochmal Weiß sein sollte
        }
        return farbe;
    }

    /**
     * Überprüft ob sich zwei Figuren überlappen
     *
     * @param f andere Figur
     * @return <code>true</code>, wenn die zwei Rechtecke überlappen
     */
    public boolean ueberlappt(Figur f) {
        if (f.maxX() >= minX() && f.minX() <= maxX()) {
            return f.maxY() >= minY() && f.minY() <= maxY();
        } else return false;

        /* ERKLÄRUNG:

        Annahme: Die zu überprüfenden Figuren sind zwei Rechtecke.
        R1... aktuelles Rechteck
        R2... anderes Rechteck (f)
        R1 ist fest in einem Koordinatensystem. Die Linke untere Ecke ist im Koordinaten Ursprung.
        R2 kann rechts, linke, über, unter R1 sein.

        für x-Werte:
            f.maxX() >= minX()...   ist rechte Kante R2 größer als linke Kante R1?
            f.minX() <= maxX()...   ist linke Kante R2 kleiner als Rechte Kante R1?
            sind beide Bedingungen erfüllt, überlappen sich Figuren in x-Richtung
        analog für y-Werte.
        Falls die Bedingungen für x erfüllt sind, wird für y geprüft. Sind die Bedingungen für beide erfüllt,
        so schneiden sich die zwei Rechtecke.

        Alternative (kompaktere) Darstellung:
        return f.maxX() >= minX() && f.minX() <= maxX() && f.maxY() >= minY() && f.minY() <= maxY();
         */
    }
}
