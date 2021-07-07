import java.awt.*;
import java.util.*;

/**
 * Beschreiben Sie hier die Klasse Spielfeld.
 *
 * @author Yannic Yu, Vivian Bär
 * @version 23.05.2021, 06. 07. 2021
 */

public class Spielfeld {
    private static final int LAENGE = 600;
    private static final int BREITE = 600;
    private static final Scanner SCANNER = new Scanner(System.in);

    private static final Random ZUFALLSGENERATOR = new Random();

    private static final int ROBOTER_DURCHMESSER = 10;
    private static Roboter roboter;
    private static Punkt roboterMittelpunkt;

    private static Punkt[] userPoi; // unsortierte poi die der Nutzer eingegeben hat
    private static Punkt[] poiArraySortiert; // poi nach abstand sortiert

    private static Leinwand leinwand;

    /**
     * Konstruktor der Klasse Spielfeld
     */
    Spielfeld() {
        roboter = new Roboter(new Punkt(1, 1), ROBOTER_DURCHMESSER, "Roboter", Color.red);
        roboterMittelpunkt = roboter.getMittelpunkt();
        leinwand = Leinwand.getLeinwand("Robotersimulation", LAENGE, BREITE, Color.WHITE);
    }

    /**
     * Hauptmethode der Klasse Spielfeld
     */
    public static void main(String[] args) {
        Spielfeld spielfeld = new Spielfeld();
        System.out.println("*----Robotersimulation: Hauptmenü----*" +
                "\n  (a) Points-of-Interest abfahren" +
                "\n  (b) Hindernisse umfahren" +
                "\n  (c) Stichwörter erkennen und antworten" +
                "\n  'ende', um Programm zu beenden");
        String userEingabe = "";
        while (!userEingabe.equalsIgnoreCase("ende")) {
            System.out.print("(a/b/c) > ");
            userEingabe = SCANNER.nextLine();
            if (userEingabe.equalsIgnoreCase("a")) {
                poiAbfahren();
                poiZeichnen();
            } else if (userEingabe.equalsIgnoreCase("b")) {
                hindernisseUmfahren();
            } else if (userEingabe.equalsIgnoreCase("c")) {
                roboter.spracherkennung();
            } else if (userEingabe.equalsIgnoreCase("ende")) {
                leinwand.close();
            }
        }
    }

    public static int getLaenge() {
        return LAENGE;
    }

    public static int getBreite() {
        return BREITE;
    }

    /*
     * ----------------------------------------Punkt----------------------------------------
     */

    /**
     * Methode punktEingabe
     * <p>
     * Nutzer wird um Eingabe für <code>punktAnzahl</code> gebeten.
     * Diese werden durch <code>checkGrenzen</code> geprüft und dann in <code>userPoi</code> gespeichert.
     */
    private static Punkt[] punktEingabe() {
        int punktAnzahl = eingabeIstZahl("Anzahl der Punkte: "); // Nutzer gibt an wie viele Punkte er erstellen möchte
        userPoi = new Punkt[punktAnzahl]; // werden hier gespeichert!

        for (int i = 0; i < punktAnzahl; i++) {
            System.out.println("Koordinaten von P" + (i + 1) + " (x, y): ");
            int x = eingabeIstZahl("x: ");
            int y = eingabeIstZahl("y: ");
            while (!checkGrenzen(x, y)) {
                x = eingabeIstZahl("x: ");
                y = eingabeIstZahl("y: ");
            }
            Punkt neuerPunkt = new Punkt(x, y); // neuer Punkt wird erstellt, der die eingegebenen Werte besitzt

            System.out.println("==> P" + (i + 1) + "(" + neuerPunkt.getX() + ", " + neuerPunkt.getY() + ")");

            userPoi[i] = neuerPunkt; // neuer Punkt wird zum Array hinzugefügt
        }
        return userPoi;
    }

