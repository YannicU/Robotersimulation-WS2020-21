import java.util.Arrays;
import java.util.Scanner;

/**
 * Beschreiben Sie hier die Klasse Spielfeld.
 *
 * @author Yannic Yu
 * @version V1 26.04.2021
 */

public class Spielfeld {
    final int laenge = 1000;
    final int breite = 1000;
    int x;
    int y;
    Punkt roboter = new Punkt(0, 0);
    Scanner scanner = new Scanner(System.in);
    Punkt[] eingegebenePunkte;
    Punkt[] punkteSortiert;

    Spielfeld () {}

    public Punkt[] punktEingeben() {
        System.out.println("Menge der Punkte: ");
        int punktAnzahl = scanner.nextInt(); // Gibt die gewollte Anzahl der Punkte an

        eingegebenePunkte = new Punkt[punktAnzahl]; // Array mit der Länge der Punktanzahl

        /*
        Die zuvor angegebene Anzahl der Punkte wird als obere Grenze des loops verwendet.
        In dem loop gibt der Nutzer die x und y Werte der Punkte an.
        Diese werden mithilfe einer anderen Methode ("checkGrenzen") auf ihre Grenzen geprüft.
        Sollten die Grenzen nicht stimmen, muss der Nutzer die Werte für den aktuellen Punkt erneut eingeben.
         */
        for (int i = 0; i < punktAnzahl; i++) {
            System.out.println("Koordinaten von P" + (i + 1));
            userPunktEingabe();
            while (!checkGrenzen(x, y)) {
                userPunktEingabe();
            }
            Punkt punktNeu = new Punkt(x, y);
            punktNeu.ausgabeAttribute();
            eingegebenePunkte[i] = punktNeu;
        }
        return eingegebenePunkte;
    }

    public void poiSortieren (Punkt[] poi){
        punkteSortiert = new Punkt[poi.length];
        double[] abstandPunkte = new double[poi.length];

        int i = 0;
        if (poi.length >= 2){
            for (Punkt punkt : poi) {
                abstandPunkte[i] = roboter.getAbstand(punkt);
                i++;
            }
            Arrays.sort(abstandPunkte);
            i = 0;
            for (double abstand : abstandPunkte) {
                for (Punkt punkt : poi) {
                    if (abstand == roboter.getAbstand(punkt)) {
                        punkteSortiert[i] = punkt;
                    }
                }
                i++;
            }
            System.out.println();
        } else {
            punkteSortiert = poi;
        }
    }

    public void poiAbfahren(){
        punktEingeben();
        poiSortieren(eingegebenePunkte);
        int i = 0;
        for (Punkt punkt : punkteSortiert) {
            roboter.ausgabeAttribute();
            roboter.bewegeUm(punkt);
            punkteSortiert[i] = null;
            System.out.println(i + ". Abstand zu Punkte");
            for (int j = 1; j < punkteSortiert.length; j++) {
                roboter.ausgabeAttribute();
                punkteSortiert[j].ausgabeAttribute();
                punkteSortiert[i] = punkteSortiert[j];
                System.out.println("----------------");
                System.out.println(roboter.getAbstand(punkteSortiert[j]));
            }
            poiSortieren(punkteSortiert);
            System.out.println("\n\n");
        }
        roboter.ausgabeAttribute();
    }

    private void userPunktEingabe(){
        System.out.print("x: ");
        x = scanner.nextInt();
        System.out.print("y: ");
        y = scanner.nextInt();
    }

    public boolean checkGrenzen(int x, int y) {
        if (x < 0 || x > breite) {
            System.out.println("x-Wert liegt ausehalb des engegebenen Bereichs (0 <= x <= " + breite + ")");
            return false;
        }
        if (y > laenge || y < 0) {
            System.out.println("y-Wert liegt ausehalb des angegebenen Bereichs (0 <= y <= " + laenge + ")");
            return false;
        } else {
            return true;
        }
    }
}
