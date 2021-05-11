import java.util.*;
import java.awt.Color;

/**
 * Beschreiben Sie hier die Klasse Spielfeld.
 *
 * @author Yannic Yu
 * @version 11.05.2021
 */

public class Spielfeld {
    private static final Punkt ROBOTERPUNKT = new Punkt(0, 0);
    private static final Random ZUFALLSGENERATOR = new Random();
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int LAENGE = 500;
    private static final int BREITE = 500;
    private static int x;
    private static int y;
    private static Punkt[] userPoi;
    private static Punkt[] poiArraySortiert;

    Spielfeld() {
        Roboter roboter = new Roboter();
    }

    public static void main(String[] args) {
        Roboter roboter = new Roboter();
        System.out.println("Hey, was willst du machen?" +
                "\n  (a) Points-of-Interest abfahren" +
                "\n  (b) Hindernisse umfahren" +
                "\n  (c) Stichwörter erkennen und antworten" +
                "\n  - Tippe 'ende', um Programm zu beenden");
        String userEingabe = "";
        while (!userEingabe.equalsIgnoreCase("ende")) {
            System.out.print("(a/b/c) > ");
            userEingabe = SCANNER.nextLine();
            if (userEingabe.equalsIgnoreCase("a")) {
                poiAbfahren();
            } else if (userEingabe.equalsIgnoreCase("b")) {
                Leinwand leinwand = new Leinwand(LAENGE, BREITE);
                while (true) {
                    Leinwand.Zeichenflaeche zeichenflaeche = new Leinwand.Zeichenflaeche();
                }
            } else if (userEingabe.equalsIgnoreCase("c")) {
                roboter.spracherkennung();
            }
        }
    }

    /**
     * Methode punktEingabe
     * <p>
     * Die zuvor angegebene Anzahl der Punkte wird als obere Grenze des loops verwendet.
     * In dem loop gibt der Nutzer die x und y Werte der Punkte an.
     * Diese werden mithilfe einer anderen Methode ("checkGrenzen") auf ihre Grenzen geprüft.
     * Sollten die Grenzen nicht stimmen, muss der Nutzer die Werte für den aktuellen Punkt erneut eingeben.
     */
    private static Punkt[] punktEingabe() {
        System.out.print("Anzahl der Punkte: ");
        int punktAnzahl = eingabeIstZahl("Anzahl der Punkte: "); // Nutzer gibt an wie viele Punkte er erstellen möchte
        userPoi = new Punkt[punktAnzahl]; // werden hier gespeichert!

        for (int i = 0; i < punktAnzahl; i++) {
            System.out.println("Koordinaten von P" + (i + 1) + " (x, y): ");
            userPunktEingabe(); // neue Nutzereingabe wird gefordert
            Punkt neuerPunkt = new Punkt(x, y); // neuer Punkt wird erstellt, der die eingegebenen Werte besitzt

            System.out.println("==> P" + (i + 1) + "(" + neuerPunkt.getX() + ", " + neuerPunkt.getY() + ")");

            userPoi[i] = neuerPunkt; // neuer Punkt wird zum Array hinzugefügt
        }
        return userPoi;
    }

    private static void userPunktEingabe() {
        System.out.print("x: ");
        x = eingabeIstZahl("x: ");
        System.out.print("y: ");
        y = eingabeIstZahl("y: ");
        while (!checkGrenzen(x, y)) {
            System.out.print("x: ");
            x = eingabeIstZahl("x: ");
            System.out.print("y: ");
            y = eingabeIstZahl("y: ");
        }
    }

    // überprüft nur ob die Eingabe auch eine Zahl ist. Sonst kommt eine Fehlermeldung
    private static int eingabeIstZahl(String bezeichnung) { // geforderter Parameter ist eigentlich nicht so wichtig, sieht am Ende aber besser aus
        int zahl = 0;
        boolean isZahl = false;
        while (!isZahl) {
            String eingabe = SCANNER.nextLine();
            try {
                zahl = Integer.parseInt(eingabe);
                isZahl = true;
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Nur Zahlen eingeben!");
                System.out.print(bezeichnung);
                isZahl = false;
            }
        }
        return zahl;
    }

