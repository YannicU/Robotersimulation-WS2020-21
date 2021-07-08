import java.awt.*;
import java.util.*;

/**
 * Stellt Roboter auf Spielfeld dar.
 * Erbt die Eigenschaften der Klasse Kreis.
 *
 * @author Vivian Bär, Yannic Yu
 * @version 23.05.2021
 */

public class Roboter extends Kreis {
    private final Scanner SCANNER = new Scanner(System.in);

    /**
     * 1. Konstruktor der Klasse Roboter
     */
    Roboter() {
    }

    /**
     * 2. Konstruktor der Klasser Roboter
     *
     * @param position    Objekt der Klasse Punkt das die x-y-Koordinaten besitzt
     * @param durchmesser Durchmesser des Roboters
     * @param bezeichnung Bezeichnung des Roboters
     * @param farbe       Farbe des Roboters
     */
    Roboter(Punkt position, int durchmesser, String bezeichnung, Color farbe) {
        super(position, durchmesser, bezeichnung, farbe);
    }

    /**
     * überprüft ob sich der Roboter innerhalb gegebener Grenzen eines Spielfeldes befindet
     *
     * @return <code>true</code>, solange er innerhalb ist
     */
    public boolean imSpielfeld() {
        return (0 < super.minX() && super.maxX() < Spielfeld.getLaenge()) &&
                (0 < super.maxY() && super.minY() < Spielfeld.getBreite());
    }

    public boolean anWandX() {
        return maxX() + 1 == Spielfeld.getLaenge() && maxY() + 1 != Spielfeld.getBreite();
    }

    public boolean anWandY() {
        return maxY() + 1 == Spielfeld.getBreite() && maxX() + 1 != Spielfeld.getLaenge();
    }

    /**
     * gibt an, ob sich der Roboter innerhalb der min. und max. x-Werte einer Figur befindet
     *
     * @param figur die überprüfende Figur
     * @return <code>true</code>, wenn Roboter innerhalb
     */
    public boolean zwischenX(Figur figur) {
        return figur.minX() <= super.maxX() && super.minX() <= figur.maxX();
    }

    /**
     * gibt an, ob sich der Roboter innerhalb der min. und max. y-Werte einer Figur befindet
     *
     * @param figur die überprüfende Figur
     * @return <code>true</code>, wenn Roboter innerhalb
     */
    public boolean zwischenY(Figur figur) {
        return figur.minY() <= super.maxY() && super.minY() <= figur.maxY();
    }

    /**
     * Methode die eine User-Eingabe auf Stichwörter überprüft und gegebenenfalls eine vorgegebene
     * Antwort auf der Konsole zurückgibt.
     */
    void spracherkennung() {
        String userEingabe = "";
        System.out.println("\n-----Roboter Spracherkennung: gestartet-----" +
                "\n 'hilfe' - zeigt valide Sitchwörter an" +
                "\n 'ende' - beendet die Spracherkennung" +
                "\n Stellen Sie mir eine Frage!");
        while (!userEingabe.equalsIgnoreCase("ende")) {
            System.out.print("> ");
            userEingabe = SCANNER.nextLine().toUpperCase().replaceAll("[/!?)*(.,<>+-]", "");
            for (String wort : userEingabe.split(" ")) {
                if (Arrays.toString(Stichwort.values()).contains(wort)) {
                    try {
                        switch (Stichwort.valueOf(wort)) {
                            case POSITION:
                            case KOORDINATEN:
                            case PUNKT:
                                System.out.println("Meine Koordinaten: (" + getX() + ", " + getY() + ")");
                                break;
                            case DURCHMESSER:
                                System.out.println("Mein Durchmesser: " + getDurchmesser());
                                break;
                            case RADIUS:
                                System.out.println("Mein Radius: " + getDurchmesser() / 2);
                                break;
                            case NAME:
                            case BEZEICHNUNG:
                                System.out.println("Meine Bezeichnung: " + getBezeichnung());
                                break;
                            case FARBE:
                                System.out.println("Meine Farbe: r=" + getColor().getRed() + ", g=" +
                                        getColor().getGreen() + ", b=" + getColor().getBlue());
                                break;
                            case HILFE:
                            case STICHWORT:
                                System.out.println("valide Stichwörter:" +
                                        "\n- Name, Bezeichnung" +
                                        "\n- Position, Koordinaten, Punkt" +
                                        "\n- Durchmesser, Radius" +
                                        "\n- Farbe" +
                                        "\n- Hilfe, Stichwort" +
                                        "\n- Ende");
                                break;
                            case ENDE:
                                System.out.println("-----Roboter Spracherkennung: beendet-----");
                        }
                    } catch (IllegalArgumentException illegalArgumentException) {
                        System.out.println("...Tut mir leid, '" + wort + "' hab ich nicht verstanden :(");
                    }
                } else {
                    System.out.println("...Tut mir leid, das habe ich leider nicht verstanden." +
                            "\n 'hilfe' - zeigt valide Sitchwörter an" +
                            "\n 'ende' - beendet die Spracherkennung");
                }
            }

        }
    }

    enum Stichwort {
        NAME,
        BEZEICHNUNG,
        POSITION,
        KOORDINATEN,
        PUNKT,
        DURCHMESSER,
        RADIUS,
        FARBE,
        HILFE,
        STICHWORT,
        ENDE,
    }
}
