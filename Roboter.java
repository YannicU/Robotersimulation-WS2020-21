import java.util.Arrays;
import java.util.Scanner;

/**
 * Beschreiben Sie hier die Klasse Roboter.
 *
 * @author Yannic Yu
 * @version 30.04.2021
 */

public class Roboter {
    private final Scanner scanner = new Scanner(System.in);

    Roboter() {
    }

    void spracherkennung() {
        System.out.println("Schreibe 'Start' um zu starten, 'Ende' um zu beenden");
        String userEingabe = scanner.nextLine().toUpperCase();
        if (userEingabe.equalsIgnoreCase("start")) {
            System.out.println("...Tagchen, wie kann ich dir Helfen?");
            while (!userEingabe.equalsIgnoreCase("ende")) {
                System.out.print("> ");
                userEingabe = scanner.nextLine().toUpperCase().replaceAll("[/!?)*(.,<>+-]", "");
                for (String wort : userEingabe.split(" ")) {
                    if (Arrays.toString(Stichwort.values()).contains(wort)) {
//                for (Stichwort stichwort : Stichwort.values()) {
//                    if (userEingabe.contains(stichwort.toString())) {
                        try {
                            switch (Stichwort.valueOf(wort)) {
                                case NAME:
                                case BEZEICHNUNG:
                                    System.out.println("Cooler Typ");
                                    break;
                                case FARBE:
                                    System.out.println("Farbe");
                                    break;
                                case POSITION:
                                case KOORDINATEN:
                                case PUNKT:
                                    System.out.println("Position");
                                    break;
                                case LANG:
                                case LAENGE:
                                    System.out.println("Länge");
                                    break;
                                case BREIT:
                                case BREITE:
                                    System.out.println("Breite");
                                    break;
                                case DANKE:
                                    System.out.println("Kein ding");
                                    break;
                                case GERNE:
                                    System.out.println("So höflich");
                                    break;
                            }
                        } catch (IllegalArgumentException illegalArgumentException) {
                            System.out.println("...Sorry, " + wort + " hab ich nicht verstanden :(");
                        }
                    }
                }
            }
        } else if (!userEingabe.equalsIgnoreCase("ende")) {
            System.out.println("Wie bitte?");
            spracherkennung();
        } else {
            System.out.println("Tschüssie");
        }
    }

    enum Stichwort {
        NAME,
        BEZEICHNUNG,
        FARBE,
        POSITION,
        KOORDINATEN,
        PUNKT,
        LANG,
        LAENGE,
        BREIT,
        BREITE,
        DANKE,
        GERNE,
    }
}