    // überprüft ob die eingegebenen Koordinaten auch innerhalb des definierten Bereichs liegen
    private static boolean checkGrenzen(int x, int y) {
        if (x < 0 || x > BREITE) {
            System.out.println("x-Wert liegt ausehalb des engegebenen Bereichs (0 <= x <= " + BREITE + ")");
            return false;
        }
        if (y < 0 || y > LAENGE) {
            System.out.println("y-Wert liegt ausehalb des angegebenen Bereichs (0 <= y <= " + LAENGE + ")");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Methode poiSortieren
     * <p>
     * Die Methode benötigt ein Array "poi". Die Punkte in dem Array "poi" werden nach ihrem Abstand zum
     * Roboterpunkt aufsteigend sortiert.
     * <p>
     * Die sortierten Punkte werden in einer ArrayList "poiArrayListSortiert" gespeichert und als Array "poiArraySortiert"
     * wieder ausgegeben.
     */
    private static void poiSortieren(Punkt[] poi) { // poi sind unsortierte Punkte
        ArrayList<Punkt> poiArrayListSortiert = new ArrayList<>();

        for (Punkt punktU : poi) {
            if (!poiArrayListSortiert.isEmpty()) {
                int indexPunktS = poiArrayListSortiert.size(); // wird benutzt um neuen Index festzulegen
                for (Punkt punktS : poiArrayListSortiert) {
                    if (ROBOTERPUNKT.getAbstand(punktU) < ROBOTERPUNKT.getAbstand(punktS)) {
                        indexPunktS--; // wenn ein der PunktU kleiner ist als ein Punkt in sortierter Liste, verringert sich sein Index
                    }
                }
                poiArrayListSortiert.add(indexPunktS, punktU); // fügt den neuen punkt an entsprechender stelle in der ArrayList ein
            } else {
                poiArrayListSortiert.add(punktU); // erster Punkt in sortierter Liste, egal welcher abstand
            }
        }
        // um die ArrayList in Array zu ändern (weil ich davor nur mit Array gearbeitet habe. So muss ich nichts ändern)
        poiArraySortiert = new Punkt[poiArrayListSortiert.size()];
        poiArraySortiert = poiArrayListSortiert.toArray(poiArraySortiert);
    }

    /**
     * Methode poiAbfahren
     * <p>
     * es wird ein neuer Punkt naechsterPunkt erzeugt, der angibt, welchen Punkt der Roboter als nächstes anfahren kann.
     * Dieser wird nur dann erzeugt, wenn es nicht der Roboterpunkt selbst ist und er nicht schon vom Roboter angefahren
     * wurde.
     * Das wird geprüft, indem der erzeugten naechstenPunkt in dem leeren Array poiAbgefahren gespeichert wird und
     * zukünftig ausgeschlossen wird.
     * Sobald ein naechsterPunkt erzeugt wurde, wird von ihm der Verschiebungsvektor ausgerechnet und der Roboterpunkt
     * um diesen verschoben.
     * Dann werden die Punkte aus dem Array poiArraySortiert erneut mit den neuen Koordinaten des Roboterpunktes, nach ihrem
     * kürzesten Abstand berechnet.
     */
    private static void poiAbfahren() {
        poiSortieren(punktEingabe());
        Punkt[] poiAbgefahren = new Punkt[poiArraySortiert.length];
        Punkt naechsterPunkt = new Punkt();
        for (int i = 0; i < poiArraySortiert.length; i++) {
            for (Punkt punkt : poiArraySortiert) {
                if (!Arrays.asList(poiAbgefahren).contains(punkt)) {
                    naechsterPunkt.setXY(punkt.getX(), punkt.getY());
                    poiAbgefahren[i] = punkt;
                    break; // sobald neuer Punkt existiert, wird dieser gespeichert und als nächstes abgefahren
                }
            }

            // ab hier wird viel an die Konsole geschrieben und der Roboter zum nächsten Punkt bewegt
            System.out.println("Ausgangspunkt: (" + ROBOTERPUNKT.getX() + ", " + ROBOTERPUNKT.getY() + ")");
            for (Punkt punkte : poiArraySortiert) {
                System.out.println("Abstand zu Punkt" + (getIndex(userPoi, punkte) + 1) + " (" + punkte.getX() + ", " +
                        punkte.getY() + ") = " + ROBOTERPUNKT.getAbstand(punkte));
            }

            int dx = naechsterPunkt.getX() - ROBOTERPUNKT.getX(); // Verschiebevektor um Roboter zu bewegen
            int dy = naechsterPunkt.getY() - ROBOTERPUNKT.getY();

            System.out.println("---\nnächster Punkt = Punkt (" + naechsterPunkt.getX() + ", " + naechsterPunkt.getY() +
                    ")\nVerschiebungsvektor: (" + dx + ", " + dy + ") = " +
                    ROBOTERPUNKT.getAbstand(naechsterPunkt));

            ROBOTERPUNKT.bewegeUm(dx, dy);
            poiSortieren(poiArraySortiert); // um mit neuen Roboterkoordinaten den nächsten näheren Punkt zu finden

            System.out.println("...Neuer Ausganspunkt: (" + ROBOTERPUNKT.getX() + ", " + ROBOTERPUNKT.getY() + ")\n----------");
        }
    }

    private static <T> int getIndex(T[] arr, T val) { // vorsicht ist geklaut! ist für den code aber auch nicht wichtig.
        return Arrays.asList(arr).indexOf(val);
    }

    public int getLaenge() {
        return LAENGE;
    }

    public int getBreite() {
        return BREITE;
    }

    /**
     * Methode hindernislisteErzeugen
     * <p>
     * Hier kann der Nutzer angeben wie viele Hindernisse er auf dem Spielfeld haben möchte.
     * Dann werden für die genannte Anzahl, unter bestimmten bestimmungen, Hindernisse ersellt und in einer
     * ArrayList gespeichert und am ende zurückgegeben.
     */
    public ArrayList<Rechteck> hindernislisteErzeugen() {
        int rechteckNummer = 1;
        int counter = 0; // gibt an wie viele Hindernisse das neue Rechteck nicht schneidet
        int ueberlappungZaehler = 0;
        int ueberlappungMax = 50;
        ArrayList<Rechteck> hindernisliste = new ArrayList<>();

        // Nutzer wird nach Angaben gefragt, die in 'eingabeIstZahl' auf korrektes Format geprüft wird
        System.out.print("Anzahl der Hindernisse: ");
        int hindernisseAnzahl = eingabeIstZahl("Anzahl der Hindernisse: ");

        while (hindernisliste.size() < hindernisseAnzahl && ueberlappungZaehler < ueberlappungMax) {
            Rechteck zufallR = createZufallsRechteck(rechteckNummer);
            for (Rechteck hindernis : hindernisliste) {
                if (!zufallR.ueberlappt(hindernis)
                        && (LAENGE - zufallR.getPositionX()) > zufallR.getLaenge()
                        && (BREITE - zufallR.getPositionY()) > zufallR.getBreite()) {
                    counter++;
                } else {
                    // schneiden sich zwei Rechtecke, wird der counter zurückgesetzt, der Überlappungszähler erhöht und
                    // der for loop unterbrochen, da kein Grund besteht, ihn weiter auszuführen
                    counter = 0;
                    ueberlappungZaehler++;
                    break;
                }
            }
            // der Counter gibt an ob das neue Rechteck keines der Vorhandenen schneidet
            if (counter == hindernisliste.size()) {
                hindernisliste.add(zufallR);
                ueberlappungZaehler = 0; // um nur Überlapp. zuzulassen die hintereinander erfolgen
                counter = 0;
                rechteckNummer++; // um die Nummer des nachfolgenden Rechteckes zu bestimmen
            }

        }
        System.out.println("Erzeugte hindernisse: " + hindernisliste.size());
        return hindernisliste;
    }

    public Rechteck createZufallsRechteck(int nummer) { // generiert ein Rechteck mit zufälligen Werten
        int laengeMax = 100;
        int laengeMin = 20;
        int breiteMax = 100;
        int breiteMin = 20;
        float r = ZUFALLSGENERATOR.nextFloat();
        float g = ZUFALLSGENERATOR.nextFloat();
        float b = ZUFALLSGENERATOR.nextFloat();
        Punkt zufallsPunkt = new Punkt(zufallszahl(1, (LAENGE - 1)), zufallszahl(1, (BREITE - 1)));
        return new Rechteck(zufallsPunkt, zufallszahl(laengeMin, laengeMax), zufallszahl(breiteMin, breiteMax),
                "Rechteck " + nummer, new Color(r, g, b));
    }

    private int zufallszahl(int von, int bis) { // generiert zufallszahl, in den vorgegebenen Grenzen
        return ZUFALLSGENERATOR.nextInt(bis + 1 - von) + von;
    }

    private void zeichnen(ArrayList<Rechteck> hindernisse) {
    }
}