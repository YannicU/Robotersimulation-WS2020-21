/**
 * Beschreiben Sie hier die Klasse Rechteck.
 *
 * @author Yannic Yu
 * @version V1 26.04.2021
 */
import java.awt.Color;

public class Rechteck {
    private Punkt position = new Punkt(0, 0);
    private int breite = 0;
    private int laenge = 0;
    private String bezeichnung;
    private Color farbe;
    /*
     * Hier folgen die Konstruktoren
     */
    Rechteck(){}
    Rechteck(Punkt position, int breite, int laenge, String bezeichnung, Color farbe) {
        this.position = position;
        this.breite = breite;
        this.laenge = laenge;
        this.bezeichnung = bezeichnung;
        checkFarbe(farbe);
    }
    /*
     * Hier folgen die "get" Methoden
     */
    public int getPositionX() {
        return this.position.getX();
    }
    public int getPositionY() {
        return this.position.getY();
    }
    public int getBreite() {
        return this.breite;
    }
    public int getLaenge() {
        return this.laenge;
    }
    public String getBezeichnung() {
        return this.bezeichnung;
    }
    public Color getColor() {
        return this.farbe;
    }
    /*
     * Hier folgen die "set" Methoden
     */
    public void setPosition(Punkt position) {
        this.position = position;
    }
    public void setPosition(int x, int y) {
        this.position.setXY(x, y);
    }
    public void setBreite(int breite) {
        this.breite = breite;
    }
    public void setLaenge(int laenge) {
        this.laenge = laenge;
    }
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
    public void setColor(Color farbe) {
        checkFarbe(farbe);
    }

    private void checkFarbe(Color farbe) {
        // überprüft ob die eingegebene Farbe weiß ist.
        // Falls ja, wird keine Farbe generiert und eine Fehlermeldung an die Konsole gegeben.
        if (farbe == Color.white) {
            System.out.println("Farbe darf nicht weiß sein!");
            this.farbe = null;
        } else {
            this.farbe = farbe;
        }
    }

    public void bewegeUm(int dx, int dy) {
        this.position.bewegeUm(dx, dy);
    }
    public void bewegeUm(Punkt verschiebevektor) {
        this.position.bewegeUm(verschiebevektor.getX(), verschiebevektor.getY());
    }

    public void ausgabeAttribute() {
        System.out.println("\nPosition: x = " + getPositionX() + ", y = " + getPositionY() +
                "\nBreite: " + getBreite() + " px" +
                "\nLänge: " + getLaenge() + " px" +
                "\nBezeichnung: " + getBezeichnung() +
                "\nColor: " + getColor());
    }
}