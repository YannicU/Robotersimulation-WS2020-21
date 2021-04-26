
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
    private String name;
    private Color farbe = Color.green;

    Rechteck(){}
    Rechteck(Punkt position, int breite, int laenge, String name, Color farbe) {
        this.position = position;
        this.breite = breite;
        this.laenge = laenge;
        this.name = name;
        this.farbe = farbe;
    }

    public int getPositionX() {
        return position.getX();
    }
    public int getPositionY() {
        return position.getY();
    }
    public int getBreite() {
        return breite;
    }
    public int getLaenge() {
        return laenge;
    }
    public String getName() {
        return name;
    }
    public Color getColor() {
        return farbe;
    }

    public void setPosition(Punkt position) {
        this.position = position;
    }
    public void setBreite(int breite) {
        this.breite = breite;
    }
    public void setLaenge(int laenge) {
        this.laenge = laenge;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setColor(Color farbe) {
        this.farbe = farbe;
    }

    public void bewegeUm(int dx, int dy){
        this.position.bewegeUm(dx, dy);
    }
    public void bewegeUm(Punkt verschiebevektor){
        this.position.bewegeUm(verschiebevektor.getX(), verschiebevektor.getY());
    }

    public String ausgabeAttribute(){
        return "    Position: " + position.ausgabeAttribute() +
                "\n    Breite: " + getBreite() + " px" +
                "\n    Länge: " + getLaenge() + " px" +
                "\n    Name: " + getName() +
                "\n    Color: " + getColor() + "\n";
    }
}