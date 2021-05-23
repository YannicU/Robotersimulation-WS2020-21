import java.awt.*;
import java.util.*;

/**
 * Beschreiben Sie hier die Klasse Spielfeld.
 *
 * @author Yannic Yu
 * @version 23.05.2021
 */

public class Spielfeld {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int LAENGE = 600;
    private static final int BREITE = 600;
    // für die Hindernisse
    private static final Random ZUFALLSGENERATOR = new Random();
    //    private static Punkt roboterpunkt = new Punkt(0, 0);
    private static Roboter roboter = new Roboter(new Punkt(1, 1), 10, "Roboter", Color.red);
    private static Punkt roboterpunkt = new Punkt(1, 1);
    // für die Punkte
    private static int x;
    private static int y;
    private static Punkt[] userPoi;
    private static Punkt[] poiArraySortiert;
    private static Punkt[] poiAbgefahren;
    // für die Leinwand
    private static Leinwand leinwand;

    Spielfeld() {
    }

    public static void main(String[] args) {
        leinwand = new Leinwand("CoolesFenster1", LAENGE, BREITE);

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
                hindernisseUmfahren();
            } else if (userEingabe.equalsIgnoreCase("c")) {
                roboter.spracherkennung();
            } else if (userEingabe.equalsIgnoreCase("ende")) {
                leinwand.close();
            }
        }
    }

    /*
     * ----------------------------------------Punkt----------------------------------------
     */

    /**
     * Methode punktEingabe
     * <p>
     * Nutzer wird um Eingabe für <code>punktAnzahl</code> gebeten.
     * In dem folgenden for loop muss er die <code>x</code> und <code>y</code> Werte der Punkte angeben.
     * Diese werden durch <code>checkGrenzen</code> geprüft und dann in <code>userPoi</code> gespeichert.
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

    /**
     * Methode eingabeIstZahl
     * <p>
     * Überprüft ob die Eingabe eine ganze Zahl ist
     * <p>
     * eigentlich soll man hier die Exception <code>InputMismatchException</code> fangen.
     * Aber mit <code>NumberFormatException</code> kann man gleichzeitig auch überprüfen, ob der Input eine Zahl ist.
     * Ob der Input ganzzahlig ist wird mithilfe von <code>Integer.parseInt</code> überprüft.
     *
     * @param bezeichnung gibt an um welche eingabe der Nutzer gebeten wird.
     */
    private static int eingabeIstZahl(String bezeichnung) { // geforderter Parameter ist eigentlich nicht so wichtig, sieht am Ende aber besser aus
        int zahl = 0; // wegen return Statement muss "zahl" einen Wert haben
        boolean isZahl = false;
        while (!isZahl) { // solange der Input keine ganze Zahl ist, wird der Loop wiederholt
            String eingabe = SCANNER.nextLine(); // "parseInt" braucht einen parameter vom typ String
            if (eingabe.equalsIgnoreCase("ende")) {
                break;
            }
            try {
                zahl = Integer.parseInt(eingabe);
                isZahl = true;
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Nur ganze Zahlen eingeben!");
                System.out.print(bezeichnung); // zeigt nochmal was der Nutzer eingeben soll
                isZahl = false;
            }
        }
        return zahl;
    }

    // überprüft ob die eingegebenen Koordinaten auch innerhalb des definierten Bereichs liegen
    private static boolean checkGrenzen(int x, int y) {
        if (x < 0 || x > LAENGE) {
            System.out.println("x-Wert liegt ausehalb des engegebenen Bereichs (0 <= x <= " + LAENGE + ")");
            return false;
        }
        if (y < 0 || y > BREITE) {
            System.out.println("y-Wert liegt ausehalb des angegebenen Bereichs (0 <= y <= " + BREITE + ")");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Methode poiSortieren
     * <p>
     * Punkte werden nach ihrem Abstand zum <code>roboterpunkt</code> aufsteigend sortiert.
     *
     * @param poi ArrayListe mit allen Points of Interest, die vom Nutzer angegeben wurden.
     */
    private static void poiSortieren(Punkt[] poi) { // poi sind unsortierte Punkte
        ArrayList<Punkt> poiArrayListSortiert = new ArrayList<>();

        for (Punkt punktU : poi) {
            if (!poiArrayListSortiert.isEmpty()) {
                int indexPunktS = poiArrayListSortiert.size(); // wird benutzt um neuen Index festzulegen
                for (Punkt punktS : poiArrayListSortiert) {
                    if (roboterpunkt.getAbstand(punktU) < roboterpunkt.getAbstand(punktS)) {
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
     * Hier wird ein Punkt gesucht, den <code>roboterpunkt</code> nich abgefahren hat und am nähesten zu ihm liegt.
     * <p>
     * Sobald <code>naechsterPunkt</code> gefunden wurde, wird <code>roboterpunkt</code> zu diesem bewegt.
     * <p>
     * Die restlichen Punkte werden wieder nach den kürzesten Abständen zum <code>roboterpunkt</code> sortiert.
     * Das wird wiederholt, bis alle <code>poi</code> abgefahren wurden.
     */
    private static void poiAbfahren() {
        poiSortieren(punktEingabe());
        poiAbgefahren = new Punkt[poiArraySortiert.length]; // speichert die schon abgefahrenen Punkte
        Punkt naechsterPunkt = new Punkt(); // nächster Punkt der vom Roboter abgefahren wird

        for (int i = 0; i < poiArraySortiert.length; i++) {
            for (Punkt punkt : poiArraySortiert) {
                if (!Arrays.asList(poiAbgefahren).contains(punkt)) { // nur wenn der Punkt noch nicht abgefahren wurde
                    naechsterPunkt.setXY(punkt.getX(), punkt.getY());
                    poiAbgefahren[i] = punkt; // "markiert" den neuen punkt als abgefahren
                    break; // sobald neuer Punkt gefunden wurde, kann der loop geschlossen werden
                }
            }

            // ab hier wird viel an die Konsole geschrieben und der Roboter wird zum nächsten Punkt bewegt
            System.out.println("Ausgangspunkt: (" + roboterpunkt.getX() + ", " + roboterpunkt.getY() + ")");
            for (Punkt punkte : poiArraySortiert) {
                System.out.println("Abstand zu Punkt" + (getIndex(userPoi, punkte) + 1) + " (" + punkte.getX() + ", " +
                        punkte.getY() + ") = " + roboterpunkt.getAbstand(punkte));
            }

            int dx = naechsterPunkt.getX() - roboterpunkt.getX(); // Verschiebevektor um Roboter zu bewegen
            int dy = naechsterPunkt.getY() - roboterpunkt.getY();

            System.out.println("---\nnächster Punkt = Punkt (" + naechsterPunkt.getX() + ", " + naechsterPunkt.getY() +
                    ")\nVerschiebungsvektor: (" + dx + ", " + dy + ") = " +
                    roboterpunkt.getAbstand(naechsterPunkt));

//            roboterpunkt.bewegeUm(dx, dy);
            roboter.bewegeUm(dx, dy);

            poiSortieren(poiArraySortiert); // um mit neuen Roboterkoordinaten den nächsten näheren Punkt zu finden

            System.out.println("...Neuer Ausganspunkt: (" + roboterpunkt.getX() + ", " + roboterpunkt.getY() + ")\n----------");
        }
    }

    // vorsicht ist geklaut! ist für den code aber auch nicht wichtig. Nur für das Aussehen, wenn an die Konsole
    // geschrieben wird.
    private static <T> int getIndex(T[] arr, T val) {
        return Arrays.asList(arr).indexOf(val);
    }

    // funktioniert noch nicht.
    // soll alle Punkte auf einer Linie angeben, die der Roboter fahren soll
    private static ArrayList<Punkt> linienpunkte(Punkt[] poiAbgefahren) {
        ArrayList<Punkt> linienpunkte = new ArrayList<>();
        Punkt ausgangspunkt = new Punkt(0, 0);
        for (Punkt punkt : poiAbgefahren) {
            double abstand = (int) ausgangspunkt.getAbstand(punkt);
            double winkel = Math.atan2((punkt.getY() - ausgangspunkt.getY()) + ausgangspunkt.getY(), (punkt.getX() - ausgangspunkt.getX()));
            int i = 0;
            while (i != abstand) {
                Punkt linienpunkt = new Punkt((int) (i * Math.cos(winkel)) + ausgangspunkt.getX(), (int) (i * Math.sin(winkel)) + ausgangspunkt.getY());
                linienpunkte.add(linienpunkt);
                i++;
            }
            ausgangspunkt.ausgabeAttribute();
            ausgangspunkt = punkt;
            ausgangspunkt.ausgabeAttribute();
        }
        return linienpunkte;
    }

    /*
     * ----------------------------------------Hindernisse----------------------------------------
     */

    /**
     * Methode hindernislisteErzeugen
     * <p>
     * Hier gibt der Nutzer an wie viele Hindernisse er erstellen möchte.
     * Diese werden dann zufällig generiert und verteilt, ohne dass sie sich schneiden, oder die definierten Grenzen
     * überschreiten.
     */
    public static ArrayList<Rechteck> hindernislisteErzeugen() {
        int nummerRechteck = 1;
        int counter = 0; // gibt an mit wie vielen Hindernissen sich das Rechteck nicht schneidet
        int zaehlerUeberlappungen = 0;
        int maxUeberlappungen = 50;
        ArrayList<Rechteck> hindernisliste = new ArrayList<>();

        // Nutzer wird nach Angaben gefragt, die in 'eingabeIstZahl' auf korrektes Format geprüft wird
        System.out.print("Anzahl der Hindernisse: ");
        int hindernisseAnzahl = eingabeIstZahl("Anzahl der Hindernisse: ");

        while (hindernisliste.size() < hindernisseAnzahl && zaehlerUeberlappungen < maxUeberlappungen) {
            Rechteck randomRechteck = createRandomRechteck(nummerRechteck);
            for (Rechteck hindernis : hindernisliste) {
                // überprüft ob das Rechteck sich überlappt und sich innerhalb der Grenzen befindet
                if (!randomRechteck.ueberlappt(hindernis)) {
                    counter++;
                } else {
                    // schneiden sich zwei Rechtecke, wird der counter zurückgesetzt, der Überlappungszähler erhöht und
                    // der for loop unterbrochen, da kein Grund besteht, ihn weiter auszuführen
                    counter = 0;
                    zaehlerUeberlappungen++;
                    break;
                }
            }
            // der Counter gibt an ob das neue Rechteck keines der Vorhandenen schneidet
            if (counter == hindernisliste.size()
                    && (LAENGE - randomRechteck.getX()) > randomRechteck.getLaenge()
                    && (BREITE - randomRechteck.getY()) > randomRechteck.getBreite()) {
                hindernisliste.add(randomRechteck);
                zaehlerUeberlappungen = 0; // um nur Überlapp. zuzulassen die hintereinander erfolgen
                counter = 0;
                nummerRechteck++; // um die Nummer des nachfolgenden Rechteckes zu bestimmen
            }

        }
        System.out.println("Erzeugte hindernisse: " + hindernisliste.size());
        return hindernisliste; // gibt die ArrayList<Rechteck> hindernisliste zurück
    }

    private static int zufallszahl(int von, int bis) { // generiert eine zufällige Zahl
        // Grenzen z.B. von: 20, bis: 75
        // (Zufallszahl im Bereich von 0 - 55) + (untere Grenze: 20) = zufällige Zahl im Bereich 20 - 75
        // "bis + 1", weil die normalerweise die 0 noch mit reinkommt und dann nur im Bereich 0 - 54 gewählt wird.
        // Durch "+ 1" wird im Bereich 0 - 55 gewählt.
        // "- von) + von" weil man dadurch die untere Grenze bekommt.
        return ZUFALLSGENERATOR.nextInt(bis + 1 - von) + von;
    }

    private static Rechteck createRandomRechteck(int nummer) { // generiert ein Rechteck mit zufälligen Werten
        int laengeMax = 100;
        int laengeMin = 20;
        int breiteMax = 100;
        int breiteMin = 20;
        int r = zufallszahl(0, 255);
        int g = zufallszahl(0, 255);
        int b = zufallszahl(0, 255);
        Punkt zufallsPunkt = new Punkt(zufallszahl(1, (LAENGE - 1)), zufallszahl(1, (BREITE - 1)));
        return new Rechteck(zufallsPunkt, zufallszahl(laengeMin, laengeMax), zufallszahl(breiteMin, breiteMax),
                "Rechteck " + nummer, new Color(r, g, b));
    }

    private static void zeichnen(ArrayList<Rechteck> hindernisse, Roboter roboter) {
        int millisekunden = 5;
        leinwand.zeichnen(hindernisse, roboter);
        leinwand.warten(millisekunden);
    }

    private static void hindernisseUmfahren() {
        ArrayList<Rechteck> hindernisse = hindernislisteErzeugen();
        leinwand.zeichnen(hindernisse, roboter);
        leinwand.setVisible(true);

        int dx = 1;
        int dy = 1;
        boolean stuck = false;
        while (roboter.imSpielfeld(LAENGE, BREITE) && !stuck) {
            if (roboter.maxX() + 1 == LAENGE && roboter.maxY() + 1 != BREITE) {
                roboter.bewegeUm(0, dy);
            } else if (roboter.maxY() + 1 == BREITE && roboter.maxX() + 1 != LAENGE) {
                roboter.bewegeUm(dx, 0);
            } else {
                roboter.bewegeUm(dx, dy);
            }
            for (Rechteck h : hindernisse) {
                while (roboter.zwischenX(h) && roboter.maxY() == h.minY() && roboter.imSpielfeld(LAENGE, BREITE) && !stuck) {
                    roboter.bewegeUm(dx, 0);
                    for (Rechteck h2 : hindernisse) {
                        if (roboter.zwischenY(h2) && roboter.maxX() == h2.minX()) {
                            stuck = true;
                        }
                    }
                    zeichnen(hindernisse, roboter);
                }
                while (roboter.zwischenY(h) && roboter.maxX() == h.minX() && roboter.imSpielfeld(LAENGE, BREITE) && !stuck) {
                    roboter.bewegeUm(0, dy);
                    for (Rechteck h2 : hindernisse) {
                        if (roboter.zwischenX(h2) && roboter.maxY() == h2.minY()) {
                            stuck = true;
                        }
                    }
                    zeichnen(hindernisse, roboter);
                }
            }
            zeichnen(hindernisse, roboter);
        }
    }
}