import java.awt.*;
import java.util.*;

/**
 * Hauptklasse des Projektes, in der alles zusammen kommt und abspielt.
 *
 * @author Vivian Bär, Yannic Yu
 * @version 06.07.2021
 */

public class Spielfeld {
    private static final int LAENGE = 700; // x-Richtung
    private static final int BREITE = 700; // y-Richtung
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random ZUFALLSGENERATOR = new Random();
    private static final int ROBOTER_DURCHMESSER = 10;
    private static final int DELAY = 5;

    private static Roboter roboter;
    private static Leinwand leinwand;

    /**
     * Konstruktor der Klasse Spielfeld
     */
    Spielfeld() {
        roboter = new Roboter(new Punkt(1, 1), ROBOTER_DURCHMESSER, "Roboter", Color.red);
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
                "\n  'ende', um Programm zu beenden" +
                "\nBitte wählen Sie eine der drei Aufgaben!");
        String userEingabe = "";
        while (!userEingabe.equalsIgnoreCase("ende")) {
            System.out.print("(a/b/c) > ");
            userEingabe = SCANNER.nextLine();
            if (userEingabe.equalsIgnoreCase("a")) {
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
        int punktAnzahl = eingabeIstZahl("Wieviele Punkte sollen abgefahren werden? "); // Nutzer gibt an wie viele Punkte er erstellen möchte
        // unsortierte poi die der Nutzer eingegeben hat
        Punkt[] userPoi = new Punkt[punktAnzahl]; // werden hier gespeichert!

        for (int i = 0; i < punktAnzahl; i++) {
            System.out.println("Koordinaten von P" + (i + 1) + " (x, y): ");
            int x = eingabeIstZahl("x: ");
            int y = eingabeIstZahl("y: ");
            while (!checkGrenzen(x, y)) {
                x = eingabeIstZahl("x: ");
                y = eingabeIstZahl("y: ");
            }
            Punkt neuerPunkt = new Punkt(x, y); // neuer Punkt mit gewünschten Werten

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
        int zahl = 0; // muss wegen return Statement einen Wert haben
        boolean istZahl = false;
        while (!istZahl) { // solange der Input keine ganze Zahl ist, wird der Loop wiederholt
            String eingabe = SCANNER.nextLine(); // "parseInt" braucht einen parameter vom Typ String
            if (eingabe.equalsIgnoreCase("ende")) {
                break;
            }
            try {
                zahl = Integer.parseInt(eingabe); // überprüft ob Input ganzzahlig ist
                istZahl = true;
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Bitte geben Sie nur ganze Zahlen ein!");
                System.out.print(bezeichnung); // zeigt nochmal was der Nutzer eingeben soll
                istZahl = false;
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
        int grenzeX = LAENGE - (roboter.getDurchmesser() / 2);
        int grenzeY = BREITE - (roboter.getDurchmesser() / 2);
        if (x < 0 || x > grenzeX) {
            System.out.println("x-Wert liegt außerhalb des angegebenen Bereichs (0 <= x <= " + grenzeX + ")");
            return false;
        }
        if (y < 0 || y > grenzeY) {
            System.out.println("x-Wert liegt außerhalb des angegebenen Bereichs (0 <= y <= " + grenzeY + ")");
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
     * @param poi unsortierte Points of Interest
     * @return Points of Interest sortiert in einem Array
     */
    private static Punkt[] poiSortieren(Punkt[] poi) {
        ArrayList<Punkt> poiSortiertArrayList = new ArrayList<>();
        for (Punkt punktU : poi) { // punktU... unsortierter Punkt
            if (!poiSortiertArrayList.isEmpty()) {
                int indexPunktS = poiSortiertArrayList.size(); // wird benutzt um Index festzustellen
                for (Punkt punktS : poiSortiertArrayList) { // punktS... sortierter Punkt
                    if (roboter.getMittelpunkt().getAbstand(punktU) < roboter.getMittelpunkt().getAbstand(punktS)) {
                        indexPunktS--; // wenn Abstand zu punktU kleiner ist als zu einem in der sortierten Liste, wird sein Index kleiner
                    }
                }
                poiSortiertArrayList.add(indexPunktS, punktU); // fügt punktU beim entsprechenden Index der ArrayList hinzu
            } else {
                poiSortiertArrayList.add(punktU); // erster Punkt in sortierter Liste, egal welcher abstand
            }
        }
        // um die ArrayList in Array zu ändern
        Punkt[] poiSortiert = new Punkt[poiSortiertArrayList.size()];
        poiSortiertArrayList.toArray(poiSortiert);
        return poiSortiert;
    }

    /**
     * Methode poiAbfahren
     * <p>
     * Hier wird ein Punkt aus <code>poi</code> gesucht, den <code>roboterMittelpunkt</code> noch nicht abgefahren hat und den kürzesten Abstand zu ihm hat.
     * Für den nächsten Punkt werden die abstände neu berechnet. Solange bis alle abgefahren wurden.
     *
     * @param poi unsortierte Points of Interest
     * @return die nacheinander abgefahrenen Points of Interest (als Array)
     */
    private static Punkt[] poiAbfahren(Punkt[] poi) {
        Punkt[] poiSortiert = poiSortieren(poi);
        Punkt[] poiAbgefahren = new Punkt[poiSortiert.length]; // speichert die schon abgefahrenen Punkte
        Punkt naechsterPunkt = new Punkt(); // nächster Punkt der vom Roboter abgefahren wird

        for (int i = 0; i < poiSortiert.length; i++) {
            for (Punkt punkt : poiSortiert) {
                if (!Arrays.asList(poiAbgefahren).contains(punkt)) { // nur wenn der Punkt noch nicht abgefahren wurde
                    naechsterPunkt.setXY(punkt.getX(), punkt.getY());
                    poiAbgefahren[i] = punkt; // "markiert" den neuen punkt als abgefahren
                    break; // da neuer Punkt gefunden wurde, kann der loop geschlossen werden
                }
            }
            Punkt roboterMittelpunkt = roboter.getMittelpunkt();
            System.out.println("Ausgangspunkt: (" + roboterMittelpunkt.getX() + ", " + roboterMittelpunkt.getY() + ")");
            for (Punkt punkte : poiSortiert) {
                System.out.println("Abstand zu Punkt" + (Arrays.asList(poi).indexOf(punkte) + 1) + " (" + punkte.getX() + ", " +
                        punkte.getY() + ") = " + roboterMittelpunkt.getAbstand(punkte));
            }
            int dx = naechsterPunkt.getX() - roboterMittelpunkt.getX(); // um Roboter zu bewegen
            int dy = naechsterPunkt.getY() - roboterMittelpunkt.getY();
            System.out.println("---\n nächster Punkt = Punkt (" + naechsterPunkt.getX() + ", " + naechsterPunkt.getY() +
                    ")\n Verschiebungsvektor: (" + dx + ", " + dy + ") = " +
                    roboterMittelpunkt.getAbstand(naechsterPunkt));
            roboter.bewegeUm(dx, dy);
            poiSortiert = poiSortieren(poiSortiert); // um mit neuen Roboterkoordinaten den nächsten näheren Punkt zu finden
            System.out.println(" Neuer Ausganspunkt: (" + roboter.getMittelpunkt().getX() + ", " + roboter.getMittelpunkt().getY() + ")\n----------");
        }
        roboter.setPos(1, 1); // Setzt roboter an ausgangsposition zurück
        return poiAbgefahren;
    }

    /**
     * Methode poiZeichnen
     * <p>
     * Zeichnet die vom Nutzer erstellten Points of Interest, die der Roboter nacheinander und nach
     * ihrem kürzestem Abstand sortiert abföhrt.
     */
    private static void poiZeichnen() {
        roboter.setPos(1, 1);
        Punkt[] userPoi = punktEingabe();
        Punkt[] poiAbgefahren = poiAbfahren(userPoi);
        Punkt ausgangspunkt = new Punkt(roboter.getMittelpunkt().getX(), roboter.getMittelpunkt().getY()); //Mittelpunkt des Roboters
        zeichnen(null, poiAbgefahren);

//        System.out.println("POI:");
//        for (Punkt poi : poiAbgefahren) {
//            poi.ausgabeAttribute();
//        }
        for (Punkt poi : poiAbgefahren) {
            double abstand = ausgangspunkt.getAbstand(poi);
            boolean negativeX = false;
            double winkel = 0;
            // ermittelt die Richtung zum nächsten Punkt
            if (poi.getX() > ausgangspunkt.getX() && poi.getY() >= ausgangspunkt.getY()) {
                winkel = Math.atan2((poi.getY() - ausgangspunkt.getY()), (poi.getX() - ausgangspunkt.getX()));
            } else if (poi.getX() > ausgangspunkt.getX() && poi.getY() < ausgangspunkt.getY()) {
                winkel = Math.atan2((poi.getY() - ausgangspunkt.getY()), (poi.getX() - ausgangspunkt.getX())) + (2 * Math.PI);
            } else if (poi.getX() < ausgangspunkt.getX()) {
                winkel = Math.atan2((poi.getY() - ausgangspunkt.getY()), (poi.getX() - ausgangspunkt.getX())) + Math.PI;
                negativeX = true;
            } else if (poi.getX() == ausgangspunkt.getX() && poi.getY() > ausgangspunkt.getY()) {
                winkel = Math.PI / 2;
            } else if (poi.getX() == ausgangspunkt.getX() && poi.getY() < ausgangspunkt.getY()) {
                winkel = (3 * Math.PI) / 2;
            }
//            roboter.getPos().ausgabeAttribute();
//            System.out.print("AUSGANGSPUNKT: ");
//            ausgangspunkt.ausgabeAttribute();
//            System.out.print("koordinaten poi: ");
//            poi.ausgabeAttribute();
//            System.out.println("abstand: " + abstand);
//            System.out.println("winkel: " + Math.toDegrees(winkel));
            double j = 0;
            while (j <= abstand && roboter.imSpielfeld()) {
                Punkt linienpunkt; // ein Punkt der sich auf der Linie zum nächsten Punkt befindet
                if (!negativeX) {
                    linienpunkt = new Punkt((int) (j * Math.cos(winkel)) + ausgangspunkt.getX(), (int) (j * Math.sin(winkel)) + ausgangspunkt.getY());
                } else {
                    linienpunkt = new Punkt((int) -(j * Math.cos(winkel)) + ausgangspunkt.getX(), -(int) (j * Math.sin(winkel)) + ausgangspunkt.getY());
                }
                roboter.setMittelpunkt(linienpunkt);
                j++;
                if (j > abstand) { // da die Double Werte nicht genau die Integer Werte annehmen können
                    roboter.setMittelpunkt(poi);
                    break;
                }
                zeichnen(null, poiAbgefahren);
            }
            ausgangspunkt.setXY(poi.getX(), poi.getY()); // um die korrekte Richtung für den nächsten Punkt zu ermitteln
        }
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
        int nichtSchnittCounter = 0; // gibt an mit wie vielen Hindernissen sich das Rechteck NICHT schneidet
        int zaehlerUeberlappungen = 0;
        int maxUeberlappungen = 50;
        ArrayList<Rechteck> hindernisliste = new ArrayList<>();
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
        return hindernisliste;
    }

    /**
     * generiert eine Zufällige Zahl in einem angegebenen Bereich.
     *
     * @param von untere Grenze
     * @param bis obere Grenze
     * @return zufällige Zahl
     */
    private static int zufallszahl(int von, int bis) { // generiert eine zufällige Zahl
        return ZUFALLSGENERATOR.nextInt(bis + 1 - von) + von;
        /* ERKLÄRUNG:
         Grenzen z.B. von: 20, bis: 75
         von = 20, bis = 75   -->   bis + 1 - von = 55 -> Zufallszahl im Bereich von 0 - 55
         "+ 1", weil die 0 noch mitgezählt wird. Dann würde der Bereich nur von 0 - 54 gehen.
         "+ von" weil man dadurch die untere Grenze bekommt.
         gibt der Zufallsgenerator z.B. 16 raus (außerhalb der geünschten Grenze) bewirkt "+ von", dass die Zahl
         immer innerhalb der Grenze ist.
         */
    }

    /**
     * generiert ein zufälliges Rechteck, mit zufälligen Werten.
     *
     * @param nummer gibt die Nummer des Rechtecks an
     * @return zufölliges Recheck
     */
    private static Rechteck createRandomRechteck(int nummer) {
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
     * Roboter umföhrt zufällig generierten Hindernisse
     */
    private static void hindernisseUmfahren() {
        ArrayList<Rechteck> hindernisse = hindernislisteErzeugen();
        roboter.setPos(1, 1); // setzt den Roboter an die Startposition zurück
        int dx = 1;
        int dy = 1;
        boolean stuck = false;
        while (roboter.imSpielfeld() && !stuck) {
            if (roboter.anWandX()) {
                roboter.bewegeUm(0, dy);
            } else if (roboter.anWandY()) {
                roboter.bewegeUm(dx, 0);
            } else {
                roboter.bewegeUm(dx, dy);
            }
            for (Rechteck h : hindernisse) { // überprüft ob Roboter zwischen zwei Rechtecken steckt
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
     * @param hindernisse <code>ArrayList</code>, die die Hindernisse enthält
     */
    private static void zeichnen(ArrayList<Rechteck> hindernisse, Punkt[] poi) {
        leinwand.zeichnen(hindernisse, poi, roboter);
        leinwand.warten(DELAY);
    }
}