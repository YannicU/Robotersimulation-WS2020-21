import java.util.*;
import java.awt.Color;

/**
 * Beschreiben Sie hier die Klasse Spielfeld.
 *
 * @author Yannic Yu
 * @version 01.05.2021
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
    private static Punkt[] poiSortiert;

    public int getLaenge() {
        return LAENGE;
    }
    public int getBreite() {
        return BREITE;
    }

    Spielfeld() {
//        Roboter roboter = new Roboter();
//        Leinwand leinwand;
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
                System.out.println("...in bearbeitung");
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
            userPunktEingabe();
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

    private static int eingabeIstZahl(String bezeichnung) { // parameter ist eigentlich nicht so wichtig, sieht am ende aber besser aus
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
     * Hier werden die unsortierten Punkte ihrem Abstand nach sortiert und einem neuen Array (this.poiSortiert)
     * hinzugefügt um die sortierten Punkte zu speichern
     * <p>
     * keine Ahnung was eine HashMap, TreeMap ist und wieso man genau die verwenden muss, aber im Grunde
     * werden in der HashMap poiUnsortiert die unsortierten Punkte mit ihren Abstandswerten zum Roboterpunkt
     * gespeichert.
     * <p>
     * Allgemein hat eine Map keys und values. Jedem key ist ein value zugeordnet und man kann über die keys
     * die values bekommen und andersrum. Hier werden die Punkte aus einem Array Punkte[] als Keys verwendet
     * und denen werden ihre Abstandswerte (zu dem aktuellen Roboterpunkt) zugeordnet.
     * <p>
     * Dann wird ein neuer Punkt erstellt, der die Werte von der HashMap hat (siehe Klasse Punkt) und dann wird
     * der als Grundlage genommen um die die HashMap nach ihren values (das sind die Abstandswerte) zu
     * sortieren.. irgendwie so.
     * Diese sortierten values mit ihren keys werden dann alle in poiSortiert gespeichert
     * <p>
     * Dann werden die values aus der TreeMap poiSortiert in dem Array poiSortiert gespeichert, weil ich nicht
     * wusste wie man mit der TreeMap weiter gehen sollte.
     */
    private static void poiSortieren(Punkt[] poi) {
        HashMap<Punkt, Double> poiUnsortiert = new HashMap<>();
        Punkt basisZumVergleichen = new Punkt(poiUnsortiert);
        TreeMap<Punkt, Double> poiSortiertTM = new TreeMap<>(basisZumVergleichen);
        poiSortiert = new Punkt[userPoi.length];

        for (Punkt punkt : poi) {
            poiUnsortiert.put(punkt, ROBOTERPUNKT.getAbstand(punkt));
        }
        poiSortiertTM.putAll(poiUnsortiert);

        int i = 0; // speichert die sortierten Punkte im Array this.poiSortiert
        for (Punkt punkt : poiSortiertTM.keySet()) {
            poiSortiert[i] = punkt;
            i++;
        }
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
     * Dann werden die Punkte aus dem Array poiSortiert erneut mit den neuen Koordinaten des Roboterpunktes, nach ihrem
     * sortiert kürzesten Abstand sortiert.
     */
    private static void poiAbfahren() {
        poiSortieren(punktEingabe());
        Punkt[] poiAbgefahren = new Punkt[userPoi.length];
        Punkt naechsterPunkt = new Punkt();
        for (int i = 0; i < poiSortiert.length; i++) {
            boolean neuerPunkt = false;
            for (Punkt punkt : poiSortiert) {
                if (!poiSortiert[0].equals(new Punkt(0, 0)) && !neuerPunkt && !Arrays.asList(poiAbgefahren).contains(punkt)) {
                    naechsterPunkt.setXY(punkt.getX(), punkt.getY());
                    poiAbgefahren[i] = punkt;
                    neuerPunkt = true;
                } else if (!neuerPunkt && !Arrays.asList(poiAbgefahren).contains(punkt)) {
                    naechsterPunkt.setXY(poiSortiert[1].getX(), poiSortiert[1].getX());
                    poiAbgefahren[i] = poiSortiert[1];
                }
            }
            // ab hier wird viel an die Konsole geschrieben und der Roboter zum nächsten Punkt bewegt
            System.out.println("Ausgangspunkt: (" + ROBOTERPUNKT.getX() + ", " + ROBOTERPUNKT.getY() + ")");
            for (Punkt punkte : poiSortiert) {
                System.out.println("Abstand zu Punkt" + (getIndex(userPoi, punkte) + 1) + " (" + punkte.getX() + ", " +
                        punkte.getY() + ") = " + ROBOTERPUNKT.getAbstand(punkte));
            }

            int dx = naechsterPunkt.getX() - ROBOTERPUNKT.getX();
            int dy = naechsterPunkt.getY() - ROBOTERPUNKT.getY();

            System.out.println("---\nnächster Punkt = Punkt (" + naechsterPunkt.getX() + ", " + naechsterPunkt.getY() +
                    ")\nVerschiebungsvektor: (" + dx + ", " + dy + ") = " +
                    ROBOTERPUNKT.getAbstand(naechsterPunkt));

            ROBOTERPUNKT.bewegeUm(dx, dy);
            poiSortieren(poiSortiert);

            System.out.println("...Neuer Ausganspunkt: (" + ROBOTERPUNKT.getX() + ", " + ROBOTERPUNKT.getY() + ")\n----------");
        }
    }

    private static <T> int getIndex(T[] arr, T val) { // vorsicht ist geklaut! ist für den code aber auch nicht wichtig.
        return Arrays.asList(arr).indexOf(val);
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