    /**
     * Methode eingabeIstZahl
     * <p>
     * Überprüft ob die Eingabe eine ganze Zahl ist
     * <p>
     * eigentlich soll man hier die Exception <code>InputMismatchException</code> fangen.
     * Aber mit <code>NumberFormatException</code> kann man gleichzeitig auch überprüfen, ob der Input eine Zahl ist.
     *
     * @param bezeichnung gibt an um welche eingabe der Nutzer gebeten wird.
     */
    private static int eingabeIstZahl(String bezeichnung) {
        System.out.print(bezeichnung);
        int zahl = 0; // wegen return Statement muss "zahl" einen Wert haben
        boolean isZahl = false;
        while (!isZahl) { // solange der Input keine ganze Zahl ist, wird der Loop wiederholt
            String eingabe = SCANNER.nextLine(); // "parseInt" braucht einen parameter vom Typ String
            if (eingabe.equalsIgnoreCase("ende")) {
                break;
            }
            try {
                zahl = Integer.parseInt(eingabe); // überprüft ob Input ganzzahlig ist
                isZahl = true;
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Nur ganze Zahlen eingeben!");
                System.out.print(bezeichnung); // zeigt nochmal was der Nutzer eingeben soll
                isZahl = false;
            }
        }
        return zahl;
    }

