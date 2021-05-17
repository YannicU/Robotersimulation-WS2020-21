import java.awt.*;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Scanner;

/**
 * Beschreiben Sie hier die Klasse Roboter.
 *
 * @author Yannic Yu
 * @version 17.05.2021
 */

public class Roboter extends Kreis {
    private final Scanner SCANNER = new Scanner(System.in);

    Roboter() {
    }

    Roboter(Punkt position, int durchmesser, String bezeichnung, Color farbe) {
        super(position, durchmesser, bezeichnung, farbe);
    }

    void spracherkennung() {
        String userEingabe = "";
        System.out.println("\nCooler Roboter 1: Home\n" +
                "um 'spracherkennung' zu beenden: 'Ende'");
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
                            case NAMEN:
                            case BEZEICHNUNG:
                                System.out.println("Meine Bezeichnung: " + getBezeichnung());
                                break;
                            case FARBE:
                                System.out.println("Meine Farbe: " + getColor());
                                break;

                            case STICHWORT:
                            case STICHWOERTER:
                            case HELP:
                                int i = 1;
                                System.out.println("Stichwörter:");
                                for (Stichwort stichwort: EnumSet.allOf(Stichwort.class)) {
                                    System.out.println(i + ". " + stichwort);
                                    i++;
                                }
                                break;

                            case DANKE:
                                System.out.println("Kein ding");
                                break;
                            case GERNE:
                                System.out.println("Wow! So höflich");
                                break;
                            case HALLO:
                            case TSCHUESS:
                            case CIAO:
                                System.out.println("Sers!");
                                break;
                            case NICHT:
                                System.out.println("Okay..");
                                break;
                        }
                    } catch (IllegalArgumentException illegalArgumentException) {
                        System.out.println("...Sorry, " + wort + " hab ich nicht verstanden :(");
                    }
                }
            }

        }
    }

    enum Stichwort {
        POSITION,
        KOORDINATEN,
        PUNKT,
        DURCHMESSER,
        RADIUS,
        NAME,
        NAMEN,
        BEZEICHNUNG,
        FARBE,

        DANKE,
        GERNE,
        HALLO,
        TSCHUESS,
        NICHT,
        CIAO,
        STICHWORT,
        STICHWOERTER,
        HELP,
    }
}
