import java.util.Arrays;
import java.util.Scanner;

/**
 * Beschreiben Sie hier die Klasse Roboter.
 *
 * @author Yannic Yu
 * @version 13.05.2021
 */

public class Roboter {
    private final Scanner scanner = new Scanner(System.in);

    Roboter() {
    }

    void spracherkennung() {
        System.out.print("Schreibe 'Start' um zu starten, 'Ende' um zu beenden\n> ");
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
                                case NAMEN:
                                case BEZEICHNUNG:
                                    System.out.println("Seppi");
                                    break;
                                case FARBE:
                                    System.out.println("Weiß ich auch nicht :(");
                                    break;
                                case POSITION:
                                case KOORDINATEN:
                                case PUNKT:
                                    System.out.println("Irgendwo im Nirgendwo");
                                break;
                                case LANG:
                                case LAENGE:
                                    System.out.println("seeehr laaaang");
                                    break;
                                case BREIT:
                                case BREITE:
                                    System.out.println("Ich bin nicht nur breit, sondern auch dicht... lol.");
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
        } else if (!userEingabe.equalsIgnoreCase("ende")) {
            System.out.println("Wie bitte?");
            spracherkennung();
        }
    }

    enum Stichwort {
        CIAO,
        NAME,
        NAMEN,
        BEZEICHNUNG,
        BEFINDE,
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
        HALLO,
        TSCHUESS,
        NICHT,
    }
}