    /**
     * Methode checkGrenzen
     * <p>
     * Überprüft ob die eingegebenen Koordinaten auch innerhalb des Spielfeldes liegen.
     *
     * @param x Wert von x
     * @param y Wert von y
     * @return <code>true</code>, wenn x und y innerhalb der Grenzen des Spielfeldes liegt
     */
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
     * Punkte werden nach ihrem Abstand zum <code>roboterMittelpunkt</code> sortiert.
     *
     * @param poi Array mit allen Points of Interest, die vom Nutzer erstellt wurden.
     */
    private static void poiSortieren(Punkt[] poi) {
        ArrayList<Punkt> poiArrayListSortiert = new ArrayList<>();
        for (Punkt punktU : poi) { // punktU... unsortierter Punkt
            if (!poiArrayListSortiert.isEmpty()) {
                int indexPunktS = poiArrayListSortiert.size(); // wird benutzt um neuen Index festzulegen
                for (Punkt punktS : poiArrayListSortiert) { // punktS... sortierter Punkt
                    if (roboterMittelpunkt.getAbstand(punktU) < roboterMittelpunkt.getAbstand(punktS)) {
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
     * Hier wird ein Punkt gesucht, den <code>roboterMittelpunkt</code> noch nicht abgefahren hat und den kürzesten Abstand zu ihm hat.
     * <p>
     * Sobald <code>naechsterPunkt</code> gefunden wurde, wird <code>roboterMittelpunkt</code> zu diesem bewegt.
     * <p>
     * Die restlichen Punkte werden wieder nach den kürzesten Abständen zum <code>roboterMittelpunkt</code> sortiert.
     * Das wird wiederholt, bis alle <code>poi</code> abgefahren wurden.
     */
    private static void poiAbfahren() {
        poiSortieren(punktEingabe()); // Sortiertes Array mit poi, die vom Nutzer erstellt worden sind
        // schon abgefahrene POI. Der letzte ist nicht dabei. Kann evtl. lokal angegeben werden
        Punkt[] poiAbgefahren = new Punkt[poiArraySortiert.length]; // speichert die schon abgefahrenen Punkte
        Punkt naechsterPunkt = new Punkt(); // nächster Punkt der vom Roboter abgefahren wird

        for (int i = 0; i < poiArraySortiert.length; i++) {
            for (Punkt punkt : poiArraySortiert) {
                if (!Arrays.asList(poiAbgefahren).contains(punkt)) { // nur wenn der Punkt noch nicht abgefahren wurde
                    naechsterPunkt.setXY(punkt.getX(), punkt.getY());
                    poiAbgefahren[i] = punkt; // "markiert" den neuen punkt als abgefahren
                    break; // sobald neuer Punkt gefunden wurde, kann der loop geschlossen werden
                }
            }

            System.out.println("Ausgangspunkt: (" + roboterMittelpunkt.getX() + ", " + roboterMittelpunkt.getY() + ")");
            for (Punkt punkte : poiArraySortiert) {
                System.out.println("Abstand zu Punkt" + (Arrays.asList(userPoi).indexOf(punkte) + 1) + " (" + punkte.getX() + ", " +
                        punkte.getY() + ") = " + roboterMittelpunkt.getAbstand(punkte));
            }

            int dx = naechsterPunkt.getX() - roboterMittelpunkt.getX(); // Verschiebevektor um Roboter zu bewegen
            int dy = naechsterPunkt.getY() - roboterMittelpunkt.getY();

            System.out.println("---\nnächster Punkt = Punkt (" + naechsterPunkt.getX() + ", " + naechsterPunkt.getY() +
                    ")\nVerschiebungsvektor: (" + dx + ", " + dy + ") = " +
                    roboterMittelpunkt.getAbstand(naechsterPunkt));

            roboterMittelpunkt.bewegeUm(dx, dy);

            poiSortieren(poiArraySortiert); // um mit neuen Roboterkoordinaten den nächsten näheren Punkt zu finden

            System.out.println("...Neuer Ausganspunkt: (" + roboterMittelpunkt.getX() + ", " + roboterMittelpunkt.getY() + ")\n----------");
        }
    }

    private static void poiZeichnen() {
        try {
            leinwand.zeichenflaeche.clearZeichenflaeche();
        } catch (NullPointerException nullPointerException) {
            System.out.println(nullPointerException);
        }
        roboter.setPos(1, 1);
        Punkt ausgangspunkt = new Punkt(roboter.getMittelpunkt().getX(), roboter.getMittelpunkt().getY()); //Mittelpunkt des Roboters
        zeichnen(null, poiArraySortiert);

        System.out.println("POI:");
        for (int i = poiArraySortiert.length - 1; i >= 0; i--) {
            poiArraySortiert[i].ausgabeAttribute();
        }

        for (int i = poiArraySortiert.length - 1; i >= 0; i--) {
            System.out.print("AUSGANGSPUNKT: ");
            ausgangspunkt.ausgabeAttribute();
            Punkt poi = poiArraySortiert[i];
            System.out.print("koordinaten poi: ");
            poi.ausgabeAttribute();
            double abstand = ausgangspunkt.getAbstand(poi);
            System.out.println("abstand: " + abstand);

            double winkel;
            if (poi.getX() > ausgangspunkt.getX() && poi.getY() >= ausgangspunkt.getY()) {
                winkel = Math.atan2((poi.getY() - ausgangspunkt.getY()), (poi.getX() - ausgangspunkt.getX()));
            } else if (poi.getX() > ausgangspunkt.getX() && poi.getY() < ausgangspunkt.getY()) {
                winkel = Math.atan2((poi.getY() - ausgangspunkt.getY()), (poi.getX() - ausgangspunkt.getX())) + 2 * Math.PI;
            } else if (poi.getX() == ausgangspunkt.getX() && poi.getY() > ausgangspunkt.getY()) {
                winkel = Math.PI/2;
            } else if (poi.getX() == ausgangspunkt.getX() && poi.getY() < ausgangspunkt.getY()) {
                winkel = (3*Math.PI)/2;
            } else {
                winkel = Math.atan2((poi.getY() - ausgangspunkt.getY()), (poi.getX() - ausgangspunkt.getX())) + Math.PI;
            }
            System.out.println("winkel: " + winkel + "\n");

            double j = 0;
            while (j != abstand && roboter.imSpielfeld()) {
                Punkt linienpunkt = new Punkt((int) (j * Math.cos(winkel)) + ausgangspunkt.getX(), (int) (j * Math.sin(winkel)) + ausgangspunkt.getY());
                roboter.setMittelpunkt(linienpunkt);
                j++;
                if (j > abstand) {
                    roboter.setMittelpunkt(poi);
                    break;
                }
                zeichnen(null, poiArraySortiert);
            }
            ausgangspunkt.setXY(poi.getX(), poi.getY());
        }
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
    private static ArrayList<Rechteck> hindernislisteErzeugen() {
        int nummerRechteck = 1;
        int nichtSchnittCounter = 0; // gibt an mit wie vielen Hindernissen sich das Rechteck nicht schneidet
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
                    nichtSchnittCounter++;
                } else {
                    // schneiden sich zwei Rechtecke, wird der nichtSchnittCounter zurückgesetzt, der Überlappungszähler erhöht und
                    // der for loop unterbrochen, da kein Grund besteht, ihn weiter auszuführen
                    nichtSchnittCounter = 0;
                    zaehlerUeberlappungen++;
                    break;
                }
            }
            // der Counter gibt an ob das neue Rechteck keines der Vorhandenen schneidet
            if (nichtSchnittCounter == hindernisliste.size()
                    && (LAENGE - randomRechteck.getX()) > randomRechteck.getLaenge()
                    && (BREITE - randomRechteck.getY()) > randomRechteck.getBreite()) {
                hindernisliste.add(randomRechteck);
                zaehlerUeberlappungen = 0; // um nur Überlapp. zuzulassen die hintereinander erfolgen
                nichtSchnittCounter = 0;
                nummerRechteck++; // um die Nummer des nachfolgenden Rechteckes zu bestimmen
            }

        }
        System.out.println("Erzeugte hindernisse: " + hindernisliste.size());
        return hindernisliste; // gibt die ArrayList<Rechteck> hindernisliste zurück
    }

    /**
     * generiert eine Zufällige Zahl in einem angegebenen Bereich
     *
     * @param von untere Grenze
     * @param bis obere Grenze
     * @return zufällige Zahl
     */
    private static int zufallszahl(int von, int bis) { // generiert eine zufällige Zahl
        return ZUFALLSGENERATOR.nextInt(bis + 1 - von) + von;
        /* ERKLÄRUNG:
         Grenzen z.B. von: 20, bis: 75
         (Zufallszahl im Bereich von 0 - 55) + (untere Grenze: 20) = zufällige Zahl im Bereich 20 - 75
         "bis + 1", weil die normalerweise die 0 noch mit reinkommt und dann nur im Bereich 0 - 54 gewählt wird.
         Durch "+ 1" wird im Bereich 0 - 55 gewählt.
         "- von) + von" weil man dadurch die untere Grenze bekommt.
         */
    }

    /**
     * generiert ein zufälliges Rechteck, mit zufälligen Attributen
     *
     * @param nummer gibt die Nummer des Rechtecks an
     * @return zufölliges Recheck
     */
    private static Rechteck createRandomRechteck(int nummer) { // generiert ein Rechteck mit zufälligen Werten
        int laengeMax = 100;
        int laengeMin = 10;
        int breiteMax = 100;
        int breiteMin = 10;
        int r = zufallszahl(0, 255);
        int g = zufallszahl(0, 255);
        int b = zufallszahl(0, 255);
        Punkt zufallsPunkt = new Punkt(zufallszahl(1, (LAENGE - 1)), zufallszahl(1, (BREITE - 1)));
        return new Rechteck(zufallsPunkt, zufallszahl(laengeMin, laengeMax), zufallszahl(breiteMin, breiteMax),
                "Rechteck " + nummer, new Color(r, g, b));
    }

    /**
     * Roboter umföhrt zuföllig generierten Hindernisse
     */
    private static void hindernisseUmfahren() {
        ArrayList<Rechteck> hindernisse = hindernislisteErzeugen();
        roboter.setPos(1, 1); // setzt den Roboter an die Startposition zurück

        int dx = 1;
        int dy = 1;
        boolean stuck = false;
        while (roboter.imSpielfeld() && !stuck) {
            if (roboter.maxX() + 1 == LAENGE && roboter.maxY() + 1 != BREITE) {
                roboter.bewegeUm(0, dy);
            } else if (roboter.maxY() + 1 == BREITE && roboter.maxX() + 1 != LAENGE) {
                roboter.bewegeUm(dx, 0);
            } else {
                roboter.bewegeUm(dx, dy);
            }
            for (Rechteck h : hindernisse) {
                while (roboter.zwischenX(h) && roboter.maxY() == h.minY() && roboter.imSpielfeld() && !stuck) {
                    roboter.bewegeUm(dx, 0);
                    for (Rechteck h2 : hindernisse) {
                        if (roboter.zwischenY(h2) && roboter.maxX() == h2.minX()) {
                            stuck = true;
                        }
                    }
                    zeichnen(hindernisse, null);
                }
                while (roboter.zwischenY(h) && roboter.maxX() == h.minX() && roboter.imSpielfeld() && !stuck) {
                    roboter.bewegeUm(0, dy);
                    for (Rechteck h2 : hindernisse) {
                        if (roboter.zwischenX(h2) && roboter.maxY() == h2.minY()) {
                            stuck = true;
                        }
                    }
                    zeichnen(hindernisse, null);
                }
            }
            zeichnen(hindernisse, null);
        }
        if (stuck) {
            System.out.println("Bin Stuck!");
        }
    }

    /**
     * zeichnet Hindernisse und Roboter auf Leinwand
     *
     * @param hindernisse <code>ArrayList</code>, die Hindernisse enthält
     */
    private static void zeichnen(ArrayList<Rechteck> hindernisse, Punkt[] poi) {
        leinwand.zeichnen(hindernisse, poi, roboter);
        leinwand.warten(5);
    }

    /**
     * zeichnet Hindernisse und Roboter auf Leinwand
     *
     * @param hindernisse <code>ArrayList</code>, die Hindernisse enthält
     */
    private static void zeichnen(ArrayList<Rechteck> hindernisse, Punkt[] poi) {
        leinwand.zeichnen(hindernisse, poi, roboter);
        leinwand.warten(5);
    }
}